package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class JumpOutOfHoleGoal extends TickedGoal<Mob> {
	// Fields
	private static final int COOLDOWN = 60;
	private LivingEntity target;
	private boolean pushed;
	private final float jumpHeight;

	public JumpOutOfHoleGoal(Mob var1) {
		this(var1,1.3F);
	}
	public JumpOutOfHoleGoal(Mob var1, float var2) {
		super(var1);
		this.jumpHeight = var2;
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (!(this.hasTimePassedSinceLastEnd(60F))){
				return false;
			}else{
				this.target = this.entity.getTarget();
				if (GoalHelper.hasSolidBlockAbove((LivingEntity)(this.entity))){
					return false;
				}else{
					if (!(GoalHelper.hasBlockInFace((LivingEntity)(this.entity)))){
						return false;
					}else{
						return true;
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if (this.hasTimePassedSinceStart(100F)){
			return false;
		}else{
			if (this.pushed){
				return false;
			}else{
				return true;
			}
		}
	}
	public void start() {
		super.start();
		this.pushed = false;
	}
	public void tick() {
		super.tick();
		if (!(this.pushed)){
			if (((this.getTickCount()) % (40L)) == (0L)){
				AbilityHelper.setDeltaMovement((Entity)(this.entity),0D,(double)(this.jumpHeight),0D);
			}
			if ((GoalHelper.canSee(this.entity,(Entity)(this.target))) && (!(GoalHelper.hasBlockInFace((LivingEntity)(this.entity))))){
				GoalHelper.lookAtEntity(this.entity,(Entity)(this.target));
				Vec3 var1 = this.target.position().subtract(this.entity.position()).normalize().multiply(2D,0D,2D);
				AbilityHelper.setDeltaMovement((Entity)(this.entity),var1.x,(this.entity.getDeltaMovement().y()) + (0.2D),var1.z);
				this.pushed = true;
			}else{
				return;
			}
		}
	}
	public void stop() {
		super.stop();
	}
}