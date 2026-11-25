package xyz.pixelatedw.mineminenomi.renderers.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.ChakramEntity;

public class ChakramRenderer$Factory implements EntityRendererProvider<ChakramEntity> {
	public EntityRenderer<ChakramEntity> create(EntityRendererProvider.Context var1) {
		return new ChakramRenderer(var1);
	}
}