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
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.entities.VivreCardEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.CubeModel;

@OnlyIn(value=Dist.CLIENT)
public class VivreCardRenderer extends EntityRenderer<VivreCardEntity> {
	// Fields
	private final CubeModel<VivreCardEntity> model;

	protected VivreCardRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.model = (CubeModel) (new CubeModel(var1.bakeLayer(CubeModel.LAYER_LOCATION)));
	}
	public ResourceLocation getTextureLocation(VivreCardEntity var1) {
		return null;
	}
	public void render(VivreCardEntity var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		var4.pushPose();
		var4.translate(0D,0.05D,0D);
		var4.mulPose(Axis.YP.rotationDegrees(-(var1.getYRot())));
		var4.scale(0.4F,0.1F,0.5F);
		VertexConsumer var7 = var5.getBuffer(ModRenderTypes.TRANSPARENT_COLOR);
		this.model.renderToBuffer(var4,var7,var6,OverlayTexture.NO_OVERLAY,Color.WHITE.getRGB());
		var4.popPose();
		super.render((Entity)(var1),var2,var3,var4,var5,var6);
	}
}