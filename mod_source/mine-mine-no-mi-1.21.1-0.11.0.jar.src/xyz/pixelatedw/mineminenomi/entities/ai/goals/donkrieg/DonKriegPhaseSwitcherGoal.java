package xyz.pixelatedw.mineminenomi.entities.ai.goals.donkrieg;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.abilities.MH5Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates.DonKriegEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class DonKriegPhaseSwitcherGoal extends TickedGoal<DonKriegEntity> {
	// Fields
	private static final float DAISENSO_HP_THRESHOLD = 70F;
	private final AbilityData abilityData;
	private final float mh5HPThreshold;

	public DonKriegPhaseSwitcherGoal(DonKriegEntity var1) {
		super(var1);
		this.abilityData = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		this.mh5HPThreshold = (var1.isDifficultyHardOrAbove()) ? (70F) : (50F);
	}
	public boolean canUse() {
		if ((this.abilityData) == (null)){
			return false;
		}else{
			if (!(GoalHelper.hasAliveTarget(this.entity))){
				return false;
			}else{
				if (!(GoalHelper.canSee(this.entity,(Entity)(((DonKriegEntity)(this.entity)).getTarget())))){
					return false;
				}else{
					if (((!(((DonKriegEntity)(this.entity)).hasFistPhaseActive())) && (!(((DonKriegEntity)(this.entity)).hasMH5PhaseActive()))) || (!(this.trySwitchToDaisensoPhase()))){
						if (((!(((DonKriegEntity)(this.entity)).hasFistPhaseActive())) && (!(((DonKriegEntity)(this.entity)).hasDaisensoPhaseActive()))) || (!(this.trySwitchToMH5Phase()))){
							return false;
						}else{
							((DonKriegEntity)(this.entity)).startMH5Phase();
							return true;
						}
					}else{
						((DonKriegEntity)(this.entity)).startDaisensoPhase();
						return true;
					}
				}
			}
		}
	}
	private boolean trySwitchToDaisensoPhase() {
		if (((DonKriegEntity)(this.entity)).hasMH5PhaseActive()){
			MH5Ability var1 = ((MH5Ability)(this.abilityData.getEquippedAbility(((AbilityCore)(MH5Ability.INSTANCE.get())))));
			if ((var1) != (null)){
				if (((Boolean)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))).map((Function)(((Function<ChargeComponent, Boolean>)(ChargeComponent::isCharging)))).orElse(Boolean.valueOf(false)))).booleanValue()){
					return false;
				}else{
					if (((Boolean)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))).map((Function)(((Function<CooldownComponent, Boolean>)(CooldownComponent::isOnCooldown)))).orElse(Boolean.valueOf(false)))).booleanValue()){
						return true;
					}else{
						return false;
					}
				}
			}else{
				return true;
			}
		}else{
			if (GoalHelper.hasHealthAbovePercentage((LivingEntity)(this.entity),70D)){
				return false;
			}else{
				return true;
			}
		}
	}
	private boolean trySwitchToMH5Phase() {
		if (GoalHelper.hasHealthAbovePercentage((LivingEntity)(this.entity),(double)(this.mh5HPThreshold))){
			return false;
		}else{
			MH5Ability var1 = ((MH5Ability)(this.abilityData.getEquippedAbility(((AbilityCore)(MH5Ability.INSTANCE.get())))));
			if (((var1) != (null)) && (((Boolean)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))).map((Function)(((Function<CooldownComponent, Boolean>)(CooldownComponent::isOnCooldown)))).orElse(Boolean.valueOf(false)))).booleanValue())){
				return false;
			}else{
				return true;
			}
		}
	}
	public boolean canContinueToUse() {
		return false;
	}
	public void start() {
		super.start();
	}
	public void tick() {
		super.tick();
	}
	public void stop() {
		super.stop();
	}
}