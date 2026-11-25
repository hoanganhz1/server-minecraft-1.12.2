package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class CloseMeleeAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
	// Fields
	private LivingEntity target;
	private float activationDistance;
	private float maxDistance;
	private boolean isCharging;

	public CloseMeleeAbilityWrapperGoal(Mob var1, AbilityCore<A> var2) {
		super(var1,var2);
		this.activationDistance = 6F;
		this.maxDistance = 100F;
		this.isCharging = false;
		AttributeInstance var3 = var1.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
		if (((var3) != (null)) && ((var3.getValue()) > ((double)(this.activationDistance)))){
			this.activationDistance = (float)(var3.getValue());
		}
	}
	public CloseMeleeAbilityWrapperGoal<A> setActivationDistance(float var1) {
		this.activationDistance = var1;
		return this;
	}
	public CloseMeleeAbilityWrapperGoal<A> setMaxDistance(float var1) {
		this.maxDistance = var1;
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
				if (((this.activationDistance) > (0F)) && (GoalHelper.isOutsideDistance((LivingEntity)(this.entity),this.target,(double)(this.activationDistance)))){
					return false;
				}else{
					return true;
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