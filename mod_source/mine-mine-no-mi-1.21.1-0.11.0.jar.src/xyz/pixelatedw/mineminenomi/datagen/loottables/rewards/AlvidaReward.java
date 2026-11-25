package xyz.pixelatedw.mineminenomi.datagen.loottables.rewards;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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
import net.minecraft.world.level.storage.loot.functions.SetCustomDataFunction;
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

public class AlvidaReward {
	// Fields
	public static final CompoundTag PLUME_HAT_NBT = createBicorneNbt();
	private static final LootPool.Builder HARD_MODE;
	private static final LootPool.Builder ITEMS;

	private static CompoundTag createBicorneNbt() {
		CompoundTag var0 = new CompoundTag();
		CompoundTag var1 = new CompoundTag();
		var1.putInt("color",12788538);
		var0.put("display",(Tag)(var1));
		return var0;
	}
	public static Pair<String, LootTable.Builder>[] getTables() {
		LootTable.Builder var0 = LootTable.lootTable().withPool(HARD_MODE).withPool(ITEMS);
		Pair[] var1 = new Pair[]{Pair.of((Object)("rewards"),var0)};
		return var1;
	}
	static  {
		ChallengeCore[] var0 = new ChallengeCore[]{((ChallengeCore)(ModChallenges.ALVIDA_HARD.get()))};
		HARD_MODE = LootPool.lootPool().name("mineminenomi:hard_mode").setRolls((NumberProvider)(ConstantValue.exactly(1F))).when(FirstCompletionRewardCondition.builder()).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.PAPER)).apply((LootItemFunction.Builder)(UnlockChallengesFunction.builder(var0)))));
		ITEMS = LootPool.lootPool().name("mineminenomi:items").setRolls((NumberProvider)(ConstantValue.exactly(1F))).add((LootPoolEntryContainer.Builder)(EmptyLootItem.emptyItem().setWeight(97).setQuality(-5))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModWeapons.MACE.get()))).setWeight(1))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem(((ItemLike)(ModArmors.PLUME_HAT.get()))).apply((LootItemFunction.Builder)(SetCustomDataFunction.setCustomData(PLUME_HAT_NBT))).setWeight(2)));
	}
}