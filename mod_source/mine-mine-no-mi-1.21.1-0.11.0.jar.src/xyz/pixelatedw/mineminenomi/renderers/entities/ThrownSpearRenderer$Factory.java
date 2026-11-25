package xyz.pixelatedw.mineminenomi.renderers.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.ThrownSpearEntity;

public class ThrownSpearRenderer$Factory implements EntityRendererProvider<ThrownSpearEntity> {
	public EntityRenderer<ThrownSpearEntity> create(EntityRendererProvider.Context var1) {
		return (EntityRenderer) (new ThrownSpearRenderer(var1));
	}
}