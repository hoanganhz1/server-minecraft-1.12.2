package xyz.pixelatedw.mineminenomi.datagen.loottables;

import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.data.conditions.RandomChanceWithLuckCondition;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModLootTables;

public final record BlocksLootTablesDataGen(HolderLookup.Provider registries) {
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> var1) {
		var1.accept(ModLootTables.KAIROSEKI_ORE_BLOCK,this.genLootTable(new LootPool.Builder[]{this.ore(((Item)(ModItems.KAIROSEKI.get())),1,2,2F,4F)}));
		var1.accept(ModLootTables.DEEPSLATE_KAIROSEKI_ORE_BLOCK,this.genLootTable(new LootPool.Builder[]{this.ore(((Item)(ModItems.KAIROSEKI.get())),1,2,3F,5F)}));
		var1.accept(ModLootTables.AXE_DIAL_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.AXE_DIAL.get())))}));
		var1.accept(ModLootTables.BREATH_DIAL_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.BREATH_DIAL.get())))}));
		var1.accept(ModLootTables.FLAME_DIAL_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.FLAME_DIAL.get())))}));
		var1.accept(ModLootTables.REJECT_DIAL_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.REJECT_DIAL.get())))}));
		var1.accept(ModLootTables.IMPACT_DIAL_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.IMPACT_DIAL.get())))}));
		var1.accept(ModLootTables.FLASH_DIAL_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.FLASH_DIAL.get())))}));
		var1.accept(ModLootTables.EISEN_DIAL_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.EISEN_DIAL.get())))}));
		var1.accept(ModLootTables.MILKY_DIAL_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.MILKY_DIAL.get())))}));
		var1.accept(ModLootTables.DEN_DEN_MUSHI_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.DEN_DEN_MUSHI.get())))}));
		var1.accept(ModLootTables.KAIROSEKI_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.KAIROSEKI.get())))}));
		var1.accept(ModLootTables.KAIROSEKI_BARS_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.KAIROSEKI_BARS.get())))}));
		var1.accept(ModLootTables.MANGROVE_LOG_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.MANGROVE_LOG.get())))}));
		var1.accept(ModLootTables.MANGROVE_PLANKS_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.MANGROVE_PLANKS.get())))}));
		var1.accept(ModLootTables.MANGROVE_WOOD_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.MANGROVE_WOOD.get())))}));
		var1.accept(ModLootTables.STRIPPED_MANGROVE_LOG_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.STRIPPED_MANGROVE_LOG.get())))}));
		var1.accept(ModLootTables.STRIPPED_MANGROVE_WOOD_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.STRIPPED_MANGROVE_WOOD.get())))}));
		var1.accept(ModLootTables.DESIGN_BARREL_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(Blocks.BARREL)}));
		var1.accept(ModLootTables.SKY_BLOCK_BLOCK,this.genLootTable(new LootPool.Builder[]{this.self(((Block)(ModBlocks.SKY_BLOCK.get())))}));
		LootTable.Builder var2 = new LootTable.Builder().withPool(LootPool.lootPool().setRolls((NumberProvider)(ConstantValue.exactly(1F))).add(LootItem.lootTableItem(((ItemLike)(ModItems.BUBBLY_CORAL.get()))).when(RandomChanceWithLuckCondition.randomChance(0.05F,-0.025F))));
		this.addVanillaLootTable(var1,ResourceLocation.parse("blocks/brain_coral_block"),var2);
	}
	protected void addVanillaLootTable(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> var1, ResourceLocation var2, LootTable.Builder var3) {
		String var4 = var2.getPath();
		String var5 = "vanilla/" + var4;
		var1.accept(ModLootTables.register(var5),var3);
	}
	protected LootPool.Builder basic(Item var1, int var2, float var3) {
		return this.basic(var1,var2,var2,var3,var3);
	}
	protected LootPool.Builder basic(Item var1, int var2, int var3, float var4, float var5) {
		Object var6 = null;
		if ((var2) == (var3)){
			var6 = ConstantValue.exactly((float)(var2));
		}else{
			var6 = UniformGenerator.between((float)(var2),(float)(var3));
		}
		Object var7 = null;
		if ((var4) == (var5)){
			var7 = ConstantValue.exactly((float)((int)(var4)));
		}else{
			var7 = UniformGenerator.between(var4,var5);
		}
		return LootPool.lootPool().setRolls((NumberProvider)(var6)).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(var1)).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(var7))))));
	}
	protected LootPool.Builder self(Block var1) {
		return LootPool.lootPool().setRolls((NumberProvider)(ConstantValue.exactly(1F))).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(var1.asItem())).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(ConstantValue.exactly(1F)))))));
	}
	protected LootPool.Builder ore(Item var1, int var2, float var3) {
		return this.ore(var1,var2,var2,var3,var3);
	}
	protected LootPool.Builder ore(Item var1, int var2, int var3, float var4, float var5) {
		Object var6 = null;
		if ((var2) == (var3)){
			var6 = ConstantValue.exactly((float)(var2));
		}else{
			var6 = UniformGenerator.between((float)(var2),(float)(var3));
		}
		Object var7 = null;
		if ((var4) == (var5)){
			var7 = ConstantValue.exactly((float)((int)(var4)));
		}else{
			var7 = UniformGenerator.between(var4,var5);
		}
		return LootPool.lootPool().setRolls((NumberProvider)(var6)).add((LootPoolEntryContainer.Builder)(LootItem.lootTableItem((ItemLike)(var1)).apply((LootItemFunction.Builder)(SetItemCountFunction.setCount((NumberProvider)(var7)))).apply((LootItemFunction.Builder)(ApplyBonusCount.addOreBonusCount(this.registries.holderOrThrow(Enchantments.FORTUNE))))));
	}
	protected LootTable.Builder genLootTable(LootPool.Builder... var1) {
		LootTable.Builder var2 = LootTable.lootTable();
		LootPool.Builder[] var3 = var1;
		int var4 = var3.length;
		int var5 = 0;
		do{
			if ((var5) < (var4)){
				var2.withPool(var3[var5]);
				var5++;
				continue;
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	public static LootTableProvider.SubProviderEntry create() {
		Function<HolderLookup.Provider, LootTableSubProvider> var0 = BlocksLootTablesDataGen::new;
		return new LootTableProvider.SubProviderEntry(var0,LootContextParamSets.BLOCK);
	}
}