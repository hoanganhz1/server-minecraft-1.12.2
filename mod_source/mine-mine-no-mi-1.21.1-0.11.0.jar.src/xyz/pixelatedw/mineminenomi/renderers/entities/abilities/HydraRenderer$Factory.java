package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.api.entities.NuLightningEntity;

public class HydraRenderer$Factory implements EntityRendererProvider<NuLightningEntity> {
	public EntityRenderer<NuLightningEntity> create(EntityRendererProvider.Context var1) {
		return (EntityRenderer) (new HydraRenderer(var1));
	}
}