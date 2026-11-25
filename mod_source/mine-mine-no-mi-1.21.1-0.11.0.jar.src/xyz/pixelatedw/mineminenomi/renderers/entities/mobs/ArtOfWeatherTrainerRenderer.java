package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.ArtOfWeatherTrainerEntity;
import xyz.pixelatedw.mineminenomi.renderers.layers.hairs.WizardBeardLayer;

public class ArtOfWeatherTrainerRenderer extends OPHumanoidRenderer<ArtOfWeatherTrainerEntity, HumanoidModel<ArtOfWeatherTrainerEntity>> {
	public ArtOfWeatherTrainerRenderer(EntityRendererProvider.Context var1) {
		super(var1,() -> {
			return new HumanoidModel(var1.bakeLayer(ModelLayers.ZOMBIE));
		},new float[]{1F,1F,1F});
		this.addLayer((RenderLayer)(new WizardBeardLayer(var1,(RenderLayerParent)(this))));
	}
}