package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Random;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class GlowingLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	// Fields
	private static final float DISTANCE;
	private float size;
	private int color;

	public GlowingLayer(RenderLayerParent<T, M> var1, float var2, int var3) {
		super(var1);
		this.size = 9.6F;
		this.size = var2;
		this.color = var3;
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		int var11 = (var4.getCommandSenderWorld().getBrightness(LightLayer.SKY,var4.blockPosition())) - (var4.getCommandSenderWorld().getSkyDarken());
		if ((var11) > (7)){
			float var12 = (float)((20) + ((var11) * (16)));
			float var13 = (((float)(var4.tickCount)) + (var7)) / (500F);
			Random var14 = new Random(500L);
			VertexConsumer var15 = var2.getBuffer(RenderType.lightning());
			Matrix4f var16 = var1.last().pose();
			int var17 = ((this.color) >> (16)) & (255);
			int var18 = ((this.color) >> (8)) & (255);
			int var19 = (this.color) & (255);
			int var20 = (8) + ((var11) / (2));
			int var21 = Math.round(((float)(var17)) * (0.8F));
			int var22 = Math.round(((float)(var18)) * (0.8F));
			int var23 = Math.round(((float)(var19)) * (0.8F));
			int var24 = Math.round(((float)(var20)) * (0.4F));
			int var25 = 0;
			do{
				if (((float)(var25)) < (var12)){
					var1.mulPose(Axis.XP.rotationDegrees((var14.nextFloat()) * (360F)));
					var1.mulPose(Axis.YP.rotationDegrees((var14.nextFloat()) * (360F)));
					var1.mulPose(Axis.ZP.rotationDegrees((var14.nextFloat()) * (360F)));
					var1.mulPose(Axis.XP.rotationDegrees((var14.nextFloat()) * (360F)));
					var1.mulPose(Axis.YP.rotationDegrees((var14.nextFloat()) * (360F)));
					var1.mulPose(Axis.ZP.rotationDegrees(((var14.nextFloat()) * (360F)) + ((var13) * (90F))));
					float var26 = (this.size) * (var14.nextFloat());
					float var27 = (this.size) * (var14.nextFloat());
					drawA(var15,var16,var17,var18,var19,var24);
					drawB(var15,var16,var26,var27,var21,var22,var23,var24);
					drawC(var15,var16,var26,var27,var17,var18,var19,var20);
					drawA(var15,var16,var17,var18,var19,var24);
					drawC(var15,var16,var26,var27,var21,var22,var23,var24);
					drawD(var15,var16,var26,var27,var17,var18,var19,var20);
					drawA(var15,var16,var17,var18,var19,var24);
					drawD(var15,var16,var26,var27,var17,var18,var19,var20);
					drawB(var15,var16,var26,var27,var21,var22,var23,var24);
					var25++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	private static void drawA(VertexConsumer var0, Matrix4f var1, int var2, int var3, int var4, int var5) {
		var0.addVertex(var1,0F,0F,0F).setColor(var2,var3,var4,var5);
		var0.addVertex(var1,0F,0F,0F).setColor(var2,var3,var4,var5);
	}
	private static void drawB(VertexConsumer var0, Matrix4f var1, float var2, float var3, int var4, int var5, int var6, int var7) {
		var0.addVertex(var1,(-(DISTANCE)) * (var3),var2,(-0.5F) * (var3)).setColor(var4,var5,var6,var7);
	}
	private static void drawC(VertexConsumer var0, Matrix4f var1, float var2, float var3, int var4, int var5, int var6, int var7) {
		var0.addVertex(var1,(DISTANCE) * (var3),var2,(-0.5F) * (var3)).setColor(var4,var5,var6,var7);
	}
	private static void drawD(VertexConsumer var0, Matrix4f var1, float var2, float var3, int var4, int var5, int var6, int var7) {
		var0.addVertex(var1,0F,var2,var3).setColor(var4,var5,var6,var7);
	}
	static  {
		DISTANCE = (float)((Math.sqrt(3D)) / (2D));
	}
}