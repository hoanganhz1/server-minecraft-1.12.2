package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class DFEncyclopediaCompletionTrigger extends SimpleCriterionTrigger<DFEncyclopediaCompletionTrigger$TriggerInstance> {
	public Codec<DFEncyclopediaCompletionTrigger$TriggerInstance> codec() {
		return DFEncyclopediaCompletionTrigger$TriggerInstance.CODEC;
	}
	public void trigger(ServerPlayer var1, ItemStack var2) {
		ServerPlayer var3 = var1;
		ItemStack var4 = var2;
		final ServerPlayer var3_f1 = var3;
		final ItemStack var4_f2 = var4;
		Predicate<DFEncyclopediaCompletionTrigger$TriggerInstance> var5 = (l0) -> {
			return l0.matches(var3_f1,var4_f2);
		};
		this.trigger(var1,var5);
	}
}