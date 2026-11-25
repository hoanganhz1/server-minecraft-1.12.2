package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.PacifistaEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.PacifistaModel;

public class PacifistaChargeLaserLayer<T extends PacifistaEntity, M extends PacifistaModel<T>> extends RenderLayer<T, M> {
	// Fields
	private static final RenderType RENDER_TYPE = RenderType.entityCutout(ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/pacifista_charging.png"));

	public PacifistaChargeLaserLayer(RenderLayerParent<T, M> var1) {
		super(var1);
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		if (var4.isChargingLaser()){
			VertexConsumer var11 = var2.getBuffer(RENDER_TYPE);
			((PacifistaModel)(this.getParentModel())).renderToBuffer(var1,var11,var3,OverlayTexture.NO_OVERLAY);
		}
	}
}