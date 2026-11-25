package xyz.pixelatedw.mineminenomi.renderers.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;

public class EmptyRenderer$Factory<T extends Entity> implements EntityRendererProvider<T> {
	public EntityRenderer<T> create(EntityRendererProvider.Context var1) {
		return new EmptyRenderer(var1);
	}
}