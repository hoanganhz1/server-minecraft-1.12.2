package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class GapCloserGoal extends TickedGoal<Mob> {
	// Fields
	private LivingEntity target;
	private PhysicalHitRevengeCheck hitCheck;
	private int maxCount;
	private double speed;

	public GapCloserGoal(Mob var1) {
		this(var1,1.7D,3);
	}
	public GapCloserGoal(Mob var1, double var2, int var3) {
		super(var1);
		this.hitCheck = new PhysicalHitRevengeCheck(0);
		this.speed = var2;
		this.maxCount = var3;
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			this.target = this.entity.getTarget();
			if (!(GoalHelper.canMove((LivingEntity)(this.entity)))){
				return false;
			}else{
				if ((!(GoalHelper.isWithinDistance((LivingEntity)(this.entity),this.target,2D))) && (!(GoalHelper.isOutsideDistance((LivingEntity)(this.entity),this.target,5D)))){
					return true;
				}else{
					return false;
				}
			}
		}
	}
	public void start() {
		this.hitCheck.resetMarkers();
		double var1 = ((double)((-(Mth.sin(((this.entity.getXRot()) / (180F)) * (3.1415927F)))) * (Mth.cos(((this.entity.getYRot()) / (180F)) * (3.1415927F))))) * (0.4D);
		double var2 = ((double)((Mth.cos(((this.entity.getXRot()) / (180F)) * (3.1415927F))) * (Mth.cos(((this.entity.getYRot()) / (180F)) * (3.1415927F))))) * (0.4D);
		double var3 = (double)(Mth.sqrt((float)((((var1) * (var1)) + ((this.entity.getDeltaMovement().y) * (this.entity.getDeltaMovement().y))) + ((var2) * (var2)))));
		var1 = (var1) / (var3);
		var2 = (var2) / (var3);
		var1 = (var1) + (((this.entity.level().random.nextGaussian()) * (0.007499999832361937D)) * (1D));
		var2 = (var2) + (((this.entity.level().random.nextGaussian()) * (0.007499999832361937D)) * (1D));
		var1 = (var1) * (this.speed);
		var2 = (var2) * (this.speed);
		AbilityHelper.setDeltaMovement((Entity)(this.entity),new Vec3(var1,0.3D,var2));
	}
}