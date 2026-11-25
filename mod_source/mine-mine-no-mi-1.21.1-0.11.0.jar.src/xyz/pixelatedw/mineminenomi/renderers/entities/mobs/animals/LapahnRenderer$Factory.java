package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.LapahnEntity;

public class LapahnRenderer$Factory implements EntityRendererProvider<LapahnEntity> {
	public EntityRenderer<LapahnEntity> create(EntityRendererProvider.Context var1) {
		return new LapahnRenderer(var1);
	}
}