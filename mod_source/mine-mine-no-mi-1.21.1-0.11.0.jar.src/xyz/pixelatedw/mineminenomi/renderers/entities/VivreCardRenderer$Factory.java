package xyz.pixelatedw.mineminenomi.renderers.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.VivreCardEntity;

public class VivreCardRenderer$Factory implements EntityRendererProvider<VivreCardEntity> {
	public EntityRenderer<VivreCardEntity> create(EntityRendererProvider.Context var1) {
		return (EntityRenderer) (new VivreCardRenderer(var1));
	}
}