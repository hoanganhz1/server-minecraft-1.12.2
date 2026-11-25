package xyz.pixelatedw.mineminenomi.datagen.loottables.entities;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.datagen.loottables.EntitiesLootTablesDataGen;

public class LapahnDrops {
	public static LootTable.Builder getTable(HolderLookup.Provider var0) {
		LootPool.Builder var1 = LootPool.lootPool().name("mineminenomi:items").setRolls((NumberProvider)(ConstantValue.exactly(1F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.SNOWBALL)).setWeight(100).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(0F,5F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.RABBIT_HIDE)).setWeight(80).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,4F))))).apply((LootItemFunction.Builder)(EnchantedCountIncreaseFunction.lootingMultiplier(var0,(NumberProvider)(UniformGenerator.between(0F,2F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.RABBIT)).setWeight(80).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(2F,4F))))).apply((LootItemFunction.Builder)(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,EntitiesLootTablesDataGen.ON_FIRE_CHECK)))).apply((LootItemFunction.Builder)(EnchantedCountIncreaseFunction.lootingMultiplier(var0,(NumberProvider)(UniformGenerator.between(0F,2F)))))));
		LootPool.Builder var2 = LootPool.lootPool().name("mineminenomi:items2").setRolls((NumberProvider)(ConstantValue.exactly(1F))).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(var0,0.3F,0.03F)).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.RABBIT_FOOT)).setWeight(100).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,2F)))))));
		return LootTable.lootTable().withPool(var1).withPool(var2);
	}
}