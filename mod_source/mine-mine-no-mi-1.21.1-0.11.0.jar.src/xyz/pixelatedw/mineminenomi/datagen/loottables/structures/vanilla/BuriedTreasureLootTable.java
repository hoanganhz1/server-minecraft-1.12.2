package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.data.functions.AncientManualFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class BuriedTreasureLootTable {
	// Fields
	private static final LootPool.Builder TREASURE = LootPool.lootPool().name("mineminenomi:treasure").setRolls((NumberProvider)(ConstantValue.exactly(1F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(ItemStack.EMPTY.getItem())).setWeight(5))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.BELLY_POUCH.get()))).setWeight(10).apply((LootItemFunction.Builder)(SetBellyInPouchFunction.builder((NumberProvider)(UniformGenerator.between(1000F,1250F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.BELLY_POUCH.get()))).setWeight(3).setQuality(5).apply((LootItemFunction.Builder)(SetBellyInPouchFunction.builder((NumberProvider)(UniformGenerator.between(1000F,2000F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.BUBBLY_CORAL.get()))).setWeight(3).setQuality(2))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.BOTTLE_OF_RUM.get()))).setWeight(6).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,3F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.TIER_1_BOX.get()))).setWeight(2).setQuality(5))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.BOOK)).setWeight(2).setQuality(2).apply((LootItemFunction.Builder)(AncientManualFunction.builder()))));

	public static LootTable.Builder getPool() {
		return LootTable.lootTable().withPool(TREASURE);
	}
}