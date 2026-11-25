package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BigDuckEntity;

public class BigDuckRenderer$Factory implements EntityRendererProvider<BigDuckEntity> {
	public EntityRenderer<BigDuckEntity> create(EntityRendererProvider.Context var1) {
		return new BigDuckRenderer(var1);
	}
}