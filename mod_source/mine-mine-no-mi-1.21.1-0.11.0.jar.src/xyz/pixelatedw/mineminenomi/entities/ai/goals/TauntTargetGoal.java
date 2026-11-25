package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class TauntTargetGoal extends TickedGoal<Mob> {
	// Fields
	private LivingEntity target;
	private int lastUpdateTick;

	public TauntTargetGoal(Mob var1) {
		super(var1);
		this.lastUpdateTick = 20;
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			this.target = this.entity.getTarget();
			if (!(GoalHelper.canSee(this.entity,(Entity)(this.target)))){
				return false;
			}else{
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
			return true;
		}
	}
	public void tick() {
		super.tick();
		GoalHelper.lookAtEntity(this.entity,(Entity)(this.target));
		this.lastUpdateTick = (this.lastUpdateTick) - (1);
		if ((this.lastUpdateTick) <= (10)){
			this.entity.setPose(Pose.CROUCHING);
		}else{
			this.entity.setPose(Pose.STANDING);
		}
		if ((this.lastUpdateTick) <= (0)){
			this.lastUpdateTick = 20;
		}
	}
	public void stop() {
		super.stop();
		this.entity.setPose(Pose.STANDING);
	}
}