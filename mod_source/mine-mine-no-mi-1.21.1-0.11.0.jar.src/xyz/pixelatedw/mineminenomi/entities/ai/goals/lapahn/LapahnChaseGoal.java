package xyz.pixelatedw.mineminenomi.entities.ai.goals.lapahn;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.LapahnEntity;

public class LapahnChaseGoal extends Goal {
	// Fields
	private static final AttributeModifier CHASE_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","lapahn_chase.speed"),1D,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
	private Interval interval;
	private LapahnEntity entity;

	public LapahnChaseGoal(LapahnEntity var1) {
		this.interval = new Interval(10);
		this.entity = var1;
	}
	public boolean canUse() {
		if (!(this.interval.canTick())){
			return false;
		}else{
			if (this.entity.isChasing()){
				return false;
			}else{
				if (!(GoalHelper.hasAliveTarget((Mob)(this.entity)))){
					return false;
				}else{
					return true;
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if (!(GoalHelper.hasAliveTarget((Mob)(this.entity)))){
			return false;
		}else{
			return true;
		}
	}
	public void start() {
		super.start();
		this.entity.setChasing(true);
		AttributeInstance var1 = this.entity.getAttribute(Attributes.MOVEMENT_SPEED);
		if ((var1) != (null)){
			var1.removeModifier(CHASE_MODIFIER);
			var1.addTransientModifier(CHASE_MODIFIER);
		}
	}
	public void stop() {
		super.stop();
		this.entity.setChasing(false);
		AttributeInstance var1 = this.entity.getAttribute(Attributes.MOVEMENT_SPEED);
		if ((var1) != (null)){
			var1.removeModifier(CHASE_MODIFIER);
		}
	}
}