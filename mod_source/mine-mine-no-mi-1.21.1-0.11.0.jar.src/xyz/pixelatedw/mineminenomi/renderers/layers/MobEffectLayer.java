package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import xyz.pixelatedw.mineminenomi.api.effects.IBlockOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITextureOverlayEffect;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;

public class MobEffectLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	public MobEffectLayer(RenderLayerParent<T, M> var1) {
		super(var1);
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		Iterator var11 = var4.getActiveEffects().iterator();
		do{
			if (var11.hasNext()){
				MobEffectInstance var12 = ((MobEffectInstance)(var11.next()));
				MobEffect var13 = ((MobEffect)(var12.getEffect().value()));
				MobEffect var14 = var13;
				int var15 = 0;
				switch (((var14) == null ? -1 : ((var15) <= 0 && (var14) instanceof IBlockOverlayEffect) ? 0 : ((var15) <= 1 && (var14) instanceof IColorOverlayEffect) ? 1 : ((var15) <= 2 && (var14) instanceof ITextureOverlayEffect) ? 2 : -1)){
				case 0:
					Block var16 = ((IBlockOverlayEffect)(var14)).getBlockOverlay(var12.getDuration(),var12.getAmplifier());
					if ((var16) != (null)){
						this.renderBlockOverlay(var16,var1,var2,var3,var4);
						break;
					}else{
						break;
					}
				case 1:
					Color var17 = ((IColorOverlayEffect)(var14)).getBodyOverlayColor(var12.getDuration(),var12.getAmplifier());
					if ((var17) != (null)){
						this.renderColorOverlay(var17,var1,var2,var3);
						break;
					}else{
						break;
					}
				case 2:
					ResourceLocation var18 = ((ITextureOverlayEffect)(var14)).getBodyTexture(var12.getDuration(),var12.getAmplifier());
					Color var19 = ((ITextureOverlayEffect)(var13)).getTextureOverlayColor();
					if ((var18) != (null)){
						this.renderTextureOverlay(var18,var19,var1,var2,var3);
						break;
					}else{
						break;
					}
				case -1:
					break;
				}
				continue;
			}else{
				break;
			}
		} while (true);
	}
	private void renderTextureOverlay(ResourceLocation var1, Color var2, PoseStack var3, MultiBufferSource var4, int var5) {
		var3.pushPose();
		VertexConsumer var6 = var4.getBuffer(RenderType.entityTranslucent(var1));
		this.getParentModel().renderToBuffer(var3,var6,var5,OverlayTexture.NO_OVERLAY,var2.getRGB());
		var3.popPose();
	}
	private void renderColorOverlay(Color var1, PoseStack var2, MultiBufferSource var3, int var4) {
		var2.pushPose();
		VertexConsumer var5 = var3.getBuffer(ModRenderTypes.TRANSPARENT_COLOR);
		this.getParentModel().renderToBuffer(var2,var5,var4,OverlayTexture.NO_OVERLAY,var1.getRGB());
		var2.popPose();
	}
	private void renderBlockOverlay(Block var1, PoseStack var2, MultiBufferSource var3, int var4, T var5) {
		ItemStack var6 = var1.asItem().getDefaultInstance();
		var2.pushPose();
		float var7 = (float)((Math.ceil((double)(var5.getBbWidth()))) + (1D));
		float var8 = (float)((Math.ceil((double)(var5.getBbHeight()))) + (1D));
		var2.translate((0.4F) - ((var7) / (2F)),((1.4F) - ((var5.getBbHeight()) / (2F))) - ((var8) / (2F)),(0.4F) - ((var7) / (2F)));
		int var9 = 0;
		LOOP_2:
		do{
			if (((float)(var9)) < (var7)){
				int var10 = 0;
				LOOP_1:
				do{
					if (((float)(var10)) < (var8)){
						int var11 = 0;
						do{
							if (((float)(var11)) < (var7)){
								var2.pushPose();
								var2.translate((float)(var9),(float)(var10),(float)(var11));
								Minecraft.getInstance().getItemRenderer().renderStatic(var6,ItemDisplayContext.HEAD,var4,OverlayTexture.NO_OVERLAY,var2,var3,var5.level(),0);
								var2.popPose();
								var11++;
								continue;
							}else{
								break;
							}
						} while (true);
						var10++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				var9++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
		var2.popPose();
	}
}