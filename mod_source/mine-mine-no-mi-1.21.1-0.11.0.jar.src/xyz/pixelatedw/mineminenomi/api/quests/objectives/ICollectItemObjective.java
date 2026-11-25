package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.world.item.ItemStack;

public interface ICollectItemObjective {
	public abstract boolean checkItem(ItemStack var0);
	public default int checkItems(ArrayList<ItemStack> var1) {
		ToIntFunction<ItemStack> var2 = (l0) -> {
			return l0.getCount();
		};
		return var1.stream().filter((Predicate)(((Predicate<ItemStack>)(this::checkItem)))).mapToInt(var2).sum();
	}
}