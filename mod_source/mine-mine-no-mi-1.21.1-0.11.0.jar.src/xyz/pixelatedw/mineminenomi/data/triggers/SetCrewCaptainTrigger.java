package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

public class SetCrewCaptainTrigger extends SimpleCriterionTrigger<SetCrewCaptainTrigger$TriggerInstance> {
	public Codec<SetCrewCaptainTrigger$TriggerInstance> codec() {
		return SetCrewCaptainTrigger$TriggerInstance.CODEC;
	}
	public void trigger(ServerPlayer var1) {
		Predicate<SetCrewCaptainTrigger$TriggerInstance> var2 = (l0) -> {
			return true;
		};
		this.trigger(var1,var2);
	}
}