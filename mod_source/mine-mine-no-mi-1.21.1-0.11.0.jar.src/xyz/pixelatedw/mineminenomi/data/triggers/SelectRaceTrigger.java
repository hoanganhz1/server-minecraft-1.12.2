package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class SelectRaceTrigger extends SimpleCriterionTrigger<SelectRaceTrigger$TriggerInstance> {
	public Codec<SelectRaceTrigger$TriggerInstance> codec() {
		return SelectRaceTrigger$TriggerInstance.CODEC;
	}
	public void trigger(ServerPlayer var1, ResourceLocation var2) {
		ServerPlayer var3 = var1;
		ResourceLocation var4 = var2;
		final ServerPlayer var3_f1 = var3;
		final ResourceLocation var4_f2 = var4;
		Predicate<SelectRaceTrigger$TriggerInstance> var5 = (l0) -> {
			return l0.matches(var3_f1,var4_f2);
		};
		this.trigger(var1,var5);
	}
}