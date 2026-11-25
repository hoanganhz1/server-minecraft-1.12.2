package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IBrewPotionObjective {
	public abstract boolean checkPotion(Player var0, ItemStack var1);
}