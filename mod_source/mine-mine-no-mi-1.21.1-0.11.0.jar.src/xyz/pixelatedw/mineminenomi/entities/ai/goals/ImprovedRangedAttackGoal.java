package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensing;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class ImprovedRangedAttackGoal extends Goal {
	// Fields
	private final Mob mob;
	private final RangedAttackMob rangedAttackMob;
	@Nullable() private LivingEntity target;
	private int attackTime;
	private final double speedModifier;
	private int seeTime;
	private final int attackIntervalMin;
	private final int attackIntervalMax;
	private final float attackRadius;
	private final float attackRadiusSqr;
	private int ticksUntilNextPath;

	public ImprovedRangedAttackGoal(RangedAttackMob var1, double var2, int var3, float var4) {
		this(var1,var2,var3,var3,var4);
	}
	public ImprovedRangedAttackGoal(RangedAttackMob var1, double var2, int var3, int var4, float var5) {
		this.attackTime = -1;
		if (!(var1 instanceof LivingEntity)){
			throw new IllegalArgumentException((String)("ArrowAttackGoal requires Mob implements RangedAttackMob"));
		}else{
			this.rangedAttackMob = var1;
			this.mob = ((Mob)(var1));
			this.speedModifier = var2;
			this.attackIntervalMin = var3;
			this.attackIntervalMax = var4;
			this.attackRadius = var5;
			this.attackRadiusSqr = (var5) * (var5);
			this.setFlags(EnumSet.of(Goal.Flag.MOVE,Goal.Flag.LOOK));
			return;
		}
	}
	public boolean canUse() {
		LivingEntity var1 = this.mob.getTarget();
		if (((var1) != (null)) && (var1.isAlive())){
			this.target = var1;
			return true;
		}else{
			return false;
		}
	}
	public boolean canContinueToUse() {
		return (this.canUse()) || ((this.target.isAlive()) && (!(this.mob.getNavigation().isDone())));
	}
	public void start() {
		this.ticksUntilNextPath = 0;
	}
	public void stop() {
		this.target = null;
		this.seeTime = 0;
		this.attackTime = -1;
	}
	public void tick() {
		double var1 = this.mob.distanceToSqr(this.target.getX(),this.target.getY(),this.target.getZ());
		boolean var2 = this.mob.getSensing().hasLineOfSight((Entity)(this.target));
		if (var2){
			this.seeTime = (this.seeTime) + (1);
		}else{
			this.seeTime = 0;
		}
		if ((!((var1) > ((double)(this.attackRadiusSqr)))) && ((this.seeTime) >= (5))){
			this.mob.getNavigation().stop();
		}else{
			int var3 = (this.ticksUntilNextPath) - (1);
			this.ticksUntilNextPath = var3;
			if ((var3) <= (0)){
				this.mob.getNavigation().moveTo((Entity)(this.target),this.speedModifier);
				this.ticksUntilNextPath = this.mob.getRandom().nextInt(20,40);
			}
		}
		this.mob.getLookControl().setLookAt((Entity)(this.target),30F,30F);
		int var3_1 = (this.attackTime) - (1);
		this.attackTime = var3_1;
		if ((var3_1) == (0)){
			if (!(var2)){
				return;
			}else{
				float var4 = ((float)(Math.sqrt(var1))) / (this.attackRadius);
				float var5 = Mth.clamp(var4,0.1F,1F);
				this.rangedAttackMob.performRangedAttack(this.target,var5);
				this.attackTime = Mth.floor(((var4) * ((float)((this.attackIntervalMax) - (this.attackIntervalMin)))) + ((float)(this.attackIntervalMin)));
			}
		}else{
			if ((this.attackTime) < (0)){
				this.attackTime = Mth.floor(Mth.lerp((Math.sqrt(var1)) / ((double)(this.attackRadius)),(double)(this.attackIntervalMin),(double)(this.attackIntervalMax)));
			}else{
				return;
			}
		}
	}
}