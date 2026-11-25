package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface()
public interface ProjectileComponent$IAfterShootEvent {
	public abstract void triggerAfterEvent(LivingEntity var0);
}