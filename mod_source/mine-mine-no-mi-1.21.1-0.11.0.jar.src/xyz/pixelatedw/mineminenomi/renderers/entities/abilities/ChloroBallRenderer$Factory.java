package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku.ChloroBallProjectile;

public class ChloroBallRenderer$Factory implements EntityRendererProvider<ChloroBallProjectile> {
	public EntityRenderer<ChloroBallProjectile> create(EntityRendererProvider.Context var1) {
		return (EntityRenderer) (new ChloroBallRenderer(var1));
	}
}