package xyz.pixelatedw.mineminenomi.renderers.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.WantedPosterPackageEntity;
import xyz.pixelatedw.mineminenomi.models.entities.WantedPosterPackageModel;

public class WantedPosterPackageRenderer$Factory implements EntityRendererProvider<WantedPosterPackageEntity> {
	public EntityRenderer<WantedPosterPackageEntity> create(EntityRendererProvider.Context var1) {
		WantedPosterPackageModel var2 = new WantedPosterPackageModel(var1);
		return new WantedPosterPackageRenderer(var1,var2);
	}
}