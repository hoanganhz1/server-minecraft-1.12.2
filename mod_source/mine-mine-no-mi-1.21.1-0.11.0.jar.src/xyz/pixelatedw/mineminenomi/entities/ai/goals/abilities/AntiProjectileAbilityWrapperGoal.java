package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public class AntiProjectileAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
	// Fields
	private static final float CHECK_AREA = 40F;
	private Projectile projectileTarget;
	private int hits;
	private int triggerHits;
	private double previousDistance;

	public AntiProjectileAbilityWrapperGoal(Mob var1, AbilityCore<A> var2) {
		super(var1,var2);
		this.triggerHits = 3;
		this.previousDistance = 0D;
	}
	public boolean canUseWrapper() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (!(GoalHelper.canMove((LivingEntity)(this.entity)))){
				return false;
			}else{
				Iterator var1 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.entity.level()),(LivingEntity)(this.entity),40D,(TargetPredicate)(null),Projectile.class).iterator();
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
					return true;
				}
			}
		}
	}
	public boolean canContinueToUseWrapper() {
		return false;
	}
	public void startWrapper() {
		GoalHelper.lookAtEntity(this.entity,(Entity)(this.projectileTarget));
	}
	public void tickWrapper() {
	}
	public void stopWrapper() {
		this.hits = 0;
	}
}