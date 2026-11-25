package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates.JangoEntity;
import xyz.pixelatedw.mineminenomi.renderers.layers.armors.JangoGlassesLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.hairs.JangoMushroomLayer;

public class JangoRenderer extends HumanoidMobRenderer<JangoEntity, HumanoidModel<JangoEntity>> {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/jango.png");

	public JangoRenderer(EntityRendererProvider.Context var1) {
		super(var1,new HumanoidModel(var1.bakeLayer(ModelLayers.ZOMBIE)),1F);
		this.addLayer((RenderLayer)(new HumanoidArmorLayer((RenderLayerParent)(this),new HumanoidModel(var1.bakeLayer(ModelLayers.ZOMBIE)),new HumanoidModel(var1.bakeLayer(ModelLayers.ZOMBIE)),var1.getModelManager())));
		this.addLayer((RenderLayer)(new JangoMushroomLayer(var1,(RenderLayerParent)(this))));
		this.addLayer((RenderLayer)(new JangoGlassesLayer(var1,(RenderLayerParent)(this))));
	}
	public ResourceLocation getTextureLocation(JangoEntity var1) {
		return TEXTURE;
	}
}