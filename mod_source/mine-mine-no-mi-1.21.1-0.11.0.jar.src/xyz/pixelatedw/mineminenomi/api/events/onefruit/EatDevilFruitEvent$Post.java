package xyz.pixelatedw.mineminenomi.api.events.onefruit;

import javax.annotation.Nonnull;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EatDevilFruitEvent$Post extends EatDevilFruitEvent {
	public EatDevilFruitEvent$Post(Player var1, @Nonnull() ItemStack var2) {
		super(var1,var2);
	}
}