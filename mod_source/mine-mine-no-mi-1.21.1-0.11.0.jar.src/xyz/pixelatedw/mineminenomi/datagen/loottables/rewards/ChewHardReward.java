package xyz.pixelatedw.mineminenomi.datagen.loottables.rewards;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.datagen.loottables.RewardsLootTablesDataGen;
import xyz.pixelatedw.mineminenomi.init.ModArmors;

public class ChewHardReward {
	// Fields
	private static final LootPool.Builder ITEMS = LootPool.lootPool().name("mineminenomi:items").setRolls((NumberProvider)(ConstantValue.exactly(1F))).add((LootPoolEntryContainer.Builder)(EmptyLootItem.emptyItem().setWeight(85).setQuality(-5))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModArmors.CHEW_CHEST.get()))).setWeight(5))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModArmors.CHEW_FEET.get()))).setWeight(5))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModArmors.CHEW_LEGS.get()))).setWeight(5)));

	public static Pair<String, LootTable.Builder>[] getTables() {
		LootTable.Builder var0 = LootTable.lootTable().withPool(ITEMS).withPool(RewardsLootTablesDataGen.ANCIENT_MANUAL);
		Pair[] var1 = new Pair[]{Pair.of((Object)("rewards"),var0)};
		return var1;
	}
}