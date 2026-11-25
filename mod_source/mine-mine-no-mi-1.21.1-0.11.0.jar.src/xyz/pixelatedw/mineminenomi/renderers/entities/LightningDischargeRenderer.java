package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity$LightningPropieties;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;

public class LightningDischargeRenderer extends EntityRenderer<LightningDischargeEntity> {
	// Fields
	private List<LightningDischargeEntity$LightningPropieties> entities;

	protected LightningDischargeRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.entities = new ArrayList();
	}
	public void render(LightningDischargeEntity var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		if (!(Minecraft.getInstance().isPaused())){
			if (((var1.tickCount) % (var1.getUpdateRate())) == (0)){
				this.entities.clear();
			}
			int var7 = var1.getDensity();
			if (this.entities.isEmpty()){
				int var8 = 0;
				do{
					if ((var8) < (var7)){
						LightningDischargeEntity$LightningPropieties var9 = new LightningDischargeEntity$LightningPropieties((Entity)(var1),var1.getSeed(),(float)(WyHelper.randomWithRange(0,360)),(float)(WyHelper.randomWithRange(0,180)),var1.getLightningLength());
						this.entities.add(var9);
						var8++;
						continue;
					}else{
						break;
					}
				} while (true);
			}
		}
		Color var7_1 = new Color(var1.getColor());
		float var8_1 = ((float)(var1.getAlpha())) / (255F);
		Color var9_1 = new Color(var1.getOutlineColor());
		float var10 = ((float)(var1.getOutlineAlpha())) / (255F);
		VertexConsumer var11 = var5.getBuffer(this.getRenderType(var1));
		var4.pushPose();
		var4.scale((var1.getSize()) / (2F),(var1.getSize()) / (2F),(var1.getSize()) / (2F));
		LightningDischargeEntity var12 = var1;
		VertexConsumer var13 = var11;
		Color var14 = var7_1;
		float var15 = var8_1;
		Color var16 = var9_1;
		float var17 = var10;
		PoseStack var18 = var4;
		int var19 = var6;
		final LightningDischargeEntity var12_f1 = var12;
		final VertexConsumer var13_f2 = var13;
		final Color var14_f3 = var14;
		final float var15_f4 = var15;
		final Color var16_f5 = var16;
		final float var17_f6 = var17;
		final PoseStack var18_f7 = var18;
		final int var19_f8 = var19;
		Consumer<? super LightningDischargeEntity$LightningPropieties> var20 = (l0) -> {
			renderLightningDischarge(var12_f1,var13_f2,l0.random(),l0.length(),l0.xRot(),l0.yRot(),var14_f3,var15_f4,var16_f5,var17_f6,var18_f7,var19_f8);
		};
		((java.lang.Iterable)(this.entities)).forEach((Consumer)(var20));
		var4.popPose();
	}
	public static void renderLightningDischarge(LightningDischargeEntity var0, VertexConsumer var1, long var2, float var3, float var4, float var5, Color var6, float var7, Color var8, float var9, PoseStack var10, int var11) {
		float var12 = 1F;
		Random var13 = new Random(var2);
		int var14 = 90;
		int var15 = var0.getDetails();
		float var16 = Math.min((((float)(var0.tickCount)) + (var12)) / (2F),1F);
		float var17 = 0.01F;
		float var18 = (var3) * (var16);
		float var19 = (var18) / ((float)(var15));
		float[] var20 = new float[var15];
		int var21 = (int)(((float)(var15)) * (var16));
		int var22 = (((var0.getAliveTicks()) > (0)) ? (var0.getAliveTicks()) : (100)) / (2);
		Minecraft var23 = Minecraft.getInstance();
		float var24 = ((float)(var6.getRed())) / (255F);
		float var25 = ((float)(var6.getGreen())) / (255F);
		float var26 = ((float)(var6.getBlue())) / (255F);
		float var27 = ((float)(var8.getRed())) / (255F);
		float var28 = ((float)(var8.getGreen())) / (255F);
		float var29 = ((float)(var8.getBlue())) / (255F);
		float var30 = 1F;
		float var31 = 1F;
		int var32 = 0;
		do{
			if ((var32) < (var20.length)){
				var20[var32] = ((var32) == (var21)) ? ((var18) - ((var19) * ((float)(var32)))) : (var19);
				var32++;
				continue;
			}else{
				break;
			}
		} while (true);
		float[] var33 = new float[var15];
		float[] var34 = new float[var15];
		Matrix4f var35 = var10.last().pose();
		var10.mulPose(Axis.YN.rotationDegrees(var4));
		var10.mulPose(Axis.XP.rotationDegrees(var5));
		var17 = (float)(((double)(var17)) + ((WyHelper.randomDouble()) / (20D)));
		float var36 = 0F;
		float var37 = 0F;
		int var38 = 0;
		do{
			if ((var38) < (var15)){
				var33[var38] = var36;
				var34[var38] = var37;
				var36 = (float)(((double)(var36)) + (Math.sin(Math.toRadians((double)(((float)(var13.nextInt(var14))) - (((float)(var14)) / (2F)))))));
				var37 = (float)(((double)(var37)) + (Math.sin(Math.toRadians((double)(((float)(var13.nextInt(var14))) - (((float)(var14)) / (2F)))))));
				var38++;
				continue;
			}else{
				break;
			}
		} while (true);
		var38 = 0;
		do{
			if ((var38) < (var15)){
				if (((var0.isSplit()) && (((var38) % (WyHelper.randomWithRange(1,3))) == (0))) || ((var38) < (var0.getSkipSegmnets()))){

				}else{
					float var39 = var33[var38];
					float var40 = var34[var38];
					int var41 = 1;
					do{
						if ((var41) < (4)){
							float var42 = ((var17) / (2F)) + (((float)(var41)) * (var17));
							float var43 = ((var17) / (2F)) + (((float)(var41)) * (var17));
							float var44 = ((var38) == ((var15) - (1))) ? (var39) : (var33[(var38) + (1)]);
							float var45 = ((var38) == ((var15) - (1))) ? (var40) : (var34[(var38) + (1)]);
							if ((var38) <= (var21)){
								drawQuad(var35,var1,var39,var40,var38,var44,var45,var24,var25,var26,var30,var42,var43,false,false,true,false,var19,var20[var38],var11);
								drawQuad(var35,var1,var39,var40,var38,var44,var45,var24,var25,var26,var30,var42,var43,true,false,true,true,var19,var20[var38],var11);
								drawQuad(var35,var1,var39,var40,var38,var44,var45,var24,var25,var26,var30,var42,var43,true,true,false,true,var19,var20[var38],var11);
								drawQuad(var35,var1,var39,var40,var38,var44,var45,var24,var25,var26,var30,var42,var43,false,true,false,false,var19,var20[var38],var11);
							}
							if ((var0.getOutlineColor()) != (0)){
								var42 = ((var17) / (2F)) + (((float)(var41)) * ((var17) + (0.01F)));
								var43 = ((var17) / (2F)) + (((float)(var41)) * ((var17) + (0.01F)));
								if ((var38) <= (var21)){
									drawQuad(var35,var1,var39,var40,var38,var44,var45,var27,var28,var29,var31,var42,var43,false,false,true,false,var19,var20[var38],var11);
									drawQuad(var35,var1,var39,var40,var38,var44,var45,var27,var28,var29,var31,var42,var43,true,false,true,true,var19,var20[var38],var11);
									drawQuad(var35,var1,var39,var40,var38,var44,var45,var27,var28,var29,var31,var42,var43,true,true,false,true,var19,var20[var38],var11);
									drawQuad(var35,var1,var39,var40,var38,var44,var45,var27,var28,var29,var31,var42,var43,false,true,false,false,var19,var20[var38],var11);
									var41++;
									continue;
								}else{
									var41++;
									continue;
								}
							}else{
								var41++;
								continue;
							}
						}else{
							break;
						}
					} while (true);
				}
				var38++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	private static void drawQuad(Matrix4f var0, VertexConsumer var1, float var2, float var3, int var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, boolean var13, boolean var14, boolean var15, boolean var16, float var17, float var18, int var19) {
		float var20 = (var3) + ((var14) ? (var12) : (-(var12)));
		float var21 = (var2) + ((var13) ? (var12) : (-(var12)));
		float var22 = (var6) + ((var14) ? (var11) : (-(var11)));
		float var23 = (var5) + ((var13) ? (var11) : (-(var11)));
		float var24 = (var6) + ((var16) ? (var11) : (-(var11)));
		float var25 = (var5) + ((var15) ? (var11) : (-(var11)));
		float var26 = (var3) + ((var16) ? (var12) : (-(var12)));
		float var27 = (var2) + ((var15) ? (var12) : (-(var12)));
		float var28 = ((float)(var4)) * (var17);
		float var29 = (var28) + (var18);
		var1.addVertex(var0,var20,var21,var28).setColor(var7,var8,var9,var10).setLight(var19).setOverlay(OverlayTexture.NO_OVERLAY);
		var1.addVertex(var0,var22,var23,var29).setColor(var7,var8,var9,var10).setLight(var19).setOverlay(OverlayTexture.NO_OVERLAY);
		var1.addVertex(var0,var24,var25,var29).setColor(var7,var8,var9,var10).setLight(var19).setOverlay(OverlayTexture.NO_OVERLAY);
		var1.addVertex(var0,var26,var27,var28).setColor(var7,var8,var9,var10).setLight(var19).setOverlay(OverlayTexture.NO_OVERLAY);
	}
	public boolean shouldRender(LightningDischargeEntity var1, Frustum var2, double var3, double var4, double var5) {
		return true;
	}
	@OnlyIn(value=Dist.CLIENT)
	public RenderType getRenderType(LightningDischargeEntity var1) {
		switch (var1.getRenderMode()){
		case 1:

		case 2:

		default:
			return ModRenderTypes.ENERGY;
		}
	}
	public ResourceLocation getTextureLocation(LightningDischargeEntity var1) {
		return null;
	}
}