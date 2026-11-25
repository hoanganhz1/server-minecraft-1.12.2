package xyz.pixelatedw.mineminenomi.blocks.sabaody;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MangrovePlanksBlock extends Block {
	public MangrovePlanksBlock() {
		super(BlockBehaviour.Properties.ofFullCopy((BlockBehaviour)(Blocks.OAK_PLANKS)));
	}
	public int getFireSpreadSpeed(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
		return 5;
	}
	public int getFlammability(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
		return 20;
	}
}