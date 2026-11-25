package xyz.pixelatedw.mineminenomi.api.entities.ai;

import net.minecraft.world.entity.Mob;

public class SimplePhase<E extends Mob> extends NPCPhase<E> {
	// Fields
	private SimplePhase$IStartPhaseEvent<E> startPhaseEvent;
	private SimplePhase$IStopPhaseEvent<E> stopPhaseEvent;

	public SimplePhase(String var1, E var2) {
		super(var1,var2);
	}
	public SimplePhase(String var1, E var2, SimplePhase$IStartPhaseEvent<E> var3) {
		super(var1,var2);
		this.startPhaseEvent = var3;
	}
	public SimplePhase(String var1, E var2, SimplePhase$IStartPhaseEvent<E> var3, SimplePhase$IStopPhaseEvent<E> var4) {
		super(var1,var2);
		this.startPhaseEvent = var3;
		this.stopPhaseEvent = var4;
	}
	public void start(E var1) {
		if ((this.startPhaseEvent) != (null)){
			this.startPhaseEvent.startPhase(var1);
		}
	}
	public void stop(E var1) {
		if ((this.stopPhaseEvent) != (null)){
			this.stopPhaseEvent.stopPhase(var1);
		}
	}
	public void doTick() {
	}
}