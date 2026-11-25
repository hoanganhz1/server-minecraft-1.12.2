package xyz.pixelatedw.mineminenomi.entities.ai.goals.mr0;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr0Entity;

public class Mr0PhaseSwitcherGoal extends TickedGoal<Mr0Entity> {
	public Mr0PhaseSwitcherGoal(Mr0Entity var1) {
		super(var1);
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if ((((Mr0Entity)(this.entity)).isFirstPhaseActive()) && (this.trySwitchToSecondPhase())){
				((Mr0Entity)(this.entity)).startSecondPhase();
				return true;
			}else{
				if ((((Mr0Entity)(this.entity)).isSecondPhaseActive()) && (this.trySwitchToThirdPhase())){
					((Mr0Entity)(this.entity)).startThirdPhase();
					return true;
				}else{
					return false;
				}
			}
		}
	}
	private boolean trySwitchToSecondPhase() {
		if (GoalHelper.hasHealthAbovePercentage((LivingEntity)(this.entity),70D)){
			return false;
		}else{
			return true;
		}
	}
	private boolean trySwitchToThirdPhase() {
		if (!(((Mr0Entity)(this.entity)).isDifficultyHardOrAbove())){
			return false;
		}else{
			if ((((Mr0Entity)(this.entity)).getPainMeter().getRevengePercentage()) > (0.5F)){
				return true;
			}else{
				if (GoalHelper.hasHealthAbovePercentage((LivingEntity)(this.entity),50D)){
					return false;
				}else{
					return true;
				}
			}
		}
	}
	public boolean canContinueToUse() {
		return false;
	}
}