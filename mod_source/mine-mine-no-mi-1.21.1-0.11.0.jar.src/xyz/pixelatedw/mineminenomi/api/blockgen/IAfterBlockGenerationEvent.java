package xyz.pixelatedw.mineminenomi.api.blockgen;

import java.util.Set;
import net.minecraft.core.BlockPos;

@FunctionalInterface()
public interface IAfterBlockGenerationEvent {
	public abstract void generated(BlockPos var0, Set<BlockPos> var1);
}