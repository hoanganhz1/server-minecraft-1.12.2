package xyz.pixelatedw.mineminenomi.datagen.loottables.rewards;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.data.conditions.FirstCompletionRewardCondition;
import xyz.pixelatedw.mineminenomi.data.functions.UnlockChallengesFunction;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class Mr5MissValentineReward {
	// Fields
	private static final LootPool.Builder HARD_MODE;
	private static final LootPool.Builder ITEMS;

	public static Pair<String, LootTable.Builder>[] getTables() {
		LootTable.Builder var0 = LootTable.lootTable().withPool(HARD_MODE).withPool(ITEMS);
		Pair[] var1 = new Pair[]{Pair.of((Object)("rewards"),var0)};
		return var1;
	}
	static  {
		ChallengeCore[] var0 = new ChallengeCore[]{((ChallengeCore)(ModChallenges.MR_5_AND_MISS_VALENTINE_HARD.get()))};
		HARD_MODE = LootPool.lootPool().name("mineminenomi:hard_mode").setRolls((NumberProvider)(ConstantValue.exactly(1F))).when(FirstCompletionRewardCondition.builder()).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.PAPER)).apply((LootItemFunction.Builder)(UnlockChallengesFunction.builder(var0)))));
		ITEMS = LootPool.lootPool().name("mineminenomi:items").setRolls((NumberProvider)(ConstantValue.exactly(1F))).add((LootPoolEntryContainer.Builder)(EmptyLootItem.emptyItem().setWeight(91).setQuality(-5))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.UMBRELLA.get()))).setWeight(1))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModArmors.MR5_CHEST.get()))).setWeight(2))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModArmors.MR5_FEET.get()))).setWeight(2))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModArmors.MR5_LEGS.get()))).setWeight(2))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModArmors.MR5_GLASSES.get()))).setWeight(2)));
	}
}