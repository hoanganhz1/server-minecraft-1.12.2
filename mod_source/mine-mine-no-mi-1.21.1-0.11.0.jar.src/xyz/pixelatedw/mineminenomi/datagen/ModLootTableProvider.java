package xyz.pixelatedw.mineminenomi.datagen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import xyz.pixelatedw.mineminenomi.datagen.loottables.BlocksLootTablesDataGen;
import xyz.pixelatedw.mineminenomi.datagen.loottables.DFBoxesLootTablesDataGen;
import xyz.pixelatedw.mineminenomi.datagen.loottables.EntitiesLootTablesDataGen;
import xyz.pixelatedw.mineminenomi.datagen.loottables.RewardsLootTablesDataGen;
import xyz.pixelatedw.mineminenomi.datagen.loottables.StructuresLootTablesDataGen;
import xyz.pixelatedw.mineminenomi.datagen.loottables.TraderLootTablesDataGen;

public class ModLootTableProvider extends LootTableProvider {
	public ModLootTableProvider(PackOutput var1, CompletableFuture<HolderLookup.Provider> var2) {
		super(var1,Collections.emptySet(),getLootTables(),var2);
	}
	public static List<LootTableProvider.SubProviderEntry> getLootTables() {
		ArrayList var0 = new ArrayList();
		var0.add(DFBoxesLootTablesDataGen.create());
		var0.add(TraderLootTablesDataGen.create());
		var0.add(BlocksLootTablesDataGen.create());
		var0.add(EntitiesLootTablesDataGen.create());
		var0.add(StructuresLootTablesDataGen.create());
		var0.add(RewardsLootTablesDataGen.create());
		return var0;
	}
}