package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class DashDodgeTargetGoal extends TickedGoal<Mob> {
	// Fields
	private LivingEntity target;
	private float ticksBetweenDashes;
	private float dashDistance;
	private boolean isSurrounded;
	private java.util.function.Predicate<LivingEntity> canUseTest;

	public DashDodgeTargetGoal(Mob var1, float var2, float var3) {
		super(var1);
		this.canUseTest = Predicates.alwaysTrue();
		this.ticksBetweenDashes = var2;
		this.dashDistance = var3;
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			this.target = this.entity.getTarget();
			if (!(this.hasTimePassedSinceLastEnd(this.ticksBetweenDashes))){
				return false;
			}else{
				if (!(GoalHelper.canMove((LivingEntity)(this.entity)))){
					return false;
				}else{
					if (GoalHelper.isOutsideDistance((LivingEntity)(this.entity),this.target,10D)){
						return false;
					}else{
						if ((WyHelper.getDifferenceToFloor((Entity)(this.entity))) > (3D)){
							return false;
						}else{
							if (!(GoalHelper.isEntityInView(this.target,(Entity)(this.entity)))){
								return false;
							}else{
								if (!(((java.util.function.Predicate)(this.canUseTest)).test((Object)(this.entity)))){
									return false;
								}else{
									return true;
								}
							}
						}
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		return false;
	}
	public void start() {
		super.start();
		if (this.isSurrounded){

		}else{
			GoalHelper.lookAtEntity(this.entity,(Entity)(this.target));
			Vec3 var1 = this.target.position().subtract(this.entity.position()).normalize().multiply((double)(this.dashDistance),1D,(double)(this.dashDistance));
			AbilityHelper.setDeltaMovement((Entity)(this.entity),-(var1.x),0.1D,-(var1.z));
		}
	}
	public void tick() {
		super.tick();
	}
	public DashDodgeTargetGoal setCanUseTest(java.util.function.Predicate<LivingEntity> var1) {
		this.canUseTest = var1;
		return this;
	}
}