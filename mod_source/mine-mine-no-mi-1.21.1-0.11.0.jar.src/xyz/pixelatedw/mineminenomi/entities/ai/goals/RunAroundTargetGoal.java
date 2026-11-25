package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class RunAroundTargetGoal<E extends PathfinderMob> extends TickedGoal<E> {
	// Fields
	private LivingEntity target;
	private double speed;
	private int ticksRunning;
	private final int defaultRunningTicks;
	private final int restBetweenRuns;
	private int restTicks;
	private int minDistance;
	private int maxDistance;
	private int uses;

	public RunAroundTargetGoal(E var1, double var2, int var3, int var4) {
		super(var1);
		this.minDistance = 10;
		this.maxDistance = 30;
		this.uses = 0;
		this.speed = var2;
		this.ticksRunning = var3;
		this.defaultRunningTicks = var3;
		this.restBetweenRuns = var4;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}
	public RunAroundTargetGoal<E> setRunningDistance(int var1, int var2) {
		this.minDistance = var1;
		this.maxDistance = var2;
		return this;
	}
	public boolean canUse() {
		if ((this.restTicks) > (0)){
			this.restTicks = (this.restTicks) - (1);
			return false;
		}else{
			if (!(GoalHelper.hasAliveTarget(this.entity))){
				return false;
			}else{
				this.target = ((PathfinderMob)(this.entity)).getTarget();
				return true;
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
	}
	public void tick() {
		super.tick();
		this.ticksRunning = (this.ticksRunning) - (1);
		BlockPos var1 = ((PathfinderMob)(this.entity)).getNavigation().getTargetPos();
		int var2 = (((var1) != (null)) && ((((PathfinderMob)(this.entity)).distanceToSqr((double)(var1.getX()),(double)(var1.getY()),(double)(var1.getZ()))) < (25D))) ? (1) : (0);
		if ((!(((PathfinderMob)(this.entity)).getNavigation().isDone())) && ((var2) == (0))){

		}else{
			Path var3 = this.findRunPosition();
			if ((var3) != (null)){
				((PathfinderMob)(this.entity)).getNavigation().moveTo(var3,this.speed);
			}else{
				return;
			}
		}
	}
	@Nullable()
	private Path findRunPosition() {
		int var1 = 0;
		do{
			if ((var1) < (20)){
				BlockPos var2 = WyHelper.findValidGroundLocation((Entity)(this.target),this.target.blockPosition(),this.maxDistance,this.minDistance);
				if (((((NuWorld.isChallengeDimension(((PathfinderMob)(this.entity)).level())) && (NuWorld.isWithinChallengeArenaBounds(((PathfinderMob)(this.entity)).level(),var2))) ? (1) : (0)) != (0)) && (!(var2.closerThan((Vec3i)(this.target.blockPosition()),(double)(this.minDistance))))){
					Path var3 = ((PathfinderMob)(this.entity)).getNavigation().createPath(var2,1);
					if (((var3) != (null)) && (var3.canReach())){
						return var3;
					}
				}
				var1++;
				continue;
			}else{
				break;
			}
		} while (true);
		return null;
	}
	public void stop() {
		super.stop();
		this.ticksRunning = this.defaultRunningTicks;
		this.restTicks = this.restBetweenRuns;
	}
}