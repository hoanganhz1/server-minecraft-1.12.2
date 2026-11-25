package xyz.pixelatedw.mineminenomi.datagen.loottables.structures;

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
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.data.conditions.RandomizedFruitsCondition;
import xyz.pixelatedw.mineminenomi.data.functions.EncyclopediaCompletionFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetFruitClueFunction;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class GhostShipLootTable {
	// Fields
	private static final LootPool.Builder SUPPLIES_LEFTOVERS = LootPool.lootPool().name("mineminenomi:leftovers").setRolls((NumberProvider)(UniformGenerator.between(0F,5F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.CUTLASS.get()))).setWeight(100).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,3F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.KATANA.get()))).setWeight(20).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,3F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.AXE.get()))).setWeight(20).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,3F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.FLINTLOCK.get()))).setWeight(20))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.BULLET.get()))).setWeight(70).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,5F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.PAPER)).setWeight(60).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,5F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.GUNPOWDER)).setWeight(70).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(2F,5F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.SUSPICIOUS_STEW)).setWeight(100).setQuality(-2).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,5F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.POISONOUS_POTATO)).setWeight(100).setQuality(-2).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(2F,5F)))))));
	private static final LootPool.Builder CAPTAIN_CHEST = LootPool.lootPool().name("mineminenomi:captain_chest").setRolls((NumberProvider)(UniformGenerator.between(2F,3F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.SUSPICIOUS_STEW)).setWeight(100).setQuality(-2).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,3F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.CUTLASS.get()))).setWeight(20))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.KATANA.get()))).setWeight(20))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.AXE.get()))).setWeight(20))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.SPYGLASS)).setWeight(20))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModBlocks.DEN_DEN_MUSHI.get()))).setWeight(18))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.TIER_1_BOX.get()))).setWeight(3).setQuality(5))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.BULLET.get()))).setWeight(80).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,5F)))))));
	private static final LootPool.Builder CAPTAIN_RANDOMIZED_FRUITS = LootPool.lootPool().name("mineminenomi:randomized_fruits").setRolls((NumberProvider)(UniformGenerator.between(1F,2F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(ItemStack.EMPTY.getItem())).setWeight(17))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.PAPER)).setWeight(2).setQuality(5).apply((LootItemFunction.Builder)(SetFruitClueFunction.builder())))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get()))).setWeight(1).setQuality(2).apply((LootItemFunction.Builder)(EncyclopediaCompletionFunction.builder())))).when(RandomizedFruitsCondition.builder());

	public static Pair<String, LootTable.Builder>[] getTables() {
		LootTable.Builder var0 = LootTable.lootTable().withPool(SUPPLIES_LEFTOVERS);
		LootTable.Builder var1 = LootTable.lootTable().withPool(CAPTAIN_CHEST).withPool(CAPTAIN_RANDOMIZED_FRUITS);
		Pair[] var2 = new Pair[]{Pair.of((Object)("supplies"),var0),Pair.of((Object)("captain"),var1)};
		return var2;
	}
}