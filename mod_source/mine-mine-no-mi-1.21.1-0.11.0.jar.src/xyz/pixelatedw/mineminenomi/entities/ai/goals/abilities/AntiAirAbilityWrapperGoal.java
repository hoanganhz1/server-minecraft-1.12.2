package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class AntiAirAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
	// Fields
	private LivingEntity target;
	private float minDistance;
	private float maxDistance;

	public AntiAirAbilityWrapperGoal(Mob var1, AbilityCore<A> var2) {
		super(var1,var2);
		this.minDistance = 4F;
		this.maxDistance = 15F;
	}
	public boolean canUseWrapper() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (!(GoalHelper.canMove((LivingEntity)(this.entity)))){
				return false;
			}else{
				this.target = this.entity.getTarget();
				if (!(GoalHelper.canSee(this.entity,(Entity)(this.target)))){
					return false;
				}else{
					if ((AbilityHelper.getDifferenceToFloor((Entity)(this.entity))) > (3D)){
						return false;
					}else{
						if ((this.minDistance) > (0F)){
							if ((AbilityHelper.getDifferenceToFloor((Entity)(this.target))) < ((double)(this.minDistance))){
								return false;
							}else{
								if (GoalHelper.isWithinDistance((LivingEntity)(this.entity),this.target,(double)(this.minDistance))){
									return false;
								}
							}
						}
						if (((this.maxDistance) > (0F)) && (GoalHelper.isOutsideDistance((LivingEntity)(this.entity),this.target,(double)(this.maxDistance)))){
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
			if (!(GoalHelper.canMove((LivingEntity)(this.entity)))){
				return false;
			}else{
				return true;
			}
		}
	}
	public void startWrapper() {
		GoalHelper.lookAtEntity(this.entity,(Entity)(this.target));
	}
	public void tickWrapper() {
		GoalHelper.lookAtEntity(this.entity,(Entity)(this.target));
	}
	public void stopWrapper() {
	}
}