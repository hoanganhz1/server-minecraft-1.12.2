package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public class JumpOverProjectilesGoal extends TickedGoal<Mob> {
	// Fields
	private static final float CHECK_AREA = 60F;
	private float cooldown;
	private float jumpHeight;
	private Projectile projectileTarget;
	private int hits;
	private int triggerHits;
	private double previousDistance;
	private java.util.function.Predicate<LivingEntity> canUseTest;

	public JumpOverProjectilesGoal(Mob var1, float var2, float var3) {
		super(var1);
		this.triggerHits = 3;
		this.previousDistance = 0D;
		this.canUseTest = Predicates.alwaysTrue();
		this.cooldown = var2;
		this.jumpHeight = var3;
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (!(this.hasTimePassedSinceLastEnd(this.cooldown))){
				return false;
			}else{
				if (AbilityUseConditions.canUseMomentumAbilities((LivingEntity)(this.entity)).isFail()){
					return false;
				}else{
					Iterator var1 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.entity.level()),(LivingEntity)(this.entity),60D,(TargetPredicate)(null),Projectile.class).iterator();
					do{
						if (var1.hasNext()){
							Projectile var2 = ((Projectile)(var1.next()));
							if (ModEntityPredicates.getEnemyFactions((LivingEntity)(this.entity)).test(var2.getOwner())){
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
		Vec3 var1 = this.entity.getLookAngle().multiply(3D,0D,3D);
		AbilityHelper.setDeltaMovement((Entity)(this.entity),var1.x,(double)(this.jumpHeight),var1.z);
	}
	public void tick() {
		super.tick();
	}
	public void stop() {
		super.stop();
		this.hits = 0;
		this.previousDistance = 0D;
	}
	public void setCanUseTest(java.util.function.Predicate<LivingEntity> var1) {
		this.canUseTest = var1;
	}
}