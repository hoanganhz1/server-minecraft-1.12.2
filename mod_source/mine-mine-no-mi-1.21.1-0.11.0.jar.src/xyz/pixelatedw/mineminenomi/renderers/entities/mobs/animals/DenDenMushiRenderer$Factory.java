package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.DenDenMushiEntity;

public class DenDenMushiRenderer$Factory implements EntityRendererProvider<DenDenMushiEntity> {
	public EntityRenderer<DenDenMushiEntity> create(EntityRendererProvider.Context var1) {
		return new DenDenMushiRenderer(var1);
	}
}