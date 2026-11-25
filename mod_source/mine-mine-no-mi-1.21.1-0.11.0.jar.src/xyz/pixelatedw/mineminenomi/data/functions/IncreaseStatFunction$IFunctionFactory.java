package xyz.pixelatedw.mineminenomi.data.functions;

import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

@FunctionalInterface()
public interface IncreaseStatFunction$IFunctionFactory<F extends IncreaseStatFunction> {
	public abstract F create(LootItemCondition[] var0, NumberProvider var1, StatChangeSource var2);
}