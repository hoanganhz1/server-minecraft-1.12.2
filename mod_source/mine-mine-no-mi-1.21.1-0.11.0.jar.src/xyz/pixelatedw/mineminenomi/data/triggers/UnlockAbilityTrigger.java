package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public class UnlockAbilityTrigger extends SimpleCriterionTrigger<UnlockAbilityTrigger$TriggerInstance> {
	public Codec<UnlockAbilityTrigger$TriggerInstance> codec() {
		return UnlockAbilityTrigger$TriggerInstance.CODEC;
	}
	public void trigger(ServerPlayer var1, AbilityCore<?> var2) {
		ServerPlayer var3 = var1;
		AbilityCore<?> var4 = var2;
		final ServerPlayer var3_f1 = var3;
		final AbilityCore<?> var4_f2 = var4;
		Predicate<UnlockAbilityTrigger$TriggerInstance> var5 = (l0) -> {
			return l0.matches(var3_f1,var4_f2);
		};
		this.trigger(var1,var5);
	}
}