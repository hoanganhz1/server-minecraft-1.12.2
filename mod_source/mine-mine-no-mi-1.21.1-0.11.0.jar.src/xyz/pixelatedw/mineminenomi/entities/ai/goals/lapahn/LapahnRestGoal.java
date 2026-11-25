package xyz.pixelatedw.mineminenomi.entities.ai.goals.lapahn;

import java.util.EnumSet;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.LapahnEntity;

public class LapahnRestGoal extends TickedGoal<LapahnEntity> {
	// Fields
	private Interval interval;
	private int restTime;
	private int nextRestTime;
	private float startHealth;

	public LapahnRestGoal(LapahnEntity var1) {
		super(var1);
		this.interval = new Interval(40);
		this.nextRestTime = 2;
		this.setFlags(EnumSet.of(Goal.Flag.JUMP,Goal.Flag.MOVE,Goal.Flag.LOOK));
	}
	public boolean canUse() {
		if (!(((LapahnEntity)(this.entity)).isIdling())){
			return false;
		}else{
			if (!(this.interval.canTick())){
				return false;
			}else{
				if ((this.getLastEndTick()) <= (0L)){
					this.setLastEndTick(((LapahnEntity)(this.entity)).level().getGameTime());
				}
				if (!(this.hasTimePassedSinceLastEnd(WyHelper.minutesToTicks((float)(this.nextRestTime))))){
					return false;
				}else{
					if (GoalHelper.hasAliveTarget(this.entity)){
						return false;
					}else{
						return true;
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if (GoalHelper.hasAliveTarget(this.entity)){
			return false;
		}else{
			if ((((LapahnEntity)(this.entity)).getHealth()) < (this.startHealth)){
				return false;
			}else{
				return !(this.hasTimePassedSinceStart(WyHelper.secondsToTicks((float)(this.restTime))));
			}
		}
	}
	public void start() {
		super.start();
		((LapahnEntity)(this.entity)).setResting(true);
		this.restTime = (20) + (((LapahnEntity)(this.entity)).getRandom().nextInt(20));
		this.nextRestTime = (2) + (((LapahnEntity)(this.entity)).getRandom().nextInt(3));
		((LapahnEntity)(this.entity)).getNavigation().stop();
		this.startHealth = ((LapahnEntity)(this.entity)).getHealth();
	}
	public void stop() {
		super.stop();
		((LapahnEntity)(this.entity)).setResting(false);
	}
}