package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.Entity;

@FunctionalInterface()
public interface AbilityExplosion$IOnEntityHit {
	public abstract boolean canHit(Entity var0);
}