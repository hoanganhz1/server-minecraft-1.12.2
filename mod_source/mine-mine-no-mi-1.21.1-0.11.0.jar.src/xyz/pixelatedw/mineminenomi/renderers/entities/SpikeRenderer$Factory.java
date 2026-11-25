package xyz.pixelatedw.mineminenomi.renderers.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.SpikeEntity;

public class SpikeRenderer$Factory implements EntityRendererProvider<SpikeEntity> {
	public EntityRenderer<SpikeEntity> create(EntityRendererProvider.Context var1) {
		return new SpikeRenderer(var1);
	}
}