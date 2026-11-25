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
import xyz.pixelatedw.mineminenomi.api.effects.IChangeSwingSpeedEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class NoroSlownessEffect extends BaseEffect implements IChangeSwingSpeedEffect {
	public NoroSlownessEffect() {
		super(MobEffectCategory.HARMFUL,WyHelper.hexToRGB("#fa5cb1").getRGB());
		this.addAttributeModifier(Attributes.ATTACK_SPEED,AttributeHelper.EFFECT_ATTACK_SPEED_ID,-0.5D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-0.5D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(NeoForgeMod.SWIM_SPEED,AttributeHelper.EFFECT_SWIM_SPEED_ID,-0.5D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(ModAttributes.JUMP_HEIGHT,AttributeHelper.EFFECT_JUMP_HEIGHT_ID,-0.5D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(ModAttributes.TIME_PROGRESSION,AttributeHelper.EFFECT_TIME_PROGRESSION_ID,-0.10000000149011612D,AttributeModifier.Operation.ADD_VALUE);
	}
	public float swingSpeedModifier(int var1, int var2) {
		return (float)((2) + (var2));
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean isLingering() {
		return true;
	}
}