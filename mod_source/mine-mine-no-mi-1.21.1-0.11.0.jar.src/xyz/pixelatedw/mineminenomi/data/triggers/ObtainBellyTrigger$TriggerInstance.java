package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;

public final record ObtainBellyTrigger$TriggerInstance(Optional<ContextAwarePredicate> player, MinMaxBounds.Ints belly, Optional<Boolean> isBountyReward) {
	// Fields
	public static final Codec<ObtainBellyTrigger$TriggerInstance> CODEC;

	public boolean matches(ServerPlayer var1, int var2, boolean var3) {
		return this.belly.matches(var2);
	}
	public static Criterion<ObtainBellyTrigger$TriggerInstance> collectBelly(int var0) {
		return (Criterion<ObtainBellyTrigger$TriggerInstance>) (Criterion) (((ObtainBellyTrigger)(ModAdvancements.OBTAIN_BELLY.get())).createCriterion((CriterionTriggerInstance)(new ObtainBellyTrigger$TriggerInstance(Optional.empty(),MinMaxBounds.Ints.atLeast(var0),Optional.empty()))));
	}
	public static Criterion<ObtainBellyTrigger$TriggerInstance> collectBelly(int var0, boolean var1) {
		return (Criterion<ObtainBellyTrigger$TriggerInstance>) (Criterion) (((ObtainBellyTrigger)(ModAdvancements.OBTAIN_BELLY.get())).createCriterion((CriterionTriggerInstance)(new ObtainBellyTrigger$TriggerInstance(Optional.empty(),MinMaxBounds.Ints.atLeast(var0),Optional.of(Boolean.valueOf(var1))))));
	}
	static { /* yak-decompiler: undecompilable <clinit>: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}