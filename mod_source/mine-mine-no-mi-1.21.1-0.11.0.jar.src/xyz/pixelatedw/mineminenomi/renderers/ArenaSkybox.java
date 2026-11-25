package xyz.pixelatedw.mineminenomi.renderers;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.awt.Color;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

@OnlyIn(value=Dist.CLIENT)
public class ArenaSkybox {
	// Fields
	private final Minecraft minecraft;
	private float time;
	private int seed;
	private Color color;
	private ResourceLocation[] textures;
	private int animationSpeed;
	private int detailLevel;
	private float radius;
	private boolean isFullWrapping;
	private boolean isGlobal;
	private long lastTick;
	private int textureId;

	public ArenaSkybox() {
		this.color = Color.WHITE;
		ResourceLocation[] var1 = new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/skyboxes/default.png")};
		this.textures = var1;
		this.animationSpeed = 10000;
		this.detailLevel = 8;
		this.radius = 1F;
		this.textureId = 0;
		this.minecraft = Minecraft.getInstance();
	}
	public ArenaSkybox setTexture(boolean var1, ResourceLocation... var2) {
		this.textures = var2;
		this.textureId = 0;
		this.isFullWrapping = var1;
		return this;
	}
	public ArenaSkybox setAnimationSpeed(int var1) {
		this.animationSpeed = var1;
		return this;
	}
	public ArenaSkybox setColor(Color var1) {
		this.color = var1;
		return this;
	}
	public ArenaSkybox setAlpha(float var1) {
		this.color = WyHelper.intToRGB(this.color.getRGB(),((int)(var1)) * (255));
		return this;
	}
	public ArenaSkybox setRadius(float var1) {
		this.radius = var1;
		return this;
	}
	public ArenaSkybox setDetailLevel(int var1) {
		this.detailLevel = var1;
		return this;
	}
	public ArenaSkybox setGlobal() {
		this.isGlobal = true;
		return this;
	}
	public void renderSphere(PoseStack var1) {
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		RenderSystem.enableDepthTest();
		this.renderActualSphere(var1,0F,0F,0F,true);
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(true);
		RenderSystem.disableBlend();
	}
	public void renderSphereInWorld(PoseStack var1, Camera var2, double var3, double var4, double var5) {
		double var6 = var2.getPosition().x;
		double var7 = var2.getPosition().y;
		double var8 = var2.getPosition().z;
		int var9 = 0;
		if ((((((var6) - (var3)) * ((var6) - (var3))) + (((var8) - (var5)) * ((var8) - (var5)))) + (((var7) - (var4)) * ((var7) - (var4)))) < ((double)((this.radius) * (this.radius)))){
			var9 = 1;
		}
		double var10 = 0.0;
		double var11 = 0.0;
		double var12 = 0.0;
		if (this.isGlobal){
			var10 = ((((double)(-(this.radius))) - (var6)) + (var3)) + ((double)(this.radius));
			var11 = ((((double)(-(this.radius))) - (var7)) + (var4)) + ((double)(this.radius));
			var12 = ((((double)(-(this.radius))) - (var8)) + (var5)) + ((double)(this.radius));
		}else{
			var10 = 0D;
			var11 = 0D;
			var12 = 0D;
		}
		if (Minecraft.useShaderTransparency()){
			Minecraft.getInstance().levelRenderer.getItemEntityTarget().bindWrite(false);
		}
		RenderSystem.enableBlend();
		RenderSystem.enableDepthTest();
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,GlStateManager.SourceFactor.ONE,GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.depthMask(Minecraft.useShaderTransparency());
		var1.pushPose();
		if ((var9) == (0)){
			if (Minecraft.useShaderTransparency()){
				RenderSystem.disableCull();
				RenderSystem.depthMask(false);
			}else{
				RenderSystem.disableCull();
			}
		}
		this.renderActualSphere(var1,(float)(var10),(float)(var11),(float)(var12),(var9) != (0));
		if ((var9) == (0)){
			RenderSystem.enableCull();
		}
		RenderSystem.disableBlend();
		var1.popPose();
		RenderSystem.depthMask(true);
		if (Minecraft.useShaderTransparency()){
			Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
		}
	}
	private void renderActualSphere(PoseStack var1, float var2, float var3, float var4, boolean var5) {
		Tesselator var6 = Tesselator.getInstance();
		if ((this.textures.length) > (1)){
			long var7 = Util.getMillis();
			if ((var7) > ((this.lastTick) + (100L))){
				this.lastTick = var7;
				this.textureId = (this.textureId) + (1);
				this.textureId = (this.textureId) % (this.textures.length);
			}
		}
		RenderSystem.setShader((Supplier)(((Supplier<ShaderInstance>)(GameRenderer::getPositionColorTexLightmapShader))));
		RenderSystem.setShaderTexture(0,this.textures[this.textureId]);
		BufferBuilder var7_1 = var6.begin(VertexFormat.Mode.TRIANGLES,DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
		float var8 = 0F;
		float var9 = 0F;
		float var10 = 6.2831855F;
		float var11 = 3.1415927F;
		float var12 = ((var10) - (var8)) / ((float)(this.detailLevel));
		float var13 = ((var11) - (var9)) / ((float)(this.detailLevel));
		float var14 = ((float)(((Util.getMillis()) + ((long)(this.seed))) % ((long)(this.animationSpeed)))) / ((float)(this.animationSpeed));
		float var15 = ((float)((Util.getMillis()) % ((long)(this.animationSpeed)))) / ((float)(this.animationSpeed));
		float var16 = (var14) + (1F);
		int var18 = this.color.getRed();
		int var19 = this.color.getGreen();
		int var20 = this.color.getBlue();
		int var21 = this.color.getAlpha();
		Matrix4f var22 = var1.last().pose();
		int var23 = 0;
		LOOP_1:
		do{
			if ((var23) < (this.detailLevel)){
				int var24 = 0;
				do{
					if ((var24) < (this.detailLevel)){
						float var25 = (((float)(var23)) * (var12)) + (var8);
						float var26 = (((float)(var24)) * (var13)) + (var9);
						float var27 = (((var23) + (1)) == (this.detailLevel)) ? (var10) : ((((float)((var23) + (1))) * (var12)) + (var8));
						float var28 = (((var24) + (1)) == (this.detailLevel)) ? (var11) : ((((float)((var24) + (1))) * (var13)) + (var9));
						Vector3f var29 = this.parametricSphere(var25,var26,this.radius);
						Vector3f var30 = this.parametricSphere(var25,var28,this.radius);
						Vector3f var31 = this.parametricSphere(var27,var26,this.radius);
						Vector3f var32 = this.parametricSphere(var27,var28,this.radius);
						float var33 = ((var25) / (var10)) * ((this.isFullWrapping) ? (1F) : (this.radius));
						float var34 = ((var26) / (var11)) * ((this.isFullWrapping) ? (1F) : (this.radius));
						float var35 = ((var27) / (var10)) * ((this.isFullWrapping) ? (1F) : (this.radius));
						float var36 = ((var28) / (var11)) * ((this.isFullWrapping) ? (1F) : (this.radius));
						var33 = (var33) + (var16);
						var35 = (var35) + (var16);
						if (var5){
							this.vertexPosColorUVLight((VertexConsumer)(var7_1),var22,(var2) - (var29.x()),(var3) - (var29.y()),(var4) - (var29.z()),var18,var19,var20,var21,var33,var34,1);
							this.vertexPosColorUVLight((VertexConsumer)(var7_1),var22,(var2) - (var31.x()),(var3) - (var31.y()),(var4) - (var31.z()),var18,var19,var20,var21,var35,var34,1);
							this.vertexPosColorUVLight((VertexConsumer)(var7_1),var22,(var2) - (var30.x()),(var3) - (var30.y()),(var4) - (var30.z()),var18,var19,var20,var21,var33,var36,1);
							this.vertexPosColorUVLight((VertexConsumer)(var7_1),var22,(var2) - (var32.x()),(var3) - (var32.y()),(var4) - (var32.z()),var18,var19,var20,var21,var35,var36,1);
							this.vertexPosColorUVLight((VertexConsumer)(var7_1),var22,(var2) - (var30.x()),(var3) - (var30.y()),(var4) - (var30.z()),var18,var19,var20,var21,var33,var36,1);
							this.vertexPosColorUVLight((VertexConsumer)(var7_1),var22,(var2) - (var31.x()),(var3) - (var31.y()),(var4) - (var31.z()),var18,var19,var20,var21,var35,var34,1);
						}else{
							this.vertexPosColorUVLight((VertexConsumer)(var7_1),var22,(var2) + (var29.x()),(var3) + (var29.y()),(var4) + (var29.z()),var18,var19,var20,var21,var33,var34,1);
							this.vertexPosColorUVLight((VertexConsumer)(var7_1),var22,(var2) + (var31.x()),(var3) + (var31.y()),(var4) + (var31.z()),var18,var19,var20,var21,var35,var34,1);
							this.vertexPosColorUVLight((VertexConsumer)(var7_1),var22,(var2) + (var30.x()),(var3) + (var30.y()),(var4) + (var30.z()),var18,var19,var20,var21,var33,var36,1);
							this.vertexPosColorUVLight((VertexConsumer)(var7_1),var22,(var2) + (var32.x()),(var3) + (var32.y()),(var4) + (var32.z()),var18,var19,var20,var21,var35,var36,1);
							this.vertexPosColorUVLight((VertexConsumer)(var7_1),var22,(var2) + (var30.x()),(var3) + (var30.y()),(var4) + (var30.z()),var18,var19,var20,var21,var33,var36,1);
							this.vertexPosColorUVLight((VertexConsumer)(var7_1),var22,(var2) + (var31.x()),(var3) + (var31.y()),(var4) + (var31.z()),var18,var19,var20,var21,var35,var34,1);
						}
						var24++;
						continue;
					}else{
						break;
					}
				} while (true);
				var23++;
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
		BufferUploader.drawWithShader(var7_1.buildOrThrow());
	}
	private Vector3f parametricSphere(float var1, float var2, float var3) {
		return new Vector3f(((Mth.cos(var1)) * (Mth.sin(var2))) * (var3),(Mth.cos(var2)) * (var3),((Mth.sin(var1)) * (Mth.sin(var2))) * (var3));
	}
	private void vertexPosColorUVLight(VertexConsumer var1, Matrix4f var2, float var3, float var4, float var5, int var6, int var7, int var8, int var9, float var10, float var11, int var12) {
		var1.addVertex(var2,var3,var4,var5).setColor(var6,var7,var8,var9).setUv(var10,var11).setLight(var12);
	}
}