package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class ReactiveRetreatAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
	// Fields
	private LivingEntity target;
	private float minDistance;
	private float maxDistance;
	@Nullable() private RevengeMeter revengeMeter;
	private int activationHits;
	private int hits;

	public ReactiveRetreatAbilityWrapperGoal(Mob var1, AbilityCore<A> var2) {
		this(var1,null,var2);
	}
	public ReactiveRetreatAbilityWrapperGoal(Mob var1, @Nullable() RevengeMeter var2, AbilityCore<A> var3) {
		super(var1,var3);
		this.minDistance = 10F;
		this.maxDistance = 30F;
		this.activationHits = 3;
		this.hits = 0;
		this.revengeMeter = var2;
		if ((var2) != (null)){
			this.activationHits = this.revengeMeter.getMaxRevengeValue();
		}
	}
	public ReactiveRetreatAbilityWrapperGoal<A> setMinDistance(float var1) {
		this.minDistance = var1;
		return this;
	}
	public ReactiveRetreatAbilityWrapperGoal<A> setMaxDistance(float var1) {
		this.maxDistance = var1;
		return this;
	}
	public ReactiveRetreatAbilityWrapperGoal<A> setActivationHits(int var1) {
		this.activationHits = var1;
		return this;
	}
	public boolean canUseWrapper() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			this.target = this.entity.getTarget();
			if (!(GoalHelper.canSee(this.entity,(Entity)(this.target)))){
				return false;
			}else{
				if (GoalHelper.isOutsideDistance((LivingEntity)(this.entity),this.target,(double)(this.minDistance))){
					return false;
				}else{
					if ((this.revengeMeter) != (null)){
						if ((this.revengeMeter.getRevengeValue()) < (this.activationHits)){
							return false;
						}else{
							return true;
						}
					}else{
						if ((this.hits) < (this.activationHits)){
							return false;
						}else{
							return true;
						}
					}
				}
			}
		}
	}
	public boolean canContinueToUseWrapper() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (((this.maxDistance) > (0F)) && (GoalHelper.isOutsideDistance((LivingEntity)(this.entity),this.target,(double)(this.maxDistance)))){
				return false;
			}else{
				return true;
			}
		}
	}
	public void startWrapper() {
		this.entity.getNavigation().stop();
		if (this.target.hasLineOfSight((Entity)(this.entity))){
			this.entity.setYRot(this.target.getYRot());
		}else{
			this.entity.setYRot((this.entity.getYRot()) + (180F));
		}
		this.revengeMeter.resetMarkers();
	}
	public void tickWrapper() {
	}
	public void stopWrapper() {
	}
}