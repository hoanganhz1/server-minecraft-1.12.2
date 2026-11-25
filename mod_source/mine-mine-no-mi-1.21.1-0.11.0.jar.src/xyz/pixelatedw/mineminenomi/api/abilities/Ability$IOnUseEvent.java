package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface()
public interface Ability$IOnUseEvent {
	public abstract void onUse(LivingEntity var0, IAbility var1);
}