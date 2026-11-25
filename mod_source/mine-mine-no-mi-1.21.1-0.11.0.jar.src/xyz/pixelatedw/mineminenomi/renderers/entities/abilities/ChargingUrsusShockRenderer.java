package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

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
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu.ChargingUrsusShockEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.PawModel;

public class ChargingUrsusShockRenderer<T extends ChargingUrsusShockEntity> extends EntityRenderer<T> {
	// Fields
	private PawModel<T> model;

	protected ChargingUrsusShockRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.model = new PawModel(var1.bakeLayer(PawModel.LAYER_LOCATION));
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		var4.pushPose();
		var4.translate(0D,1.5D,0D);
		var4.mulPose(Axis.XP.rotationDegrees(180F));
		var4.mulPose(Axis.YP.rotationDegrees(((var1.yRotO) + (((var1.getYRot()) - (var1.yRotO)) * (var3))) - (180F)));
		float var7 = (1F) + ((var1.getCharge()) / (2F));
		var4.scale(var7,var7,var7);
		VertexConsumer var8 = var5.getBuffer(ModRenderTypes.TRANSPARENT_COLOR);
		this.model.renderToBuffer(var4,var8,var6,OverlayTexture.NO_OVERLAY,WyHelper.packColor(1F,1F,1F,0.6F));
		var4.pushPose();
		double var9 = (double)(((var1.tickCount) * (3)) % (100));
		float var10 = (float)(((var9) >= (50D)) ? ((2D) - ((var9) / (100D))) : ((1D) + ((var9) / (100D))));
		var4.scale(var10,var10,var10);
		this.model.renderToBuffer(var4,var8,var6,OverlayTexture.NO_OVERLAY,WyHelper.packColor(1F,1F,1F,0.2F));
		var4.popPose();
		var4.popPose();
	}
	public ResourceLocation getTextureLocation(ChargingUrsusShockEntity var1) {
		return null;
	}
}