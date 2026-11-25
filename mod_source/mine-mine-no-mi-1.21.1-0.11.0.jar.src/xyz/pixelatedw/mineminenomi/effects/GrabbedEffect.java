package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class GrabbedEffect extends BaseEffect implements IBindBodyEffect, IBindHandsEffect {
	public GrabbedEffect() {
		super(MobEffectCategory.HARMFUL,WyHelper.hexToRGB("#000000").getRGB());
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_SPEED,AttributeHelper.EFFECT_ATTACK_SPEED_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(NeoForgeMod.SWIM_SPEED,AttributeHelper.EFFECT_SWIM_SPEED_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(ModAttributes.JUMP_HEIGHT,AttributeHelper.EFFECT_JUMP_HEIGHT_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,AttributeHelper.EFFECT_KNOCKBACK_RESISTANCE_ID,1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean isBlockingRotation() {
		return true;
	}
	public boolean isBlockingSwings() {
		return true;
	}
}