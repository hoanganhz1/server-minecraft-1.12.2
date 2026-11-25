package xyz.pixelatedw.mineminenomi.renderers.entities.vehicles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.entities.vehicles.UnicycleEntity;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.UnicycleModel;

public class UnicycleRenderer extends EntityRenderer<UnicycleEntity> {
	// Fields
	public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/unicycle.png");
	private final UnicycleModel model;

	protected UnicycleRenderer(EntityRendererProvider.Context var1, UnicycleModel var2) {
		super(var1);
		this.model = var2;
		this.shadowRadius = 0.3F;
	}
	public void render(UnicycleEntity var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		var4.pushPose();
		var4.translate(0D,1.5D,0D);
		var4.scale(1F,1F,1F);
		var4.mulPose(Axis.YP.rotationDegrees((180F) - (var2)));
		float var7 = ((float)(var1.getHurtTime())) - (var3);
		if ((var7) > (0F)){
			var4.mulPose(Axis.YP.rotationDegrees((Mth.sin(var7)) * (var7)));
		}
		var4.scale(-1F,-1F,1F);
		this.model.setupAnim(var1,0F,0F,-0.1F,0F,0F);
		VertexConsumer var8 = var5.getBuffer(this.model.renderType(this.getTextureLocation(var1)));
		this.model.renderToBuffer(var4,var8,var6,OverlayTexture.NO_OVERLAY);
		var4.popPose();
		super.render((Entity)(var1),var2,var3,var4,var5,var6);
	}
	public ResourceLocation getTextureLocation(UnicycleEntity var1) {
		return TEXTURE;
	}
}