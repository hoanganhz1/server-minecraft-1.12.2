package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class GrabAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
	// Fields
	private LivingEntity target;
	private float activationDistance;
	private float maxDistance;

	public GrabAbilityWrapperGoal(Mob var1, AbilityCore<A> var2) {
		super(var1,var2);
		this.maxDistance = 30F;
		AttributeInstance var3 = var1.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
		this.activationDistance = ((var3) != (null)) ? ((float)(var3.getValue())) : (6F);
	}
	public boolean canUseWrapper() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
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
	public boolean canContinueToUseWrapper() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (((this.maxDistance) > (0F)) && (GoalHelper.isOutsideDistance((LivingEntity)(this.entity),this.target,(double)((this.maxDistance) * (2F))))){
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
		this.entity.getNavigation().stop();
		GoalHelper.lookAtEntity(this.entity,(Entity)(this.target));
	}
	public void stopWrapper() {
	}
}