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
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.entities.BombEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.SphereModel;

public class BombRenderer extends EntityRenderer<BombEntity> {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/bomb.png");
	private SphereModel<BombEntity> model;

	protected BombRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.shadowRadius = 0.6F;
		this.model = (SphereModel) (new SphereModel(var1.bakeLayer(SphereModel.LAYER_LOCATION)));
	}
	public void render(BombEntity var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		var4.pushPose();
		var4.translate(0D,0.8D,0D);
		var4.mulPose(Axis.XP.rotationDegrees(180F));
		if ((var1.tickCount) < (4)){
			var4.translate(0D,(1.5D) - ((double)(((float)(var1.tickCount)) / (3F))),0D);
		}else{
			var4.translate(0D,0.4D,0D);
		}
		float var7 = 3F;
		var4.scale(var7,var7,var7);
		int var8 = 1;
		var4.mulPose(Axis.YN.rotationDegrees(Mth.lerp(var3,var1.getYRot(),var1.yRotO)));
		int var9 = (Math.min(var8,6)) - (1);
		RenderType var10 = RenderType.entityTranslucent(this.getTextureLocation(var1));
		if ((var8) > (0)){
			var8 = Math.max(3,var8);
			int var11 = (Math.max(40,(var8) * (10))) + ((var1.hashCode()) % (10));
			if (((var11) > (0)) && ((((var1.tickCount) % (var11)) > (0)) && (((var1.tickCount) % (var11)) < (var8)))){
				var10 = ModRenderTypes.TRANSPARENT_COLOR;
			}
		}
		VertexConsumer var11_1 = var5.getBuffer(var10);
		this.model.renderToBuffer(var4,var11_1,var6,OverlayTexture.NO_OVERLAY);
		if (((var1.tickCount) % (15)) == (0)){
			int var12 = 0;
			do{
				if ((var12) < (var9)){
					var1.level().addParticle((ParticleOptions)(ParticleTypes.FLAME),var1.getX(),((var1.getY()) + (3.700000047683716D)) + ((double)(((float)(var12)) / (8F))),var1.getZ(),0D,0D,0D);
					var12++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		var4.popPose();
	}
	public ResourceLocation getTextureLocation(BombEntity var1) {
		return TEXTURE;
	}
}