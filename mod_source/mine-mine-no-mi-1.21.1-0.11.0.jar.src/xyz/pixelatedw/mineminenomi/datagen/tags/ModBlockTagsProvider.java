package xyz.pixelatedw.mineminenomi.datagen.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.pixelatedw.mineminenomi.api.WyBlockTagsProvider;
import xyz.pixelatedw.mineminenomi.datagen.tags.blocks.BlockProtectionTags;
import xyz.pixelatedw.mineminenomi.datagen.tags.blocks.VanillaTags;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;

public class ModBlockTagsProvider extends WyBlockTagsProvider {
	public ModBlockTagsProvider(PackOutput var1, CompletableFuture<HolderLookup.Provider> var2, ExistingFileHelper var3) {
		super(var1,var2,"mineminenomi",var3);
	}
	public void addTags(HolderLookup.Provider var1) {
		VanillaTags.addTags(this);
		BlockProtectionTags.addTags(this);
		Block[] var2 = new Block[]{((Block)(ModBlocks.CHALLENGE_AIR.get())),((Block)(ModBlocks.STRUCTURE_AIR.get()))};
		this.tag(BlockTags.AIR).add(var2);
		Block[] var3 = new Block[]{((Block)(ModBlocks.MANGROVE_LOG.get())),((Block)(ModBlocks.MANGROVE_WOOD.get())),((Block)(ModBlocks.STRIPPED_MANGROVE_LOG.get())),((Block)(ModBlocks.STRIPPED_MANGROVE_WOOD.get()))};
		this.tag(ModTags$Blocks.MANGROVE_LOGS).add(var3);
		Block[] var4 = new Block[]{((Block)(ModBlocks.KAIROSEKI.get())),((Block)(ModBlocks.KAIROSEKI_BARS.get())),((Block)(ModBlocks.KAIROSEKI_ORE.get())),((Block)(ModBlocks.DEEPSLATE_KAIROSEKI_ORE.get()))};
		this.tag(ModTags$Blocks.KAIROSEKI).add(var4);
		Block[] var5 = new Block[]{Blocks.IRON_ORE,Blocks.IRON_BLOCK,Blocks.IRON_BARS,Blocks.IRON_TRAPDOOR,Blocks.IRON_DOOR,Blocks.ANVIL,Blocks.CHIPPED_ANVIL,Blocks.DAMAGED_ANVIL,Blocks.HOPPER};
		this.tag(ModTags$Blocks.RUSTY).add(var5);
		Block[] var6 = new Block[]{Blocks.BEDROCK,Blocks.BARRIER,((Block)(ModBlocks.BARRIER.get()))};
		this.tag(ModTags$Blocks.NO_PHASE).add(var6).addTag(ModTags$Blocks.KAIROSEKI);
		this.tag(ModTags$Blocks.LOGIA_BLOCK_PASS_HIE).addTag(BlockTags.ICE);
		this.tag(ModTags$Blocks.LOGIA_BLOCK_PASS_GORO).addTag(ModTags$Blocks.RUSTY).addTag(Tags.Blocks.STORAGE_BLOCKS_GOLD);
		this.tag(ModTags$Blocks.LOGIA_BLOCK_PASS_PIKA).addTag(Tags.Blocks.GLASS_BLOCKS).addTag(Tags.Blocks.GLASS_PANES);
		Block[] var7 = new Block[]{Blocks.SANDSTONE_STAIRS,Blocks.SANDSTONE_SLAB,Blocks.SANDSTONE_WALL,Blocks.CUT_SANDSTONE_SLAB,Blocks.SMOOTH_SANDSTONE_STAIRS,Blocks.SMOOTH_SANDSTONE_SLAB};
		this.tag(ModTags$Blocks.LOGIA_BLOCK_PASS_SUNA).addTag(Tags.Blocks.SANDS).addTag(Tags.Blocks.SANDSTONE_BLOCKS).add(var7);
		Block[] var8 = new Block[]{Blocks.SNOW,Blocks.SNOW_BLOCK,((Block)(ModBlocks.HARDENED_SNOW.get()))};
		this.tag(ModTags$Blocks.LOGIA_BLOCK_PASS_YUKI).add(var8);
		TagKey[] var9 = new TagKey[]{ModTags$Blocks.LOGIA_BLOCK_PASS_HIE,ModTags$Blocks.LOGIA_BLOCK_PASS_GORO,ModTags$Blocks.LOGIA_BLOCK_PASS_PIKA,ModTags$Blocks.LOGIA_BLOCK_PASS_SUNA,ModTags$Blocks.LOGIA_BLOCK_PASS_YUKI};
		this.tag(ModTags$Blocks.LOGIA_BLOCK_PASS_ALL).addTags(var9);
		this.tag(BlockTags.LOGS).addTag(ModTags$Blocks.MANGROVE_LOGS);
		this.tag(BlockTags.LOGS_THAT_BURN).addTag(ModTags$Blocks.MANGROVE_LOGS);
		this.tag(BlockTags.PLANKS).add((Object)(((Block)(ModBlocks.MANGROVE_PLANKS.get()))));
		this.tag(BlockTags.LEAVES).add((Object)(((Block)(ModBlocks.MANGROVE_LEAVES.get()))));
		Block[] var10 = new Block[]{((Block)(ModBlocks.KAIROSEKI_ORE.get())),((Block)(ModBlocks.DEEPSLATE_KAIROSEKI_ORE.get())),((Block)(ModBlocks.KAIROSEKI.get())),((Block)(ModBlocks.KAIROSEKI_BARS.get()))};
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(var10);
		this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add((Object)(((Block)(ModBlocks.SKY_BLOCK.get()))));
		this.tag(BlockTags.MINEABLE_WITH_AXE).add((Object)(((Block)(ModBlocks.DESIGN_BARREL.get()))));
		Block[] var11 = new Block[]{((Block)(ModBlocks.KAIROSEKI_ORE.get())),((Block)(ModBlocks.DEEPSLATE_KAIROSEKI_ORE.get())),((Block)(ModBlocks.KAIROSEKI_BARS.get()))};
		this.tag(BlockTags.NEEDS_IRON_TOOL).add(var11);
		this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add((Object)(((Block)(ModBlocks.KAIROSEKI.get()))));
	}
}