package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.abilities.zushi.AbareHimatsuriAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;

public class AbareHimatsuriLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	public AbareHimatsuriLayer(RenderLayerParent<T, M> var1) {
		super(var1);
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		if ((var4.isAlive()) && (!(var4.onGround()))){
			AbilityData var11 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var4))).orElse((Object)(null))));
			if ((var11) == (null)){
				return;
			}else{
				AbareHimatsuriAbility var12 = ((AbareHimatsuriAbility)(var11.getEquippedAbility(((AbilityCore)(AbareHimatsuriAbility.INSTANCE.get())))));
				if ((var12) == (null)){
					return;
				}else{
					this.renderBlockPlatform(var12.blocks,var1,var2,var3,var7,var4);
					return;
				}
			}
		}else{
			return;
		}
	}
	private void renderBlockPlatform(BlockState[] var1, PoseStack var2, MultiBufferSource var3, int var4, float var5, T var6) {
		if ((var1) == (null)){
			return;
		}else{
			var2.pushPose();
			var2.mulPose(Axis.YP.rotationDegrees(-(Mth.lerp(var5,var6.yBodyRotO,var6.yBodyRot))));
			int var7 = (var1.length) - (1);
			int var8 = -1;
			LOOP_2:
			do{
				if ((var8) <= (1)){
					int var9 = 0;
					LOOP_1:
					do{
						if ((var9) <= (1)){
							int var10 = -1;
							do{
								if ((var10) <= (1)){
									if ((var7) < (0)){
										return;
									}else{
										if (((var7) != (0)) && (((var7) != (2)) && (((var7) != (12)) && ((var7) != (14))))){
											ItemStack var11 = var1[var7].getBlock().asItem().getDefaultInstance();
											var2.pushPose();
											var2.translate((float)(var8),(float)((var9) + (2)),(float)(var10));
											var2.mulPose(Axis.XP.rotationDegrees(180F));
											Minecraft.getInstance().getItemRenderer().renderStatic(var11,ItemDisplayContext.HEAD,var4,OverlayTexture.NO_OVERLAY,var2,var3,var6.level(),0);
											var2.popPose();
											var7--;
										}else{
											var7--;
										}
										var10++;
										continue;
									}
								}else{
									break;
								}
							} while (true);
							var9++;
							continue LOOP_1;
						}else{
							break;
						}
					} while (true);
					var8++;
					continue LOOP_2;
				}else{
					break;
				}
			} while (true);
			var2.popPose();
			return;
		}
	}
}