package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@FunctionalInterface()
public interface DisableComponent$IEndDisableEvent {
	public abstract void endDisable(LivingEntity var0, IAbility var1);
}