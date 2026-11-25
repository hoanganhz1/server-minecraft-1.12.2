package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$GrabState;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullEndEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullStartEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DesertEncierroAbility extends Ability {
	// Fields
	private static final float COOLDOWN_BONUS = 0.8F;
	private static final int COOLDOWN = 300;
	private static final int CHARGE_TIME = 100;
	private static final int PULL_TIME = 200;
	private static final float DAMAGE = 20F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DesertEncierroAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final DealDamageComponent dealDamageComponent;
	private final ChargeComponent chargeComponent;
	private final AnimationComponent animationComponent;
	private final GrabEntityComponent grabComponent;
	private final HitTriggerComponent hitTriggerComponent;
	private final PoolComponent poolComponent;
	private final Interval particleInterval;

	public DesertEncierroAbility(AbilityCore<DesertEncierroAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent(100,(ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::onChargeStart)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::onChargeTick)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::onChargeEnd))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.grabComponent = new GrabEntityComponent((IAbility)(this),true,true,true,2F).addPullStartEvent((GrabEntityComponent$IOnPullStartEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullStartEvent)(this::onPullStart)))).addPullEndEvent((GrabEntityComponent$IOnPullEndEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullEndEvent)(this::onPullEnd))));
		this.hitTriggerComponent = new HitTriggerComponent((IAbility)(this)).addTryHitEvent((HitTriggerComponent$ITryHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent)(this::tryHitEvent)))).addOnHitEvent((HitTriggerComponent$IOnHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent)(this::onHitEvent))));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.GRAB_ABILITY,new AbilityPool[0]);
		this.particleInterval = new Interval(6);
		AbilityComponent[] var2 = new AbilityComponent[]{this.dealDamageComponent,this.chargeComponent,this.animationComponent,this.continuousComponent,this.grabComponent,this.hitTriggerComponent,this.poolComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresEmptyHand))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if (this.chargeComponent.isCharging()){
			return;
		}else{
			if (this.continuousComponent.isContinuous()){
				this.grabComponent.release(var1);
				this.continuousComponent.stopContinuity(var1);
			}else{
				if (((this.grabComponent.getState()) == (GrabEntityComponent$GrabState.IDLE)) && (this.grabComponent.grabNearest(var1,false))){
					this.grabComponent.triggerPulling(var1);
				}else{
					this.continuousComponent.startContinuity(var1);
				}
			}
			return;
		}
	}
	private HitTriggerComponent$HitResult tryHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if ((this.continuousComponent.isContinuous()) && (!(this.grabComponent.hasGrabbedEntity()))){
			return HitTriggerComponent$HitResult.HIT;
		}else{
			return HitTriggerComponent$HitResult.PASS;
		}
	}
	private boolean onHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if (this.grabComponent.grabManually(var1,var2)){
			this.grabComponent.startPulling(var1);
		}
		var2.addEffect(new MobEffectInstance((Holder)(ModEffects.ANTI_KNOCKBACK),1));
		return false;
	}
	public void onPullStart(LivingEntity var1, IAbility var2) {
		this.continuousComponent.setThresholdTime(var1,200F);
	}
	public void onPullEnd(LivingEntity var1, IAbility var2) {
		this.continuousComponent.stopContinuity(var1);
		if (this.grabComponent.canContinueGrab(var1)){
			this.chargeComponent.startCharging(var1,100F);
		}
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((this.grabComponent.getState()) != (GrabEntityComponent$GrabState.GRABBED)){
				this.grabComponent.release(var1);
			}
			if (!(this.grabComponent.canContinueGrab(var1))){
				this.cooldownComponent.startCooldown(var1,300F);
			}
			return;
		}
	}
	public void onChargeStart(LivingEntity var1, IAbility var2) {
		SunaHelper.drainLiquids(this.grabComponent.getGrabbedEntity(),WyHelper.randomWithRange(0,1),WyHelper.randomWithRange(1,3),WyHelper.randomWithRange(0,1));
		this.animationComponent.start(var1,ModAnimations.POINT_RIGHT_ARM);
		this.particleInterval.restartIntervalToZero();
	}
	public void onChargeTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((!(super.canUse(var1).isFail())) && (this.grabComponent.canContinueGrab(var1))){
				LivingEntity var3 = this.grabComponent.getGrabbedEntity();
				if (this.particleInterval.canTick()){
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.DESERT_ENCIERRO.get())),(Entity)(var1),var3.getX(),var3.getY(),var3.getZ());
				}
				AbilityDamageSource var4 = this.dealDamageComponent.getDamageSource(var1);
				IDamageSourceHandler var5 = IDamageSourceHandler.getHandler(var4);
				var5.bypassLogia();
				var5.addAbilityPiercing(1F,this.getCore());
				this.dealDamageComponent.hurtTarget(var1,var3,2F,var4);
				return;
			}else{
				this.chargeComponent.stopCharging(var1);
				return;
			}
		}
	}
	public void onChargeEnd(LivingEntity var1, IAbility var2) {
		if (this.grabComponent.canContinueGrab(var1)){
			LivingEntity var3 = this.grabComponent.getGrabbedEntity();
			AbilityDamageSource var4 = this.dealDamageComponent.getDamageSource(var1);
			IDamageSourceHandler var5 = IDamageSourceHandler.getHandler(var4);
			var5.bypassLogia();
			var5.addAbilityPiercing(1F,this.getCore());
			if (this.dealDamageComponent.hurtTarget(var1,var3,20F,var4)){
				var3.addEffect(new MobEffectInstance((Holder)(ModEffects.DEHYDRATION),300,2,false,true));
				var3.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,300,1,false,false));
				var3.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,300,1,false,false));
				var3.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,300,1,false,false));
			}
			this.grabComponent.release(var1);
		}
		this.animationComponent.stop(var1);
		this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
		if (SunaHelper.isFruitBoosted(var1)){
			this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS,"Desert Cooldown Bonus",BonusOperation.MUL,0.8F);
		}
		this.cooldownComponent.startCooldown(var1,300F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[2];
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("Quickly drains the enemy in front of the user of their moisture, leaving them weak for a few seconds."),(Object)(null));
			Object[] lv1_3 = new Object[1];
			int lv1_4 = Math.round(19.999998F);
			String lv1_5 = "§a" + lv1_4 + "%§r";
			lv1_3[0] = lv1_5;
			((java.lang.Object[]) (lv1_2))[1] = ImmutablePair.of((Object)("While in a desert the cooldown of this ability is reduced by %s."),lv1_3);
			Component[] lv1_6 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_7 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(300F),ChargeComponent.getTooltip(100F),DealDamageComponent.getTooltip(20F)};
			SourceType[] lv1_8 = new SourceType[]{SourceType.INTERNAL};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DesertEncierroAbility::new)))).addDescriptionLine(lv1_6).addAdvancedDescriptionLine(lv1_7).setSourceType(lv1_8).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("desert_encierro","Desert Encierro",var0));
	}
}