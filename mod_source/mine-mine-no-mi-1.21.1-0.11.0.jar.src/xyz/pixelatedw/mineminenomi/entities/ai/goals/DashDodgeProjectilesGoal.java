package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public class DashDodgeProjectilesGoal extends TickedGoal<Mob> {
	// Fields
	private static final float CHECK_AREA = 40F;
	private float ticksBetweenDashes;
	private float dashDistance;
	private NuProjectileEntity projectileTarget;
	private int hits;
	private int triggerHits;
	private double previousDistance;
	private java.util.function.Predicate<LivingEntity> canUseTest;

	public DashDodgeProjectilesGoal(Mob var1, float var2, float var3) {
		super(var1);
		this.triggerHits = 3;
		this.previousDistance = 0D;
		this.canUseTest = Predicates.alwaysTrue();
		this.ticksBetweenDashes = var2;
		this.dashDistance = var3;
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (!(this.hasTimePassedSinceLastEnd(this.ticksBetweenDashes))){
				return false;
			}else{
				if (!(GoalHelper.canMove((LivingEntity)(this.entity)))){
					return false;
				}else{
					Iterator var1 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.entity.level()),(LivingEntity)(this.entity),40D,(TargetPredicate)(null),NuProjectileEntity.class).iterator();
					do{
						if (var1.hasNext()){
							NuProjectileEntity var2 = ((NuProjectileEntity)(var1.next()));
							if (((java.util.function.Predicate)(ModEntityPredicates.getEnemyFactions((LivingEntity)(this.entity)))).test((Object)(var2.getOwner()))){
								this.projectileTarget = var2;
								double var3 = var2.distanceToSqr((Entity)(this.entity));
								if ((this.previousDistance) == (0D)){
									this.previousDistance = var3;
								}
								if ((var3) < (this.previousDistance)){
									this.hits = (this.hits) + (1);
									this.previousDistance = var3;
									break;
								}else{
									break;
								}
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
					if ((this.hits) < (this.triggerHits)){
						return false;
					}else{
						if ((this.projectileTarget) == (null)){
							return false;
						}else{
							if (!(((java.util.function.Predicate)(this.canUseTest)).test((Object)(this.entity)))){
								return false;
							}else{
								return true;
							}
						}
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
		GoalHelper.lookAtEntity(this.entity,(Entity)(this.projectileTarget));
		double var1 = (this.entity.getRandom().nextBoolean()) ? (-45D) : (135D);
		double var2 = (Mth.wrapDegrees(((double)(this.entity.getYRot())) * (0.017453292519943295D))) + (Math.toRadians(var1));
		double var3 = (((double)(this.dashDistance)) * (Math.cos(var2))) - (((double)(this.dashDistance)) * (Math.sin(var2)));
		double var4 = (((double)(this.dashDistance)) * (Math.cos(var2))) + (((double)(this.dashDistance)) * (Math.sin(var2)));
		AbilityHelper.setDeltaMovement((Entity)(this.entity),-(var3),0.1D,-(var4));
	}
	public void tick() {
		super.tick();
	}
	public void stop() {
		super.stop();
		this.hits = 0;
		this.previousDistance = 0D;
	}
	public DashDodgeProjectilesGoal setCanUseTest(java.util.function.Predicate<LivingEntity> var1) {
		this.canUseTest = var1;
		return this;
	}
}