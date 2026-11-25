package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface()
public interface RepeaterAbility$IOnTriggerShootEvent {
	public abstract void customShootLogic(LivingEntity var0);
}