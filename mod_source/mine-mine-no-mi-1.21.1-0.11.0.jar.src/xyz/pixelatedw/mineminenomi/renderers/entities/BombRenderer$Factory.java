package xyz.pixelatedw.mineminenomi.renderers.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.BombEntity;

public class BombRenderer$Factory implements EntityRendererProvider<BombEntity> {
	public EntityRenderer<BombEntity> create(EntityRendererProvider.Context var1) {
		return (EntityRenderer) (new BombRenderer(var1));
	}
}