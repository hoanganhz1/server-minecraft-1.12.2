package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface()
public interface Ability$IOnTickEvent {
	public abstract void tick(LivingEntity var0, IAbility var1);
}