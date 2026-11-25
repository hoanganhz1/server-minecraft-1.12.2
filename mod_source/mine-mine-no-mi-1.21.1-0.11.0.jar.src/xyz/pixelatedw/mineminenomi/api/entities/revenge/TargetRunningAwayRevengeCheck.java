package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class TargetRunningAwayRevengeCheck extends BaseRevengeCheck implements ITickRevengeCheck {
	// Fields
	private final int revengeGain;
	private final float distance;

	public TargetRunningAwayRevengeCheck(int var1, float var2) {
		this.revengeGain = var1;
		this.distance = var2;
	}
	public boolean check(LivingEntity var1) {
		if (var1 instanceof Mob){
			LivingEntity var2 = ((Mob)(var1)).getTarget();
			if (((var2) != (null)) && (var2.isAlive())){
				if ((var1.distanceTo((Entity)(var2))) > ((this.distance) * (this.distance))){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	public int revengeMeterGain() {
		return this.revengeGain;
	}
}