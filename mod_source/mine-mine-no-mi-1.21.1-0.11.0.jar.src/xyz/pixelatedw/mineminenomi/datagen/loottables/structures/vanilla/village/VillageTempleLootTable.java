package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village;

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
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class VillageTempleLootTable {
	// Fields
	private static final LootPool.Builder ITEMS = LootPool.lootPool().name("mineminenomi:items").setRolls((NumberProvider)(ConstantValue.exactly(1F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(ItemStack.EMPTY.getItem())).setWeight(90))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.TANGERINE.get()))).setWeight(10).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(3F,5F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModBlocks.DEN_DEN_MUSHI.get()))).setWeight(50))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModArmors.MEDIC_BAG.get()))).setWeight(20).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,2F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.TIER_1_BOX.get()))).setWeight(5).setQuality(2)));

	public static LootTable.Builder getPool() {
		return LootTable.lootTable().withPool(ITEMS);
	}
}