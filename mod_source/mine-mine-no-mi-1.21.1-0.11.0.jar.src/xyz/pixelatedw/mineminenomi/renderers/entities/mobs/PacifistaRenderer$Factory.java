package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.PacifistaEntity;

public class PacifistaRenderer$Factory implements EntityRendererProvider<PacifistaEntity> {
	public EntityRenderer<PacifistaEntity> create(EntityRendererProvider.Context var1) {
		return new PacifistaRenderer(var1);
	}
}