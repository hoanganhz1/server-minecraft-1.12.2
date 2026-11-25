package xyz.pixelatedw.mineminenomi.renderers.entities.vehicles;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.vehicles.CannonEntity;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.CannonModel;

public class CannonRenderer$Factory implements EntityRendererProvider<CannonEntity> {
	public EntityRenderer<CannonEntity> create(EntityRendererProvider.Context var1) {
		CannonModel var2 = new CannonModel(var1);
		return (EntityRenderer) (new CannonRenderer(var1,var2));
	}
}