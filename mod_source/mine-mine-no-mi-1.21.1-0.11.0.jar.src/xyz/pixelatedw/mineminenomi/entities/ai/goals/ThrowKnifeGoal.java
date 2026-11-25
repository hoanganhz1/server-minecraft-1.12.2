package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.ThrowingWeaponEntity;

public class ThrowKnifeGoal extends TickedGoal<Mob> {
	// Fields
	private LivingEntity target;
	private final int cooldown;
	private int amount;
	private float speed;
	private double spread;

	public ThrowKnifeGoal(Mob var1) {
		this(var1,200);
	}
	public ThrowKnifeGoal(Mob var1, int var2) {
		super(var1);
		this.amount = 1;
		this.speed = 1F;
		this.spread = 5D;
		this.cooldown = var2;
	}
	public ThrowKnifeGoal setSpread(double var1) {
		this.spread = var1;
		return this;
	}
	public ThrowKnifeGoal setSpeed(float var1) {
		this.speed = var1;
		return this;
	}
	public ThrowKnifeGoal setAmount(int var1) {
		this.amount = var1;
		return this;
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (!(this.hasTimePassedSinceLastEnd((float)(this.cooldown)))){
				return false;
			}else{
				this.target = this.entity.getTarget();
				if (GoalHelper.isWithinDistance((LivingEntity)(this.entity),this.target,6D)){
					return false;
				}else{
					if (!(this.entity.hasLineOfSight((Entity)(this.entity.getTarget())))){
						return false;
					}else{
						return true;
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		return false;
	}
	public void start() {
		super.start();
		LivingEntity var1 = this.entity.getTarget();
		if ((var1) == (null)){
			return;
		}else{
			float var2 = 0.6F;
			if ((this.entity.distanceTo((Entity)(var1))) < (10F)){
				double var3 = ((this.entity.level().getDifficulty()) == (Difficulty.HARD)) ? (15D) : (10D);
				Vec3 var4 = this.entity.position().subtract(var1.position()).normalize().multiply(var3,0D,var3).add(0D,this.entity.getDeltaMovement().y,0D);
				this.entity.move(MoverType.SELF,var4);
			}
			if ((this.entity.level().getDifficulty()) == (Difficulty.HARD)){
				this.speed = (this.speed) + (0.5F);
				var2 = 0.2F;
			}
			int var3_1 = 1;
			if ((this.entity.getRandom().nextFloat()) > (var2)){
				var3_1 = this.amount;
			}
			int var4_1 = (var3_1) / (2);
			int var5 = (var3_1) / (2);
			float var6 = (float)((var1.getX()) - (this.entity.getX()));
			float var7 = (float)(Math.toDegrees(Math.atan2((double)(var6),(double)((float)((var1.getZ()) - (this.entity.getZ()))))));
			int var8 = -(var4_1);
			do{
				if ((var8) <= (var5)){
					ThrowingWeaponEntity var9 = new ThrowingWeaponEntity((Level)(this.entity.level()),(LivingEntity)(this.entity));
					var9.setPos(var9.getX(),((this.entity.getY()) + ((double)((this.entity.getBbHeight()) / (2F)))) + (1D),var9.getZ());
					var9.shootFromRotation((Entity)(this.entity),(this.entity.getXRot()) - (7F),(float)(((double)(-(var7))) + (((double)(var8)) * (this.spread))),0F,this.speed,0F);
					var9.setOwner((Entity)(this.entity));
					this.entity.level().addFreshEntity((Entity)(var9));
					var8++;
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
}