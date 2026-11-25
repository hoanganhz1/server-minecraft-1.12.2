package xyz.pixelatedw.mineminenomi.world.features;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.FluidState;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.blocks.DialBlock;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class NaturalDialFeature extends Feature<NoneFeatureConfiguration> {
	// Fields
	private static final Supplier<Block>[] DIAL_BLOCKS;

	public NaturalDialFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
		WorldGenLevel var2 = var1.level();
		BlockPos var3 = var1.origin();
		int var4 = ((var3.getY()) > (150)) ? (100) : (10);
		if (((((var2.getRandom().nextDouble()) * (100D)) < ((double)(var4))) ? (1) : (0)) == (0)){
			return false;
		}else{
			BlockState var5 = var2.getBlockState(var3);
			FluidState var6 = var2.getFluidState(var3);
			BlockState var7 = var2.getBlockState(var3.below());
			BlockState var8 = var2.getBlockState(var3.above());
			int var9 = ((var6.is(FluidTags.WATER)) && ((var6.getAmount()) == (8))) ? (1) : (0);
			int var10 = ((var5.isAir()) || ((var9) != (0))) ? (1) : (0);
			if ((!(var7.isAir())) && ((var8.isAir()) && ((var7.isFaceSturdy((BlockGetter)(var2),var3,Direction.UP)) && (((var10) != (0)) && (!(((var7.getBlock()) != (Blocks.SAND)) && ((var7.getBlock()) != (ModBlocks.SKY_BLOCK.get())))))))){
				int var11 = var2.getRandom().nextInt((DIAL_BLOCKS.length) - (1));
				BlockState var12 = ((Block)(DIAL_BLOCKS[var11].get())).defaultBlockState();
				var12.setValue((Property)(DialBlock.WATERLOGGED),(Comparable)(Boolean.valueOf(false)));
				var2.setBlock(var3,var12,2);
				int var13 = var3.getX();
				int var14 = var3.getY();
				int var15 = var3.getZ();
				String var16 = "Dial spawned at: /tp " + var13 + " " + var14 + " " + var15;
				WyDebug.debug(var16);
				return true;
			}else{
				return false;
			}
		}
	}
	static  {
		Supplier[] var0 = new Supplier[]{ModBlocks.FLAME_DIAL,ModBlocks.FLASH_DIAL,ModBlocks.AXE_DIAL,ModBlocks.EISEN_DIAL,ModBlocks.BREATH_DIAL,ModBlocks.IMPACT_DIAL,ModBlocks.MILKY_DIAL};
		DIAL_BLOCKS = var0;
	}
}