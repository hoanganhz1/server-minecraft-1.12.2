package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BigDuckEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.BigDuckModel;
import xyz.pixelatedw.mineminenomi.renderers.layers.BigDuckSaddleLayer;

public class BigDuckRenderer<T extends BigDuckEntity, M extends BigDuckModel<T>> extends MobRenderer<T, M> {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/big_duck.png");

	public BigDuckRenderer(EntityRendererProvider.Context var1) {
		super(var1,(EntityModel)(new BigDuckModel(var1.bakeLayer(BigDuckModel.LAYER_LOCATION))),0.7F);
		this.addLayer((RenderLayer)(new BigDuckSaddleLayer((RenderLayerParent)(this))));
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		var4.pushPose();
		super.render(var1,var2,var3,var4,var5,var6);
		var4.popPose();
	}
	public ResourceLocation getTextureLocation(T var1) {
		return TEXTURE;
	}
}