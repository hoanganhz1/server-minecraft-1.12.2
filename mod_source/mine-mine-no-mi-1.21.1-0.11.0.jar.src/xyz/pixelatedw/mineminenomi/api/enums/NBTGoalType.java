package xyz.pixelatedw.mineminenomi.api.enums;

import java.util.function.BiFunction;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NoAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.RepeaterAbilityWrapperGoal;

public enum NBTGoalType {
	// Fields
	PASSIVE((BiFunction)(((BiFunction<Mob, AbilityCore, AbilityWrapperGoal>)(AlwaysActiveAbilityWrapperGoal::new)))),
	DASH((BiFunction)(((BiFunction<Mob, AbilityCore, AbilityWrapperGoal>)(DashAbilityWrapperGoal::new)))),
	GRAB((BiFunction)(((BiFunction<Mob, AbilityCore, AbilityWrapperGoal>)(GrabAbilityWrapperGoal::new)))),
	JUMP((BiFunction)(((BiFunction<Mob, AbilityCore, AbilityWrapperGoal>)(JumpAbilityWrapperGoal::new)))),
	PROJECTILE((BiFunction)(((BiFunction<Mob, AbilityCore, AbilityWrapperGoal>)(ProjectileAbilityWrapperGoal::new)))),
	PUNCH((BiFunction)(((BiFunction<Mob, AbilityCore, AbilityWrapperGoal>)(CloseMeleeAbilityWrapperGoal::new)))),
	REPEATER((BiFunction)(((BiFunction<Mob, AbilityCore, AbilityWrapperGoal>)(RepeaterAbilityWrapperGoal::new)))),
	NOOP((BiFunction)(((BiFunction<Mob, AbilityCore, AbilityWrapperGoal>)(NoAbilityWrapperGoal::new))));
	private final BiFunction<Mob, AbilityCore<?>, AbilityWrapperGoal<?, ?>> func;

	private NBTGoalType(BiFunction<Mob, AbilityCore<?>, AbilityWrapperGoal<?, ?>> var3) {
		this.func = var3;
	}
	public BiFunction<Mob, AbilityCore<?>, AbilityWrapperGoal<?, ?>> getWrapperFactory() {
		return this.func;
	}
}