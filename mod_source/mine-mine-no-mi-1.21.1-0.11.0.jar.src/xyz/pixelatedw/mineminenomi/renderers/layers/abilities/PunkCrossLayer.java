package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class PunkCrossLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	public PunkCrossLayer(EntityRendererProvider.Context var1, RenderLayerParent<T, M> var2) {
		super(var2);
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		if (!(var4.hasEffect((Holder)(ModEffects.PUNK_CROSS)))){
			return;
		}else{
			int var15 = 0;
			int var16 = 0;
			float var11 = (float)((Math.ceil((double)(var4.getBbWidth()))) + (1D));
			float var12 = (float)((Math.ceil((double)(var4.getBbHeight()))) + (1D));
			var1.translate((0.4F) - ((var11) / (2F)),((1.4F) - ((var4.getBbHeight()) / (2F))) - ((var12) / (2F)),(0.4F) - ((var11) / (2F)));
			var1.translate(0F,-2F,0F);
			Random var13 = new Random();
			int var14 = 0;
			LOOP_2:
			do{
				if (((float)(var14)) < (var11)){
					var15 = 0;
					LOOP_1:
					do{
						if (((float)(var15)) < ((var12) + (2F))){
							var16 = 0;
							do{
								if (((float)(var16)) < (var11)){
									int var17 = (((1) + (var14)) + (var15)) + (var16);
									var13.setSeed((long)(var17));
									int var18 = var13.nextInt(180);
									var1.pushPose();
									var1.translate((float)(var14),(float)(var15),(float)(var16));
									var1.mulPose(Axis.YP.rotationDegrees((float)(var18)));
									var1.mulPose(Axis.ZP.rotationDegrees((float)(-(var18))));
									Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack((ItemLike)(Blocks.IRON_BLOCK)),ItemDisplayContext.HEAD,var3,OverlayTexture.NO_OVERLAY,var1,var2,var4.level(),0);
									var1.popPose();
									var16++;
									continue;
								}else{
									break;
								}
							} while (true);
							var15++;
							continue LOOP_1;
						}else{
							break;
						}
					} while (true);
					var14++;
					continue LOOP_2;
				}else{
					break;
				}
			} while (true);
			var1.translate(-1.5D,1D,0.5D);
			var14 = 0;
			LOOP_3:
			do{
				if (((float)(var14)) < ((var12) + (2F))){
					var15 = 0;
					do{
						if (((float)(var15)) < (var11)){
							var16 = ((1) + (var14)) + (var15);
							var13.setSeed((long)(var16));
							int var17 = var13.nextInt(180);
							var1.pushPose();
							var1.translate((float)(var14),(float)(var15),0F);
							var1.mulPose(Axis.YP.rotationDegrees((float)(var17)));
							var1.mulPose(Axis.ZP.rotationDegrees((float)(-(var17))));
							Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack((ItemLike)(Blocks.IRON_BLOCK)),ItemDisplayContext.HEAD,var3,OverlayTexture.NO_OVERLAY,var1,var2,var4.level(),0);
							var1.popPose();
							var15++;
							continue;
						}else{
							break;
						}
					} while (true);
					var14++;
					continue LOOP_3;
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
}