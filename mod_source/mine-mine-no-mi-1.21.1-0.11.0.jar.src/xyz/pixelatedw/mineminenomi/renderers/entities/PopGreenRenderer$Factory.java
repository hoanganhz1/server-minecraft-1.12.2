package xyz.pixelatedw.mineminenomi.renderers.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.projectiles.PopGreenProjectile;

public class PopGreenRenderer$Factory implements EntityRendererProvider<PopGreenProjectile> {
	public EntityRenderer<PopGreenProjectile> create(EntityRendererProvider.Context var1) {
		return new PopGreenRenderer(var1);
	}
}