package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;

public final record CompleteChallengeTrigger$TriggerInstance(Optional<ContextAwarePredicate> player, ResourceLocation challengeId) {
	// Fields
	public static final Codec<CompleteChallengeTrigger$TriggerInstance> CODEC;

	public static Criterion<CompleteChallengeTrigger$TriggerInstance> completeChallenge(ChallengeCore<?> var0) {
		ResourceLocation var1 = ((var0) != (null)) ? (var0.getRegistryKey()) : (null);
		return (Criterion<CompleteChallengeTrigger$TriggerInstance>) (Criterion) (((CompleteChallengeTrigger)(ModAdvancements.COMPLETE_CHALLENGE.get())).createCriterion((CriterionTriggerInstance)(new CompleteChallengeTrigger$TriggerInstance(Optional.empty(),var1))));
	}
	public boolean matches(ServerPlayer var1, @Nullable() ResourceLocation var2) {
		return ((this.challengeId) == (null)) || (this.challengeId.equals(var2));
	}
	static { /* yak-decompiler: undecompilable <clinit>: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}