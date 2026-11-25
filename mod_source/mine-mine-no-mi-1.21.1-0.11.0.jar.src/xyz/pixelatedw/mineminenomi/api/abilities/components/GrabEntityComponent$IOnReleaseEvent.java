package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@FunctionalInterface()
public interface GrabEntityComponent$IOnReleaseEvent {
	public abstract void release(LivingEntity var0, LivingEntity var1, IAbility var2);
}