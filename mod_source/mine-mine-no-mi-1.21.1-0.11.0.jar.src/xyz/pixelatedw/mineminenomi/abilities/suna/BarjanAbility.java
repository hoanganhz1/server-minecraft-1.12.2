package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DashComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BarjanAbility extends Ability {
	// Fields
	private static final float COOLDOWN_BONUS = 0.8F;
	private static final float DAMAGE_BONUS = 1.25F;
	private static final int COOLDOWN = 200;
	private static final int HOLD_TIME = 40;
	private static final float DAMAGE = 30F;
	private static final float RANGE = 1.6F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BarjanAbility>> INSTANCE;
	private final ContinuousComponent continuityComponent;
	private final HitTrackerComponent hitTrackerComponent;
	private final AnimationComponent animationComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final DashComponent dashComponent;

	public BarjanAbility(AbilityCore<BarjanAbility> var1) {
		super((AbilityCore)(var1));
		this.continuityComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.dashComponent = new DashComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.hitTrackerComponent,this.animationComponent,this.continuityComponent,this.rangeComponent,this.dealDamageComponent,this.dashComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresDryUser))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuityComponent.startContinuity(var1,40F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.BARJAN,40);
		this.dashComponent.dashXYZ(var1,4F);
		this.hitTrackerComponent.clearHits();
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.BARJAN.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		List var3 = this.rangeComponent.getTargetsInArea(var1,1.6F);
		this.dealDamageComponent.getBonusManager().removeBonus(SunaHelper.DESERT_DAMAGE_BONUS);
		if (SunaHelper.isFruitBoosted(var1)){
			this.dealDamageComponent.getBonusManager().addBonus(SunaHelper.DESERT_DAMAGE_BONUS,"Desert Damage Bonus",BonusOperation.MUL,1.25F);
		}
		float var4 = (30F) * ((SunaHelper.isFruitBoosted(var1)) ? (1.25F) : (1F));
		Iterator var5 = var3.iterator();
		do{
			if (var5.hasNext()){
				LivingEntity var6 = ((LivingEntity)(var5.next()));
				if ((this.hitTrackerComponent.canHit((Entity)(var6))) && (this.dealDamageComponent.hurtTarget(var1,var6,var4))){
					var6.addEffect(new MobEffectInstance((Holder)(ModEffects.DEHYDRATION),200,2,false,true));
					var6.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,150,0,false,false));
					var6.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,150,0,false,false));
					var6.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,150,0,false,false));
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
		if (SunaHelper.isFruitBoosted(var1)){
			this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS,"Desert Cooldown Bonus",BonusOperation.MUL,0.8F);
		}
		this.cooldownComponent.startCooldown(var1,200F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[2];
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("Dashes forwards hitting all enemies it touches dehydrating them."),(Object)(null));
			Object[] lv1_3 = new Object[2];
			int lv1_4 = Math.round(19.999998F);
			String lv1_5 = "§a" + lv1_4 + "%§r";
			lv1_3[0] = lv1_5;
			int lv1_6 = Math.round((Math.abs(-0.25F)) * (100F));
			String lv1_7 = "§a" + lv1_6 + "%§r";
			lv1_3[1] = lv1_7;
			((java.lang.Object[]) (lv1_2))[1] = ImmutablePair.of((Object)("While in a desert the cooldown of this ability is reduced by %s and the damage is increased by %s."),lv1_3);
			Component[] lv1_8 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_9 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),ContinuousComponent.getTooltip(40F),DealDamageComponent.getTooltip(30F),RangeComponent.getTooltip(1.6F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BarjanAbility::new)))).addDescriptionLine(lv1_8).addAdvancedDescriptionLine(lv1_9).setSourceHakiNature(SourceHakiNature.IMBUING).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("barjan","Barjan",var0));
	}
}