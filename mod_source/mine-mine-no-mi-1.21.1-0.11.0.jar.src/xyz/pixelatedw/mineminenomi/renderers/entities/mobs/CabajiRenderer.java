package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.CabajiEntity;
import xyz.pixelatedw.mineminenomi.renderers.layers.CabajiUnicycleLayer;

public class CabajiRenderer extends HumanoidMobRenderer<CabajiEntity, HumanoidModel<CabajiEntity>> {
	public CabajiRenderer(EntityRendererProvider.Context var1) {
		super(var1,new HumanoidModel(var1.bakeLayer(ModelLayers.ZOMBIE)),1F);
		this.addLayer((RenderLayer)(new CabajiUnicycleLayer(var1,(RenderLayerParent)(this))));
	}
	public void render(CabajiEntity var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		var4.pushPose();
		if (var1.hasUnicycle()){
			var4.translate(0D,0.3D,0D);
		}
		super.render((LivingEntity)(var1),var2,var3,var4,var5,var6);
		var4.popPose();
	}
	public ResourceLocation getTextureLocation(CabajiEntity var1) {
		return ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/cabaji.png");
	}
}