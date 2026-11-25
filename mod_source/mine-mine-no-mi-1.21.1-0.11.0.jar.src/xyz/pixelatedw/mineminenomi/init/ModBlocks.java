package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.blocks.BarrierBlock;
import xyz.pixelatedw.mineminenomi.blocks.CandyBlock;
import xyz.pixelatedw.mineminenomi.blocks.CustomSpawnerBlock;
import xyz.pixelatedw.mineminenomi.blocks.DarknessBlock;
import xyz.pixelatedw.mineminenomi.blocks.DemonPoisonBlock;
import xyz.pixelatedw.mineminenomi.blocks.DenDenMushiBlock;
import xyz.pixelatedw.mineminenomi.blocks.DesignBarrelBlock;
import xyz.pixelatedw.mineminenomi.blocks.DialBlock;
import xyz.pixelatedw.mineminenomi.blocks.FlagBlock;
import xyz.pixelatedw.mineminenomi.blocks.HardenedSnowBlock;
import xyz.pixelatedw.mineminenomi.blocks.KairosekiBarsBlock;
import xyz.pixelatedw.mineminenomi.blocks.MucusBlock;
import xyz.pixelatedw.mineminenomi.blocks.OilSpillBlock;
import xyz.pixelatedw.mineminenomi.blocks.OpeBlock;
import xyz.pixelatedw.mineminenomi.blocks.OriBarsBlock;
import xyz.pixelatedw.mineminenomi.blocks.PoisonBlock;
import xyz.pixelatedw.mineminenomi.blocks.PoneglyphBlock;
import xyz.pixelatedw.mineminenomi.blocks.RoadPoneglyphBlock;
import xyz.pixelatedw.mineminenomi.blocks.SkyBlockBlock;
import xyz.pixelatedw.mineminenomi.blocks.SpongeCakeBlock;
import xyz.pixelatedw.mineminenomi.blocks.StringWallBlock;
import xyz.pixelatedw.mineminenomi.blocks.SunaSandBlock;
import xyz.pixelatedw.mineminenomi.blocks.TangerineCropsBlock;
import xyz.pixelatedw.mineminenomi.blocks.WantedPosterBlock;
import xyz.pixelatedw.mineminenomi.blocks.WaxBlock;
import xyz.pixelatedw.mineminenomi.blocks.sabaody.MangroveLeavesBlock;
import xyz.pixelatedw.mineminenomi.blocks.sabaody.MangroveLogBlock;
import xyz.pixelatedw.mineminenomi.blocks.sabaody.MangrovePlanksBlock;
import xyz.pixelatedw.mineminenomi.blocks.sabaody.MangroveWoodBlock;
import xyz.pixelatedw.mineminenomi.blocks.sabaody.StrippedMangroveLog;
import xyz.pixelatedw.mineminenomi.blocks.sabaody.StrippedMangroveWood;
import xyz.pixelatedw.mineminenomi.items.FlagItem;
import xyz.pixelatedw.mineminenomi.items.WantedPosterItem;
import xyz.pixelatedw.mineminenomi.items.dials.AxeDialItem;
import xyz.pixelatedw.mineminenomi.items.dials.BreathDialItem;
import xyz.pixelatedw.mineminenomi.items.dials.EisenDialItem;
import xyz.pixelatedw.mineminenomi.items.dials.FlameDialItem;
import xyz.pixelatedw.mineminenomi.items.dials.FlashDialItem;
import xyz.pixelatedw.mineminenomi.items.dials.ImpactDialItem;
import xyz.pixelatedw.mineminenomi.items.dials.MilkyDialItem;
import xyz.pixelatedw.mineminenomi.items.dials.RejectDialItem;

public class ModBlocks {
	// Fields
	public static final Supplier<Block> OPE = ModRegistry.registerBlock("Ope",(Supplier)(((Supplier<Block>)(OpeBlock::new))));
	public static final Supplier<Block> SUNA_SAND = ModRegistry.registerBlock("Suna Sand",(Supplier)(((Supplier<Block>)(SunaSandBlock::new))));
	public static final Supplier<Block> WAX = ModRegistry.registerBlock("Wax Block",(Supplier)(((Supplier<Block>)(WaxBlock::new))));
	public static final Supplier<Block> CANDY = ModRegistry.registerBlock("Candy Block",(Supplier)(((Supplier<Block>)(CandyBlock::new))));
	public static final Supplier<Block> POISON = ModRegistry.registerBlock("Poison",(Supplier)(((Supplier<Block>)(PoisonBlock::new))));
	public static final Supplier<Block> DEMON_POISON = ModRegistry.registerBlock("Demon Poison",(Supplier)(((Supplier<Block>)(DemonPoisonBlock::new))));
	public static final Supplier<Block> STRING_WALL = ModRegistry.registerBlock("String Wall",(Supplier)(((Supplier<Block>)(StringWallBlock::new))));
	public static final Supplier<Block> BARRIER = ModRegistry.registerBlock("Barrier",(Supplier)(((Supplier<Block>)(BarrierBlock::new))));
	public static final Supplier<Block> DARKNESS = ModRegistry.registerBlock("Darkness",(Supplier)(((Supplier<Block>)(DarknessBlock::new))));
	public static final Supplier<Block> ORI_BARS = ModRegistry.registerBlock("Ori Bars",(Supplier)(((Supplier<Block>)(OriBarsBlock::new))));
	public static final Supplier<Block> MUCUS = ModRegistry.registerBlock("Mucus",(Supplier)(((Supplier<Block>)(MucusBlock::new))));
	public static final Supplier<Block> SPONGE_CAKE = ModRegistry.registerBlock("Sponge Cake",(Supplier)(((Supplier<Block>)(SpongeCakeBlock::new))));
	public static final Supplier<Block> HARDENED_SNOW = ModRegistry.registerBlock("Hardened Snow",(Supplier)(((Supplier<Block>)(HardenedSnowBlock::new))));
	public static final Supplier<Block> KAIROSEKI;
	public static final Supplier<Block> KAIROSEKI_ORE;
	public static final Supplier<Block> DEEPSLATE_KAIROSEKI_ORE;
	public static final Supplier<Block> KAIROSEKI_BARS;
	public static final Supplier<Block> SKY_BLOCK;
	public static final Supplier<Block> WANTED_POSTER;
	public static final Supplier<Block> CUSTOM_SPAWNER;
	public static final Supplier<Block> DEN_DEN_MUSHI;
	public static final Supplier<Block> OIL_SPILL;
	public static final Supplier<Block> DESIGN_BARREL;
	public static final Supplier<Block> STRUCTURE_AIR;
	public static final Supplier<Block> CHALLENGE_AIR;
	public static final Supplier<Block> FLAG;
	public static final Supplier<Block> AXE_DIAL;
	public static final Supplier<Block> IMPACT_DIAL;
	public static final Supplier<Block> FLASH_DIAL;
	public static final Supplier<Block> BREATH_DIAL;
	public static final Supplier<Block> EISEN_DIAL;
	public static final Supplier<Block> MILKY_DIAL;
	public static final Supplier<Block> FLAME_DIAL;
	public static final Supplier<Block> REJECT_DIAL;
	public static final Supplier<Block> MANGROVE_LOG;
	public static final Supplier<Block> MANGROVE_WOOD;
	public static final Supplier<Block> STRIPPED_MANGROVE_LOG;
	public static final Supplier<Block> STRIPPED_MANGROVE_WOOD;
	public static final Supplier<Block> MANGROVE_LEAVES;
	public static final Supplier<Block> MANGROVE_PLANKS;
	public static final Supplier<Block> PONEGLYPH;
	public static final Supplier<Block> ROAD_PONEGLYPH;
	public static final Supplier<Block> TANGERINE_CROP;

	public static void init() {
	}
	static  {
		Supplier<Block> var0 = () -> {
			return new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(50F,600F).requiresCorrectToolForDrops());
		};
		KAIROSEKI = ModRegistry.registerBlock("Kairoseki Block",var0);
		Supplier<Block> var1 = () -> {
			return new DropExperienceBlock((IntProvider)(UniformInt.of(3,5)),BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5F,3F));
		};
		KAIROSEKI_ORE = ModRegistry.registerBlock("Kairoseki Ore",var1);
		Supplier<Block> var2 = () -> {
			return new DropExperienceBlock((IntProvider)(UniformInt.of(3,5)),BlockBehaviour.Properties.ofFullCopy(((BlockBehaviour)(KAIROSEKI_ORE.get()))).mapColor(MapColor.DEEPSLATE).strength(6F,3F).sound(SoundType.DEEPSLATE));
		};
		DEEPSLATE_KAIROSEKI_ORE = ModRegistry.registerBlock("Deepslate Kairoseki Ore",var2);
		KAIROSEKI_BARS = ModRegistry.registerBlock("Kairoseki Bars",(Supplier)(((Supplier<Block>)(KairosekiBarsBlock::new))));
		SKY_BLOCK = ModRegistry.registerBlock("Sky Block",(Supplier)(((Supplier<Block>)(SkyBlockBlock::new))));
		Supplier<Block> var3 = WantedPosterBlock::new;
		WANTED_POSTER = ModRegistry.registerBlock("Wanted Poster",var3,(Function)(((Function<Block, BlockItem>)(WantedPosterItem::new))));
		CUSTOM_SPAWNER = ModRegistry.registerBlock("Custom Spawner",(Supplier)(((Supplier<Block>)(CustomSpawnerBlock::new))));
		DEN_DEN_MUSHI = ModRegistry.registerBlock("Den Den Mushi",(Supplier)(((Supplier<Block>)(DenDenMushiBlock::new))));
		OIL_SPILL = ModRegistry.registerBlock("Oil Spill",(Supplier)(((Supplier<Block>)(OilSpillBlock::new))));
		DESIGN_BARREL = ModRegistry.registerBlock("Design Barrel",(Supplier)(((Supplier<Block>)(DesignBarrelBlock::new))));
		Supplier<Block> var4 = () -> {
			return new AirBlock(BlockBehaviour.Properties.of().replaceable().noCollission().noLootTable().air());
		};
		STRUCTURE_AIR = ModRegistry.registerBlock("Structure Air",var4);
		Supplier<Block> var5 = () -> {
			ToIntFunction<BlockState> lv6_0 = (l2_0) -> {
				return 15;
			};
			return new AirBlock(BlockBehaviour.Properties.of().replaceable().noCollission().noLootTable().lightLevel(lv6_0).air());
		};
		CHALLENGE_AIR = ModRegistry.registerBlock("Challenge Air",var5);
		Supplier<Block> var6 = FlagBlock::new;
		FLAG = ModRegistry.registerBlock("Flag",var6,(Function)(((Function<Block, BlockItem>)(FlagItem::new))));
		Supplier<Block> var7 = DialBlock::new;
		AXE_DIAL = ModRegistry.registerBlock("Axe Dial",var7,(Function)(((Function<Block, BlockItem>)(AxeDialItem::new))));
		Supplier<Block> var8 = DialBlock::new;
		IMPACT_DIAL = ModRegistry.registerBlock("Impact Dial",var8,(Function)(((Function<Block, BlockItem>)(ImpactDialItem::new))));
		Supplier<Block> var9 = () -> {
			ToIntFunction<BlockState> lv8_0 = (l2_0) -> {
				return 10;
			};
			return new DialBlock(BlockBehaviour.Properties.of().strength(0.2F).lightLevel(lv8_0));
		};
		FLASH_DIAL = ModRegistry.registerBlock("Flash Dial",var9,(Function)(((Function<Block, BlockItem>)(FlashDialItem::new))));
		Supplier<Block> var10 = DialBlock::new;
		BREATH_DIAL = ModRegistry.registerBlock("Breath Dial",var10,(Function)(((Function<Block, BlockItem>)(BreathDialItem::new))));
		Supplier<Block> var11 = DialBlock::new;
		EISEN_DIAL = ModRegistry.registerBlock("Eisen Dial",var11,(Function)(((Function<Block, BlockItem>)(EisenDialItem::new))));
		Supplier<Block> var12 = DialBlock::new;
		MILKY_DIAL = ModRegistry.registerBlock("Milky Dial",var12,(Function)(((Function<Block, BlockItem>)(MilkyDialItem::new))));
		Supplier<Block> var13 = DialBlock::new;
		FLAME_DIAL = ModRegistry.registerBlock("Flame Dial",var13,(Function)(((Function<Block, BlockItem>)(FlameDialItem::new))));
		Supplier<Block> var14 = DialBlock::new;
		REJECT_DIAL = ModRegistry.registerBlock("Reject Dial",var14,(Function)(((Function<Block, BlockItem>)(RejectDialItem::new))));
		MANGROVE_LOG = ModRegistry.registerBlock("Mangrove Log",(Supplier)(((Supplier<Block>)(MangroveLogBlock::new))));
		MANGROVE_WOOD = ModRegistry.registerBlock("Mangrove Wood",(Supplier)(((Supplier<Block>)(MangroveWoodBlock::new))));
		STRIPPED_MANGROVE_LOG = ModRegistry.registerBlock("Stripped Mangrove Log",(Supplier)(((Supplier<Block>)(StrippedMangroveLog::new))));
		STRIPPED_MANGROVE_WOOD = ModRegistry.registerBlock("Stripped Mangrove Wood",(Supplier)(((Supplier<Block>)(StrippedMangroveWood::new))));
		MANGROVE_LEAVES = ModRegistry.registerBlock("Mangrove Leaves",(Supplier)(((Supplier<Block>)(MangroveLeavesBlock::new))));
		MANGROVE_PLANKS = ModRegistry.registerBlock("Mangrove Planks",(Supplier)(((Supplier<Block>)(MangrovePlanksBlock::new))));
		PONEGLYPH = ModRegistry.registerBlock("Poneglyph",(Supplier)(((Supplier<Block>)(PoneglyphBlock::new))));
		ROAD_PONEGLYPH = ModRegistry.registerBlock("Road Poneglyph",(Supplier)(((Supplier<Block>)(RoadPoneglyphBlock::new))));
		TANGERINE_CROP = ModRegistry.registerBlock("Tangerine Crop",(Supplier)(((Supplier<Block>)(TangerineCropsBlock::new))));
	}
}