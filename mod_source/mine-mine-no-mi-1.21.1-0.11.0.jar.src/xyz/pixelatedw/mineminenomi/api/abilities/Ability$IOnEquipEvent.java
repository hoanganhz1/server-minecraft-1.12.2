package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface()
public interface Ability$IOnEquipEvent {
	public abstract void onEquip(LivingEntity var0, IAbility var1);
}