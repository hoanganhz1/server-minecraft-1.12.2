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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BananawaniEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.BananawaniModel;
import xyz.pixelatedw.mineminenomi.renderers.layers.BananawaniSaddleLayer;

public class BananawaniRenderer<T extends BananawaniEntity, M extends BananawaniModel<T>> extends MobRenderer<T, M> {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/bananawani.png");

	public BananawaniRenderer(EntityRendererProvider.Context var1) {
		super(var1,(EntityModel)(new BananawaniModel(var1.bakeLayer(BananawaniModel.LAYER_LOCATION))),1.5F);
		this.addLayer((RenderLayer)(new BananawaniSaddleLayer(var1,(RenderLayerParent)(this))));
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		var4.pushPose();
		var4.translate(0D,0.1D,0D);
		super.render(var1,var2,var3,var4,var5,var6);
		var4.popPose();
	}
	public void scale(T var1, PoseStack var2, float var3) {
		var2.scale(1.5F,1.5F,1.5F);
	}
	public ResourceLocation getTextureLocation(T var1) {
		return TEXTURE;
	}
}