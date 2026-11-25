package xyz.pixelatedw.mineminenomi.abilities;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.clouds.MH5CloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.items.armors.WootzArmorItem;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MH5Ability extends Ability {
	// Fields
	private static final int COOLDOWN = 1200;
	private static final int CHARGE_TIME = 200;
	public static final int RANGE = 100;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<MH5Ability>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final AnimationComponent animationComponent;
	private BlockPos targetPosition;
	private float initialHealth;
	private double hpThreshold;

	public MH5Ability(AbilityCore<MH5Ability> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::startChargeEvent)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::duringChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.animationComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresWootzArmor))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,200F);
	}
	private void startChargeEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.MH5_CHARGING);
		this.initialHealth = var1.getHealth();
		this.hpThreshold = WyHelper.percentage(20D,(double)(var1.getMaxHealth()));
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),200,0));
	}
	private void duringChargeEvent(LivingEntity var1, IAbility var2) {
		if (((double)(var1.getHealth())) <= (((double)(this.initialHealth)) - (this.hpThreshold))){
			this.chargeComponent.forceStopCharging(var1);
			var1.removeEffect((Holder)(ModEffects.MOVEMENT_BLOCKED));
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.DIZZY),100,0));
			this.cooldownComponent.startCooldown(var1,1200F);
			return;
		}else{
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.CHARGE_MH5_BOMB.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
			return;
		}
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		if ((this.targetPosition) == (null)){
			Vec3 var3 = var1.position().add(0D,(double)(var1.getEyeHeight()),0D);
			Vec3 var4 = var3.add(var1.getLookAngle().scale(128D));
			BlockHitResult var5 = var1.level().clip(new ClipContext(var3,var4,ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,(Entity)(var1)));
			this.targetPosition = var5.getBlockPos();
		}
		MH5CloudEntity var3_1 = new MH5CloudEntity((Level)(var1.level()),(LivingEntity)(var1));
		var3_1.setLife(100);
		var3_1.moveTo(this.targetPosition,0F,0F);
		AbilityHelper.setDeltaMovement(var3_1,0D,0D,0D);
		var1.level().addFreshEntity((Entity)(var3_1));
		this.cooldownComponent.startCooldown(var1,1200F);
		this.targetPosition = null;
	}
	public void setTargetPosition(BlockPos var1) {
		this.targetPosition = var1;
	}
	private static boolean canUnlock(LivingEntity var0) {
		if (var0 instanceof Player){
			return false;
		}else{
			return WootzArmorItem.hasArmorEquipped(var0);
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Charges a deathly poison bomb causing all within its radius to die when inhaling the resulting gas."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(1200F),ChargeComponent.getTooltip(200F),RangeComponent.getTooltip(100F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.EQUIPMENT,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(MH5Ability::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceElement(SourceElement.POISON).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(MH5Ability::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("mh5","MH5",var0));
	}
}