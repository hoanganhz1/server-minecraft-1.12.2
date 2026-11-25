package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.entities.TornadoModel;

public class CycloneTempoLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	// Fields
	private final TornadoModel<?> model;

	public CycloneTempoLayer(EntityRendererProvider.Context var1, RenderLayerParent<T, M> var2) {
		super(var2);
		this.model = (TornadoModel) (new TornadoModel(var1.bakeLayer(TornadoModel.LAYER_LOCATION)));
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		MobEffectInstance var11 = var4.getEffect((Holder)(ModEffects.CYCLONE_TEMPO));
		if (((var11) != (null)) && ((var11.getDuration()) > (0))){
			var1.pushPose();
			float var12 = (var4.getBbWidth()) * (6F);
			var1.scale(var12,(var4.getBbHeight()) * (1.15F),var12);
			var1.translate(0F,-0.3F,0F);
			var1.mulPose(Axis.YP.rotationDegrees((((float)(var4.tickCount)) * (3.1415927F)) * (1.5F)));
			VertexConsumer var13 = var2.getBuffer(ModRenderTypes.TRANSPARENT_COLOR);
			this.model.renderToBuffer(var1,var13,var3,OverlayTexture.NO_OVERLAY,WyHelper.packColor(0.5F,0.8F,1F,0.4F));
			float var14 = (0.5F) + ((float)(Math.sin((double)(((((float)(var11.getDuration())) % (20F)) / (20F)) * (3.1415927F)))));
			var1.scale(var14,var14,var14);
			var1.translate(0F,-0.1F,0F);
			var1.mulPose(Axis.YP.rotationDegrees((((float)(var4.tickCount)) * (3.1415927F)) * (2.5F)));
			this.model.renderToBuffer(var1,var13,var3,OverlayTexture.NO_OVERLAY,WyHelper.packColor(0.5F,0.8F,1F,0.4F));
			var1.popPose();
			return;
		}else{
			return;
		}
	}
}