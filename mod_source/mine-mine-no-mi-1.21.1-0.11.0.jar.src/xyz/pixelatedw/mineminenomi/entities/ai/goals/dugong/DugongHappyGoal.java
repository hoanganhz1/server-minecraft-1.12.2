package xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong;

import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;

public class DugongHappyGoal extends TickedGoal<AbstractDugongEntity> {
	// Fields
	private Interval canUseInterval;

	public DugongHappyGoal(AbstractDugongEntity var1) {
		super(var1);
		this.canUseInterval = new Interval(20);
		this.setFlags(EnumSet.of(Goal.Flag.LOOK));
	}
	public boolean canUse() {
		if (!(this.canUseInterval.canTick())){
			return false;
		}else{
			if (!(((AbstractDugongEntity)(this.entity)).isTame())){
				return false;
			}else{
				if (!(GoalHelper.hasAliveOwner(((TamableAnimal)(this.entity))))){
					return false;
				}else{
					if (!(((AbstractDugongEntity)(this.entity)).isOrderedToSit())){
						return false;
					}else{
						if ((((AbstractDugongEntity)(this.entity)).getHealth()) <= ((((AbstractDugongEntity)(this.entity)).getMaxHealth()) / (3F))){
							return false;
						}else{
							return true;
						}
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if (((AbstractDugongEntity)(this.entity)).isEnraged()){
			return false;
		}else{
			if (GoalHelper.hasAliveTarget(this.entity)){
				return false;
			}else{
				if (!(GoalHelper.hasAliveOwner(((TamableAnimal)(this.entity))))){
					return false;
				}else{
					if (!(((AbstractDugongEntity)(this.entity)).isOrderedToSit())){
						return false;
					}else{
						return true;
					}
				}
			}
		}
	}
	public void start() {
		super.start();
		((AbstractDugongEntity)(this.entity)).setHappy(true);
	}
	public void tick() {
		super.tick();
		GoalHelper.lookAtEntity(this.entity,(Entity)(((AbstractDugongEntity)(this.entity)).getOwner()));
	}
	public void stop() {
		super.stop();
		((AbstractDugongEntity)(this.entity)).setHappy(false);
	}
}