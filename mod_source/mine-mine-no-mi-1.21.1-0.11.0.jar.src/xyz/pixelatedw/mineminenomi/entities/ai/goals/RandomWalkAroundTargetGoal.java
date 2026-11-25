package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;

public class RandomWalkAroundTargetGoal extends TickedGoal<PathfinderMob> {
	// Fields
	private LivingEntity target;
	private BlockPos targetPos;
	private double speed;
	private int ticksRunning;
	private final int defaultRunningTicks;
	private Interval restBetweenRuns;
	private int minDistance;
	private int maxDistance;

	public RandomWalkAroundTargetGoal(PathfinderMob var1, double var2, int var3, int var4) {
		super(var1);
		this.minDistance = 20;
		this.maxDistance = 30;
		this.speed = var2;
		this.ticksRunning = var3;
		this.defaultRunningTicks = var3;
		this.restBetweenRuns = Interval.startAtZero(var4);
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}
	public RandomWalkAroundTargetGoal setRunningDistance(int var1, int var2) {
		this.minDistance = var1;
		this.maxDistance = var2;
		return this;
	}
	public boolean canUse() {
		if (!(this.restBetweenRuns.canTick())){
			return false;
		}else{
			if (!(GoalHelper.hasAliveTarget(this.entity))){
				return false;
			}else{
				this.target = ((PathfinderMob)(this.entity)).getTarget();
				if (GoalHelper.isWithinDistance((LivingEntity)(this.entity),this.target,10D)){
					return false;
				}else{
					return true;
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if ((this.ticksRunning) <= (0)){
				return false;
			}else{
				return true;
			}
		}
	}
	public void start() {
		super.start();
		this.targetPos = WyHelper.findValidGroundLocation((Entity)(this.entity),this.target.blockPosition(),this.maxDistance,this.minDistance);
	}
	public void tick() {
		super.tick();
		this.ticksRunning = (this.ticksRunning) - (1);
		if (((this.ticksRunning) % (20)) == (0)){
			this.findRunPosition(0);
		}
		if (((PathfinderMob)(this.entity)).getNavigation().isDone()){
			this.findRunPosition(0);
		}
		((PathfinderMob)(this.entity)).getNavigation().moveTo((double)(this.targetPos.getX()),(double)(this.targetPos.getY()),(double)(this.targetPos.getZ()),this.speed);
	}
	public void stop() {
		super.stop();
		this.ticksRunning = this.defaultRunningTicks;
	}
	private void findRunPosition(int var1) {
		if ((var1) == (0)){

		}
		BlockPos var2 = WyHelper.findValidGroundLocation((Entity)(this.entity),this.target.blockPosition(),this.maxDistance,this.minDistance);
		if (((var1) < (5)) && (!(var2.closerThan((Vec3i)(this.target.blockPosition()),(double)(this.maxDistance))))){
			this.findRunPosition(var1++);
			return;
		}else{
			this.targetPos = var2;
			return;
		}
	}
}