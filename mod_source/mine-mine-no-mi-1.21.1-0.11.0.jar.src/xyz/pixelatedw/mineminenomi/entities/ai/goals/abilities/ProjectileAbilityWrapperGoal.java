package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BowTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class ProjectileAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
	// Fields
	private static final int MAX_PULL_TIME = 40;
	private LivingEntity target;
	private float minDistance;
	private float maxDistance;
	@Nullable() protected final BowTriggerComponent bowTrigger;
	@Nullable() protected final ChargeComponent chargeComponent;
	private int useTime;

	public ProjectileAbilityWrapperGoal(Mob var1, AbilityCore<A> var2) {
		super(var1,var2);
		this.minDistance = 0F;
		this.maxDistance = 100F;
		this.useTime = 0;
		this.bowTrigger = ((BowTriggerComponent)(this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.BOW_TRIGGER.get()))).orElse(null)));
		this.chargeComponent = ((ChargeComponent)(this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))).orElse(null)));
		if ((this.chargeComponent) != (null)){
			ProjectileAbilityWrapperGoal var3 = this;
			ChargeComponent$IStartChargingEvent var4 = (l0, l1) -> {
			this.useTime = Math.round(this.chargeComponent.getMaxChargeTime());
		};
			this.chargeComponent.addStartEvent(200,var4);
		}
	}
	public ProjectileAbilityWrapperGoal<A> setMinDistance(float var1) {
		this.minDistance = var1;
		return this;
	}
	public ProjectileAbilityWrapperGoal<A> setMaxDistance(float var1) {
		this.maxDistance = var1;
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
				if (((this.minDistance) > (0F)) && (GoalHelper.isWithinDistance((LivingEntity)(this.entity),this.target,(double)(this.minDistance)))){
					return false;
				}else{
					if (((this.maxDistance) > (0F)) && (GoalHelper.isOutsideDistance((LivingEntity)(this.entity),this.target,(double)(this.maxDistance)))){
						return false;
					}else{
						return true;
					}
				}
			}
		}
	}
	public boolean canContinueToUseWrapper() {
		if ((this.useTime) > (0)){
			return true;
		}else{
			return false;
		}
	}
	public void startWrapper() {
		GoalHelper.lookAtEntity(this.entity,(Entity)(this.target));
		ItemStack var1 = this.entity.getMainHandItem();
		if (((this.bowTrigger) != (null)) && (ItemsHelper.isBowOrGun(var1))){
			this.entity.startUsingItem(InteractionHand.MAIN_HAND);
			this.useTime = 40;
		}
	}
	public void tickWrapper() {
		this.useTime = (this.useTime) - (1);
	}
	public void stopWrapper() {
		ItemStack var1 = this.entity.getMainHandItem();
		if (((this.useTime) <= (0)) && (((this.bowTrigger) != (null)) && (ItemsHelper.isBowOrGun(var1)))){
			this.entity.releaseUsingItem();
		}
	}
	public LivingEntity getTarget() {
		return this.target;
	}
}