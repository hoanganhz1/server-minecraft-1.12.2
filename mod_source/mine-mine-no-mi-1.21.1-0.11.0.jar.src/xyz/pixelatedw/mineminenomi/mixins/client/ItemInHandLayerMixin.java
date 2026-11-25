package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.handlers.ability.MorphsHandler;

@Mixin(value={ItemInHandLayer.class})
public class ItemInHandLayerMixin {
	@Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$render(PoseStack var1, MultiBufferSource var2, int var3, LivingEntity var4, float var5, float var6, float var7, float var8, float var9, float var10, CallbackInfo var11) {
		ItemInHandLayer var12 = ((ItemInHandLayer)(this));
		Function var13 = (Function) ((Function<DevilFruitData, Optional>) ((l0) -> {
			return l0.getCurrentMorph(true);
		}));
		Optional var14 = DevilFruitData.get(var4).flatMap(var13);
		if ((var14.isPresent()) && (((MorphInfo)(var14.get())).isPartial())){
			MorphsHandler.renderHandItemForPartials(var4,((MorphInfo)(var14.get())),var1,var2,var3,var12);
			var11.cancel();
		}
	}
	@Inject(method={"renderArmWithItem"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift=At.Shift.BEFORE)}, cancellable=true)
	public void mineminenomi$renderItemStack(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7, CallbackInfo var8) {
		Animation.animationHeldItem(var1,var2,var3,var4,var5,var6,var7);
	}
}