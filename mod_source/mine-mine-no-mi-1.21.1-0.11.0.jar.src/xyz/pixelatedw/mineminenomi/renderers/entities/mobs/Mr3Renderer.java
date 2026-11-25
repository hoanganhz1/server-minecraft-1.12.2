package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr3Entity;
import xyz.pixelatedw.mineminenomi.renderers.layers.hairs.Mr3HairLayer;

public class Mr3Renderer extends OPHumanoidRenderer<Mr3Entity, HumanoidModel<Mr3Entity>> {
	public Mr3Renderer(EntityRendererProvider.Context var1) {
		super(var1,() -> {
			return new HumanoidModel(var1.bakeLayer(ModelLayers.ZOMBIE));
		},new float[]{1F,1F,1F});
		this.addLayer((RenderLayer)(new Mr3HairLayer(var1,(RenderLayerParent)(this))));
	}
	public void render(Mr3Entity var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		Consumer<? super ModelPart> var7 = (l0) -> {
			l0.visible = true;
		};
		((java.lang.Iterable)(RendererHelper.getModelParts(((HumanoidModel)(this.getModel()))))).forEach((Consumer)(var7));
		super.render((LivingEntity)(var1),var2,var3,var4,var5,var6);
	}
	public ResourceLocation getTextureLocation(Mr3Entity var1) {
		return ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/mr3.png");
	}
}