package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.data.entity.KairosekiCoatingData;
import xyz.pixelatedw.mineminenomi.init.ModResources;

@Mixin(value={BoatRenderer.class})
public class BoatRendererMixin {
	@Inject(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/model/ListModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift=At.Shift.AFTER)})
	public void mineminenomi$render(Boat var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6, CallbackInfo var7) {
		BoatRenderer var8 = ((BoatRenderer)(this));
		int var9 = ((KairosekiCoatingData)(((java.util.Optional)(KairosekiCoatingData.get((Entity)(var1)))).orElse((Object)(null)))).getCoatingLevel();
		if ((var9) > (0)){
			ListModel var10 = ((ListModel)(var8.getModelWithLocation(var1).getSecond()));
			float var11 = (((float)(var9)) / (5F)) * (0.8F);
			VertexConsumer var12 = var5.getBuffer(RenderType.entityTranslucent(ModResources.KAIROSEKI_BLOCK));
			int var13 = WyHelper.packColor(0.8F,0.8F,0.8F,var11);
			var10.renderToBuffer(var4,var12,var6,OverlayTexture.NO_OVERLAY,var13);
		}
	}
}