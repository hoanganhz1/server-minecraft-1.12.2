package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.PandaSharkEntity;

public class PandaSharkRenderer$Factory implements EntityRendererProvider<PandaSharkEntity> {
	public EntityRenderer<PandaSharkEntity> create(EntityRendererProvider.Context var1) {
		return new PandaSharkRenderer(var1);
	}
}