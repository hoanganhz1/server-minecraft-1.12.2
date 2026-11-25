package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IScreenShaderEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;

public class NegativeEffect extends BaseEffect implements IScreenShaderEffect {
	// Fields
	private static final ResourceLocation SHADER = ResourceLocation.parse("shaders/post/desaturate.json");

	public NegativeEffect() {
		super(MobEffectCategory.HARMFUL,WyHelper.hexToRGB("#b6a8cb").getRGB());
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_SPEED,AttributeHelper.EFFECT_ATTACK_SPEED_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE,AttributeHelper.EFFECT_ATTACK_DAMAGE_ID,-5D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}
	public ResourceLocation getScreenShader() {
		return SHADER;
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean isLingering() {
		return true;
	}
}