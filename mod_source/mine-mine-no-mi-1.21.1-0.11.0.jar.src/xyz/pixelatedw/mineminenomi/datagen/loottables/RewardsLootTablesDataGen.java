package xyz.pixelatedw.mineminenomi.datagen.loottables;

import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.data.functions.AncientManualFunction;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.AlvidaHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.AlvidaReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.ArlongHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.ArlongReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.BuggyHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.BuggyReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.CabajiHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.CabajiReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.ChewHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.ChewReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.DonKriegHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.DonKriegReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.GinHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.GinReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.HigumaHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.HigumaReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.JangoHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.JangoReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.KuroHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.KuroReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.KuroobiHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.KuroobiReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.MorganHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.MorganReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr0HardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr0Reward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr1HardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr1Reward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr3HardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr3Reward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr5MissValentineHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr5MissValentineReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.NyanbanBrothersHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.NyanbanBrothersReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.PearlHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.PearlReward;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

public final record RewardsLootTablesDataGen(HolderLookup.Provider registries) {
	// Fields
	public static final LootPool.Builder ANCIENT_MANUAL = LootPool.lootPool().name("mineminenomi:ancient_manual").setRolls((NumberProvider)(ConstantValue.exactly(1F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(Items.BOOK)).apply((LootItemFunction.Builder)(AncientManualFunction.builder())))).when(LootItemRandomChanceCondition.randomChance(0.1F));

	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> var1) {
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.HIGUMA.get())),HigumaReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.HIGUMA_HARD.get())),HigumaHardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.MORGAN.get())),MorganReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.MORGAN_HARD.get())),MorganHardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.BUGGY.get())),BuggyReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.BUGGY_HARD.get())),BuggyHardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.ALVIDA.get())),AlvidaReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.ALVIDA_HARD.get())),AlvidaHardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.CABAJI.get())),CabajiReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.CABAJI_HARD.get())),CabajiHardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.KURO.get())),KuroReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.KURO_HARD.get())),KuroHardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.JANGO.get())),JangoReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.JANGO_HARD.get())),JangoHardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.NYANBAN_BROTHERS.get())),NyanbanBrothersReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.NYANBAN_BROTHERS_HARD.get())),NyanbanBrothersHardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.DON_KRIEG.get())),DonKriegReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.DON_KRIEG_HARD.get())),DonKriegHardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.GIN.get())),GinReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.GIN_HARD.get())),GinHardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.PEARL.get())),PearlReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.PEARL_HARD.get())),PearlHardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.ARLONG.get())),ArlongReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.ARLONG_HARD.get())),ArlongHardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.CHEW.get())),ChewReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.CHEW_HARD.get())),ChewHardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.KUROOBI.get())),KuroobiReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.KUROOBI_HARD.get())),KuroobiHardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.MR_0.get())),Mr0Reward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.MR_0_HARD.get())),Mr0HardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.MR_1.get())),Mr1Reward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.MR_1_HARD.get())),Mr1HardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.MR_3.get())),Mr3Reward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.MR_3_HARD.get())),Mr3HardReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.MR_5_AND_MISS_VALENTINE.get())),Mr5MissValentineReward.getTables());
		this.addLootTable(var1,((ChallengeCore)(ModChallenges.MR_5_AND_MISS_VALENTINE_HARD.get())),Mr5MissValentineHardReward.getTables());
	}
	protected void addLootTable(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> var1, ChallengeCore<?> var2, Pair<String, LootTable.Builder>[] var3) {
		Pair<String, LootTable.Builder>[] var4 = var3;
		int var5 = var4.length;
		int var6 = 0;
		do{
			if ((var6) < (var5)){
				Pair<String, LootTable.Builder> var7 = var4[var6];
				String var8 = var2.getRegistryKey().getPath();
				String var9 = "rewards/" + var8;
				var1.accept(ResourceKey.create(Registries.LOOT_TABLE,ResourceLocation.fromNamespaceAndPath("mineminenomi",var9)),((LootTable.Builder)(var7.getRight())));
				var6++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public static LootTableProvider.SubProviderEntry create() {
		Function<HolderLookup.Provider, LootTableSubProvider> var0 = RewardsLootTablesDataGen::new;
		return new LootTableProvider.SubProviderEntry(var0,ModLootTypes.CHALLENGE);
	}
}