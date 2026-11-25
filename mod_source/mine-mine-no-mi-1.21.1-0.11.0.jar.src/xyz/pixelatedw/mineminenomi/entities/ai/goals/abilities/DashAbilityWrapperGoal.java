package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class DashAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
	// Fields
	private LivingEntity target;
	private float activationDistance;
	private float maxDistance;

	public DashAbilityWrapperGoal(Mob var1, AbilityCore<A> var2) {
		super(var1,var2);
		this.activationDistance = 15F;
		this.maxDistance = 100F;
	}
	public boolean canUseWrapper() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (!(GoalHelper.canMove((LivingEntity)(this.entity)))){
				return false;
			}else{
				if (!(this.entity.onGround())){
					return false;
				}else{
					this.target = this.entity.getTarget();
					if (!(GoalHelper.canSee(this.entity,(Entity)(this.target)))){
						return false;
					}else{
						if (GoalHelper.isOutsideDistance((LivingEntity)(this.entity),this.target,(double)(this.activationDistance))){
							return false;
						}else{
							return true;
						}
					}
				}
			}
		}
	}
	public boolean canContinueToUseWrapper() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (((this.maxDistance) > (0F)) && (GoalHelper.isOutsideDistance((LivingEntity)(this.entity),this.target,(double)(this.maxDistance)))){
				return false;
			}else{
				return true;
			}
		}
	}
	public void startWrapper() {
		this.entity.getNavigation().stop();
		GoalHelper.lookAtEntity(this.entity,(Entity)(this.target));
	}
	public void tickWrapper() {
		GoalHelper.lookAtEntity(this.entity,(Entity)(this.target));
	}
	public void stopWrapper() {
		this.entity.getNavigation().stop();
	}
}