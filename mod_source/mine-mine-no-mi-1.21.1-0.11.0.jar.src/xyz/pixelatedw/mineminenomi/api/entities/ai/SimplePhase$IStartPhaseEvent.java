package xyz.pixelatedw.mineminenomi.api.entities.ai;

import net.minecraft.world.entity.Mob;

@FunctionalInterface()
public interface SimplePhase$IStartPhaseEvent<E extends Mob> {
	public abstract void startPhase(E var0);
}