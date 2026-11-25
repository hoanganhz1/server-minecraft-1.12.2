package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IUseItemObjective {
	public abstract boolean checkItem(Player var0, ItemStack var1, int var2);
}