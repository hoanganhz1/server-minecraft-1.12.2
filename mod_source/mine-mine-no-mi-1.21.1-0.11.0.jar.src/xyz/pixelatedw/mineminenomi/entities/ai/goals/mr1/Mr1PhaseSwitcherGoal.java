package xyz.pixelatedw.mineminenomi.entities.ai.goals.mr1;

import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr1Entity;

public class Mr1PhaseSwitcherGoal extends TickedGoal<Mr1Entity> {
	// Fields
	private float ultiReuseTime;

	public Mr1PhaseSwitcherGoal(Mr1Entity var1) {
		super(var1);
		this.ultiReuseTime = (var1.getChallengeInfo().isDifficultyHard()) ? (WyHelper.minutesToTicks(3F)) : (WyHelper.minutesToTicks(6F));
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (((double)(((Mr1Entity)(this.entity)).getHealth())) > (WyHelper.percentage(50D,(double)(((Mr1Entity)(this.entity)).getMaxHealth())))){
				return false;
			}else{
				if (!(((Mr1Entity)(this.entity)).isUltiAvailable())){
					return false;
				}else{
					if (((this.getLastEndTick()) > (0L)) && (!(this.hasTimePassedSinceLastEnd(this.ultiReuseTime)))){
						return false;
					}else{
						return true;
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if (!(((Mr1Entity)(this.entity)).isUltiAvailable())){
			return false;
		}else{
			return true;
		}
	}
	public void start() {
		super.start();
		((Mr1Entity)(this.entity)).startUltiPhase();
	}
	public void tick() {
		super.tick();
	}
	public void stop() {
		super.stop();
		((Mr1Entity)(this.entity)).startSecondPhase();
	}
}