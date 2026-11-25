package xyz.pixelatedw.mineminenomi.datagen.loottables.entities;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class DenDenMushiDrops {
	public static LootTable.Builder getTable(HolderLookup.Provider var0) {
		LootPool.Builder var1 = LootPool.lootPool().name("mineminenomi:items").setRolls((NumberProvider)(ConstantValue.exactly(1F))).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(var0,0.2F,0.015F)).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.NAUTILUS_SHELL)).setWeight(100).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(ConstantValue.exactly(1F))))).apply((LootItemFunction.Builder)(EnchantedCountIncreaseFunction.lootingMultiplier(var0,(NumberProvider)(UniformGenerator.between(0F,1F)))))));
		return LootTable.lootTable().withPool(var1);
	}
}