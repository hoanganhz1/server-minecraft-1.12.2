package xyz.pixelatedw.mineminenomi.api.blockgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface()
public interface BlockQueue$ICanPlaceBlock {
	public abstract boolean check(LevelAccessor var0, BlockPos var1, BlockState var2);
}