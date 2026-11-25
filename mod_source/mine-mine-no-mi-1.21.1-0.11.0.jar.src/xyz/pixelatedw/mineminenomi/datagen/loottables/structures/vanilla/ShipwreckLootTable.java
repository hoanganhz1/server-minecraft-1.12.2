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
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.data.functions.AncientManualFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class ShipwreckLootTable {
	// Fields
	private static final LootPool.Builder TREASURE = LootPool.lootPool().name("mineminenomi:treasure").setRolls((NumberProvider)(UniformGenerator.between(0F,1F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(ItemStack.EMPTY.getItem())).setWeight(200))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.BELLY_POUCH.get()))).setWeight(100).apply((LootItemFunction.Builder)(SetBellyInPouchFunction.builder((NumberProvider)(UniformGenerator.between(10F,50F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.TIER_1_BOX.get()))).setWeight(6).setQuality(1))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.TIER_2_BOX.get()))).setWeight(3).setQuality(2))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.BOOK)).setWeight(5).setQuality(2).apply((LootItemFunction.Builder)(AncientManualFunction.builder()))));
	private static final LootPool.Builder SUPPLY = LootPool.lootPool().name("mineminenomi:weapons").setRolls((NumberProvider)(UniformGenerator.between(1F,2F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(ItemStack.EMPTY.getItem())).setWeight(200))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.BROADSWORD.get()))).setWeight(40))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.KATANA.get()))).setWeight(40))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.DAGGER.get()))).setWeight(40))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.CUTLASS.get()))).setWeight(40))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.SPEAR.get()))).setWeight(40)));
	private static final LootPool.Builder ITEMS = LootPool.lootPool().name("mineminenomi:items").setRolls((NumberProvider)(UniformGenerator.between(1F,2F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(ItemStack.EMPTY.getItem())).setWeight(50))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.BULLET.get()))).setWeight(40).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(3F,10F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.KEY.get()))).setWeight(20))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.KAIROSEKI_BULLET.get()))).setWeight(20).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(3F,8F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.BOTTLE_OF_RUM.get()))).setWeight(30).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(1F,3F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.CANNON_BALL.get()))).setWeight(40).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(2F,7F)))))));

	public static LootTable.Builder getTreasurePool() {
		return LootTable.lootTable().withPool(TREASURE);
	}
	public static LootTable.Builder getSupplyPool() {
		return LootTable.lootTable().withPool(SUPPLY).withPool(ITEMS);
	}
}