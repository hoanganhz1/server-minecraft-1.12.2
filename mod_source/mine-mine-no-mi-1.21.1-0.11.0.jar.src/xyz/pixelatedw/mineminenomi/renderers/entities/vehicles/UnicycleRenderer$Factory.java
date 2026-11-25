package xyz.pixelatedw.mineminenomi.renderers.entities.vehicles;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.vehicles.UnicycleEntity;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.UnicycleModel;

public class UnicycleRenderer$Factory implements EntityRendererProvider<UnicycleEntity> {
	public EntityRenderer<UnicycleEntity> create(EntityRendererProvider.Context var1) {
		UnicycleModel var2 = new UnicycleModel(var1);
		return (EntityRenderer) (new UnicycleRenderer(var1,var2));
	}
}