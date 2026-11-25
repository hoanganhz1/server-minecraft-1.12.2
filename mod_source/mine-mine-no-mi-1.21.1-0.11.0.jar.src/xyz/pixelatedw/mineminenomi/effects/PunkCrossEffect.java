package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITextureOverlayEffect;

public class PunkCrossEffect extends ParalysisEffect implements ITextureOverlayEffect, IBindBodyEffect {
	// Fields
	public static final ResourceLocation TEXTURE = ResourceLocation.parse("textures/block/iron_block.png");

	public boolean shouldUpdateClient() {
		return true;
	}
	public boolean isBlockingRotation() {
		return true;
	}
	public boolean isRemoveable() {
		return false;
	}
	public ResourceLocation getBodyTexture(int var1, int var2) {
		return TEXTURE;
	}
}