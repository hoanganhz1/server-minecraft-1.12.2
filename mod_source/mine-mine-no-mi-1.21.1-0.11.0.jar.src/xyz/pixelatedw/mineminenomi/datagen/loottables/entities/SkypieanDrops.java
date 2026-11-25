package xyz.pixelatedw.mineminenomi.datagen.loottables.entities;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.data.functions.SetExtolInPouchFunction;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class SkypieanDrops {
	// Fields
	private static final LootPool.Builder ITEMS = LootPool.lootPool().name("mineminenomi:items").setRolls((NumberProvider)(UniformGenerator.between(0F,1F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.EXTOL_POUCH.get()))).setWeight(100).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(0F,1F))))).apply((LootItemFunction.Builder)(SetExtolInPouchFunction.builder((NumberProvider)(UniformGenerator.between(10000F,50000F)))))));
	private static final LootPool.Builder ITEMS2 = LootPool.lootPool().name("mineminenomi:items2").setRolls((NumberProvider)(UniformGenerator.between(0F,1F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.FEATHER)).setWeight(100).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,2F)))))));

	public static LootTable.Builder getTable() {
		return LootTable.lootTable().withPool(ITEMS).withPool(ITEMS2);
	}
}