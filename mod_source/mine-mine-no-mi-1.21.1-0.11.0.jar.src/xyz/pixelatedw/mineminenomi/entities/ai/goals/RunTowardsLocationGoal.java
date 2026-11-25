package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;

public class RunTowardsLocationGoal<E extends PathfinderMob> extends TickedGoal<E> {
	// Fields
	private Path path;
	private double speed;
	private Vec3 location;

	public RunTowardsLocationGoal(E var1, double var2, Vec3 var3) {
		super(var1);
		this.speed = var2;
		this.location = var3;
	}
	public boolean canUse() {
		if ((((PathfinderMob)(this.entity)).distanceToSqr(this.location)) <= (25D)){
			return false;
		}else{
			this.path = ((PathfinderMob)(this.entity)).getNavigation().createPath(BlockPos.containing((Position)(this.location)),1);
			if ((this.path) == (null)){
				return false;
			}else{
				return true;
			}
		}
	}
	public boolean canContinueToUse() {
		if ((((PathfinderMob)(this.entity)).distanceToSqr(this.location)) <= (25D)){
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
		((PathfinderMob)(this.entity)).getNavigation().moveTo(this.path,this.speed);
	}
	public void stop() {
		super.stop();
	}
}