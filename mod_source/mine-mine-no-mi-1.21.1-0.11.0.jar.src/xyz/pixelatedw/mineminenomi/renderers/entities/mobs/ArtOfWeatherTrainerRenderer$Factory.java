package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.ArtOfWeatherTrainerEntity;

public class ArtOfWeatherTrainerRenderer$Factory implements EntityRendererProvider<ArtOfWeatherTrainerEntity> {
	public EntityRenderer<ArtOfWeatherTrainerEntity> create(EntityRendererProvider.Context var1) {
		return (EntityRenderer) (new ArtOfWeatherTrainerRenderer(var1));
	}
}