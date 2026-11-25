package xyz.pixelatedw.mineminenomi.datagen.loottables.rewards;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.datagen.loottables.RewardsLootTablesDataGen;

public class NyanbanBrothersHardReward {
	public static Pair<String, LootTable.Builder>[] getTables() {
		LootTable.Builder var0 = LootTable.lootTable().withPool(RewardsLootTablesDataGen.ANCIENT_MANUAL);
		Pair[] var1 = new Pair[]{Pair.of((Object)("rewards"),var0)};
		return var1;
	}
}