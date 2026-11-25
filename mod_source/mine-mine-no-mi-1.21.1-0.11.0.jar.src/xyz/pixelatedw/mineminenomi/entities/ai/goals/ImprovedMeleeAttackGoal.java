package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensing;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class ImprovedMeleeAttackGoal extends MeleeAttackGoal {
	// Fields
	private boolean moveTowardsTarget;
	private int attackInterval;
	private int ticksUntilNextAttack;
	private boolean longMemory;
	private Supplier<Boolean> earlyStop;

	public ImprovedMeleeAttackGoal(PathfinderMob var1, double var2, boolean var3) {
		super(var1,var2,var3);
		this.moveTowardsTarget = true;
		this.attackInterval = 20;
		Supplier<Boolean> var4 = () -> {
			return Boolean.valueOf(false);
		};
		this.earlyStop = var4;
		this.longMemory = var3;
	}
	public ImprovedMeleeAttackGoal setAttackInterval(int var1) {
		this.attackInterval = var1;
		return this;
	}
	public ImprovedMeleeAttackGoal setMoveTowardsTarget(boolean var1) {
		this.moveTowardsTarget = var1;
		return this;
	}
	public ImprovedMeleeAttackGoal setEarlyStop(Supplier<Boolean> var1) {
		this.earlyStop = var1;
		return this;
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget((Mob)(this.mob)))){
			return false;
		}else{
			LivingEntity var1 = this.mob.getTarget();
			if (!(EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(var1))){
				return false;
			}else{
				if (((this.mob.getPose()) != (Pose.CROUCHING)) && (((this.mob.getPose()) != (Pose.DYING)) && ((this.mob.getPose()) != (Pose.SLEEPING)))){
					if (((Boolean)(this.earlyStop.get())).booleanValue()){
						return false;
					}else{
						if (!(super.canUse())){
							return false;
						}else{
							return true;
						}
					}
				}else{
					return false;
				}
			}
		}
	}
	public boolean canContinueToUse() {
		LivingEntity var1 = this.mob.getTarget();
		if (((var1) != (null)) && (var1.isAlive())){
			if (((Boolean)(this.earlyStop.get())).booleanValue()){
				return false;
			}else{
				if (!(EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(var1))){
					return false;
				}else{
					if (var1.isInvisible()){
						return false;
					}else{
						if (((this.mob.getPose()) != (Pose.CROUCHING)) && (((this.mob.getPose()) != (Pose.DYING)) && ((this.mob.getPose()) != (Pose.SLEEPING)))){
							if ((!(this.longMemory)) && (this.mob.getNavigation().isDone())){
								return false;
							}else{
								return true;
							}
						}else{
							return false;
						}
					}
				}
			}
		}else{
			return false;
		}
	}
	public void start() {
		super.start();
		this.ticksUntilNextAttack = 0;
	}
	public void tick() {
		LivingEntity var1 = this.mob.getTarget();
		int var2 = (((var1) != (null)) && (var1.hasEffect(MobEffects.INVISIBILITY))) ? (1) : (0);
		if (((var1) != (null)) && (var1.isAlive())){
			if (((((EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(var1)) && (((this.mob.distanceToSqr((Entity)(var1))) < (5D)) && ((this.mob.tickCount) >= ((this.mob.getLastHurtMobTimestamp()) + (100))))) ? (1) : (0)) != (0)) && ((var1.onGround()) && (this.mob.onGround()))){
				Vec3 var3 = this.mob.getLookAngle().multiply(0.4D,0.4D,0.4D);
				AbilityHelper.setDeltaMovement((Entity)(this.mob),this.mob.getDeltaMovement().add(var3));
			}
			if ((var2) != (0)){
				this.mob.setTarget(((LivingEntity)(null)));
				return;
			}else{
				super.tick();
				if (!(this.moveTowardsTarget)){
					this.mob.getNavigation().stop();
				}
				this.ticksUntilNextAttack = Math.max((this.ticksUntilNextAttack) - (1),0);
				return;
			}
		}else{
			return;
		}
	}
	protected void checkAndPerformAttack(LivingEntity var1) {
		if (!(GoalHelper.canSee((Mob)(this.mob),(Entity)(var1)))){
			return;
		}else{
			if (this.canPerformAttack(var1)){
				this.resetAttackCooldown();
				this.mob.swing(InteractionHand.MAIN_HAND);
				this.mob.doHurtTarget((Entity)(var1));
			}
			return;
		}
	}
	protected boolean canPerformAttack(LivingEntity var1) {
		return (this.isTimeToAttack()) && ((this.mob.isWithinMeleeAttackRange(var1)) && (this.mob.getSensing().hasLineOfSight((Entity)(var1))));
	}
	protected boolean isTimeToAttack() {
		return (this.ticksUntilNextAttack) <= (0);
	}
	protected void resetAttackCooldown() {
		super.resetAttackCooldown();
		this.ticksUntilNextAttack = this.getAttackInterval();
	}
	protected int getAttackInterval() {
		return this.attackInterval;
	}
}