package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.bandit;

import net.minecraft.world.item.Item;
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
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class BanditArenaLootTable {
	// Fields
	private static final LootPool.Builder TOWER_SUPPLIES = LootPool.lootPool().name("mineminenomi:tower_supplies").setRolls((NumberProvider)(UniformGenerator.between(2F,4F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.ARROW)).setWeight(100).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(5F,10F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.BULLET.get()))).setWeight(100).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(5F,10F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.GUNPOWDER)).setWeight(80).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(5F,20F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.BOW)).setWeight(80))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.FLINTLOCK.get()))).setWeight(80))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.SPYGLASS)).setWeight(80))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.CROSSBOW)).setWeight(20))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.SENRIKU.get()))).setWeight(20))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.ROPE_NET.get()))).setWeight(10))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.CLOCK)).setWeight(80))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.IRON_AXE)).setWeight(30))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.IRON_SWORD)).setWeight(30))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.DAGGER.get()))).setWeight(30))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.APPLE)).setWeight(70).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(3F,10F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModItems.TANGERINE.get()))).setWeight(70).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(2F,5F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.COOKIE)).setWeight(70).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(UniformGenerator.between(3F,15F))))))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModArmors.SNIPER_GOGGLES.get()))).setWeight(10).setQuality(5)));

	public static Pair<String, LootTable.Builder>[] getTables() {
		LootTable.Builder var0 = LootTable.lootTable().withPool(TOWER_SUPPLIES);
		Pair[] var1 = new Pair[]{Pair.of((Object)("tower"),var0)};
		return var1;
	}
}