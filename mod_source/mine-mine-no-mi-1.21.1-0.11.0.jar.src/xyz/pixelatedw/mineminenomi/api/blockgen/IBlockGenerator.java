package xyz.pixelatedw.mineminenomi.api.blockgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

@FunctionalInterface()
public interface IBlockGenerator {
	public abstract void generate(IBlockPlacer var0, Level var1, BlockPos var2);
}