package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IEquipItemObjective {
	public abstract boolean checkEquippedItem(Player var0, ItemStack var1);
	public abstract boolean checkSlot(EquipmentSlot var0);
}