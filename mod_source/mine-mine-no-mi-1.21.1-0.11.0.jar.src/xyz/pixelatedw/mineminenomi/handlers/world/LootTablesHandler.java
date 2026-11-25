package xyz.pixelatedw.mineminenomi.handlers.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

public class LootTablesHandler {
	public static void appendVanillaLootTables(ResourceLocation var0, LootTable var1) {
		String var2 = var0.getPath();
		String var3 = "vanilla/" + var2;
		LootPoolSingletonContainer.Builder var4 = NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE,ResourceLocation.fromNamespaceAndPath("mineminenomi",var3)));
		var1.addPool(LootPool.lootPool().add((LootPoolEntryContainer.Builder)(var4)).build());
	}
}