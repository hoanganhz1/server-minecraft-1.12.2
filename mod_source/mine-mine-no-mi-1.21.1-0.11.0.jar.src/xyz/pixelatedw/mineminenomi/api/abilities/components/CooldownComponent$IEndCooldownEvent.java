package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@FunctionalInterface()
public interface CooldownComponent$IEndCooldownEvent {
	public abstract void endCooldown(LivingEntity var0, IAbility var1);
}