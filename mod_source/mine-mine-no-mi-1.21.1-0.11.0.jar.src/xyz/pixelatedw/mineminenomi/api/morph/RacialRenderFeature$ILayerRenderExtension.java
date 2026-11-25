package xyz.pixelatedw.mineminenomi.api.morph;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface()
public interface RacialRenderFeature$ILayerRenderExtension<T extends LivingEntity> {
	public abstract boolean render(PoseStack var0, MultiBufferSource var1, int var2, T var3, float var4, float var5, float var6, float var7, float var8, float var9, RenderLayerParent<T, ? extends EntityModel<T>> var10);
}