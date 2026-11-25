package xyz.pixelatedw.mineminenomi.api.blockgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface()
public interface IAfterPlaceBlockEvent {
	public abstract void placed(BlockPos var0, BlockState var1, BlockState var2);
}