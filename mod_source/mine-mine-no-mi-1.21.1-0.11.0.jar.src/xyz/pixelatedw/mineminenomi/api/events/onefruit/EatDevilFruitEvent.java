package xyz.pixelatedw.mineminenomi.api.events.onefruit;

import javax.annotation.Nonnull;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class EatDevilFruitEvent extends PlayerEvent {
	// Fields
	private final ItemStack devilFruit;

	EatDevilFruitEvent(Player var1, @Nonnull() ItemStack var2) {
		super(var1);
		this.devilFruit = var2;
	}
	public ItemStack getItem() {
		return this.devilFruit;
	}
}