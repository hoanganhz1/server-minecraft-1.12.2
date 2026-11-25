package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;

public class AlwaysActiveAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
	public AlwaysActiveAbilityWrapperGoal(Mob var1, AbilityCore<A> var2) {
		super(var1,var2);
	}
	public boolean canUseWrapper() {
		return true;
	}
	public boolean canContinueToUseWrapper() {
		return true;
	}
	public void startWrapper() {
	}
	public void tickWrapper() {
	}
	public void stopWrapper() {
	}
}