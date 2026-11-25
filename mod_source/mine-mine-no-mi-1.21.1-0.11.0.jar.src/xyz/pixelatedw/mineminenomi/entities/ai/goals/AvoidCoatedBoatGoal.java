package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.data.entity.KairosekiCoatingData;

public class AvoidCoatedBoatGoal extends Goal {
	// Fields
	private PathfinderMob entity;
	private Vec3 toAvoid;
	private long lastCheck;
	private Path path;
	private final PathNavigation pathNav;
	private double speed;
	private double sprintSpeed;

	public AvoidCoatedBoatGoal(PathfinderMob var1) {
		this.speed = 2D;
		this.sprintSpeed = 4D;
		this.entity = var1;
		this.pathNav = var1.getNavigation();
	}
	public void setSpeed(double var1) {
		this.speed = var1;
	}
	public void setSprintSpeed(double var1) {
		this.sprintSpeed = var1;
	}
	public boolean canUse() {
		if (((this.entity) != (null)) && (this.entity.isAlive())){
			if (((System.currentTimeMillis()) - (this.lastCheck)) < (1000L)){
				return false;
			}else{
				Iterator var1 = this.entity.level().getEntitiesOfClass(Boat.class,this.entity.getBoundingBox().inflate(20D,20D,20D)).iterator();
				do{
					if (var1.hasNext()){
						Boat var2 = ((Boat)(var1.next()));
						LivingEntity var3 = var2.getControllingPassenger();
						if (((var3) != (null)) && ((var3 instanceof LivingEntity) && ((var2.isAlive()) && (GoalHelper.canSee((Mob)(this.entity),(Entity)(var2)))))){
							int var4 = ((Integer)(KairosekiCoatingData.get((Entity)(var2)).map((Function)(((Function<KairosekiCoatingData, Integer>)(KairosekiCoatingData::getCoatingLevel)))).orElse(Integer.valueOf(0)))).intValue();
							if ((var4) > (0)){
								int var5 = (int)((128F) * (((float)(var4)) / (5F)));
								var5 = Math.max(24,var5);
								this.toAvoid = DefaultRandomPos.getPosTowards(this.entity,var5,(var5) / (2),new Vec3(var2.getX(),var2.getY(),var2.getZ()),1.5707963267948966D);
								if ((this.toAvoid) == (null)){
									continue;
								}else{
									this.path = this.entity.getNavigation().createPath(this.toAvoid.x,this.toAvoid.y,this.toAvoid.z,0);
									if ((this.path) == (null)){
										continue;
									}else{
										return true;
									}
								}
							}else{
								continue;
							}
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				return false;
			}
		}else{
			return false;
		}
	}
	public boolean canContinueToUse() {
		return !(this.pathNav.isDone());
	}
	public void start() {
		this.pathNav.moveTo(this.path,this.speed);
	}
	public void stop() {
		this.toAvoid = null;
	}
	public void tick() {
		if ((this.entity.distanceToSqr(this.toAvoid)) < (49D)){
			this.entity.getNavigation().setSpeedModifier(this.sprintSpeed);
		}else{
			this.entity.getNavigation().setSpeedModifier(this.speed);
		}
		this.pathNav.moveTo(this.path,this.speed);
	}
}