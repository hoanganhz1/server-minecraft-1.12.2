package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.FlyingFishEntity;

public class FlyingFishRenderer$Factory implements EntityRendererProvider<FlyingFishEntity> {
	public EntityRenderer<FlyingFishEntity> create(EntityRendererProvider.Context var1) {
		return new FlyingFishRenderer(var1);
	}
}