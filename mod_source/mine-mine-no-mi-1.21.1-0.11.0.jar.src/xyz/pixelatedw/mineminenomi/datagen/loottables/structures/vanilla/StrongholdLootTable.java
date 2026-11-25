package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class StrongholdLootTable {
	// Fields
	private static final LootPool.Builder ITEMS = LootPool.lootPool().name("mineminenomi:items").setRolls((NumberProvider)(UniformGenerator.between(1F,2F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(ItemStack.EMPTY.getItem())).setWeight(20))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.BROADSWORD.get()))).setWeight(20))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.BISENTO.get()))).setWeight(20))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.KATANA.get()))).setWeight(20))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.CLEAVER.get()))).setWeight(20))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.BOTTLE_OF_RUM.get()))).setWeight(10))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.BULLET.get()))).setWeight(50).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(5F,10F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.KAIROSEKI_BULLET.get()))).setWeight(20).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(2F,5F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.NORMAL_HANDCUFFS.get()))).setWeight(20))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.KAIROSEKI_NET.get()))).setWeight(5))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.ROPE_NET.get()))).setWeight(5)));

	public static LootTable.Builder getPool() {
		return LootTable.lootTable().withPool(ITEMS);
	}
}