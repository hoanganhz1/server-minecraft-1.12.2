package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class ReactiveGuardAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
	// Fields
	private static final int HURT_COOLDOWN = 100;
	private LivingEntity target;
	private float activationDistance;
	private float maxDistance;
	private float prevHealth;
	private int activationHits;
	private int hits;

	public ReactiveGuardAbilityWrapperGoal(Mob var1, AbilityCore<A> var2) {
		super(var1,var2);
		this.maxDistance = 10F;
		this.activationHits = 3;
		this.hits = 0;
		AttributeInstance var3 = var1.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
		this.activationDistance = ((var3) != (null)) ? ((float)(var3.getValue())) : (6F);
	}
	public ReactiveGuardAbilityWrapperGoal<A> setActivationHits(int var1) {
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
				if (GoalHelper.isOutsideDistance((LivingEntity)(this.entity),this.target,(double)(this.activationDistance))){
					return false;
				}else{
					if (((this.entity.getLastHurtByMobTimestamp()) > (0)) && ((this.entity.tickCount) <= ((this.entity.getLastHurtByMobTimestamp()) + (100)))){
						if (((this.prevHealth) != (0F)) && (!((this.entity.getHealth()) < (this.prevHealth)))){

						}else{
							this.hits = (this.hits) + (1);
						}
						this.prevHealth = this.entity.getHealth();
						if ((this.hits) < (this.activationHits)){
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
	}
	public boolean canContinueToUseWrapper() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (((this.maxDistance) > (0F)) && (GoalHelper.isOutsideDistance((LivingEntity)(this.entity),this.target,(double)((this.maxDistance) * (2F))))){
				return false;
			}else{
				return true;
			}
		}
	}
	public void startWrapper() {
	}
	public void tickWrapper() {
	}
	public void stopWrapper() {
		this.hits = 0;
	}
}