package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

public class FreedSlavesTrigger extends SimpleCriterionTrigger<FreedSlavesTrigger$TriggerInstance> {
	public Codec<FreedSlavesTrigger$TriggerInstance> codec() {
		return FreedSlavesTrigger$TriggerInstance.CODEC;
	}
	public void trigger(ServerPlayer var1, int var2) {
		ServerPlayer var3 = var1;
		int var4 = var2;
		final ServerPlayer var3_f1 = var3;
		final int var4_f2 = var4;
		Predicate<FreedSlavesTrigger$TriggerInstance> var5 = (l0) -> {
			return l0.matches(var3_f1,var4_f2);
		};
		this.trigger(var1,var5);
	}
}