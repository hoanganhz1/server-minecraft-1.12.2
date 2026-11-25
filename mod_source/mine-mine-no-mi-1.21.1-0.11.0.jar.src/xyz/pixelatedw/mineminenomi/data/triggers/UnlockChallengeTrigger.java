package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;

public class UnlockChallengeTrigger extends SimpleCriterionTrigger<UnlockChallengeTrigger$TriggerInstance> {
	public Codec<UnlockChallengeTrigger$TriggerInstance> codec() {
		return UnlockChallengeTrigger$TriggerInstance.CODEC;
	}
	public void trigger(ServerPlayer var1, @Nullable() ChallengeCore<?> var2) {
		ServerPlayer var3 = var1;
		ChallengeCore<?> var4 = var2;
		final ServerPlayer var3_f1 = var3;
		final ChallengeCore<?> var4_f2 = var4;
		Predicate<UnlockChallengeTrigger$TriggerInstance> var5 = (l0) -> {
			return l0.matches(var3_f1,((var4_f2.getRegistryKey()) != (null)) ? (var4_f2.getRegistryKey()) : (null));
		};
		this.trigger(var1,var5);
	}
}