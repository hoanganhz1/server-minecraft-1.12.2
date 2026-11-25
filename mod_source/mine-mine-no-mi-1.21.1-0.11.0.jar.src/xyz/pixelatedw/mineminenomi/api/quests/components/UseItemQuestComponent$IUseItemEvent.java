package xyz.pixelatedw.mineminenomi.api.quests.components;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

@FunctionalInterface()
public interface UseItemQuestComponent$IUseItemEvent {
	public abstract void useItem(Player var0, ItemStack var1, int var2, Objective var3);
}