package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.events.entity.PlayerUseItemResultEvent;

@Mixin(value={ServerPlayerGameMode.class})
public abstract class ServerPlayerGameModeMixin {
	// Fields
	@Unique() private ItemStack originalUseItemStack;

	public ServerPlayerGameModeMixin() {
		this.originalUseItemStack = ItemStack.EMPTY;
	}
	@Inject(method={"useItem"}, at={@At(value="HEAD")})
	private void useItemHead(ServerPlayer var1, Level var2, ItemStack var3, InteractionHand var4, CallbackInfoReturnable<InteractionResult> var5) {
		this.originalUseItemStack = var3.copy();
	}
	@Inject(method={"useItem"}, at={@At(value="RETURN")})
	private void useItemReturn(ServerPlayer var1, Level var2, ItemStack var3, InteractionHand var4, CallbackInfoReturnable<InteractionResult> var5) {
		NeoForge.EVENT_BUS.post((Event)(new PlayerUseItemResultEvent((Player)(var1),var4,this.originalUseItemStack.copy(),var1.getItemInHand(var4).copy(),((InteractionResult)(var5.getReturnValue())))));
		this.originalUseItemStack = ItemStack.EMPTY;
	}
}