package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.entities.SpikeEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.CubeModel;

public class SpikeRenderer<T extends SpikeEntity> extends EntityRenderer<T> {
	// Fields
	private CubeModel<SpikeEntity> model;

	protected SpikeRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.shadowRadius = 0.05F;
		this.model = (CubeModel) (new CubeModel(var1.bakeLayer(CubeModel.LAYER_LOCATION)));
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		var4.pushPose();
		var4.translate(0D,0.02D,0D);
		float var7 = 0.2F;
		var4.scale(var7,var7,var7);
		var4.mulPose(Axis.XP.rotationDegrees(45F));
		var4.mulPose(Axis.YP.rotationDegrees(45F));
		VertexConsumer var8 = var5.getBuffer(ModRenderTypes.TRANSPARENT_COLOR);
		this.model.renderToBuffer(var4,var8,var6,OverlayTexture.NO_OVERLAY,WyHelper.packColor(0.2F,0.2F,0.2F,1F));
		var4.popPose();
	}
	public ResourceLocation getTextureLocation(T var1) {
		return null;
	}
}