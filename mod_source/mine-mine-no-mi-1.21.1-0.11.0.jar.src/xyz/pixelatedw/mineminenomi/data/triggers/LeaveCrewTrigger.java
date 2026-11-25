package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

public class LeaveCrewTrigger extends SimpleCriterionTrigger<LeaveCrewTrigger$TriggerInstance> {
	public Codec<LeaveCrewTrigger$TriggerInstance> codec() {
		return LeaveCrewTrigger$TriggerInstance.CODEC;
	}
	public void trigger(ServerPlayer var1) {
		Predicate<LeaveCrewTrigger$TriggerInstance> var2 = (l0) -> {
			return true;
		};
		this.trigger(var1,var2);
	}
}