package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.WhiteWalkieSaddleModel;

public class WhiteWalkieSaddleLayer<T extends WhiteWalkieEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	// Fields
	private static final ResourceLocation SADDLE_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/white_walkie_saddle.png");
	private final WhiteWalkieSaddleModel<T> model;

	public WhiteWalkieSaddleLayer(EntityRendererProvider.Context var1, RenderLayerParent<T, M> var2) {
		super(var2);
		this.model = new WhiteWalkieSaddleModel(var1.bakeLayer(WhiteWalkieSaddleModel.LAYER_LOCATION));
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		if ((var4.isTame()) && (var4.isSaddled())){
			var1.pushPose();
			var1.scale(1.1F,1.1F,1.1F);
			this.model.prepareMobModel(var4,var5,var6,var7);
			RenderLayer.renderColoredCutoutModel((EntityModel)(this.model),SADDLE_TEXTURE,var1,var2,var3,var4,-1);
			var1.popPose();
		}
	}
}