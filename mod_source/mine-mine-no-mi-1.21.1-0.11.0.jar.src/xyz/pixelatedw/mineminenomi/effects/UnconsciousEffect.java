package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IDisableAbilitiesEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public class UnconsciousEffect extends BaseEffect implements IColorOverlayEffect, IDisableAbilitiesEffect {
	// Fields
	private static final Color VIEW_OVERLAY_COLOR = new Color(0F,0F,0F,1F);

	public UnconsciousEffect() {
		super(MobEffectCategory.HARMFUL,WyHelper.hexToRGB("#000000").getRGB());
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(NeoForgeMod.SWIM_SPEED,AttributeHelper.EFFECT_SWIM_SPEED_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(ModAttributes.JUMP_HEIGHT,AttributeHelper.EFFECT_JUMP_HEIGHT_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_SPEED,AttributeHelper.EFFECT_ATTACK_SPEED_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(ModAttributes.MINING_SPEED,AttributeHelper.EFFECT_MINING_SPEED_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return ((var1) % (20)) == (0);
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		return true;
	}
	public void startEffect(LivingEntity var1, MobEffectInstance var2) {
		this.startAnimation(var1);
	}
	private void startAnimation(LivingEntity var1) {
		if (var1.level().isClientSide){
			return;
		}else{
			MobEffectInstance var2 = var1.getEffect((Holder)(ModEffects.UNCONSCIOUS));
			if ((var2) == (null)){
				return;
			}else{
				int var3 = (var2.getDuration()) + (1);
				ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation(var1,ModAnimations.UNCONSCIOUS,var3,true),var1);
				return;
			}
		}
	}
	public void stopEffect(LivingEntity var1) {
		if (var1.level().isClientSide){
			return;
		}else{
			ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation(var1,ModAnimations.UNCONSCIOUS),var1);
			return;
		}
	}
	public boolean shouldUpdateClient() {
		return true;
	}
	public Color getViewOverlayColor(int var1, int var2) {
		return VIEW_OVERLAY_COLOR;
	}
	@Nullable()
	public @Nullable() Color getBodyOverlayColor(int var1, int var2) {
		return null;
	}
	public boolean isBlockingRotation() {
		return true;
	}
	public boolean isBlockingSwings() {
		return true;
	}
	public boolean isRemoveable() {
		return false;
	}
	public Predicate<IAbility> getDisabledAbilities() {
		Predicate<IAbility> var1 = (l0) -> {
			return true;
		};
		return var1;
	}
}