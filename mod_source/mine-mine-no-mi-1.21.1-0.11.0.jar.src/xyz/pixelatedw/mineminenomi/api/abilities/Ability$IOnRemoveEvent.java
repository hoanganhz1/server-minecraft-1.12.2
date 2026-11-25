package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface()
public interface Ability$IOnRemoveEvent {
	public abstract void onRemove(LivingEntity var0, IAbility var1);
}