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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.models.entities.CandleLockModel;

public class CandleLockLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	// Fields
	private final CandleLockModel<T> model;

	public CandleLockLayer(EntityRendererProvider.Context var1, RenderLayerParent<T, M> var2) {
		super(var2);
		this.model = new CandleLockModel(var1.bakeLayer(CandleLockModel.LAYER_LOCATION));
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		MobEffectInstance var11 = var4.getEffect((Holder)(ModEffects.CANDLE_LOCK));
		if (((var11) != (null)) && ((var11.getDuration()) > (0))){
			var1.pushPose();
			var1.mulPose(Axis.YN.rotationDegrees(((var4.xRotO) + (((var4.getXRot()) - (var4.xRotO)) * (var7))) + (180F)));
			VertexConsumer var12 = var2.getBuffer(RenderType.entityCutout(ModResources.CANDLE_TEXTURE));
			this.model.renderToBuffer(var1,var12,var3,OverlayTexture.NO_OVERLAY);
			var1.popPose();
			return;
		}else{
			return;
		}
	}
}