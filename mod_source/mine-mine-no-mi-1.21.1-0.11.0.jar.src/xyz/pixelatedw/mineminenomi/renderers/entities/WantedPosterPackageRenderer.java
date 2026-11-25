package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.entities.WantedPosterPackageEntity;
import xyz.pixelatedw.mineminenomi.models.entities.WantedPosterPackageModel;

public class WantedPosterPackageRenderer<T extends WantedPosterPackageEntity> extends EntityRenderer<T> {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/wanted_posters_package.png");
	private final WantedPosterPackageModel model;

	protected WantedPosterPackageRenderer(EntityRendererProvider.Context var1, WantedPosterPackageModel var2) {
		super(var1);
		this.model = var2;
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		var4.pushPose();
		var4.translate(0F,1.5F,0F);
		var4.mulPose(Axis.XP.rotationDegrees(180F));
		var4.mulPose(Axis.YP.rotationDegrees(((var1.xRotO) + (((var1.getXRot()) - (var1.xRotO)) * (var3))) - (180F)));
		var4.scale(1.25F,1F,1.25F);
		float var7 = ((float)(var1.tickCount)) + (var3);
		this.model.setupAnim(var1,0F,0F,var7,0F,0F);
		VertexConsumer var8 = var5.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(var1)));
		this.model.renderToBuffer(var4,var8,var6,OverlayTexture.NO_OVERLAY);
		var4.popPose();
	}
	public ResourceLocation getTextureLocation(WantedPosterPackageEntity var1) {
		return TEXTURE;
	}
}