package xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong;

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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity$TrainingMethod;

public class DugongTrainGoal extends TickedGoal<AbstractDugongEntity> {
	// Fields
	private Interval interval;
	private int trainingTime;
	private int nextTrainingTime;

	public DugongTrainGoal(AbstractDugongEntity var1) {
		super(var1);
		this.interval = new Interval(10);
		this.nextTrainingTime = 2;
		this.setFlags(EnumSet.of(Goal.Flag.JUMP,Goal.Flag.MOVE,Goal.Flag.LOOK));
	}
	public boolean canUse() {
		if (!(this.interval.canTick())){
			return false;
		}else{
			if (!(((AbstractDugongEntity)(this.entity)).isIdling())){
				return false;
			}else{
				if ((this.getLastEndTick()) <= (0L)){
					this.setLastEndTick(((AbstractDugongEntity)(this.entity)).level().getGameTime());
				}
				if (!(this.hasTimePassedSinceLastEnd(WyHelper.minutesToTicks((float)(this.nextTrainingTime))))){
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
		if (GoalHelper.shouldMove(this.entity)){
			return false;
		}else{
			if (GoalHelper.hasAliveTarget(this.entity)){
				return false;
			}else{
				return !(this.hasTimePassedSinceStart(WyHelper.secondsToTicks((float)(this.trainingTime))));
			}
		}
	}
	public void start() {
		super.start();
		int var1 = (1) + (AbstractDugongEntity$TrainingMethod.values()[((AbstractDugongEntity)(this.entity)).getRandom().nextInt(AbstractDugongEntity$TrainingMethod.values().length)].ordinal());
		((AbstractDugongEntity)(this.entity)).setTraining(var1);
		this.trainingTime = (10) + (((AbstractDugongEntity)(this.entity)).getRandom().nextInt(5));
		this.nextTrainingTime = (2) + (((AbstractDugongEntity)(this.entity)).getRandom().nextInt(3));
		((AbstractDugongEntity)(this.entity)).getNavigation().stop();
	}
	public void stop() {
		super.stop();
		((AbstractDugongEntity)(this.entity)).stopTraining();
	}
}