package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@FunctionalInterface()
public interface ChargeComponent$IDuringChargingEvent {
	public abstract void duringCharging(LivingEntity var0, IAbility var1);
}