package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;

@FunctionalInterface()
public interface ProjectileComponent$IProjectileFactory<P extends NuProjectileEntity> {
	public abstract P createProjectile(LivingEntity var0);
}