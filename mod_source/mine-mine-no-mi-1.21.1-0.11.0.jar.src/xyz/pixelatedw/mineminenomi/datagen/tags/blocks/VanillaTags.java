package xyz.pixelatedw.mineminenomi.datagen.tags.blocks;

import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import xyz.pixelatedw.mineminenomi.api.WyBlockTagsProvider;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;

public class VanillaTags {
	public static void addTags(WyBlockTagsProvider var0) {
		Block[] var1 = new Block[]{Blocks.BASALT,Blocks.POLISHED_BASALT,Blocks.SMOOTH_BASALT};
		var0.tag(ModTags$Blocks.VANILLA_BASALT).add(var1);
		Block[] var2 = new Block[]{Blocks.POLISHED_BLACKSTONE,Blocks.POLISHED_BLACKSTONE_BRICKS,Blocks.CHISELED_POLISHED_BLACKSTONE};
		var0.tag(ModTags$Blocks.VANILLA_BLACKSTONE).add(var2);
		Block[] var3 = new Block[]{Blocks.DEAD_TUBE_CORAL_BLOCK,Blocks.DEAD_BRAIN_CORAL_BLOCK,Blocks.DEAD_BUBBLE_CORAL_BLOCK,Blocks.DEAD_FIRE_CORAL_BLOCK,Blocks.DEAD_HORN_CORAL_BLOCK};
		var0.tag(ModTags$Blocks.VANILLA_DEAD_CORAL_BLOCKS).add(var3);
		Block[] var4 = new Block[]{Blocks.DEAD_TUBE_CORAL,Blocks.DEAD_BRAIN_CORAL,Blocks.DEAD_BUBBLE_CORAL,Blocks.DEAD_FIRE_CORAL,Blocks.DEAD_HORN_CORAL};
		var0.tag(ModTags$Blocks.VANILLA_DEAD_CORAL_PLANTS).add(var4);
		Block[] var5 = new Block[]{Blocks.DEAD_TUBE_CORAL_FAN,Blocks.DEAD_BRAIN_CORAL_FAN,Blocks.DEAD_BUBBLE_CORAL_FAN,Blocks.DEAD_FIRE_CORAL_FAN,Blocks.DEAD_HORN_CORAL_FAN};
		var0.tag(ModTags$Blocks.VANILLA_DEAD_CORALS).addTag(ModTags$Blocks.VANILLA_DEAD_CORAL_PLANTS).add(var5);
		Block[] var6 = new Block[]{Blocks.DEAD_TUBE_CORAL_WALL_FAN,Blocks.DEAD_BRAIN_CORAL_WALL_FAN,Blocks.DEAD_BUBBLE_CORAL_WALL_FAN,Blocks.DEAD_FIRE_CORAL_WALL_FAN,Blocks.DEAD_HORN_CORAL_WALL_FAN};
		var0.tag(ModTags$Blocks.VANILLA_DEAD_WALL_CORALS).add(var6);
		Block[] var7 = new Block[]{Blocks.CHISELED_DEEPSLATE,Blocks.DEEPSLATE_TILES,Blocks.CRACKED_DEEPSLATE_BRICKS,Blocks.POLISHED_DEEPSLATE};
		var0.tag(ModTags$Blocks.VANILLA_DEEPSLATE).add(var7);
		Block[] var8 = new Block[]{Blocks.PRISMARINE,Blocks.PRISMARINE_BRICKS,Blocks.DARK_PRISMARINE};
		var0.tag(ModTags$Blocks.VANILLA_PRISMARINE).add(var8);
		Block[] var9 = new Block[]{Blocks.PURPUR_BLOCK,Blocks.PURPUR_PILLAR};
		var0.tag(ModTags$Blocks.VANILLA_PURPUR).add(var9);
		Block[] var10 = new Block[]{Blocks.QUARTZ_BLOCK,Blocks.QUARTZ_BRICKS,Blocks.QUARTZ_PILLAR,Blocks.SMOOTH_QUARTZ,Blocks.CHISELED_QUARTZ_BLOCK};
		var0.tag(ModTags$Blocks.VANILLA_QUARTZ).add(var10);
	}
}