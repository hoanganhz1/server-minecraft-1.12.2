package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.ViewArea;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={LevelRenderer.class})
public interface ILevelRendererMixin {
	@Accessor(value="entityTarget")
	public abstract void setEntityTarget(RenderTarget var0);
	@Accessor(value="viewArea")
	public abstract void setViewFrustum(ViewArea var0);
	@Accessor(value="viewArea")
	public abstract ViewArea getViewFrustum();
	@Accessor(value="renderBuffers")
	public abstract RenderBuffers getRenderTypeTextures();
	@Accessor(value="entityEffect")
	public abstract PostChain getEntityEffect();
}