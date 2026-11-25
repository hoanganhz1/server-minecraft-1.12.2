package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface()
public interface PassiveAbility$IOnEquipEvent {
	public abstract void onEquip(LivingEntity var0, PassiveAbility var1);
}