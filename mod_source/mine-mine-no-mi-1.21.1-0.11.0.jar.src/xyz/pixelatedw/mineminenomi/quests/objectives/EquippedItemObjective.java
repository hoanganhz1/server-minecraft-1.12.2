package xyz.pixelatedw.mineminenomi.quests.objectives;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IEquipItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class EquippedItemObjective extends Objective implements IEquipItemObjective {
	// Fields
	private Supplier<Item> itemTarget;
	private EquipmentSlot slotTarget;

	public EquippedItemObjective(Quest var1, Component var2, Supplier<Item> var3, EquipmentSlot var4) {
		super(var1,var2);
		this.setMaxProgress(1F);
		this.itemTarget = var3;
		this.slotTarget = var4;
	}
	public boolean checkEquippedItem(Player var1, ItemStack var2) {
		return ((java.lang.Object)(var2.getItem())).equals(this.itemTarget.get());
	}
	public boolean checkSlot(EquipmentSlot var1) {
		return (var1) == (this.slotTarget);
	}
}