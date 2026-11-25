package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.KuroobiEntity;
import xyz.pixelatedw.mineminenomi.renderers.layers.hairs.KuroobiHairLayer;

public class KuroobiRenderer extends OPHumanoidRenderer<KuroobiEntity, HumanoidModel<KuroobiEntity>> {
	public KuroobiRenderer(EntityRendererProvider.Context var1) {
		super(var1,() -> {
			return new HumanoidModel(var1.bakeLayer(ModelLayers.ZOMBIE));
		},new float[]{1.28F,1.28F,1.28F});
		this.addLayer((RenderLayer)(new KuroobiHairLayer(var1,(RenderLayerParent)(this))));
	}
	public ResourceLocation getTextureLocation(KuroobiEntity var1) {
		return ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/kuroobi.png");
	}
}