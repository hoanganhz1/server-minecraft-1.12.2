package xyz.pixelatedw.mineminenomi.entities.ai.goals.buggy;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.BuggyEntity;

public class BuggyDashGoal extends TickedGoal<BuggyEntity> {
	// Fields
	private static final int COOLDOWN = 200;
	private LivingEntity target;
	private boolean hasReachedTarget;
	private int stopAccel;
	private Vec3 targetPos;
	private double speed;
	private float damage;
	private Set<LivingEntity> alreadyHurt;

	public BuggyDashGoal(BuggyEntity var1, double var2, float var3) {
		super(var1);
		this.alreadyHurt = new HashSet();
		this.speed = var2;
		this.damage = var3;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			this.target = ((BuggyEntity)(this.entity)).getTarget();
			if (!(this.hasTimePassedSinceLastEnd(200F))){
				return false;
			}else{
				if (!(this.target.onGround())){
					return false;
				}else{
					return true;
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if ((((BuggyEntity)(this.entity)).distanceToSqr(this.targetPos)) < (4D)){
			return false;
		}else{
			if ((this.stopAccel) >= (20)){
				return false;
			}else{
				if (this.hasTimePassedSinceStart(200F)){
					return false;
				}else{
					return true;
				}
			}
		}
	}
	public void start() {
		super.start();
		this.alreadyHurt.clear();
		this.hasReachedTarget = false;
		this.stopAccel = 0;
		GoalHelper.lookAtEntity(this.entity,(Entity)(this.target));
		Vec3 var1 = this.target.position().subtract(((BuggyEntity)(this.entity)).position()).scale(0.5D);
		this.targetPos = this.target.position().add(var1);
	}
	public void tick() {
		super.tick();
		if (this.hasReachedTarget){
			this.stopAccel = (this.stopAccel) + (1);
		}
		if (this.target.onGround()){
			GoalHelper.lookAt(this.entity,this.targetPos);
			Vec3 var1 = ((BuggyEntity)(this.entity)).getLookAngle().multiply(this.speed,0D,this.speed);
			((BuggyEntity)(this.entity)).move(MoverType.SELF,var1);
		}
		Iterator var1_1 = TargetHelper.getEntitiesInArea((LevelAccessor)(((BuggyEntity)(this.entity)).level()),(LivingEntity)(this.entity),1.5D,(TargetPredicate)(null),LivingEntity.class).iterator();
		do{
			if (var1_1.hasNext()){
				LivingEntity var2 = ((LivingEntity)(var1_1.next()));
				if (this.alreadyHurt.contains(var2)){
					continue;
				}else{
					var2.hurt(((BuggyEntity)(this.entity)).damageSources().mobAttack((LivingEntity)(this.entity)),this.damage);
					this.alreadyHurt.add(var2);
					this.hasReachedTarget = true;
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
}