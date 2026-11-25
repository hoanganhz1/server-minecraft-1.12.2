package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

public class SubtleTweaksTrigger extends SimpleCriterionTrigger<SubtleTweaksTrigger$TriggerInstance> {
	public Codec<SubtleTweaksTrigger$TriggerInstance> codec() {
		return SubtleTweaksTrigger$TriggerInstance.CODEC;
	}
	public void trigger(ServerPlayer var1) {
		Predicate<SubtleTweaksTrigger$TriggerInstance> var2 = (l0) -> {
			return true;
		};
		this.trigger(var1,var2);
	}
}