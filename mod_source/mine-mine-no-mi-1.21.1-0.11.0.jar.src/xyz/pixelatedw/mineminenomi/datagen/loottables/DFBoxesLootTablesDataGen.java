package xyz.pixelatedw.mineminenomi.datagen.loottables;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import xyz.pixelatedw.mineminenomi.data.conditions.RandomChanceWithLuckCondition;
import xyz.pixelatedw.mineminenomi.data.functions.FruitAlreadyExistsFunction;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModLootTables;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiBoxItem;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public final record DFBoxesLootTablesDataGen(HolderLookup.Provider registries) {
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> var1) {
		var1.accept(ModLootTables.WOODEN_BOX,this.genLootTable(((AkumaNoMiBoxItem)(ModItems.TIER_1_BOX.get()))));
		var1.accept(ModLootTables.IRON_BOX,this.genLootTable(((AkumaNoMiBoxItem)(ModItems.TIER_2_BOX.get()))));
		var1.accept(ModLootTables.GOLDEN_BOX,this.genLootTable(((AkumaNoMiBoxItem)(ModItems.TIER_3_BOX.get()))));
	}
	protected LootTable.Builder genLootTable(AkumaNoMiBoxItem var1) {
		LootTable.Builder var2 = LootTable.lootTable();
		LootPool.Builder var3 = LootPool.lootPool().name("mineminenomi:fruits").setRolls((NumberProvider)(ConstantValue.exactly(5F))).when(RandomChanceWithLuckCondition.randomChance(0.95F,-0.05F));
		AkumaNoMiBoxItem var4 = var1;
		final AkumaNoMiBoxItem var4_f1 = var4;
		Predicate<AkumaNoMiItem> var5 = (l0) -> {
			return (l0.getTier()) == (var4_f1.getTierLevel());
		};
		LootPool.Builder var6 = var3;
		final LootPool.Builder var6_f2 = var6;
		Consumer<AkumaNoMiItem> var7 = (l0) -> {
			var6_f2.add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(l0)).setWeight(100).apply((LootItemFunction.Builder)(FruitAlreadyExistsFunction.builder()))));
		};
		((java.util.stream.Stream)(((java.util.stream.Stream)(ModValues.DEVIL_FRUITS.stream())).filter((Predicate)(var5)))).forEach((Consumer)(var7));
		switch (var1.getTierLevel()){
		case 1:

		case 2:

		default:
			Object var8 = null;
			if ((null) != (null)){
				var2.withPool(LootPool.lootPool().name("mineminenomi:next_tier_fruits").setRolls((NumberProvider)(ConstantValue.exactly(1F))).when(RandomChanceWithLuckCondition.randomChance(0.05F,0.05F)).add((LootPoolEntryContainer.Builder)(NestedLootTable.lootTableReference((ResourceKey)(null)))));
			}
			var2.withPool(var3);
			return var2;
		}
	}
	public static LootTableProvider.SubProviderEntry create() {
		Function<HolderLookup.Provider, LootTableSubProvider> var0 = DFBoxesLootTablesDataGen::new;
		return new LootTableProvider.SubProviderEntry(var0,LootContextParamSets.EMPTY);
	}
}