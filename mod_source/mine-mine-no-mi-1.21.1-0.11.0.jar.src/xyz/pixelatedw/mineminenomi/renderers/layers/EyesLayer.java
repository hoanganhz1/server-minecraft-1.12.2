package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Predicate;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;

public class EyesLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	// Fields
	private final RenderType eyesRenderer;
	private final Predicate<T> predicate;

	public EyesLayer(RenderLayerParent<T, M> var1, RenderType var2, Predicate<T> var3) {
		super(var1);
		this.eyesRenderer = var2;
		this.predicate = var3;
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		if (this.predicate.test(var4)){
			VertexConsumer var11 = var2.getBuffer(this.eyesRenderer);
			this.getParentModel().renderToBuffer(var1,var11,var3,OverlayTexture.NO_OVERLAY);
		}
	}
}