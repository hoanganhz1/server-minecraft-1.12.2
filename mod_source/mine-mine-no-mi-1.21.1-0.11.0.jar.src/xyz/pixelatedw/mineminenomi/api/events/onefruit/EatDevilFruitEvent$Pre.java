package xyz.pixelatedw.mineminenomi.api.events.onefruit;

import javax.annotation.Nonnull;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.ICancellableEvent;

public class EatDevilFruitEvent$Pre extends EatDevilFruitEvent implements ICancellableEvent {
	public EatDevilFruitEvent$Pre(Player var1, @Nonnull() ItemStack var2) {
		super(var1,var2);
	}
}