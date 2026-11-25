package xyz.pixelatedw.mineminenomi.quests.objectives;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ICollectItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class ObtainItemObjective<T extends Item> extends Objective implements ICollectItemObjective {
	// Fields
	private Predicate<ItemStack> check;

	public ObtainItemObjective(Quest var1, Component var2, int var3, Supplier<T> var4) {
		super(var1,var2);
		Predicate<ItemStack> var5 = (l0) -> {
			return false;
		};
		this.check = var5;
		this.setMaxProgress((float)(var3));
		Supplier<T> var6 = var4;
		final Supplier<T> var6_f1 = var6;
		Predicate<ItemStack> var7 = (l0) -> {
			return (l0.getItem().asItem()) == (var6_f1.get());
		};
		this.check = var7;
	}
	public ObtainItemObjective(Quest var1, Component var2, int var3, Predicate<ItemStack> var4) {
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
	public int checkItems(ArrayList<ItemStack> var1) {
		ToIntFunction<ItemStack> var2 = (l0) -> {
			return l0.getCount();
		};
		return var1.stream().filter((Predicate)(((Predicate<ItemStack>)(this::checkItem)))).mapToInt(var2).sum();
	}
}