package xyz.pixelatedw.mineminenomi.abilities.brawler;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.RangeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DamageAbsorptionAbility extends Ability {
	// Fields
	public static final ResourceLocation DAMAGE_BONUS_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","damage_absorption.damage");
	private static final float HOLD_TIME = 100F;
	private static final float COOLDOWN = 300F;
	private static final float RANGE = 10F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DamageAbsorptionAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final AnimationComponent animationComponent;
	private final RangeComponent rangeComponent;
	private float prevHealth;
	private int hits;
	private float receivedDamage;

	public DamageAbsorptionAbility(AbilityCore<DamageAbsorptionAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.animationComponent,this.rangeComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,100F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		AttributeInstance var3 = var1.getAttribute(Attributes.ATTACK_DAMAGE);
		if ((var3) != (null)){
			var3.removeModifier(DAMAGE_BONUS_ID);
		}
		this.hits = 0;
		this.prevHealth = var1.getHealth();
		this.receivedDamage = 0F;
		this.animationComponent.start(var1,ModAnimations.CROSSED_ARMS);
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),20,0));
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (((var1.tickCount) % (10)) == (0)){
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),20,0));
		}
		if ((var1.getHealth()) < (this.prevHealth)){
			this.hits = (this.hits) + (1);
			this.receivedDamage = (this.receivedDamage) + ((this.prevHealth) - (var1.getHealth()));
			this.prevHealth = var1.getHealth();
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		Iterator var3 = this.rangeComponent.getTargetsInArea(var1,10F).iterator();
		do{
			if (var3.hasNext()){
				LivingEntity var4 = ((LivingEntity)(var3.next()));
				Vec3 var5 = var4.position().subtract(var1.position()).normalize().multiply(3.5D,1D,3.5D);
				AbilityHelper.setDeltaMovement((Entity)(var4),var5.x,0D,var5.z);
				continue;
			}else{
				break;
			}
		} while (true);
		if (!(var1.level().isClientSide)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.BERSERKER_POWERUP.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		}
		AttributeInstance var4_1 = var1.getAttribute(Attributes.ATTACK_DAMAGE);
		if ((var4_1) != (null)){
			var4_1.removeModifier(DAMAGE_BONUS_ID);
			var4_1.addTransientModifier(this.getModifier());
		}
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,300F);
	}
	private AttributeModifier getModifier() {
		this.receivedDamage = (Math.min(this.receivedDamage,30F)) / (5F);
		return new AttributeModifier(DAMAGE_BONUS_ID,(double)(this.receivedDamage),AttributeModifier.Operation.ADD_VALUE);
	}
	public int getHits() {
		return this.hits;
	}
	public float getReceivedDamage() {
		return this.receivedDamage;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to absorb all the incoming damage granting a damage buff at the end based on the total damage received."),(Object)(null))};
			Component[] lv4_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DamageAbsorptionAbility::new)))).addDescriptionLine(lv4_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("damage_absorption","Damage Absorption",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(300F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ContinuousComponentTooltip> var4 = (l0) -> {
			l0.holdTime(100F);
		};
		ContinuousComponentTooltip.generate(var2,var4);
		Consumer<RangeComponentTooltip> var5 = (l0) -> {
			l0.range(10F,RangeComponent$RangeType.AOE);
		};
		RangeComponentTooltip.generate(var2,var5);
	}
}