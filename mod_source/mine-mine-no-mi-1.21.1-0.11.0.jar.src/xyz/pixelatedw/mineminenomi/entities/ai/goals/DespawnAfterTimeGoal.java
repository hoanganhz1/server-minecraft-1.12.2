package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;

public class DespawnAfterTimeGoal extends TickedGoal<Mob> {
	// Fields
	private final int ticksTilDespawn;

	public DespawnAfterTimeGoal(Mob var1, int var2) {
		super(var1);
		this.ticksTilDespawn = var2;
	}
	public boolean canUse() {
		return true;
	}
	public boolean canContinueToUse() {
		if (this.hasTimePassedSinceStart((float)(this.ticksTilDespawn))){
			return false;
		}else{
			return true;
		}
	}
	public void stop() {
		super.stop();
		this.entity.remove(Entity.RemovalReason.DISCARDED);
	}
}