package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;

@FunctionalInterface()
public interface AbilityCore$IAbilityGoalFactory<A extends IAbility> {
	public abstract AbilityWrapperGoal<Mob, A> create(Mob var0, AbilityCore<A> var1);
}