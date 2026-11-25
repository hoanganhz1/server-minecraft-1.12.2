package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.DenDenMushiEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DenDenMushiModel;

public class DenDenMushiRenderer<T extends DenDenMushiEntity, M extends DenDenMushiModel<T>> extends MobRenderer<T, M> {
	public DenDenMushiRenderer(EntityRendererProvider.Context var1) {
		super(var1,(EntityModel)(new DenDenMushiModel(var1.bakeLayer(DenDenMushiModel.LAYER_LOCATION))),0.3F);
	}
	public ResourceLocation getTextureLocation(T var1) {
		return var1.getCurrentTexture();
	}
}