package xyz.pixelatedw.mineminenomi.api.entities;

import net.minecraft.world.phys.EntityHitResult;

@FunctionalInterface()
public interface NuProjectileEntity$IOnHitEntityEvent {
	public abstract void onHitEntity(EntityHitResult var0);
}