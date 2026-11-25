package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana.HanaHandsEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana.HanaHandsEntity$HandsKind;

public class HanaHandsRenderer<T extends HanaHandsEntity> extends EntityRenderer<T> {
	// Fields
	private EntityRenderer<? super Entity> ownerRenderer;
	private EntityRenderer<? super Entity> targetRenderer;

	HanaHandsRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.shadowRadius = 0F;
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		HanaHandsEntity$HandsKind var7 = var1.getHandsType();
		float var8 = var1.getAnimationProgress(var3);
		LivingEntity var9 = var1.getTarget();
		if (((var9) != (null)) && ((var1.getOwner()) != (null))){
			float var10 = 1F;
			if (((var8) > (0.5F)) && ((var8) < (0.6F))){
				var10 = (var10) + ((var8) / (2F));
			}else{
				if ((var8) > (0.6F)){
					var10 = (float)(((double)(var10)) * (((1D) - ((double)(var8))) / (0.4000000059604645D)));
				}
			}
			var4.pushPose();
			if ((this.ownerRenderer) == (null)){
				this.ownerRenderer = (EntityRenderer) (Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(var1.getOwner()));
			}
			if ((this.targetRenderer) == (null)){
				this.targetRenderer = (EntityRenderer) (Minecraft.getInstance().getEntityRenderDispatcher().getRenderer((Entity)(var9)));
			}
			if ((this.ownerRenderer) != (null)){
				EntityRenderer var11 = this.ownerRenderer;
				if (var11 instanceof LivingEntityRenderer){
					LivingEntityRenderer var12 = ((LivingEntityRenderer)(var11));
					ResourceLocation var13 = DefaultPlayerSkin.get(var1.getUUID()).texture();
					if ((var1.getOwner()) != (null)){
						Entity var14 = var1.getOwner();
						if (var14 instanceof AbstractClientPlayer){
							var13 = ((AbstractClientPlayer)(var14)).getSkin().texture();
						}
					}
					VertexConsumer var14_1 = var5.getBuffer(RenderType.entityCutout(var13));
					ModelPart var15 = null;
					float var16 = -1F;
					EntityRenderer var17 = this.targetRenderer;
					if (var17 instanceof LivingEntityRenderer){
						LivingEntityRenderer var18 = ((LivingEntityRenderer)(var17));
						EntityModel var19 = var18.getModel();
						if (var19 instanceof HumanoidModel){
							var15 = ((HumanoidModel)(var19)).body;
						}else{
							EntityModel var20 = var18.getModel();
							if (var20 instanceof HeadedModel){
								var15 = ((HeadedModel)(var20)).getHead();
							}
						}
					}else{
						var16 = (var1.getBbHeight()) / (8F);
					}
					if ((var15) != (null)){
						var15.translateAndRotate(var4);
					}else{
						var4.translate(0F,-(var16),0F);
					}
					List var18_1 = RendererHelper.getArmPartsFrom(var12.getModel());
					if (var18_1.isEmpty()){
						return;
					}else{
						float var19_1 = Mth.rotLerp(var3,var9.yBodyRotO,var9.yBodyRot);
						Iterator var20_1 = var18_1.iterator();
						do{
							if (var20_1.hasNext()){
								ModelPart var21 = ((ModelPart)(var20_1.next()));
								var4.pushPose();
								var21.resetPose();
								if ((var7) == (HanaHandsEntity$HandsKind.CLUTCH)){
									var4.scale(var10,var10,var10);
									var4.mulPose(Axis.ZN.rotationDegrees(90F));
									var4.mulPose(Axis.XN.rotationDegrees(-(var19_1)));
									var4.translate(-0.5D,-0.4D,0.05D);
									var21.render(var4,var14_1,var6,OverlayTexture.NO_OVERLAY);
									var4.translate(-0.5D,0D,0.05D);
									var21.render(var4,var14_1,var6,OverlayTexture.NO_OVERLAY);
								}else{
									if ((var7) == (HanaHandsEntity$HandsKind.SLAP)){
										var4.translate(0D,1D,0D);
										var4.mulPose(Axis.YP.rotationDegrees((-(var19_1)) + (90F)));
										var4.mulPose(Axis.XP.rotationDegrees(((90F) * (var10)) - (45F)));
										var4.translate(0.2D,0D,0D);
										var21.render(var4,var14_1,var6,OverlayTexture.NO_OVERLAY);
									}
								}
								var4.popPose();
								continue;
							}else{
								break;
							}
						} while (true);
					}
				}
			}
			var4.popPose();
			return;
		}else{
			return;
		}
	}
	public ResourceLocation getTextureLocation(HanaHandsEntity var1) {
		return null;
	}
}