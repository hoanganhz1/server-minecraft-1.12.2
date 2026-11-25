package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@FunctionalInterface()
public interface GrabEntityComponent$IOnPullStartEvent {
	public abstract void pullStart(LivingEntity var0, IAbility var1);
}