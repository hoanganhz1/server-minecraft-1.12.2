package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class WaxBlock extends Block {
	public WaxBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(8F,3F).ignitedByLava());
	}
	public int getFireSpreadSpeed(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
		return 100;
	}
	public int getFlammability(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
		return 200;
	}
}