package xyz.pixelatedw.mineminenomi.datagen.tags.blocks;

import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import xyz.pixelatedw.mineminenomi.api.WyBlockTagsProvider;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;

public class BlockProtectionTags {
	public static void addTags(WyBlockTagsProvider var0) {
		var0.tag(ModTags$Blocks.BLOCK_PROT_AIR).addTag(BlockTags.AIR);
		var0.tag(ModTags$Blocks.BLOCK_PROT_WATER).add((Object)(Blocks.WATER));
		var0.tag(ModTags$Blocks.BLOCK_PROT_LAVA).add((Object)(Blocks.LAVA));
		var0.tag(ModTags$Blocks.BLOCK_PROT_SNOW).addTag(BlockTags.SNOW);
		TagKey[] var1 = new TagKey[]{BlockTags.LOGS,BlockTags.PLANKS,BlockTags.DIRT,BlockTags.BASE_STONE_NETHER,BlockTags.BASE_STONE_OVERWORLD,BlockTags.FENCES,BlockTags.FENCE_GATES,BlockTags.CAULDRONS,BlockTags.DOORS,BlockTags.SLABS,BlockTags.STAIRS,BlockTags.STONE_BRICKS,BlockTags.TERRACOTTA,BlockTags.TRAPDOORS,BlockTags.NYLIUM,BlockTags.WALLS,BlockTags.WART_BLOCKS,BlockTags.WOOL,BlockTags.BEDS,BlockTags.BAMBOO_BLOCKS,BlockTags.ICE,BlockTags.CORAL_BLOCKS,Tags.Blocks.SANDS,Tags.Blocks.SANDSTONE_BLOCKS,Tags.Blocks.COBBLESTONES,Tags.Blocks.GLASS_BLOCKS,Tags.Blocks.GLASS_PANES,Tags.Blocks.NETHERRACKS,Tags.Blocks.GRAVELS,Tags.Blocks.BOOKSHELVES,ModTags$Blocks.VANILLA_BASALT,ModTags$Blocks.VANILLA_BLACKSTONE,Tags.Blocks.CONCRETES,BlockTags.CONCRETE_POWDER,ModTags$Blocks.VANILLA_DEEPSLATE,ModTags$Blocks.VANILLA_PURPUR,ModTags$Blocks.VANILLA_QUARTZ,Tags.Blocks.GLAZED_TERRACOTTAS};
		Block[] var2 = new Block[]{Blocks.BAMBOO,Blocks.SUSPICIOUS_GRAVEL,Blocks.SUSPICIOUS_SAND,Blocks.BROWN_MUSHROOM_BLOCK,Blocks.RED_MUSHROOM_BLOCK,Blocks.TNT,Blocks.CACTUS,Blocks.CLAY,Blocks.PUMPKIN,Blocks.CARVED_PUMPKIN,Blocks.MELON,Blocks.COBWEB,Blocks.POLISHED_DEEPSLATE,Blocks.POLISHED_DIORITE,Blocks.POLISHED_GRANITE,Blocks.BRICKS,Blocks.PACKED_MUD,Blocks.MUD_BRICKS,Blocks.SEA_LANTERN,Blocks.FARMLAND,Blocks.DIRT_PATH,Blocks.CALCITE,Blocks.DRIPSTONE_BLOCK,Blocks.SOUL_SAND,Blocks.SOUL_SOIL,Blocks.BONE_BLOCK,Blocks.SHROOMLIGHT,Blocks.HAY_BLOCK,Blocks.REDSTONE_LAMP,Blocks.BAMBOO_MOSAIC,Blocks.SNOW_BLOCK,Blocks.POWDER_SNOW};
		var0.tag(ModTags$Blocks.BLOCK_PROT_CORE).addTags(var1).add(var2);
		Block[] var3 = new Block[]{Blocks.BARRIER,Blocks.BEDROCK,Blocks.END_PORTAL,Blocks.END_PORTAL_FRAME,Blocks.END_GATEWAY,Blocks.COMMAND_BLOCK,Blocks.CHAIN_COMMAND_BLOCK,Blocks.REPEATING_COMMAND_BLOCK,Blocks.STRUCTURE_BLOCK,Blocks.JIGSAW,Blocks.MOVING_PISTON,Blocks.LIGHT,Blocks.REINFORCED_DEEPSLATE,((Block)(ModBlocks.PONEGLYPH.get())),((Block)(ModBlocks.BARRIER.get())),((Block)(ModBlocks.OPE.get())),((Block)(ModBlocks.STRING_WALL.get()))};
		var0.tag(ModTags$Blocks.BLOCK_PROT_RESTRICTED).add(var3);
		Block[] var4 = new Block[]{Blocks.ANCIENT_DEBRIS,Blocks.NETHERITE_BLOCK};
		var0.tag(ModTags$Blocks.BLOCK_PROT_LAVA_IMMUNE).add(var4).addTag(ModTags$Blocks.BLOCK_PROT_RESTRICTED);
		TagKey[] var5 = new TagKey[]{BlockTags.LEAVES,BlockTags.SMALL_FLOWERS,BlockTags.TALL_FLOWERS,BlockTags.SAPLINGS};
		Block[] var6 = new Block[]{Blocks.VINE,Blocks.WEEPING_VINES,Blocks.WEEPING_VINES_PLANT,Blocks.TWISTING_VINES,Blocks.TWISTING_VINES_PLANT,Blocks.CAVE_VINES,Blocks.CAVE_VINES_PLANT};
		var0.tag(ModTags$Blocks.BLOCK_PROT_FOLIAGE).addTags(var5).add(var6);
		TagKey[] var7 = new TagKey[]{BlockTags.CORAL_BLOCKS,BlockTags.CORALS,BlockTags.WALL_CORALS,ModTags$Blocks.VANILLA_DEAD_CORAL_BLOCKS,ModTags$Blocks.VANILLA_DEAD_CORALS,ModTags$Blocks.VANILLA_DEAD_WALL_CORALS};
		Block[] var8 = new Block[]{Blocks.KELP,Blocks.KELP_PLANT,Blocks.SEAGRASS,Blocks.TALL_SEAGRASS,Blocks.SEA_PICKLE};
		var0.tag(ModTags$Blocks.BLOCK_PROT_OCEAN_PLANTS).addTags(var7).add(var8);
		Block[] var9 = new Block[]{Blocks.WATER,Blocks.LAVA};
		var0.tag(ModTags$Blocks.BLOCK_PROT_LIQUIDS).add(var9);
	}
}