package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.core.BlockPos;

@FunctionalInterface()
public interface AbilityExplosion$IOnBlockDestroyed {
	public abstract void onBlockDestroyed(BlockPos var0);
}