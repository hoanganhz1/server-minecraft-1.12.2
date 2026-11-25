package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;

public class AntiKnockbackEffect extends BaseEffect {
	public AntiKnockbackEffect() {
		super(MobEffectCategory.NEUTRAL,WyHelper.hexToRGB("#000000").getRGB());
		this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,AttributeHelper.EFFECT_KNOCKBACK_RESISTANCE_ID,1D,AttributeModifier.Operation.ADD_VALUE);
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean shouldRenderParticles(MobEffectInstance var1) {
		return false;
	}
	public boolean shouldRenderHUD(MobEffectInstance var1) {
		return false;
	}
}