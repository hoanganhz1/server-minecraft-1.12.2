package xyz.pixelatedw.mineminenomi.api.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.awt.Color;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;

public class NuLightningEntityRenderer extends EntityRenderer<NuLightningEntity> {
	// Fields
	private static final int MAX_DEPTH = 8;
	private static final float U_ARM_SIDE_MIN = 0.65F;
	private static final float U_ARM_SIDE_MAX = 0.68F;
	private static final float V_ARM_SIDE_MIN = 0.317F;
	private static final float V_ARM_SIDE_MAX = 0.5F;
	private static final float V_ARM_SIDE_DIFF = 0.183F;
	private static final float U_ARM_BACK_CAP_MIN = 0.687F;
	private static final float U_ARM_BACK_CAP_MAX = 0.718F;
	private static final float U_ARM_FRONT_CAP_MIN = 0.734F;
	private static final float U_ARM_FRONT_CAP_MAX = 0.781F;
	private static final float V_ARM_CAP_MIN = 0.25F;
	private static final float V_ARM_CAP_MAX = 0.312F;
	private static final float V_ARM_CAP_DIFF = 0.062000006F;
	 ResourceLocation texture;
	 boolean useArmSkin;
	 int renderMode;
	 int color;
	 Supplier<Color> calcColor;

	protected NuLightningEntityRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.useArmSkin = false;
		this.renderMode = 0;
		this.color = -1;
	}
	public void render(NuLightningEntity var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		var4.pushPose();
		this.renderLightning(var1,var3,var4,var5,var6);
		var4.popPose();
	}
	public void renderLightning(NuLightningEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5) {
		if (((var1.tickCount) >= (1)) && ((var1.getSegments()) >= (0))){
			var1.getRandom().setSeed(var1.seed);
			int var6 = var1.getDepth();
			int var7 = var1.getAngle();
			int var8 = var1.getSegments();
			if ((var8) == (0)){
				return;
			}else{
				int var9 = var1.getBranches();
				float var10 = Math.min((((float)(var1.tickCount)) + (var2)) / (2F),1F);
				float var11 = (var1.getLength()) * (var10);
				float var12 = var1.getSize();
				float var13 = (var1.getLength()) / ((float)(var8));
				float var14 = var1.getRotation();
				float[] var15 = new float[var8];
				int var16 = (int)(((float)(var8)) * (var10));
				float var17 = WyHelper.unpackColorAlpha(this.color);
				if ((var1.getAlpha()) > (0)){
					var17 = ((float)(var1.getAlpha())) / (255F);
				}
				float var18 = ((float)(var1.getFadeTime())) / ((float)(var1.getStartFadeTime()));
				float var19 = var17;
				if ((var1.getFadeTime()) >= (0)){
					var19 = Math.min(var18,var19);
				}
				Minecraft var20 = Minecraft.getInstance();
				if (((((var20.options.getCameraType()) == (CameraType.FIRST_PERSON)) ? (1) : (0)) != (0)) && (((var1.getOwner()) != (null)) && (((var1.getOwner()) == (var20.player)) && (var1.position().closerThan((Position)(var20.player.getEyePosition()),(double)(((8F) * (var12)) * (2F))))))){
					var19 = (var19) * (0.2F);
				}
				int var21 = var1.getSegmentLength();
				int var22 = 0;
				do{
					if ((var22) < (var8)){
						if ((var21) <= (0)){
							var15[var22] = ((var22) == (var16)) ? ((var11) - ((var13) * ((float)(var22)))) : (var13);
						}else{
							var15[var22] = (float)(var21);
						}
						var22++;
						continue;
					}else{
						break;
					}
				} while (true);
				float[] var23 = new float[var8];
				float[] var24 = new float[var8];
				float[] var25 = new float[var8];
				if ((var1.isTrailFading()) && ((var1.getStartFadeTime()) > (0))){
					int var26 = 0;
					do{
						if ((var26) < (var8)){
							float var27 = (float)((var26) + (var1.getStartFadeTime()));
							float var28 = (1F) - ((Math.min((float)(var1.tickCount),var27)) / (var27));
							if ((!(Float.isNaN(var28))) && (!(Float.isInfinite(var28)))){

							}else{
								var28 = 0F;
							}
							var25[var26] = (var19) * (var28);
							var26++;
							continue;
						}else{
							break;
						}
					} while (true);
				}else{
					Arrays.fill(var25,var19);
				}
				VertexConsumer var26_1 = var4.getBuffer(this.getRenderType(var1));
				Matrix4f var27_1 = var3.last().pose();
				var3.mulPose(Axis.YN.rotationDegrees(var1.getYRot()));
				var3.mulPose(Axis.XP.rotationDegrees(var1.getXRot()));
				var3.mulPose(Axis.ZP.rotationDegrees((float)((((double)(var1.tickCount)) * (3.141592653589793D)) * ((double)(var14)))));
				int var28_1 = this.color;
				int var29 = Math.round((WyHelper.unpackColorRed(var28_1)) * (255F));
				int var30 = Math.round((WyHelper.unpackColorGreen(var28_1)) * (255F));
				int var31 = Math.round((WyHelper.unpackColorBlue(var28_1)) * (255F));
				if ((this.calcColor) != (null)){
					Color var32 = ((Color)(this.calcColor.get()));
					var29 = var32.getRed();
					var30 = var32.getGreen();
					var31 = var32.getBlue();
				}
				int var32_1 = (((var1.getRed()) + (var1.getGreen())) + (var1.getBlue())) / (3);
				if (((var32_1) > (0)) && ((var32_1) < (255))){
					var29 = var1.getRed();
					var30 = var1.getGreen();
					var31 = var1.getBlue();
				}
				Color var33 = WyHelper.intToRGB(HakiHelper.getHaoshokuColour(var1.getOwner()),102);
				int var34 = 0;
				LOOP_1:
				do{
					if ((var34) < (var9)){
						float var35 = 0F;
						float var36 = 0F;
						int var37 = 0;
						do{
							if ((var37) < (var8)){
								var24[var37] = var35;
								var23[var37] = var36;
								if (((var8) > (1)) && ((var7) > (0))){
									var35 = (float)(((double)(var35)) + (Math.sin(Math.toRadians((double)(((float)(WyHelper.randomWithRange(var1.getRandom(),-(var7),var7))) / (2F))))));
									var36 = (float)(((double)(var36)) + (Math.sin(Math.toRadians((double)(((float)(WyHelper.randomWithRange(var1.getRandom(),-(var7),var7))) / (2F))))));
								}
								var37++;
								continue;
							}else{
								break;
							}
						} while (true);
						var37 = 0;
						do{
							if ((var37) < (var8)){
								float var38 = var24[var37];
								float var39 = var23[var37];
								if ((var1.isTrailFading()) && ((var25[var37]) <= (0F))){

								}else{
									int var40 = var6;
									do{
										if ((var40) > (0)){
											float var41 = ((float)((8) - (var40))) * (var12);
											float var42 = ((var37) == ((var8) - (1))) ? (var38) : (var24[(var37) + (1)]);
											float var43 = ((var37) == ((var8) - (1))) ? (var39) : (var23[(var37) + (1)]);
											if ((var37) <= (var16)){
												this.drawSides(var27_1,var26_1,var38,var39,var37,var8,var42,var43,var29,var30,var31,var25[var37],var41,var41,false,false,true,false,var13,var15[var37],var5);
												this.drawSides(var27_1,var26_1,var38,var39,var37,var8,var42,var43,var29,var30,var31,var25[var37],var41,var41,true,false,true,true,var13,var15[var37],var5);
												this.drawSides(var27_1,var26_1,var38,var39,var37,var8,var42,var43,var29,var30,var31,var25[var37],var41,var41,true,true,false,true,var13,var15[var37],var5);
												this.drawSides(var27_1,var26_1,var38,var39,var37,var8,var42,var43,var29,var30,var31,var25[var37],var41,var41,false,true,false,false,var13,var15[var37],var5);
												if (((var40) == (var6)) && (((this.renderMode) == (2)) && (this.shouldApplyAdvancedHakiOverlay(var1)))){
													var3.pushPose();
													float var44 = (var41) + (0.02F);
													this.drawSides(var27_1,var26_1,var38,var39,var37,var8,var42,var43,var33.getRed(),var33.getGreen(),var33.getBlue(),0.4F,var44,var44,false,false,true,false,var13,var15[var37],var5);
													this.drawSides(var27_1,var26_1,var38,var39,var37,var8,var42,var43,var33.getRed(),var33.getGreen(),var33.getBlue(),0.4F,var44,var44,true,false,true,true,var13,var15[var37],var5);
													this.drawSides(var27_1,var26_1,var38,var39,var37,var8,var42,var43,var33.getRed(),var33.getGreen(),var33.getBlue(),0.4F,var44,var44,true,true,false,true,var13,var15[var37],var5);
													this.drawSides(var27_1,var26_1,var38,var39,var37,var8,var42,var43,var33.getRed(),var33.getGreen(),var33.getBlue(),0.4F,var44,var44,false,true,false,false,var13,var15[var37],var5);
													var3.popPose();
													var40--;
													continue;
												}else{
													var40--;
													continue;
												}
											}else{
												var40--;
												continue;
											}
										}else{
											break;
										}
									} while (true);
								}
								var37++;
								continue;
							}else{
								break;
							}
						} while (true);
						var34++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				this.drawCaps(var27_1,var26_1,var23[0],var24[0],var12,var11,(float)(var29),(float)(var30),(float)(var31),var19,var5);
				return;
			}
		}else{
			return;
		}
	}
	private void drawSides(Matrix4f var1, VertexConsumer var2, float var3, float var4, int var5, int var6, float var7, float var8, int var9, int var10, int var11, float var12, float var13, float var14, boolean var15, boolean var16, boolean var17, boolean var18, float var19, float var20, int var21) {
		float var22 = ((float)(var9)) / (255F);
		float var23 = ((float)(var10)) / (255F);
		float var24 = ((float)(var11)) / (255F);
		var12 = Mth.clamp(var12,0F,1F);
		float var25 = (var4) + ((var16) ? (var14) : (-(var14)));
		float var26 = (var3) + ((var15) ? (var14) : (-(var14)));
		float var27 = (var8) + ((var16) ? (var13) : (-(var13)));
		float var28 = (var7) + ((var15) ? (var13) : (-(var13)));
		float var29 = (var8) + ((var18) ? (var13) : (-(var13)));
		float var30 = (var7) + ((var17) ? (var13) : (-(var13)));
		float var31 = (var4) + ((var18) ? (var14) : (-(var14)));
		float var32 = (var3) + ((var17) ? (var14) : (-(var14)));
		float var33 = ((float)(var5)) * (var19);
		float var34 = (var33) + (var20);
		float var35 = ((float)(var5)) / ((float)(var6));
		float var36 = 0F;
		float var37 = 0F;
		float var38 = 1F;
		float var39 = 1F;
		if (this.useArmSkin){
			var36 = 0.65F;
			var37 = (0.317F) + ((var35) * (0.183F));
			var38 = 0.68F;
			var39 = Math.min(0.5F,(var37) + ((var35) * (0.183F)));
		}
		var2.addVertex(var1,var25,var26,var33).setColor(var22,var23,var24,var12).setUv(var36,var37).setOverlay(OverlayTexture.NO_OVERLAY).setLight(var21);
		var2.addVertex(var1,var27,var28,var34).setColor(var22,var23,var24,var12).setUv(var36,var39).setOverlay(OverlayTexture.NO_OVERLAY).setLight(var21);
		var2.addVertex(var1,var29,var30,var34).setColor(var22,var23,var24,var12).setUv(var38,var39).setOverlay(OverlayTexture.NO_OVERLAY).setLight(var21);
		var2.addVertex(var1,var31,var32,var33).setColor(var22,var23,var24,var12).setUv(var38,var37).setOverlay(OverlayTexture.NO_OVERLAY).setLight(var21);
	}
	private void drawCaps(Matrix4f var1, VertexConsumer var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, int var11) {
		if ((this.renderMode) != (2)){
			return;
		}else{
			int var12 = 7;
			float var13 = (var3) - (((float)(var12)) * (var5));
			float var14 = (var4) - (((float)(var12)) * (var5));
			float var15 = (var13) + ((((float)(var12)) * (var5)) * (2F));
			float var16 = (var14) + ((((float)(var12)) * (var5)) * (2F));
			float var17 = 0F;
			float var18 = 0F;
			float var19 = 1F;
			float var20 = 1F;
			if (this.useArmSkin){
				var17 = 0.687F;
				var18 = 0.312F;
				var19 = 0.718F;
				var20 = Math.min(0.312F,(var18) + (0.062000006F));
			}
			var2.addVertex(var1,var13,var14,0F).setColor(var7,var8,var9,var10).setUv(var17,var18).setOverlay(OverlayTexture.NO_OVERLAY).setLight(var11);
			var2.addVertex(var1,var13,var16,0F).setColor(var7,var8,var9,var10).setUv(var17,var20).setOverlay(OverlayTexture.NO_OVERLAY).setLight(var11);
			var2.addVertex(var1,var15,var16,0F).setColor(var7,var8,var9,var10).setUv(var19,var20).setOverlay(OverlayTexture.NO_OVERLAY).setLight(var11);
			var2.addVertex(var1,var15,var14,0F).setColor(var7,var8,var9,var10).setUv(var19,var18).setOverlay(OverlayTexture.NO_OVERLAY).setLight(var11);
			if (this.useArmSkin){
				var17 = 0.734F;
				var18 = 0.312F;
				var19 = 0.781F;
				var20 = Math.min(0.312F,(var18) + (0.062000006F));
			}
			var2.addVertex(var1,var13,var14,var6).setColor(var7,var8,var9,var10).setUv(var17,var18).setOverlay(OverlayTexture.NO_OVERLAY).setLight(var11);
			var2.addVertex(var1,var13,var16,var6).setColor(var7,var8,var9,var10).setUv(var17,var20).setOverlay(OverlayTexture.NO_OVERLAY).setLight(var11);
			var2.addVertex(var1,var15,var16,var6).setColor(var7,var8,var9,var10).setUv(var19,var20).setOverlay(OverlayTexture.NO_OVERLAY).setLight(var11);
			var2.addVertex(var1,var15,var14,var6).setColor(var7,var8,var9,var10).setUv(var19,var18).setOverlay(OverlayTexture.NO_OVERLAY).setLight(var11);
			return;
		}
	}
	private boolean shouldApplyBasicHakiOverlay(NuLightningEntity var1) {
		int var2 = ((var1.isAffectedByHardening()) && (HakiHelper.hasHardeningActive(var1.getOwner(),false,false))) ? (1) : (0);
		int var3 = ((var1.isAffectedByImbuing()) && (HakiHelper.isProjectileBusoshokuImbued((Projectile)(var1)))) ? (1) : (0);
		return ((var2) != (0)) || ((var3) != (0));
	}
	private boolean shouldApplyAdvancedHakiOverlay(NuLightningEntity var1) {
		int var2 = ((var1.isAffectedByHardening()) && (HakiHelper.hasAdvancedBusoActive(var1.getOwner()))) ? (1) : (0);
		int var3 = ((var1.isAffectedByImbuing()) && (HakiHelper.isProjectileBusoshokuShrouded((Projectile)(var1)))) ? (1) : (0);
		return ((var2) != (0)) || ((var3) != (0));
	}
	@OnlyIn(value=Dist.CLIENT)
	public RenderType getRenderType(NuLightningEntity var1) {
		switch (this.renderMode){
		case 1:

		case 2:
			if (this.shouldApplyBasicHakiOverlay(var1)){

			}
		default:
			return ModRenderTypes.ENERGY;
		}
	}
	public ResourceLocation getTextureLocation(NuLightningEntity var1) {
		if (this.useArmSkin){
			if ((var1.getOwner()) != (null)){
				LivingEntity var2 = var1.getOwner();
				if (var2 instanceof AbstractClientPlayer){
					return ((AbstractClientPlayer)(var2)).getSkin().texture();
				}
			}
			return DefaultPlayerSkin.get(var1.getUUID()).texture();
		}else{
			return this.texture;
		}
	}
	public void setTexture(ResourceLocation var1) {
		this.texture = var1;
		this.renderMode = 2;
	}
	public void setUseArmSkin() {
		this.useArmSkin = true;
		this.renderMode = 2;
	}
	public void setRenderMode(int var1) {
		this.renderMode = var1;
	}
	public void setColor(int var1) {
		this.color = var1;
	}
	public void setCalculatedColor(Supplier<Color> var1) {
		this.calcColor = var1;
	}
}