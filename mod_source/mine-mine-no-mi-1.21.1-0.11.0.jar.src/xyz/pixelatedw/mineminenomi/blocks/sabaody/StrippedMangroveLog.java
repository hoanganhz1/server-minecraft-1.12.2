package xyz.pixelatedw.mineminenomi.blocks.sabaody;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class StrippedMangroveLog extends RotatedPillarBlock {
	public StrippedMangroveLog() {
		super(BlockBehaviour.Properties.ofFullCopy((BlockBehaviour)(Blocks.STRIPPED_OAK_LOG)));
	}
	public int getFireSpreadSpeed(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
		return 5;
	}
	public int getFlammability(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
		return 5;
	}
}