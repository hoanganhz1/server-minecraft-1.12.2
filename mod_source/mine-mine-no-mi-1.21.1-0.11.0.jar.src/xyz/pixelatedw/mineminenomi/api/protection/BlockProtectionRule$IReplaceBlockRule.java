package xyz.pixelatedw.mineminenomi.api.protection;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface()
public interface BlockProtectionRule$IReplaceBlockRule {
	public abstract boolean replace(Level var0, BlockPos var1, BlockState var2);
}