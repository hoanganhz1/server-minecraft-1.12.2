package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

public class ModTags$Blocks {
	// Fields
	public static final TagKey<Block> KAIROSEKI = tag("kairoseki");
	public static final TagKey<Block> NO_PHASE = tag("nophase");
	public static final TagKey<Block> RUSTY = tag("rusty");
	public static final TagKey<Block> MANGROVE_LOGS = tag("mangrove_logs");
	@Deprecated() public static final TagKey<Block> VANILLA_AIR = BlockTags.AIR;
	public static final TagKey<Block> VANILLA_BASALT = tag("vanilla/basalt");
	public static final TagKey<Block> VANILLA_BLACKSTONE = tag("vanilla/blackstone");
	@Deprecated() public static final TagKey<Block> VANILLA_CONCRETE = Tags.Blocks.CONCRETES;
	@Deprecated() public static final TagKey<Block> VANILLA_CONCRETE_POWDER = BlockTags.CONCRETE_POWDER;
	public static final TagKey<Block> VANILLA_DEAD_CORAL_BLOCKS = tag("vanilla/dead_coral_blocks");
	public static final TagKey<Block> VANILLA_DEAD_CORAL_PLANTS = tag("vanilla/dead_coral_plants");
	public static final TagKey<Block> VANILLA_DEAD_CORALS = tag("vanilla/dead_corals");
	public static final TagKey<Block> VANILLA_DEAD_WALL_CORALS = tag("vanilla/dead_wall_corals");
	public static final TagKey<Block> VANILLA_DEEPSLATE = tag("vanilla/deepslate");
	@Deprecated() public static final TagKey<Block> VANILLA_GLAZED_TERRACOTTA = Tags.Blocks.GLAZED_TERRACOTTAS;
	public static final TagKey<Block> VANILLA_PRISMARINE = tag("vanilla/prismarine");
	public static final TagKey<Block> VANILLA_PURPUR = tag("vanilla/purpur");
	public static final TagKey<Block> VANILLA_QUARTZ = tag("vanilla/quartz");
	public static final TagKey<Block> LOGIA_BLOCK_PASS_ALL = tag("logia_block_pass/all");
	public static final TagKey<Block> LOGIA_BLOCK_PASS_HIE = tag("logia_block_pass/hie");
	public static final TagKey<Block> LOGIA_BLOCK_PASS_GORO = tag("logia_block_pass/goro");
	public static final TagKey<Block> LOGIA_BLOCK_PASS_PIKA = tag("logia_block_pass/pika");
	public static final TagKey<Block> LOGIA_BLOCK_PASS_SUNA = tag("logia_block_pass/suna");
	public static final TagKey<Block> LOGIA_BLOCK_PASS_YUKI = tag("logia_block_pass/yuki");
	public static final TagKey<Block> BLOCK_PROT_CORE = tag("block_protection/core");
	public static final TagKey<Block> BLOCK_PROT_FOLIAGE = tag("block_protection/foliage");
	public static final TagKey<Block> BLOCK_PROT_LIQUIDS = tag("block_protection/liquids");
	public static final TagKey<Block> BLOCK_PROT_WATER = tag("block_protection/water");
	public static final TagKey<Block> BLOCK_PROT_AIR = tag("block_protection/air");
	public static final TagKey<Block> BLOCK_PROT_RESTRICTED = tag("block_protection/restricted");
	public static final TagKey<Block> BLOCK_PROT_SNOW = tag("block_protection/snow");
	public static final TagKey<Block> BLOCK_PROT_OCEAN_PLANTS = tag("block_protection/ocean_plants");
	public static final TagKey<Block> BLOCK_PROT_LAVA_IMMUNE = tag("block_protection/lava_immune");
	public static final TagKey<Block> BLOCK_PROT_LAVA = tag("block_protection/lava");

	static void init() {
	}
	static TagKey<Block> tag(String var0) {
		return BlockTags.create(ResourceLocation.fromNamespaceAndPath("mineminenomi",var0));
	}
}