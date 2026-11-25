package xyz.pixelatedw.mineminenomi.renderers.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.SphereEntity;

public class SphereRenderer$Factory implements EntityRendererProvider<SphereEntity> {
	public EntityRenderer<SphereEntity> create(EntityRendererProvider.Context var1) {
		return new SphereRenderer(var1);
	}
}