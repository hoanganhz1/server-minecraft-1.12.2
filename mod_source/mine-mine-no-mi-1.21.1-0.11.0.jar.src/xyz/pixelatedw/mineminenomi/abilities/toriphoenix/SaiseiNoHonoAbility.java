package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HealComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SaiseiNoHonoAbility extends Ability {
	// Fields
	private static final TargetPredicate TARGETS_CHECK = new TargetPredicate().testFriendlyFaction();
	private static final float COOLDOWN = 300F;
	private static final int RANGE = 10;
	private static final int HEAL = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SaiseiNoHonoAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final HitTriggerComponent hitTriggerComponent;
	private final RangeComponent rangeComponent;
	private final HealComponent healComponent;
	private int healed;

	public SaiseiNoHonoAbility(AbilityCore<SaiseiNoHonoAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.hitTriggerComponent = new HitTriggerComponent((IAbility)(this)).addOnHitEvent((HitTriggerComponent$IOnHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent)(this::hitEvent)))).addTryHitEvent((HitTriggerComponent$ITryHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent)(this::tryHitEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.healComponent = new HealComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.hitTriggerComponent,this.rangeComponent,this.healComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.healed = 0;
		if (var1.isCrouching()){
			List var3 = this.rangeComponent.getTargetsInArea(var1,10F,TARGETS_CHECK);
			var3.add(var1);
			int var4 = 0;
			Iterator var5 = var3.iterator();
			do{
				if (var5.hasNext()){
					LivingEntity var6 = ((LivingEntity)(var5.next()));
					var4++;
					this.healComponent.healTarget(var1,var6,10F);
					var6.addEffect(new MobEffectInstance(MobEffects.REGENERATION,400,1));
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.BLUE_FLAMES.get())),(Entity)(var1),var6.getX(),var6.getY(),var6.getZ());
					continue;
				}else{
					break;
				}
			} while (true);
			this.healed = Math.max(1,var4);
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.TENSEI_NO_SOEN_2.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
			this.continuousComponent.stopContinuity(var1);
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,(300F) + ((float)((this.healed) * (120))));
	}
	private HitTriggerComponent$HitResult tryHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if (!(this.continuousComponent.isContinuous())){
			return HitTriggerComponent$HitResult.PASS;
		}else{
			return HitTriggerComponent$HitResult.HIT;
		}
	}
	private boolean hitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		this.healComponent.healTarget(var1,var2,10F);
		var2.addEffect(new MobEffectInstance(MobEffects.REGENERATION,400,1));
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.BLUE_FLAMES.get())),(Entity)(var1),var2.getX(),var2.getY(),var2.getZ());
		this.continuousComponent.stopContinuity(var1);
		return false;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Uses the blue flames to heal the target by hitting them. Using it while crouching will heal friendly entities around the user including the user themselves at the expense of a longer cooldown based on the number of healed entities."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE};
			AbilityDescriptionLine$IDescriptionLine[] lv1_5 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(300F),ContinuousComponent.getTooltip(),HealComponent.getTooltip(10F),RangeComponent.getTooltip(10F,RangeComponent$RangeType.AOE)};
			SourceType[] lv1_6 = new SourceType[]{SourceType.FRIENDLY};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SaiseiNoHonoAbility::new)))).addDescriptionLine(lv1_3).addDescriptionLine(lv1_4).addAdvancedDescriptionLine(lv1_5).setSourceType(lv1_6).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("saisei_no_hono","Saisei No Hono",var0));
	}
}