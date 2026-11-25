package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public class CompleteQuestTrigger extends SimpleCriterionTrigger<CompleteQuestTrigger$TriggerInstance> {
	public Codec<CompleteQuestTrigger$TriggerInstance> codec() {
		return CompleteQuestTrigger$TriggerInstance.CODEC;
	}
	public void trigger(ServerPlayer var1, QuestId<?> var2) {
		ServerPlayer var3 = var1;
		QuestId<?> var4 = var2;
		final ServerPlayer var3_f1 = var3;
		final QuestId<?> var4_f2 = var4;
		Predicate<CompleteQuestTrigger$TriggerInstance> var5 = (l0) -> {
			return l0.matches(var3_f1,var4_f2.getRegistryKey());
		};
		this.trigger(var1,var5);
	}
}