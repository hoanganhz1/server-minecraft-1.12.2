package xyz.pixelatedw.mineminenomi.api.entities.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public abstract class TickedGoal<E extends Mob> extends Goal {
	// Fields
	protected E entity;
	private long startTick;
	private long lastEndTick;
	private long tickCount;

	public TickedGoal(E var1) {
		this.entity = var1;
	}
	public void start() {
		super.start();
		this.startTick = this.entity.level().getGameTime();
	}
	public void tick() {
		super.tick();
		this.tickCount = (this.tickCount) + (1L);
	}
	public void stop() {
		super.stop();
		this.lastEndTick = this.entity.level().getGameTime();
	}
	public long getStartTick() {
		return this.startTick;
	}
	public void setLastEndTick(long var1) {
		this.lastEndTick = var1;
	}
	public long getLastEndTick() {
		return this.lastEndTick;
	}
	public long getTickCount() {
		return this.tickCount;
	}
	public boolean hasTimePassedSinceStart(float var1) {
		return ((float)(this.entity.level().getGameTime())) >= (((float)(this.getStartTick())) + (var1));
	}
	public boolean hasTimePassedSinceLastEnd(float var1) {
		if ((this.getLastEndTick()) == (0L)){
			this.setLastEndTick(this.entity.level().getGameTime());
		}
		return ((float)(this.entity.level().getGameTime())) >= (((float)(this.getLastEndTick())) + (var1));
	}
}