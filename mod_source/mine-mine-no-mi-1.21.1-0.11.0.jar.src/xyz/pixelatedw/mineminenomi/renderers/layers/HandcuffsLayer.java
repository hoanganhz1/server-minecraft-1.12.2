package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
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
import xyz.pixelatedw.mineminenomi.effects.HandcuffedEffect;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.models.entities.HandcuffsModel;

public class HandcuffsLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	// Fields
	private final HandcuffsModel model;

	public HandcuffsLayer(EntityRendererProvider.Context var1, RenderLayerParent<T, M> var2) {
		super(var2);
		this.model = new HandcuffsModel(var1.bakeLayer(HandcuffsModel.LAYER_LOCATION));
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		T var11 = var4;
		final T var11_f1 = var11;
		Predicate<MobEffectInstance> var12 = (l0) -> {
			return (l0.getEffect() instanceof HandcuffedEffect) && (var11_f1.hasEffect(l0.getEffect()));
		};
		Optional var13 = var4.getActiveEffects().stream().filter(var12).findFirst();
		if (var13.isPresent()){
			if ((((MobEffectInstance)(var13.get())).getDuration()) <= (0)){
				var4.removeEffect(((MobEffectInstance)(var13.get())).getEffect());
			}
			ResourceLocation var14 = ModResources.HANDCUFFS;
			if ((((MobEffectInstance)(var13.get())).getEffect()) == (ModEffects.HANDCUFFED_KAIROSEKI.get())){
				var14 = ModResources.KAIROSEKI_HANDCUFFS;
			}
			if (this.getParentModel() instanceof HumanoidModel){
				var1.translate(0D,-0.05D,0.05D);
				this.model.renderToBuffer(var1,var2.getBuffer(ModRenderTypes.getAbilityHand(var14)),var3,OverlayTexture.NO_OVERLAY);
			}else{
				return;
			}
		}
	}
}