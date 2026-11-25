package xyz.pixelatedw.mineminenomi.data.conditions;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

public class RandomizedFruitsCondition implements LootItemCondition {
	// Fields
	private static final RandomizedFruitsCondition INSTANCE = new RandomizedFruitsCondition();
	public static final MapCodec<RandomizedFruitsCondition> CODEC = MapCodec.unit(INSTANCE);

	public boolean test(LootContext var1) {
		return ServerConfig.getRandomizedFruits();
	}
	public LootItemConditionType getType() {
		return ((LootItemConditionType)(ModLootTypes.RANDOMIZED_FRUIT.get()));
	}
	public static LootItemCondition.Builder builder() {
		LootItemCondition.Builder var0 = () -> {
			return INSTANCE;
		};
		return var0;
	}
}