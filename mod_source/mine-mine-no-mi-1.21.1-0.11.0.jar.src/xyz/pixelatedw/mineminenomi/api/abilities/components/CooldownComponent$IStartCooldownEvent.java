package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@FunctionalInterface()
public interface CooldownComponent$IStartCooldownEvent {
	public abstract void startCooldown(LivingEntity var0, IAbility var1);
}