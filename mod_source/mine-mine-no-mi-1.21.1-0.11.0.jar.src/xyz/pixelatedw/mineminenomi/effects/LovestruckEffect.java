package xyz.pixelatedw.mineminenomi.effects;

import it.unimi.dsi.fastutil.ints.Int2DoubleFunction;
import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.effects.ITextureOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IWeakenedByHaoshokuEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class LovestruckEffect extends BaseEffect implements ITextureOverlayEffect, IWeakenedByHaoshokuEffect {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft","textures/block/stone.png");
	private static final Color OVERLAY_COLOR = new Color(1F,1F,1F,0.8F);

	public LovestruckEffect() {
		super(MobEffectCategory.HARMFUL,0);
		Int2DoubleFunction var1 = (l0) -> {
			return -1D;
		};
		Int2DoubleFunction var2 = (l0) -> {
			return 1D;
		};
		Int2DoubleFunction var3 = (l0) -> {
			return -1D;
		};
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,var1).addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,AttributeHelper.EFFECT_KNOCKBACK_RESISTANCE_ID,AttributeModifier.Operation.ADD_VALUE,var2).addAttributeModifier(ModAttributes.TOUGHNESS,AttributeHelper.EFFECT_TOUGHNESS_ID,2D,AttributeModifier.Operation.ADD_VALUE).addAttributeModifier(ModAttributes.JUMP_HEIGHT,AttributeHelper.EFFECT_JUMP_HEIGHT_ID,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,var3);
	}
	public boolean shouldUpdateClient() {
		return true;
	}
	public boolean isBlockingRotation() {
		return true;
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean isBlockingSwings() {
		return true;
	}
	public ResourceLocation getBodyTexture(int var1, int var2) {
		return TEXTURE;
	}
	public Color getTextureOverlayColor() {
		return OVERLAY_COLOR;
	}
	public boolean isWeakenedByHaoshoku() {
		return true;
	}
}