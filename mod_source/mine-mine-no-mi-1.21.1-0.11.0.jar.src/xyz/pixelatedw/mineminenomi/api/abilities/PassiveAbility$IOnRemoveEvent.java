package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface()
public interface PassiveAbility$IOnRemoveEvent {
	public abstract void onRemove(LivingEntity var0, PassiveAbility var1);
}