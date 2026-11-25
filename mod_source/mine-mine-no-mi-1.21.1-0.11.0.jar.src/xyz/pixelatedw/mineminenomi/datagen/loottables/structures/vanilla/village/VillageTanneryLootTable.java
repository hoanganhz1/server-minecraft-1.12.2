package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import xyz.pixelatedw.mineminenomi.init.ModArmors;

public class VillageTanneryLootTable {
	// Fields
	private static final LootPool.Builder ITEMS = LootPool.lootPool().name("mineminenomi:items").setRolls((NumberProvider)(ConstantValue.exactly(1F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(ItemStack.EMPTY.getItem())).setWeight(75))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModArmors.FLUFFY_CAPE.get()))).setWeight(5).setQuality(2))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModArmors.TRICORNE.get()))).setWeight(5).setQuality(2))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModArmors.BANDANA.get()))).setWeight(5).setQuality(2))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.SADDLE)).setWeight(10).setQuality(2)));

	public static LootTable.Builder getPool() {
		return LootTable.lootTable().withPool(ITEMS);
	}
}