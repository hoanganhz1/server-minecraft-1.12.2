package xyz.pixelatedw.mineminenomi.datagen.loottables.entities;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class CaptainDrops {
	// Fields
	private static final LootPool.Builder ITEMS = LootPool.lootPool().name("mineminenomi:items").setRolls((NumberProvider)(ConstantValue.exactly(1F))).add((LootPoolEntryContainer.Builder)(EmptyLootItem.emptyItem().setWeight(100))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.BELLY_POUCH.get()))).setWeight(100).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(ConstantValue.exactly(1F))))).apply((LootItemFunction.Builder)(SetBellyInPouchFunction.builder((NumberProvider)(UniformGenerator.between(50F,100F)))))));
	private static final LootPool.Builder RARE_ITEMS = LootPool.lootPool().name("mineminenomi:items2").setRolls((NumberProvider)(ConstantValue.exactly(1F))).when(LootItemKilledByPlayerCondition.killedByPlayer()).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.BOTTLE_OF_RUM.get()))).setWeight(100).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(ConstantValue.exactly(1F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.CIGAR.get()))).setWeight(43).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(ConstantValue.exactly(1F)))))));

	public static LootTable.Builder getTable(HolderLookup.Provider var0) {
		LootPool.Builder var1 = LootPool.lootPool().name("mineminenomi:key").setRolls((NumberProvider)(ConstantValue.exactly(1F))).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(var0,0.5F,0.1F)).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.KEY.get()))).setWeight(100).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(ConstantValue.exactly(1F)))))));
		return LootTable.lootTable().withPool(ITEMS).withPool(RARE_ITEMS).withPool(var1);
	}
}