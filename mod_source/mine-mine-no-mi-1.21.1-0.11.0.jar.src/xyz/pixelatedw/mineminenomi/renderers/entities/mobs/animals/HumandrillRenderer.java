package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.HumandrillEntity;
import xyz.pixelatedw.mineminenomi.models.armors.HumandrillArmorModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.HumandrillModel;

public class HumandrillRenderer<T extends HumandrillEntity, M extends HumandrillModel<T>> extends MobRenderer<T, M> {
	// Fields
	public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/humandrill.png");

	public HumandrillRenderer(EntityRendererProvider.Context var1) {
		super(var1,(EntityModel)(new HumandrillModel(var1.bakeLayer(HumandrillModel.LAYER_LOCATION))),0.8F);
		this.addLayer((RenderLayer)(new ItemInHandLayer((RenderLayerParent)(this),var1.getItemInHandRenderer())));
		this.addLayer((RenderLayer)(new HumanoidArmorLayer((RenderLayerParent)(this),(HumanoidModel)(new HumandrillArmorModel(var1.bakeLayer(HumandrillArmorModel.LAYER_LOCATION))),(HumanoidModel)(new HumandrillArmorModel(var1.bakeLayer(HumandrillArmorModel.LAYER_LOCATION))),var1.getModelManager())));
	}
	public void scale(T var1, PoseStack var2, float var3) {
		var2.scale(var1.getSize(),var1.getSize(),var1.getSize());
		this.shadowRadius = (var1.getSize()) / (1.2F);
	}
	public ResourceLocation getTextureLocation(T var1) {
		return TEXTURE;
	}
}