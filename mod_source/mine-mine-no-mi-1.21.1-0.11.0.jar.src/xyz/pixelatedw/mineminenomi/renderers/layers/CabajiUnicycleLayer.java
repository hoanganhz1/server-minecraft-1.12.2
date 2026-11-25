package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.CabajiEntity;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.UnicycleModel;
import xyz.pixelatedw.mineminenomi.renderers.entities.vehicles.UnicycleRenderer;

public class CabajiUnicycleLayer<E extends CabajiEntity, M extends HumanoidModel<E>> extends RenderLayer<E, M> {
	// Fields
	private UnicycleModel unicycle;

	public CabajiUnicycleLayer(EntityRendererProvider.Context var1, RenderLayerParent<E, M> var2) {
		super(var2);
		this.unicycle = new UnicycleModel(var1);
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, E var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		if (var4.hasUnicycle()){
			var1.pushPose();
			var1.translate(0D,0.3D,0D);
			RenderType var11 = RenderType.entityCutout(UnicycleRenderer.TEXTURE);
			this.unicycle.renderToBuffer(var1,var2.getBuffer(var11),var3,OverlayTexture.NO_OVERLAY);
			var1.popPose();
		}
	}
}