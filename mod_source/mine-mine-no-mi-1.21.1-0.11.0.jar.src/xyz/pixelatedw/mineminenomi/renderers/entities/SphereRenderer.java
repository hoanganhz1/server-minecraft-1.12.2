package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.SphereEntity;
import xyz.pixelatedw.mineminenomi.renderers.ArenaSkybox;

public class SphereRenderer<T extends SphereEntity> extends EntityRenderer<T> {
	// Fields
	private final Minecraft minecraft;
	private Camera camera;

	protected SphereRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.shadowRadius = 0F;
		this.minecraft = Minecraft.getInstance();
		this.camera = this.minecraft.gameRenderer.getMainCamera();
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		var4.pushPose();
		var1.getSkybox().renderSphereInWorld(var4,this.camera,var1.getX(),var1.getY(),var1.getZ());
		var4.popPose();
	}
	public boolean shouldRender(T var1, Frustum var2, double var3, double var4, double var5) {
		return true;
	}
	public ResourceLocation getTextureLocation(T var1) {
		return null;
	}
}