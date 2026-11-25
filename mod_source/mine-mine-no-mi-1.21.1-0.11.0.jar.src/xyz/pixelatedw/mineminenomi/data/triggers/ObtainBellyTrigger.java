package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

public class ObtainBellyTrigger extends SimpleCriterionTrigger<ObtainBellyTrigger$TriggerInstance> {
	public Codec<ObtainBellyTrigger$TriggerInstance> codec() {
		return ObtainBellyTrigger$TriggerInstance.CODEC;
	}
	public void trigger(ServerPlayer var1, int var2, boolean var3) {
		ServerPlayer var4 = var1;
		int var5 = var2;
		boolean var6 = var3;
		final ServerPlayer var4_f1 = var4;
		final int var5_f2 = var5;
		final boolean var6_f3 = var6;
		Predicate<ObtainBellyTrigger$TriggerInstance> var7 = (l0) -> {
			return l0.matches(var4_f1,var5_f2,var6_f3);
		};
		this.trigger(var1,var7);
	}
}