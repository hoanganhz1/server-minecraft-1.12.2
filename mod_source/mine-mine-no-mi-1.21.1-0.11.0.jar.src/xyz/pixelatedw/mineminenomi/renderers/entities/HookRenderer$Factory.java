package xyz.pixelatedw.mineminenomi.renderers.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.projectiles.HookEntity;

public class HookRenderer$Factory implements EntityRendererProvider<HookEntity> {
	public EntityRenderer<HookEntity> create(EntityRendererProvider.Context var1) {
		return new HookRenderer(var1);
	}
}