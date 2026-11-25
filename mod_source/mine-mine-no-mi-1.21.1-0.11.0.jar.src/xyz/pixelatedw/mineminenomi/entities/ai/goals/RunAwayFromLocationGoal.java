package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;

public class RunAwayFromLocationGoal<E extends PathfinderMob> extends TickedGoal<E> {
	// Fields
	private double speed;
	private Vec3 location;
	private int minDistance;
	private int maxDistance;

	public RunAwayFromLocationGoal(E var1, double var2, Vec3 var3) {
		super(var1);
		this.minDistance = 20;
		this.maxDistance = 30;
		this.speed = var2;
		this.location = var3;
	}
	public RunAwayFromLocationGoal setRunningDistance(int var1, int var2) {
		this.minDistance = var1;
		this.maxDistance = var2;
		return this;
	}
	public boolean canUse() {
		if ((((PathfinderMob)(this.entity)).distanceToSqr(this.location)) > ((double)((this.minDistance) * (this.minDistance)))){
			return false;
		}else{
			return true;
		}
	}
	public boolean canContinueToUse() {
		if ((((PathfinderMob)(this.entity)).distanceToSqr(this.location)) <= ((double)((this.maxDistance) * (this.maxDistance)))){
			return false;
		}else{
			return true;
		}
	}
	public void start() {
		super.start();
	}
	public void tick() {
		super.tick();
		Mob var1 = this.entity;
		if (var1 instanceof Villager){
			Villager var2 = ((Villager)(var1));
			var2.getBrain().stopAll(((ServerLevel)(((PathfinderMob)(this.entity)).level())),(LivingEntity)(var2));
		}
		BlockPos var2_1 = ((PathfinderMob)(this.entity)).getNavigation().getTargetPos();
		int var3 = (((var2_1) != (null)) && ((((PathfinderMob)(this.entity)).distanceToSqr((double)(var2_1.getX()),(double)(var2_1.getY()),(double)(var2_1.getZ()))) < (25D))) ? (1) : (0);
		if ((!(((PathfinderMob)(this.entity)).getNavigation().isDone())) && ((var3) == (0))){

		}else{
			Path var4 = this.findRunPosition();
			if ((var4) != (null)){
				((PathfinderMob)(this.entity)).getNavigation().moveTo(var4,this.speed);
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
				BlockPos var2 = WyHelper.findValidGroundLocation((Entity)(this.entity),((PathfinderMob)(this.entity)).blockPosition(),this.maxDistance,this.minDistance);
				if (((((!(NuWorld.isChallengeDimension(((PathfinderMob)(this.entity)).level()))) || ((NuWorld.isChallengeDimension(((PathfinderMob)(this.entity)).level())) && (NuWorld.isWithinChallengeArenaBounds(((PathfinderMob)(this.entity)).level(),var2)))) ? (1) : (0)) != (0)) && (var2.closerThan((Vec3i)(((PathfinderMob)(this.entity)).blockPosition()),(double)(this.minDistance)))){
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
	}
}