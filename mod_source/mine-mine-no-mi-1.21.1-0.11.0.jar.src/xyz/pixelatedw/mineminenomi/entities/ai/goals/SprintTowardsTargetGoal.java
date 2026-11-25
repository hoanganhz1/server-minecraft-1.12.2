package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class SprintTowardsTargetGoal extends Goal {
	// Fields
	private static final ResourceLocation SPEED_MODIFIER_SPRINTING_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","sprint_goal.speed");
	private Mob entity;
	private float speedModifier;
	private double currentSpeedStat;

	public SprintTowardsTargetGoal(Mob var1) {
		this.speedModifier = 0.3F;
		this.entity = var1;
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			LivingEntity var1 = this.entity.getTarget();
			if (!(GoalHelper.canSee(this.entity,(Entity)(var1)))){
				return false;
			}else{
				if ((!(this.entity.isSprinting())) && (GoalHelper.isOutsideDistance((LivingEntity)(this.entity),var1,15D))){
					return true;
				}else{
					if ((this.entity.isSprinting()) && (GoalHelper.isWithinDistance((LivingEntity)(this.entity),var1,5D))){
						return false;
					}else{
						return this.entity.isSprinting();
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		return super.canContinueToUse();
	}
	public void start() {
		this.setSprinting(true);
	}
	public void tick() {
	}
	public void stop() {
		this.setSprinting(false);
	}
	public void setSprinting(boolean var1) {
		this.entity.setSprinting(var1);
		AttributeInstance var2 = this.entity.getAttribute(Attributes.MOVEMENT_SPEED);
		var2.removeModifier(AttributeHelper.SPEED_MODIFIER_SPRINTING_UUID);
		var2.removeModifier(SPEED_MODIFIER_SPRINTING_UUID);
		if (var1){
			var2.addTransientModifier(this.getSpeedModifier());
		}
		this.currentSpeedStat = var2.getValue();
		this.entity.getNavigation().setSpeedModifier(this.currentSpeedStat);
	}
	public AttributeModifier getSpeedModifier() {
		return new AttributeModifier(SPEED_MODIFIER_SPRINTING_UUID,(double)(this.speedModifier),AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}
}