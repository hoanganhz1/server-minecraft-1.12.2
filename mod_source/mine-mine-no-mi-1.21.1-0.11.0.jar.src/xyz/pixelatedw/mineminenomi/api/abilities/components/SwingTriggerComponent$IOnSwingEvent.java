package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@FunctionalInterface()
public interface SwingTriggerComponent$IOnSwingEvent {
	public abstract void swing(LivingEntity var0, IAbility var1);
}