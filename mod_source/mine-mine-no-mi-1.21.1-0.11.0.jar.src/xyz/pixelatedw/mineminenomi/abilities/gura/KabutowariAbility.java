package xyz.pixelatedw.mineminenomi.abilities.gura;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
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
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class KabutowariAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 200;
	private static final int CHARGE_TIME = 40;
	private static final int DAMAGE = 60;
	private static final int PULL_TIME = 200;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KabutowariAbility>> INSTANCE;
	private final DealDamageComponent dealDamageComponent;
	private final ChargeComponent chargeComponent;
	private final AnimationComponent animationComponent;
	private final ContinuousComponent continuousComponent;
	private final GrabEntityComponent grabComponent;
	private final HitTriggerComponent hitTriggerComponent;
	private final PoolComponent poolComponent;
	private final ExplosionComponent explosionComponent;

	public KabutowariAbility(AbilityCore<KabutowariAbility> var1) {
		super((AbilityCore)(var1));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::onChargeStart)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::onChargeTick)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::onChargeEnd))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.grabComponent = new GrabEntityComponent((IAbility)(this),true,false,true,1F).addPullStartEvent((GrabEntityComponent$IOnPullStartEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullStartEvent)(this::onPullStart)))).addPullEndEvent((GrabEntityComponent$IOnPullEndEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent$IOnPullEndEvent)(this::onPullEnd))));
		this.hitTriggerComponent = new HitTriggerComponent((IAbility)(this)).addTryHitEvent((HitTriggerComponent$ITryHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent)(this::tryHitEvent)))).addOnHitEvent((HitTriggerComponent$IOnHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent)(this::onHitEvent))));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.GRAB_ABILITY,new AbilityPool[0]);
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.animationComponent,this.dealDamageComponent,this.continuousComponent,this.grabComponent,this.hitTriggerComponent,this.poolComponent,this.explosionComponent};
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
			this.chargeComponent.startCharging(var1,40F);
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
				this.cooldownComponent.startCooldown(var1,200F);
			}
			return;
		}
	}
	private void onChargeStart(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			this.animationComponent.start(var1,ModAnimations.POINT_RIGHT_ARM);
			LivingEntity var3 = this.grabComponent.getGrabbedEntity();
			int var4 = (int)(this.chargeComponent.getMaxChargeTime());
			if ((var3) != (null)){
				var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.GURA_SFX.get())),SoundSource.PLAYERS,5F,1F);
				var3.addEffect(new MobEffectInstance((Holder)(ModEffects.SEISMIC_BUBBLE),var4,0));
				AbilityHelper.setDeltaMovement((Entity)(var3),0D,0D,0D);
			}
			return;
		}
	}
	private void onChargeTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((!(super.canUse(var1).isFail())) && (this.grabComponent.canContinueGrab(var1))){
				LivingEntity var3 = this.grabComponent.getGrabbedEntity();
				var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),2,1));
				var3.addEffect(new MobEffectInstance((Holder)(ModEffects.GRABBED),2,3));
				AbilityHelper.setDeltaMovement((Entity)(var3),VectorHelper.getRelativeOffset(var1.position(),var1.yBodyRot,new Vec3(0.5D,(double)(-(var3.getEyeHeight())),((double)(var3.getBbWidth())) - (0.2D))).subtract(var3.position()),true);
				return;
			}else{
				this.chargeComponent.stopCharging(var1);
				return;
			}
		}
	}
	private void onChargeEnd(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (this.grabComponent.canContinueGrab(var1)){
				LivingEntity var3 = this.grabComponent.getGrabbedEntity();
				AbilityDamageSource var4 = this.dealDamageComponent.getDamageSource(var1);
				IDamageSourceHandler.getHandler(var4).addAbilityPiercing(0.75F,this.getCore());
				this.dealDamageComponent.hurtTarget(var1,var3,60F,var4);
				AbilityExplosion var5 = this.explosionComponent.createExplosion((Entity)(var1),var3.getX(),var3.getY(),var3.getZ(),2F);
				var5.setStaticDamage(20F);
				var5.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION4));
				var5.explode();
				var3.getItemBySlot(EquipmentSlot.HEAD).hurtAndBreak(15,var3,EquipmentSlot.HEAD);
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.AIR_CRACKS.get())),(Entity)(var1),var3.getX(),var3.getEyeY(),var3.getZ());
				var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.GURA_SFX.get())),SoundSource.PLAYERS,5F,1F);
				var3.addEffect(new MobEffectInstance((Holder)(ModEffects.DIZZY),40,0));
				this.grabComponent.release(var1);
			}
			this.animationComponent.stop(var1);
			this.cooldownComponent.startCooldown(var1,200F);
			return;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user grabs their opponent's head and concentrates their vibrations around it, resulting in crushing damage"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),ChargeComponent.getTooltip(40F),DealDamageComponent.getTooltip(60F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KabutowariAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.SHOCKWAVE).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kabutowari","Kabutowari",var0));
	}
}