package xyz.pixelatedw.mineminenomi.entities.ai.goals.lapahn;

import java.util.List;
import java.util.function.Consumer;
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

public class LapahnRageGoal extends Goal {
	// Fields
	private static final AttributeModifier RAGE_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","lapahn_rage.attack"),10D,AttributeModifier.Operation.ADD_VALUE);
	private Interval canUseInterval;
	private LapahnEntity entity;

	public LapahnRageGoal(LapahnEntity var1) {
		this.canUseInterval = new Interval(10);
		this.entity = var1;
	}
	public boolean canUse() {
		if (this.entity.isEnraged()){
			return false;
		}else{
			if (!(this.canUseInterval.canTick())){
				return false;
			}else{
				if (!(GoalHelper.hasAliveTarget((Mob)(this.entity)))){
					return false;
				}else{
					if ((this.entity.getHealth()) > ((this.entity.getMaxHealth()) / (3F))){
						return false;
					}else{
						return true;
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		return true;
	}
	public void start() {
		this.entity.setEnraged(true);
		Consumer<LapahnEntity> var1 = (l0) -> {
			l0.setEnraged(true);
		};
		((java.lang.Iterable)(this.entity.getNearbyLapahns())).forEach((Consumer)(var1));
		this.entity.setResting(false);
		AttributeInstance var2 = this.entity.getAttribute(Attributes.ATTACK_DAMAGE);
		if ((var2) != (null)){
			var2.removeModifier(RAGE_MODIFIER);
			var2.addTransientModifier(RAGE_MODIFIER);
		}
	}
}