package xyz.pixelatedw.mineminenomi.quests.objectives;

import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ICollectItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class CollectItemObjective<T extends Item> extends Objective implements ICollectItemObjective {
	// Fields
	private Predicate<ItemStack> check;

	public CollectItemObjective(Quest var1, Component var2, int var3, Predicate<ItemStack> var4) {
		super(var1,var2);
		Predicate<ItemStack> var5 = (l0) -> {
			return false;
		};
		this.check = var5;
		this.setMaxProgress((float)(var3));
		this.check = var4;
	}
	public boolean checkItem(ItemStack var1) {
		return this.check.test(var1);
	}
}