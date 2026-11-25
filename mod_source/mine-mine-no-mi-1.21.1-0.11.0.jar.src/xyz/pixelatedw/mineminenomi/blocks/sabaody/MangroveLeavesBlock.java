package xyz.pixelatedw.mineminenomi.blocks.sabaody;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class MangroveLeavesBlock extends LeavesBlock {
	// Fields
	private static final int DISTANCE_VALUE = 15;

	public MangroveLeavesBlock() {
		super(BlockBehaviour.Properties.ofFullCopy((BlockBehaviour)(Blocks.MANGROVE_LEAVES)));
	}
	public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
		int var7 = (getDistanceAt(var3)) + (1);
		if (((var7) == (1)) && ((((Integer)(var1.getValue((Property)(DISTANCE)))).intValue()) == (var7))){

		}else{
			var4.scheduleTick(var5,(Block)(this),1);
		}
		return var1;
	}
	private static int getDistanceAt(BlockState var0) {
		if (var0.is(BlockTags.LOGS)){
			return 0;
		}else{
			return (var0.getBlock() instanceof LeavesBlock) ? (((Integer)(var0.getValue((Property)(DISTANCE)))).intValue()) : (15);
		}
	}
	public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, RandomSource var4) {
		if ((!(((Boolean)(var1.getValue((Property)(PERSISTENT)))).booleanValue())) && ((((Integer)(var1.getValue((Property)(DISTANCE)))).intValue()) == (15))){
			dropResources(var1,(Level)(var2),var3);
			var2.removeBlock(var3,false);
		}
	}
	public boolean isRandomlyTicking(BlockState var1) {
		return ((((Integer)(var1.getValue((Property)(DISTANCE)))).intValue()) == (15)) && (!(((Boolean)(var1.getValue((Property)(PERSISTENT)))).booleanValue()));
	}
	public int getFireSpreadSpeed(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
		return 30;
	}
	public int getFlammability(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
		return 60;
	}
}