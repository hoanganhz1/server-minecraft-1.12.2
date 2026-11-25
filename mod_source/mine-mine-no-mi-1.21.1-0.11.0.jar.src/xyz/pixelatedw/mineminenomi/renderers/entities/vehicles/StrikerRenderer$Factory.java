package xyz.pixelatedw.mineminenomi.renderers.entities.vehicles;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.StrikerModel;

public class StrikerRenderer$Factory implements EntityRendererProvider<StrikerEntity> {
	public EntityRenderer<StrikerEntity> create(EntityRendererProvider.Context var1) {
		StrikerModel var2 = new StrikerModel(var1);
		return (EntityRenderer) (new StrikerRenderer(var1,var2));
	}
}