package xyz.pixelatedw.mineminenomi.handlers.ability;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.abilities.electro.EleclawAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalShowerAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalTempestaAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;

public class MinkPassivesHandler$Client {
	// Fields
	private static ArrayList<Long> lightningValues1;
	private static ArrayList<Long> lightningValues2;

	public static void renderElectroFirstPerson(Player var0, PoseStack var1, MultiBufferSource var2, float var3, float var4, float var5) {
		AbilityData var6 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var6) == (null)){
			return;
		}else{
			EleclawAbility var7 = ((EleclawAbility)(var6.getEquippedAbility(((AbilityCore)(EleclawAbility.INSTANCE.get())))));
			if (((((var7) != (null)) && (var7.isContinuous())) ? (1) : (0)) == (0)){
				return;
			}else{
				ElectricalTempestaAbility var8 = ((ElectricalTempestaAbility)(var6.getEquippedAbility(((AbilityCore)(ElectricalTempestaAbility.INSTANCE.get())))));
				int var9 = (((var8) != (null)) && (var8.isCharging())) ? (1) : (0);
				ElectricalShowerAbility var10 = ((ElectricalShowerAbility)(var6.getEquippedAbility(((AbilityCore)(ElectricalShowerAbility.INSTANCE.get())))));
				int var11 = (((var10) != (null)) && (var10.isCharging())) ? (1) : (0);
				if (((var9) == (0)) && ((var11) == (0))){
					int var12 = 5;
					int var13 = ((var0.getMainArm()) == (HumanoidArm.RIGHT)) ? (1) : (0);
					var1.pushPose();
					float var14 = ((var13) != (0)) ? (1F) : (-1F);
					float var15 = Mth.sqrt(var4);
					float var16 = (-0.3F) * (Mth.sin((var15) * (3.1415927F)));
					float var17 = (0.4F) * (Mth.sin((var15) * (6.2831855F)));
					var1.translate((var14) * ((var16) + (0.64000005F)),((var17) + (-0.6F)) + ((var5) * (-0.6F)),((-0.4F) * (Mth.sin((var4) * (3.1415927F)))) + (-0.71999997F));
					var1.mulPose(Axis.YP.rotationDegrees((var14) * (45F)));
					float var18 = Mth.sin(((var4) * (var4)) * (3.1415927F));
					float var19 = Mth.sin((var15) * (3.1415927F));
					var1.mulPose(Axis.YP.rotationDegrees(((var14) * (var19)) * (70F)));
					var1.mulPose(Axis.ZP.rotationDegrees(((var14) * (var18)) * (-20F)));
					var1.translate((var14) * (-1F),3.6F,3.5F);
					var1.mulPose(Axis.ZP.rotationDegrees((var14) * (120F)));
					var1.mulPose(Axis.XP.rotationDegrees(200F));
					var1.mulPose(Axis.YP.rotationDegrees((var14) * (-135F)));
					var1.translate((var14) * (5.6F),0F,0F);
					int var20 = 0;
					LOOP_1:
					do{
						if ((var20) < (var12)){
							int var21 = 1;
							do{
								if ((var21) < (5)){
									Color var22 = (((var20) == (1)) && (((Math.random()) > (0.6D)) && (!(Minecraft.getInstance().isPaused())))) ? (Color.WHITE) : (MinkPassivesHandler.MINK_LIGHTNING_COLOR);
									var1.pushPose();
									var1.scale(0.01F,0.035F,0.01F);
									var1.translate(((var13) != (0)) ? (-40F) : (40F),-24F,0F);
									drawMinkLightning(getRandomLightningLong(lightningValues2,(Entity)(var0),((Math.random()) > (0.5D)) ? (2) : (3),((var20) + (var21)) - (1)),var1,var2,4,6F,6,var22,255);
									var1.popPose();
									var21++;
									continue;
								}else{
									break;
								}
							} while (true);
							var20++;
							continue LOOP_1;
						}else{
							break;
						}
					} while (true);
					var1.popPose();
					return;
				}else{
					return;
				}
			}
		}
	}
	public static void renderElectro(PoseStack var0, ArmedModel var1, MultiBufferSource var2, LivingEntity var3, float var4, float var5, int var6) {
		int var7 = 0;
		LOOP_1:
		do{
			if ((var7) < (var6)){
				int var8 = 1;
				do{
					if ((var8) < (5)){
						var0.pushPose();
						Color var9 = (((var7) == (1)) && (((Math.random()) > (0.6D)) && (!(Minecraft.getInstance().isPaused())))) ? (Color.white) : ((Color)(WyHelper.hexToRGB("#71A3F0")));
						var1.translateToHand(var3.getMainArm(),var0);
						var0.scale((var5) * (0.8F),(var5) * (1.4F),(var5) * (0.75F));
						var0.translate((float)(((var3.getMainArm()) == (HumanoidArm.LEFT)) ? (1) : (-1)),0F,0F);
						drawMinkLightning(getRandomLightningLong(lightningValues1,(Entity)(var3),2,((var7) + (var8)) - (1)),var0,var2,3,6F,8,var9,255);
						var0.popPose();
						var8++;
						continue;
					}else{
						break;
					}
				} while (true);
				var7++;
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
	}
	public static long getRandomLightningLong(ArrayList<Long> var0, Entity var1, int var2, int var3) {
		int var4 = ((var0.size()) > (var3)) ? (1) : (0);
		if ((!(Minecraft.getInstance().isPaused())) && ((var1) != (null))){
			if (((var1.tickCount) % (var2)) == (0)){
				long var5 = var1.level().random.nextLong();
				if ((var4) != (0)){
					var0.set(var3,Long.valueOf(var5));
				}else{
					var0.add(Long.valueOf(var5));
				}
				return var5;
			}else{
				return ((var4) != (0)) ? (((Long)(var0.get(var3))).longValue()) : (0L);
			}
		}else{
			return ((var4) != (0)) ? (((Long)(var0.get(var3))).longValue()) : (0L);
		}
	}
	public static void drawMinkLightning(long var0, PoseStack var1, MultiBufferSource var2, int var3, float var4, int var5, Color var6, int var7) {
		drawMinkLightning(var0,var1,var2,var3,var4,var5,var6,var7,5);
	}
	public static void drawMinkLightning(long var0, PoseStack var1, MultiBufferSource var2, int var3, float var4, int var5, Color var6, int var7, int var8) {
		float[] var9 = new float[256];
		float[] var10 = new float[256];
		float var11 = 0F;
		float var12 = 0F;
		VertexConsumer var13 = var2.getBuffer(ModRenderTypes.ENERGY);
		Matrix4f var14 = var1.last().pose();
		Random var15 = new Random(var0);
		Random var16 = new Random(var0);
		var7 = Math.min(var7,255);
		int var17 = var7;
		do{
			if ((var17) >= (0)){
				var9[var17] = var11;
				var10[var17] = var12;
				var11 = (var11) + ((float)((var15.nextInt(11)) - (5)));
				var12 = (var12) + ((float)((var15.nextInt(11)) - (5)));
				var17--;
				continue;
			}else{
				break;
			}
		} while (true);
		var17 = 0;
		LOOP_2:
		do{
			if ((var17) < (3)){
				int var18 = 7;
				int var19 = 0;
				if ((var17) > (0)){
					var18 = (7) - (var17);
					var19 = (var18) - (2);
				}
				float var20 = (var9[var18]) - (var11);
				float var21 = (var10[var18]) - (var12);
				int var22 = var18;
				LOOP_1:
				do{
					if ((var22) >= (var19)){
						float var23 = var20;
						float var24 = var21;
						var20 = (var20) + ((float)((var16.nextInt(11)) - (5)));
						var21 = (var21) + ((float)((var16.nextInt(11)) - (5)));
						Color var25 = var6;
						int var26 = 1;
						do{
							if ((var26) <= (var8)){
								float var27 = (0.1F) + (((float)(var5)) * ((0.015F) * ((float)(var26))));
								var6 = ((Math.round(((float)(var8)) / (3F))) > (var26)) ? (new Color(255,255,255,var25.getAlpha())) : (var25);
								drawLightningQuad(var14,var13,var20,var21,var22,var4,var23,var24,var27,false,false,true,false,var3,var6);
								drawLightningQuad(var14,var13,var20,var21,var22,var4,var23,var24,var27,true,false,true,true,var3,var6);
								drawLightningQuad(var14,var13,var20,var21,var22,var4,var23,var24,var27,true,true,false,true,var3,var6);
								drawLightningQuad(var14,var13,var20,var21,var22,var4,var23,var24,var27,false,true,false,false,var3,var6);
								var26++;
								continue;
							}else{
								break;
							}
						} while (true);
						var22--;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				var17++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
	}
	private static void drawLightningQuad(Matrix4f var0, VertexConsumer var1, float var2, float var3, int var4, float var5, float var6, float var7, float var8, boolean var9, boolean var10, boolean var11, boolean var12, int var13, Color var14) {
		var1.addVertex(var0,(var2) + ((var9) ? (var8) : (-(var8))),((float)(var4)) * (var5),(var3) + ((var10) ? (var8) : (-(var8)))).setColor(((float)(var14.getRed())) / (255F),((float)(var14.getGreen())) / (255F),((float)(var14.getBlue())) / (255F),((float)(var14.getAlpha())) / (255F)).setLight(var13);
		var1.addVertex(var0,(var6) + ((var9) ? (var8) : (-(var8))),((float)((var4) + (1))) * (var5),(var7) + ((var10) ? (var8) : (-(var8)))).setColor(((float)(var14.getRed())) / (255F),((float)(var14.getGreen())) / (255F),((float)(var14.getBlue())) / (255F),((float)(var14.getAlpha())) / (255F)).setLight(var13);
		var1.addVertex(var0,(var6) + ((var11) ? (var8) : (-(var8))),((float)((var4) + (1))) * (var5),(var7) + ((var12) ? (var8) : (-(var8)))).setColor(((float)(var14.getRed())) / (255F),((float)(var14.getGreen())) / (255F),((float)(var14.getBlue())) / (255F),((float)(var14.getAlpha())) / (255F)).setLight(var13);
		var1.addVertex(var0,(var2) + ((var11) ? (var8) : (-(var8))),((float)(var4)) * (var5),(var3) + ((var12) ? (var8) : (-(var8)))).setColor(((float)(var14.getRed())) / (255F),((float)(var14.getGreen())) / (255F),((float)(var14.getBlue())) / (255F),((float)(var14.getAlpha())) / (255F)).setLight(var13);
	}
	static  {
		lightningValues1 = (ArrayList) (new ArrayList());
		lightningValues2 = (ArrayList) (new ArrayList());
	}
}