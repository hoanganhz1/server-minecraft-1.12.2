package xyz.pixelatedw.mineminenomi.abilities;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class StealthFootAbility extends Ability {
	// Fields
	private static final int CHARGE_TIME = 20;
	private static final int COOLDOWN = 100;
	public static final int RANGE = 10;
	private static final int DAMAGE = 20;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<StealthFootAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final AnimationComponent animationComponent;
	private LivingEntity target;

	public StealthFootAbility(AbilityCore<StealthFootAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent,this.chargeComponent,this.dealDamageComponent,this.animationComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		if (this.chargeComponent.isCharging()){
			return;
		}else{
			List var3 = this.rangeComponent.getTargetsInLine(var1,10F,2F);
			if ((var3.size()) > (0)){
				this.target = ((LivingEntity)(var3.get(0)));
			}
			if ((this.target) != (null)){
				Vec3 var4 = VectorHelper.calculateViewVectorFromBodyRot(this.target.getXRot(),this.target.yBodyRot).multiply(-2D,0D,-2D);
				Vec3 var5 = this.target.position().add(var4);
				var1.addEffect(new MobEffectInstance((Holder)(ModEffects.VANISH),10,0,false,false));
				var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),20,0,false,false));
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.GEPPO.get())),(Entity)(var1),var1.getX(),(var1.getY()) + (0.5D),var1.getZ());
				var1.teleportTo(var5.x,var5.y,var5.z);
				var1.lookAt(EntityAnchorArgument.Anchor.EYES,this.target.position().add(0D,(double)(this.target.getEyeHeight()),0D));
				this.chargeComponent.startCharging(var1,20F);
			}else{
				this.cooldownComponent.startCooldown(var1,100F);
			}
			return;
		}
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		if ((!(var1.level().isClientSide)) && (((this.target) != (null)) && ((this.target.isAlive()) && ((Math.abs(this.target.distanceTo((Entity)(var1)))) < (5F))))){
			this.animationComponent.start(var1,ModAnimations.CROSSED_ATTACK,7);
			var1.swing(InteractionHand.MAIN_HAND,true);
			Iterator var3 = this.rangeComponent.getTargetsInArea(var1,this.target.blockPosition(),2F).iterator();
			do{
				if (var3.hasNext()){
					LivingEntity var4 = ((LivingEntity)(var3.next()));
					this.dealDamageComponent.hurtTarget(var1,var4,20F);
					WyHelper.spawnParticles((ParticleOptions)(ParticleTypes.SWEEP_ATTACK),((ServerLevel)(var1.level())),var4.getX(),(var4.getY()) + ((double)(var4.getEyeHeight())),var4.getZ());
					continue;
				}else{
					break;
				}
			} while (true);
		}
		this.target = null;
		this.cooldownComponent.startCooldown(var1,100F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			AbilityDescriptionLine$IDescriptionLine[] lv1_2 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(100F),ChargeComponent.getTooltip(20F),RangeComponent.getTooltip(10F,RangeComponent$RangeType.AOE),DealDamageComponent.getTooltip(20F)};
			SourceType[] lv1_3 = new SourceType[]{SourceType.SLASH};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(StealthFootAbility::new)))).addAdvancedDescriptionLine(lv1_2).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv1_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("stealth_foot","Stealth Foot",var0));
	}
}