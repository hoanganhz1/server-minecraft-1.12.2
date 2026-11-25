package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerUseItemResultEvent extends PlayerEvent {
	// Fields
	private final InteractionHand hand;
	private final ItemStack originalStack;
	private final ItemStack finalStack;
	private final InteractionResult interactionResult;

	public PlayerUseItemResultEvent(Player var1, InteractionHand var2, ItemStack var3, ItemStack var4, InteractionResult var5) {
		super(var1);
		this.hand = var2;
		this.originalStack = var3;
		this.finalStack = var4;
		this.interactionResult = var5;
	}
	public InteractionHand getHand() {
		return this.hand;
	}
	public ItemStack getOriginalStack() {
		return this.originalStack;
	}
	public ItemStack getFinalStack() {
		return this.finalStack;
	}
	public InteractionResult getInteractionResult() {
		return this.interactionResult;
	}
}