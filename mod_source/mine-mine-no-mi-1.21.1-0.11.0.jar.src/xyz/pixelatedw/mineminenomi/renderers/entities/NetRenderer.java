package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.awt.Color;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.enums.NetType;
import xyz.pixelatedw.mineminenomi.effects.CaughtInNetEffect;
import xyz.pixelatedw.mineminenomi.entities.NetEntity;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.PlaneModel;

public class NetRenderer<T extends NetEntity> extends EntityRenderer<T> {
	// Fields
	protected PlaneModel<NetEntity> model;

	public NetRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.shadowRadius = 0.05F;
		this.model = (PlaneModel) (new PlaneModel(var1.bakeLayer(PlaneModel.LAYER_LOCATION)));
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		var4.pushPose();
		var1.setXRot(Mth.clamp(var1.getXRot(),-30F,50F));
		var1.xRotO = Mth.clamp(var1.xRotO,-30F,50F);
		var4.mulPose(Axis.YP.rotationDegrees((Mth.lerp(var3,var1.yRotO,var1.getYRot())) + (0F)));
		var4.mulPose(Axis.XP.rotationDegrees((Mth.lerp(var3,var1.xRotO,var1.getXRot())) + (-10F)));
		var4.scale(6F,6F,6F);
		var4.translate(0D,-1.45D,0D);
		VertexConsumer var7 = var5.getBuffer(RenderType.entityTranslucentCull(this.getTextureLocation(var1)));
		this.model.renderToBuffer(var4,var7,var6,OverlayTexture.NO_OVERLAY,Color.WHITE.getRGB());
		var4.popPose();
	}
	public ResourceLocation getTextureLocation(T var1) {
		switch (NetRenderer$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$NetType[var1.getNetType().ordinal()]){
		case 1:

		case 2:

		default:
			return CaughtInNetEffect.NET_TEXTURE;
		}
	}
}