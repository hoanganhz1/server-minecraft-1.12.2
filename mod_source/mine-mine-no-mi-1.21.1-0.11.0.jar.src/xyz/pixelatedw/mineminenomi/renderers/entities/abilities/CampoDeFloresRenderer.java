package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana.CampoDeFloresEntity;

public class CampoDeFloresRenderer<T extends CampoDeFloresEntity> extends EntityRenderer<T> {
	// Fields
	private EntityRenderer<? super Entity> ownerRenderer;

	CampoDeFloresRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.shadowRadius = 0F;
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		if ((var1.getOwner()) == (null)){
			return;
		}else{
			if ((this.ownerRenderer) == (null)){
				this.ownerRenderer = (EntityRenderer) (Minecraft.getInstance().getEntityRenderDispatcher().getRenderer((Entity)(var1.getOwner())));
			}
			if ((this.ownerRenderer) != (null)){
				EntityRenderer var7 = this.ownerRenderer;
				if (var7 instanceof LivingEntityRenderer){
					List var8 = RendererHelper.getArmPartsFrom(((LivingEntityRenderer)(var7)).getModel());
					ResourceLocation var9 = DefaultPlayerSkin.get(var1.getUUID()).texture();
					if ((var1.getOwner()) != (null)){
						LivingEntity var10 = var1.getOwner();
						if (var10 instanceof AbstractClientPlayer){
							var9 = ((AbstractClientPlayer)(var10)).getSkin().texture();
						}
					}
					VertexConsumer var10_1 = var5.getBuffer(RenderType.entityCutout(var9));
					var4.pushPose();
					var4.translate(0D,-0.25D,0D);
					Random var11 = new Random();
					var11.setSeed(1L);
					int var12 = 10;
					int var13 = (var12) - (1);
					int var14 = -(var13);
					LOOP_1:
					do{
						if ((var14) < (var12)){
							int var15 = -(var13);
							do{
								if ((var15) < (var12)){
									if (((double)(((var14) * (var14)) + ((var15) * (var15)))) > (Math.pow((double)(var12),2D))){

									}else{
										float var16 = (0.2F) + ((var11.nextFloat()) / (10F));
										float var17 = (0.3F) + ((var11.nextFloat()) / (10F));
										float var18 = ((float)(Math.sin((((double)(var1.tickCount)) * (3.141592653589793D)) * ((double)(var17))))) * (var16);
										var4.pushPose();
										var4.translate(((float)(var14)) / (1F),var18,((float)(var15)) / (1F));
										Iterator var19 = var8.iterator();
										do{
											if (var19.hasNext()){
												ModelPart var20 = ((ModelPart)(var19.next()));
												var20.resetPose();
												var20.render(var4,var10_1,var6,OverlayTexture.NO_OVERLAY);
												continue;
											}else{
												break;
											}
										} while (true);
										var4.popPose();
									}
									var15++;
									continue;
								}else{
									break;
								}
							} while (true);
							var14++;
							continue LOOP_1;
						}else{
							break;
						}
					} while (true);
					var4.popPose();
				}else{
					return;
				}
			}
			return;
		}
	}
	public ResourceLocation getTextureLocation(CampoDeFloresEntity var1) {
		return null;
	}
}