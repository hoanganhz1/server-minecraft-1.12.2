package xyz.pixelatedw.mineminenomi.api.entities;

import net.minecraft.world.phys.BlockHitResult;

@FunctionalInterface()
public interface NuProjectileEntity$IOnHitBlockEvent {
	public abstract void onHitBlock(BlockHitResult var0);
}