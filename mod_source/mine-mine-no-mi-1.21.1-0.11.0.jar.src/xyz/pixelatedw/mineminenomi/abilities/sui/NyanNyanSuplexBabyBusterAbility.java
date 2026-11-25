package xyz.pixelatedw.mineminenomi.abilities.sui;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class NyanNyanSuplexBabyBusterAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 300;
	private static final int CHARGE_TIME = 200;
	private static final float RANGE = 1.3F;
	private static final float DAMAGE = 40F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<NyanNyanSuplexBabyBusterAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final ChargeComponent chargeComponent;
	private final GrabEntityComponent grabEntityComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final AnimationComponent animationComponent;
	private final PoolComponent poolComponent;
	private final ExplosionComponent explosionComponent;

	public NyanNyanSuplexBabyBusterAbility(AbilityCore<NyanNyanSuplexBabyBusterAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::tickChargingEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargingEvent))));
		this.grabEntityComponent = new GrabEntityComponent((IAbility)(this),true,true,1F);
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.GRAB_ABILITY,new AbilityPool[0]);
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.grabEntityComponent,this.continuousComponent,this.rangeComponent,this.dealDamageComponent,this.animationComponent,this.poolComponent,this.explosionComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(SuiHelper::requiresFreeSwimming))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(SuiHelper::requiresFreeSwimming))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void tickChargingEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (!(this.grabEntityComponent.canContinueGrab(var1))){
				this.grabEntityComponent.release(var1);
				this.chargeComponent.stopCharging(var1);
				return;
			}else{
				LivingEntity var3 = this.grabEntityComponent.getGrabbedEntity();
				if ((((double)(this.chargeComponent.getChargePercentage())) >= (0.5D)) && (!((!(var1.onGround())) && (!(var3.onGround()))))){
					this.chargeComponent.stopCharging(var1);
					return;
				}else{
					var3.fallDistance = 0F;
					var1.fallDistance = 0F;
					if (((double)(this.chargeComponent.getChargePercentage())) < (0.5D)){
						AbilityHelper.setDeltaMovement((Entity)(var1),var1.getDeltaMovement().x,1.2D,var1.getDeltaMovement().z,true);
					}else{
						AbilityHelper.setDeltaMovement((Entity)(var1),var1.getDeltaMovement().x,-1.2D,var1.getDeltaMovement().z,true);
					}
					var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,1));
					var3.addEffect(new MobEffectInstance((Holder)(ModEffects.GRABBED),5,3));
					return;
				}
			}
		}
	}
	private void endChargingEvent(LivingEntity var1, IAbility var2) {
		if (this.grabEntityComponent.canContinueGrab(var1)){
			LivingEntity var3 = this.grabEntityComponent.getGrabbedEntity();
			this.dealDamageComponent.hurtTarget(var1,var3,40F);
			AbilityExplosion var4 = this.explosionComponent.createExplosion((Entity)(var1),var3.getX(),var3.getY(),var3.getZ(),4F);
			var4.setStaticDamage(70F);
			var4.setExplosionSound(true);
			var4.setDamageOwner(false);
			var4.setDestroyBlocks(true);
			var4.setFireAfterExplosion(false);
			var4.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION4));
			var4.setDamageEntities(true);
			var4.explode();
			this.grabEntityComponent.release(var1);
		}
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,300F);
	}
	private void tickContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (!(this.grabEntityComponent.hasGrabbedEntity())){
				Iterator var3 = this.rangeComponent.getTargetsInArea(var1,1.3F).iterator();
				do{
					if (var3.hasNext()){
						LivingEntity var4 = ((LivingEntity)(var3.next()));
						if (this.grabEntityComponent.grabManually(var1,var4)){
							Vec3 var5 = var4.getLookAngle().reverse().multiply(1.1D,1D,1.1D);
							Vec3 var6 = var4.position().add(var5);
							var1.teleportTo(var6.x,var6.y,var6.z);
							this.animationComponent.start(var1,ModAnimations.POINT_ARMS);
							this.chargeComponent.startCharging(var1,200F);
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}
			return;
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.grabEntityComponent.release(var1);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("While swimming, grabs the nearest enemy from the back and launches them into the sky on when hitting the ground it creates a big crater."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(300F),ChargeComponent.getTooltip(200F),RangeComponent.getTooltip(1.3F,RangeComponent$RangeType.AOE),DealDamageComponent.getTooltip(40F)};
			SourceType[] lv1_5 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(NyanNyanSuplexBabyBusterAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("nyan_nyan_suplex_baby_buster","Nyan Nyan Suplex: Baby Buster",var0));
	}
}