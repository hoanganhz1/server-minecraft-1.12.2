package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.NuLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuLightningEntityRenderer;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku.HydraProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.HydraModel;

public class HydraRenderer extends NuLightningEntityRenderer {
	// Fields
	private static final ResourceLocation HYDRA_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/projectiles/hydra.png");
	private static final ResourceLocation HYDRA_HEAD_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/projectiles/hydra_head.png");
	protected HydraModel<HydraProjectile> model;

	protected HydraRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.setTexture(HYDRA_TEXTURE);
		this.model = (HydraModel) (new HydraModel(var1.bakeLayer(HydraModel.LAYER_LOCATION)));
	}
	public void render(NuLightningEntity var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		super.render(var1,var2,var3,var4,var5,var6);
		var4.pushPose();
		double var7 = (var1.position().x()) - (var1.getPreciseCurrentX());
		double var8 = (var1.position().y()) - (var1.getPreciseCurrentY());
		var4.translate(-(var7),-(var8),-((var1.position().z()) - (var1.getPreciseCurrentZ())));
		var4.mulPose(Axis.YN.rotationDegrees(var1.getYRot()));
		var4.mulPose(Axis.XP.rotationDegrees(var1.getXRot()));
		var4.scale(-1F,-1F,-1F);
		float var9 = 3F;
		var4.scale(var9,var9,var9);
		float var10 = ((float)(var1.tickCount)) + (var3);
		this.model.setupAnim((Entity)(var1),0F,0F,var10,0F,0F);
		VertexConsumer var11 = var5.getBuffer(ModRenderTypes.getPosColorTexLightmap(HYDRA_HEAD_TEXTURE));
		this.model.renderToBuffer(var4,var11,var6,OverlayTexture.NO_OVERLAY,WyHelper.packColor(1F,1F,1F,0.5F));
		var4.popPose();
	}
	public ResourceLocation getTextureLocation(NuLightningEntity var1) {
		return super.getTextureLocation(var1);
	}
}