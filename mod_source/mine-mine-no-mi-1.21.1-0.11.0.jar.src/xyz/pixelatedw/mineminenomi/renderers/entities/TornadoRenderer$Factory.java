package xyz.pixelatedw.mineminenomi.renderers.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.TornadoEntity;

public class TornadoRenderer$Factory implements EntityRendererProvider<TornadoEntity> {
	public EntityRenderer<TornadoEntity> create(EntityRendererProvider.Context var1) {
		return new TornadoRenderer(var1);
	}
}