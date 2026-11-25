package xyz.pixelatedw.mineminenomi.datagen.loottables.entities;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class TraderDrops {
	// Fields
	private static final LootPool.Builder ITEMS = LootPool.lootPool().name("mineminenomi:items").setRolls((NumberProvider)(UniformGenerator.between(0F,1F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.BELLY_POUCH.get()))).setWeight(100).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(ConstantValue.exactly(1F))))).apply((LootItemFunction.Builder)(SetBellyInPouchFunction.builder((NumberProvider)(UniformGenerator.between(100F,300F)))))));

	public static LootTable.Builder getTable() {
		return LootTable.lootTable().withPool(ITEMS);
	}
}