package xyz.pixelatedw.mineminenomi.abilities;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ShakushiAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 400;
	private static final int CHARGE_TIME = 40;
	private static final int RANGE = 10;
	private static final int DAMAGE = 30;
	private static final int STACKS = 5;
	private static final AnimationId<?>[] HIT_ANIMATIONS;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ShakushiAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final AnimationComponent animationComponent;
	private final StackComponent stackComponent;

	public ShakushiAbility(AbilityCore<ShakushiAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::tickChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.stackComponent = new StackComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent,this.chargeComponent,this.dealDamageComponent,this.animationComponent,this.stackComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
		this.addTickEvent((Ability$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent)(this::tickEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		if (this.chargeComponent.isCharging()){
			this.chargeComponent.stopCharging(var1);
			this.cooldownComponent.startCooldown(var1,400F);
			return;
		}else{
			this.stackComponent.setDefaultStacks(5);
			this.stackComponent.setStacks(var1,var2,5);
			this.animationComponent.start(var1,ModAnimations.LOW_SWING_ARMS,39);
			this.chargeComponent.startCharging(var1,40F);
			return;
		}
	}
	private void tickEvent(LivingEntity var1, IAbility var2) {
		if ((!(this.isCharging())) && (((this.stackComponent.getStacks()) > (0)) && ((this.stackComponent.getStacks()) < (this.stackComponent.getDefaultStacks())))){
			this.chargeComponent.startCharging(var1,20F);
		}
	}
	private void tickChargeEvent(LivingEntity var1, IAbility var2) {
		if (((this.stackComponent.getStacks()) > (0)) && (((this.stackComponent.getStacks()) < (this.stackComponent.getDefaultStacks())) && (((this.chargeComponent.getChargePercentage()) >= (0.5F)) && (!(var1.hasEffect((Holder)(ModEffects.VANISH))))))){
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.VANISH),40,0,false,false));
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),40,0,false,false));
			if ((this.getNearbyTarget(var1)) != (null)){
				BlockPos var3 = WyHelper.findValidGroundLocation((Entity)(var1),var1.blockPosition(),5,0);
				if ((var3) != (null)){
					var1.teleportTo((double)(var3.getX()),(double)(var3.getY()),(double)(var3.getZ()));
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		if (!(var1.level().isClientSide)){
			this.stackComponent.addStacks(var1,var2,-1);
			var1.removeEffect((Holder)(ModEffects.VANISH));
			LivingEntity var3 = this.getNearbyTarget(var1);
			if (((var3) != (null)) && (var3.isAlive())){
				Vec3 var4 = VectorHelper.calculateViewVectorFromBodyRot(var3.getXRot(),var3.yBodyRot).multiply(-2D,0D,-2D);
				Vec3 var5 = var3.position().add(var4);
				var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,0,false,false));
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.GEPPO.get())),(Entity)(var1),var1.getX(),(var1.getY()) + (0.5D),var1.getZ());
				var1.teleportTo(var5.x,var5.y,var5.z);
				var1.lookAt(EntityAnchorArgument.Anchor.EYES,var3.position().add(0D,(double)(var3.getEyeHeight()),0D));
				Iterator var6 = this.rangeComponent.getTargetsInArea(var1,var3.blockPosition(),2F).iterator();
				do{
					if (var6.hasNext()){
						LivingEntity var7 = ((LivingEntity)(var6.next()));
						this.dealDamageComponent.hurtTarget(var1,var7,30F);
						WyHelper.spawnParticles((ParticleOptions)(ParticleTypes.SWEEP_ATTACK),((ServerLevel)(var1.level())),var7.getX(),(var7.getY()) + ((double)(var7.getEyeHeight())),var7.getZ());
						continue;
					}else{
						break;
					}
				} while (true);
				AnimationId var7_1 = HIT_ANIMATIONS[var1.getRandom().nextInt(HIT_ANIMATIONS.length)];
				this.animationComponent.start(var1,var7_1,7);
				var1.swing(InteractionHand.MAIN_HAND,true);
			}
			if (((this.stackComponent.getStacks()) > (0)) && (((var3) != (null)) && (var3.isAlive()))){
				return;
			}else{
				this.stackComponent.revertStacksToDefault(var1,this);
				this.cooldownComponent.startCooldown(var1,400F);
				var1.removeEffect((Holder)(ModEffects.MOVEMENT_BLOCKED));
			}
		}
	}
	@Nullable()
	private LivingEntity getNearbyTarget(LivingEntity var1) {
		List var2 = this.rangeComponent.getTargetsInArea(var1,10F,TargetPredicate.EVERYTHING);
		if ((var2.size()) > (0)){
			Collections.shuffle(var2);
			return ((LivingEntity)(var2.get(0)));
		}else{
			return null;
		}
	}
	static  {
		AnimationId[] var0 = new AnimationId[]{ModAnimations.CROSSED_ATTACK,ModAnimations.BODY_ROTATION_WIDE_ARMS,ModAnimations.SCRATCHING};
		HIT_ANIMATIONS = var0;
		AbilityRegistryEntry var1 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			AbilityDescriptionLine$IDescriptionLine[] lv1_2 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(400F),ChargeComponent.getTooltip(40F),RangeComponent.getTooltip(10F,RangeComponent$RangeType.AOE),DealDamageComponent.getTooltip(30F),StackComponent.getTooltip(5)};
			SourceType[] lv1_3 = new SourceType[]{SourceType.SLASH};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ShakushiAbility::new)))).addAdvancedDescriptionLine(lv1_2).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv1_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("shakushi","Shakushi",var1));
	}
}