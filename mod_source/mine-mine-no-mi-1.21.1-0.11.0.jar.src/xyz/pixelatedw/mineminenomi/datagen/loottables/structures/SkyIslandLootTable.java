package xyz.pixelatedw.mineminenomi.datagen.loottables.structures;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class SkyIslandLootTable {
	// Fields
	private static final LootPool.Builder HOUSE_FOOD = LootPool.lootPool().name("mineminenomi:food").setRolls((NumberProvider)(UniformGenerator.between(1F,5F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.GLASS_BOTTLE)).setWeight(100).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,3F))))).apply((LootItemFunction.Builder)(SetPotionFunction.setPotion(Potions.WATER))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.TANGERINE.get()))).setWeight(80).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(3F,5F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.MUSHROOM_STEW)).setWeight(80).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,2F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.COOKIE)).setWeight(80).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(4F,8F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.BREAD)).setWeight(70).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(2F,4F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.CARROT)).setWeight(70).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(4F,6F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.GOLDEN_CARROT)).setWeight(5).setQuality(2).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,3F)))))));
	private static final LootPool.Builder HOUSE_DIALS = LootPool.lootPool().name("mineminenomi:dials").setRolls((NumberProvider)(UniformGenerator.between(1F,3F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(ItemStack.EMPTY.getItem())).setWeight(100).setQuality(-2))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModBlocks.EISEN_DIAL.get()))).setWeight(10).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,3F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModBlocks.FLASH_DIAL.get()))).setWeight(10).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,3F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModBlocks.MILKY_DIAL.get()))).setWeight(10).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,3F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModBlocks.BREATH_DIAL.get()))).setWeight(10).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,3F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModBlocks.AXE_DIAL.get()))).setWeight(10).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,3F)))))));
	private static final LootPool.Builder HOUSE_TOOLS = LootPool.lootPool().name("mineminenomi:tools").setRolls((NumberProvider)(UniformGenerator.between(1F,2F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(ItemStack.EMPTY.getItem())).setWeight(50).setQuality(-2))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.IRON_AXE)).setWeight(100))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.IRON_SHOVEL)).setWeight(100))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.FISHING_ROD)).setWeight(80))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.BRUSH)).setWeight(60))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.BUCKET)).setWeight(60))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.STRING)).setWeight(100).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(3F,6F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.FEATHER)).setWeight(100).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(3F,6F)))))));

	public static Pair<String, LootTable.Builder>[] getTables() {
		LootTable.Builder var0 = LootTable.lootTable().withPool(HOUSE_FOOD).withPool(HOUSE_DIALS).withPool(HOUSE_TOOLS);
		Pair[] var1 = new Pair[]{Pair.of((Object)("house"),var0)};
		return var1;
	}
}