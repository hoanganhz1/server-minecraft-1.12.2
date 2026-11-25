package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

@Mixin(value={ItemRenderer.class})
public abstract class ItemRendererMixin {
	@Accessor()
	public abstract ItemColors getItemColors();
	@Inject(method={"renderQuadList"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$renderQuadList(PoseStack var1, VertexConsumer var2, List<BakedQuad> var3, ItemStack var4, int var5, int var6, CallbackInfo var7) {
		if (var4.isEmpty()){
			return;
		}else{
			PoseStack.Pose var8 = var1.last();
			if (this.tryHueShiftImbuing(var4,var2,var8,var3,var5,var6)){
				var7.cancel();
				return;
			}else{
				if (this.tryHueShiftAncientManual(var4,var2,var8,var3,var5,var6)){
					var7.cancel();
					return;
				}else{
					return;
				}
			}
		}
	}
	private boolean tryHueShiftAncientManual(ItemStack var1, VertexConsumer var2, PoseStack.Pose var3, List<BakedQuad> var4, int var5, int var6) {
		if (!(((Boolean)(var1.getOrDefault(ModDataComponents.IS_ANCIENT_MANUAL,Boolean.valueOf(false)))).booleanValue())){
			return false;
		}else{
			Iterator<BakedQuad> var7 = var4.iterator();
			do{
				if (var7.hasNext()){
					var2.putBulkData(var3,((BakedQuad)(var7.next())),0.6F,0.5F,0.5F,1F,var5,var6,true);
					continue;
				}else{
					break;
				}
			} while (true);
			return true;
		}
	}
	private boolean tryHueShiftImbuing(ItemStack var1, VertexConsumer var2, PoseStack.Pose var3, List<BakedQuad> var4, int var5, int var6) {
		if (!(((Boolean)(var1.getOrDefault(ModDataComponents.IMBUING,Boolean.valueOf(false)))).booleanValue())){
			return false;
		}else{
			if ((ItemsHelper.isImbuable(var1)) && (!(ItemsHelper.hasImbuingOverride(var1)))){
				Iterator<BakedQuad> var7 = var4.iterator();
				do{
					if (var7.hasNext()){
						var2.putBulkData(var3,((BakedQuad)(var7.next())),0.4F,0F,0.4F,1F,var5,var6,true);
						continue;
					}else{
						break;
					}
				} while (true);
				return true;
			}else{
				return false;
			}
		}
	}
}