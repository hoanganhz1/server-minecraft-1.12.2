package xyz.pixelatedw.mineminenomi.mixins.client;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.events.entity.PlayerUseItemResultEvent;

@Mixin(value={MultiPlayerGameMode.class})
public abstract class MultiPlayerGameModeMixin {
	// Fields
	@Unique() private ItemStack originalUseItemStack;

	public MultiPlayerGameModeMixin() {
		this.originalUseItemStack = ItemStack.EMPTY;
	}
	@Inject(method={"useItem"}, at={@At(value="HEAD")})
	private void useItemHead(Player var1, InteractionHand var2, CallbackInfoReturnable<InteractionResult> var3) {
		this.originalUseItemStack = var1.getItemInHand(var2).copy();
	}
	@Inject(method={"useItem"}, at={@At(value="RETURN")})
	private void useItemReturn(Player var1, InteractionHand var2, CallbackInfoReturnable<InteractionResult> var3) {
		NeoForge.EVENT_BUS.post((Event)(new PlayerUseItemResultEvent(var1,var2,this.originalUseItemStack.copy(),var1.getItemInHand(var2).copy(),((InteractionResult)(var3.getReturnValue())))));
		this.originalUseItemStack = ItemStack.EMPTY;
	}
}