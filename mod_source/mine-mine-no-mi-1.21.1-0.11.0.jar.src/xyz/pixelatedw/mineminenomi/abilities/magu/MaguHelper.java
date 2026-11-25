package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule$Builder;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;

public class MaguHelper {
	// Fields
	public static final BlockProtectionRule GRIEF_RULE;

	public static void generateLavaPool(Level var0, BlockPos var1, int var2) {
		new SimpleBlockPlacer().setBlock(Blocks.LAVA.defaultBlockState()).setRule(GRIEF_RULE).setSize(var2).generate(var0,var1,BlockGenerators.SPHERE);
	}
	static  {
		BlockProtectionRule[] var0 = new BlockProtectionRule[]{DefaultProtectionRules.CORE_FOLIAGE_ORE};
		TagKey[] var1 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_LAVA_IMMUNE};
		GRIEF_RULE = new BlockProtectionRule$Builder(var0).addBannedTags(var1).build();
	}
}