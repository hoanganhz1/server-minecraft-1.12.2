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
import xyz.pixelatedw.mineminenomi.entities.projectiles.PopGreenProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.SphereModel;

public class PopGreenRenderer<T extends PopGreenProjectile> extends EntityRenderer<T> {
	// Fields
	private static final int COLOR = 6924891;
	private SphereModel<PopGreenProjectile> model;

	protected PopGreenRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.shadowRadius = 0.05F;
		this.model = (SphereModel) (new SphereModel(var1.bakeLayer(SphereModel.LAYER_LOCATION)));
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		if ((var1.tickCount) < (2)){
			return;
		}else{
			var4.pushPose();
			if (!(var1.isOnGround())){
				float var7 = (((float)(var1.tickCount)) * (20F)) % (360F);
				var4.mulPose(Axis.YP.rotationDegrees(var7));
				var4.mulPose(Axis.XP.rotationDegrees(-(var7)));
			}
			var4.scale(0.5F,0.5F,0.5F);
			VertexConsumer var7_1 = var5.getBuffer(ModRenderTypes.TRANSPARENT_COLOR);
			this.model.renderToBuffer(var4,var7_1,var6,OverlayTexture.NO_OVERLAY,6924891);
			var4.popPose();
			return;
		}
	}
	public ResourceLocation getTextureLocation(T var1) {
		return null;
	}
}