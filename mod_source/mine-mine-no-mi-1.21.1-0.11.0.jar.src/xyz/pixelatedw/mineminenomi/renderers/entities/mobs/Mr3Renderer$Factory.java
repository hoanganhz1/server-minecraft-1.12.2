package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr3Entity;

public class Mr3Renderer$Factory implements EntityRendererProvider<Mr3Entity> {
	public EntityRenderer<Mr3Entity> create(EntityRendererProvider.Context var1) {
		return (EntityRenderer) (new Mr3Renderer(var1));
	}
}