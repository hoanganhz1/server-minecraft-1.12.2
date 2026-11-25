package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class TargetFlyStallingRevengeCheck extends BaseRevengeCheck implements ITickRevengeCheck {
	// Fields
	private final int revengeGain;

	public TargetFlyStallingRevengeCheck(int var1) {
		this.revengeGain = var1;
	}
	public boolean check(LivingEntity var1) {
		if (var1 instanceof Mob){
			LivingEntity var2 = ((Mob)(var1)).getTarget();
			if (((var2) != (null)) && (var2.isAlive())){
				if ((var2 instanceof Player) && (((Player)(var2)).getAbilities().flying)){
					return true;
				}else{
					if ((AbilityHelper.getDifferenceToFloor((Entity)(var2))) > (10D)){
						return true;
					}else{
						return false;
					}
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