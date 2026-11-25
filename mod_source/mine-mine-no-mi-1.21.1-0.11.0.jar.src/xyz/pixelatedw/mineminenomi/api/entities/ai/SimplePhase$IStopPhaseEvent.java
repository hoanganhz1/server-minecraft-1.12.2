package xyz.pixelatedw.mineminenomi.api.entities.ai;

import net.minecraft.world.entity.Mob;

@FunctionalInterface()
public interface SimplePhase$IStopPhaseEvent<E extends Mob> {
	public abstract void stopPhase(E var0);
}