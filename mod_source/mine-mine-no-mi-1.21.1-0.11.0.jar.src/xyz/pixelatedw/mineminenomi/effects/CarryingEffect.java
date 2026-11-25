package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public class CarryingEffect extends BaseEffect {
	public CarryingEffect() {
		super(MobEffectCategory.NEUTRAL,WyHelper.hexToRGB("#000000").getRGB());
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-0.15000000596046448D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(NeoForgeMod.SWIM_SPEED,AttributeHelper.EFFECT_SWIM_SPEED_ID,-0.15000000596046448D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(ModAttributes.JUMP_HEIGHT,AttributeHelper.EFFECT_JUMP_HEIGHT_ID,-0.15000000596046448D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_SPEED,AttributeHelper.EFFECT_ATTACK_SPEED_ID,-0.15000000596046448D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(ModAttributes.MINING_SPEED,AttributeHelper.EFFECT_MINING_SPEED_ID,-0.15000000596046448D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}
	public void startEffect(LivingEntity var1, MobEffectInstance var2) {
		ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation(var1,ModAnimations.RAISE_ARMS,var2.getDuration(),true),var1);
	}
	public void stopEffect(LivingEntity var1) {
		ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation(var1,ModAnimations.RAISE_ARMS),var1);
	}
	public boolean isRemoveable() {
		return false;
	}
}