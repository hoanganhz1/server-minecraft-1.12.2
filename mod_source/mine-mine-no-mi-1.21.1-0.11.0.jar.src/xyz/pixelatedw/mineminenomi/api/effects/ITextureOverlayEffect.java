package xyz.pixelatedw.mineminenomi.api.effects;

import java.awt.Color;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;

public interface ITextureOverlayEffect {
	// Fields
	public static final Color DEFAULT_OVERLAY_COLOR = new Color(1F,1F,1F,1F);

	public abstract ResourceLocation getBodyTexture(int var0, int var1);
	public default ResourceLocation getViewTexture(int var1, int var2) {
		return this.getBodyTexture(var1,var2);
	}
	public default Color getTextureOverlayColor() {
		return DEFAULT_OVERLAY_COLOR;
	}
	@OnlyIn(value=Dist.CLIENT)
	public default RenderType getRenderType() {
		return ModRenderTypes.TRANSPARENT_COLOR;
	}
}