package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;

public class GanmenSeichoHormoneEffect extends BaseEffect {
	public GanmenSeichoHormoneEffect() {
		super(MobEffectCategory.NEUTRAL,WyHelper.hexToRGB("#968fb1").getRGB());
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-0.2D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}
	public boolean shouldUpdateClient() {
		return true;
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean isLingering() {
		return true;
	}
}