package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.SeaCowEntity;

public class SeaCowRenderer$Factory implements EntityRendererProvider<SeaCowEntity> {
	public EntityRenderer<SeaCowEntity> create(EntityRendererProvider.Context var1) {
		return new SeaCowRenderer(var1);
	}
}