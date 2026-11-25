package xyz.pixelatedw.mineminenomi.api.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class TexturedRectUI {
	// Fields
	private final ResourceLocation texture;
	private float zLevel;
	private float u;
	private float v;
	private float width;
	private float height;
	private float uScale;
	private float vScale;
	private float red;
	private float green;
	private float blue;
	private float alpha;
	private boolean flipX;
	private boolean flipY;

	public TexturedRectUI(ResourceLocation var1) {
		this.zLevel = 0F;
		this.u = 0F;
		this.v = 0F;
		this.width = 256F;
		this.height = 256F;
		this.uScale = 1F;
		this.vScale = 1F;
		this.red = 1F;
		this.green = 1F;
		this.blue = 1F;
		this.alpha = 1F;
		this.flipX = false;
		this.flipY = false;
		Objects.requireNonNull(var1,"Texture must never be null!");
		this.texture = var1;
	}
	public void draw(PoseStack var1, float var2, float var3) {
		float var4 = ((this.flipX) ? (-1F) : (1F)) / (256F);
		float var5 = ((this.flipY) ? (-1F) : (1F)) / (256F);
		float var6 = (this.width) * (this.uScale);
		float var7 = (this.height) * (this.vScale);
		Tesselator var8 = Tesselator.getInstance();
		RenderSystem.setShaderTexture(0,this.texture);
		RenderSystem.setShader((Supplier)(((Supplier<ShaderInstance>)(GameRenderer::getPositionTexColorShader))));
		BufferBuilder var9 = var8.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_TEX_COLOR);
		Matrix4f var10 = var1.last().pose();
		var9.addVertex(var10,var2,(var3) + (var7),this.zLevel).setUv((this.u) * (var4),((this.v) + (this.height)) * (var5)).setColor(this.red,this.green,this.blue,this.alpha);
		var9.addVertex(var10,(var2) + (var6),(var3) + (var7),this.zLevel).setUv(((this.u) + (this.width)) * (var4),((this.v) + (this.height)) * (var5)).setColor(this.red,this.green,this.blue,this.alpha);
		var9.addVertex(var10,(var2) + (var6),var3,this.zLevel).setUv(((this.u) + (this.width)) * (var4),(this.v) * (var5)).setColor(this.red,this.green,this.blue,this.alpha);
		var9.addVertex(var10,var2,var3,this.zLevel).setUv((this.u) * (var4),(this.v) * (var5)).setColor(this.red,this.green,this.blue,this.alpha);
		BufferUploader.drawWithShader(var9.buildOrThrow());
	}
	public ResourceLocation getTexture() {
		return this.texture;
	}
	public void reset() {
		this.setZLevel(0F);
		this.setUV(0F,0F);
		this.setSize(256F,256F);
		this.setScale(1F);
		this.setColor(1F,1F,1F,1F);
		this.setFlipX(false);
		this.setFlipY(false);
	}
	public TexturedRectUI setUV(float var1, float var2) {
		this.u = var1;
		this.v = var2;
		return this;
	}
	public TexturedRectUI setSize(float var1, float var2) {
		this.width = var1;
		this.height = var2;
		return this;
	}
	public TexturedRectUI setZLevel(float var1) {
		this.zLevel = var1;
		return this;
	}
	public TexturedRectUI setColor(float var1, float var2, float var3, float var4) {
		this.red = var1;
		this.green = var2;
		this.blue = var3;
		this.alpha = var4;
		return this;
	}
	public TexturedRectUI setScale(float var1) {
		this.uScale = var1;
		this.vScale = var1;
		return this;
	}
	public TexturedRectUI setScale(float var1, float var2) {
		this.uScale = var1;
		this.vScale = var2;
		return this;
	}
	public TexturedRectUI setFlipX(boolean var1) {
		this.flipX = var1;
		return this;
	}
	public TexturedRectUI setFlipY(boolean var1) {
		this.flipY = var1;
		return this;
	}
}