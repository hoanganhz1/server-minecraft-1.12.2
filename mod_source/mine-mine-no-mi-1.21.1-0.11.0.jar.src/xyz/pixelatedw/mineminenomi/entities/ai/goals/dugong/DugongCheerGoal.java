package xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong;

import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;

public class DugongCheerGoal extends TickedGoal<AbstractDugongEntity> {
	// Fields
	private Interval canUseInterval;

	public DugongCheerGoal(AbstractDugongEntity var1) {
		super(var1);
		this.canUseInterval = new Interval(10);
		this.setFlags(EnumSet.of(Goal.Flag.TARGET,Goal.Flag.MOVE,Goal.Flag.JUMP,Goal.Flag.LOOK));
	}
	public boolean canUse() {
		if (!(this.canUseInterval.canTick())){
			return false;
		}else{
			if (((AbstractDugongEntity)(this.entity)).isEnraged()){
				return false;
			}else{
				if ((((AbstractDugongEntity)(this.entity)).getCheerTarget()) == (null)){
					return false;
				}else{
					if (GoalHelper.hasAliveTarget(this.entity)){
						return false;
					}else{
						return true;
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if (((AbstractDugongEntity)(this.entity)).isEnraged()){
			return false;
		}else{
			if (GoalHelper.shouldMove(this.entity)){
				return false;
			}else{
				if (!(((AbstractDugongEntity)(this.entity)).isCheering())){
					return false;
				}else{
					if (((((AbstractDugongEntity)(this.entity)).getCheerTarget()) != (null)) && (((AbstractDugongEntity)(this.entity)).getCheerTarget().isAlive())){
						if (GoalHelper.hasAliveTarget(this.entity)){
							return false;
						}else{
							return !(this.hasTimePassedSinceStart(WyHelper.secondsToTicks(30F)));
						}
					}else{
						return false;
					}
				}
			}
		}
	}
	public void start() {
		super.start();
		((AbstractDugongEntity)(this.entity)).getNavigation().stop();
	}
	public void tick() {
		if (((((AbstractDugongEntity)(this.entity)).level().getGameTime()) % (5L)) == (0L)){
			((AbstractDugongEntity)(this.entity)).getJumpControl().jump();
		}
		GoalHelper.lookAtEntity(this.entity,(Entity)(((AbstractDugongEntity)(this.entity)).getCheerTarget()));
		super.tick();
	}
	public void stop() {
		super.stop();
		((AbstractDugongEntity)(this.entity)).setCheering((LivingEntity)(null));
	}
}