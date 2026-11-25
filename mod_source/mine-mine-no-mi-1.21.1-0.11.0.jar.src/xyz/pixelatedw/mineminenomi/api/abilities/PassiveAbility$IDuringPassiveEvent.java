package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface()
public interface PassiveAbility$IDuringPassiveEvent {
	public abstract void duringPassive(LivingEntity var0);
}