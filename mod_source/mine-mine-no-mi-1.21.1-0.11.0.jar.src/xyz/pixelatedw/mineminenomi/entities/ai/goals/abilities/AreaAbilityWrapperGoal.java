package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class AreaAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
	// Fields
	private LivingEntity target;
	private float minDistance;
	private float maxDistance;
	private int targetsRequired;
	private boolean isCharging;

	public AreaAbilityWrapperGoal(Mob var1, AbilityCore<A> var2) {
		super(var1,var2);
		this.minDistance = 10F;
		this.maxDistance = 100F;
		this.targetsRequired = 1;
		this.isCharging = false;
	}
	public AreaAbilityWrapperGoal<A> setMinDistance(float var1) {
		this.minDistance = var1;
		return this;
	}
	public AreaAbilityWrapperGoal<A> setMaxDistance(float var1) {
		this.maxDistance = var1;
		return this;
	}
	public AreaAbilityWrapperGoal<A> setMinTargetsRequired(int var1) {
		this.targetsRequired = var1;
		return this;
	}
	public boolean canUseWrapper() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			this.target = this.entity.getTarget();
			if (!(GoalHelper.canSee(this.entity,(Entity)(this.target)))){
				return false;
			}else{
				if (((this.minDistance) > (0F)) && (GoalHelper.isWithinDistance((LivingEntity)(this.entity),this.target,(double)(this.minDistance)))){
					return false;
				}else{
					if ((this.maxDistance) > (0F)){
						if (GoalHelper.isOutsideDistance((LivingEntity)(this.entity),this.target,(double)(this.maxDistance))){
							return false;
						}else{
							if (!(GoalHelper.hasEnoughTargetsAround(this.entity,this.targetsRequired,this.maxDistance))){
								return false;
							}else{
								return true;
							}
						}
					}else{
						return true;
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
	}
	public void tickWrapper() {
		this.isCharging = ((Boolean)(this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))).map((Function)(((Function<ChargeComponent, Boolean>)(ChargeComponent::isCharging)))).orElse(Boolean.valueOf(false)))).booleanValue();
		if (this.isCharging){
			GoalHelper.lookAtEntity(this.entity,(Entity)(this.target));
		}
	}
	public void stopWrapper() {
	}
}