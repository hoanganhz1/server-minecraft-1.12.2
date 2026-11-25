package xyz.pixelatedw.mineminenomi.mixins;

import java.util.List;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xyz.pixelatedw.mineminenomi.handlers.entity.KairosekiCoatingHandler;

@Mixin(value={BoatItem.class})
public class BoatItemMixin {
	@Inject(method={"use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z", shift=At.Shift.AFTER)}, require=0, expect=1, locals=LocalCapture.CAPTURE_FAILSOFT)
	public void mineminenomi$use(Level var1, Player var2, InteractionHand var3, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> var4, ItemStack var5, HitResult var6, Vec3 var7, double var8, List<Entity> var9, Boat var10) {
		KairosekiCoatingHandler.applyExistingKairosekiCoatingFromItem(var2,var3,(Entity)(var10));
	}
	@Inject(method={"use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z", shift=At.Shift.AFTER)}, require=0, expect=1, locals=LocalCapture.CAPTURE_FAILSOFT)
	public void mineminenomi$arclight$use(Level var1, Player var2, InteractionHand var3, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> var4, ItemStack var5, BlockHitResult var6, Vec3 var7, double var8, List<Entity> var9, Boat var10) {
		KairosekiCoatingHandler.applyExistingKairosekiCoatingFromItem(var2,var3,(Entity)(var10));
	}
}