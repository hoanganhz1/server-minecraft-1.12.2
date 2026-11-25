package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara.BaraFestivalEntity;

public class BaraFestivalRenderer$Factory implements EntityRendererProvider<BaraFestivalEntity> {
	public EntityRenderer<BaraFestivalEntity> create(EntityRendererProvider.Context var1) {
		return new BaraFestivalRenderer(var1);
	}
}