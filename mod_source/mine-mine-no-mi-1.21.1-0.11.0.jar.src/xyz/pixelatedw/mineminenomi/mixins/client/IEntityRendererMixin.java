package xyz.pixelatedw.mineminenomi.mixins.client;

import net.minecraft.client.renderer.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={EntityRenderer.class})
public interface IEntityRendererMixin {
	@Accessor(value="shadowRadius")
	public abstract void setShadowRadius(float var0);
	@Accessor(value="shadowRadius")
	public abstract float getShadowRadius();
	@Accessor(value="shadowStrength")
	public abstract float getShadowStrength();
}