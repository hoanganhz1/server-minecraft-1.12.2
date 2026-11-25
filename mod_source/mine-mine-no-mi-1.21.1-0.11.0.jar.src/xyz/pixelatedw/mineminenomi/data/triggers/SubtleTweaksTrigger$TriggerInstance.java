package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;

public final record SubtleTweaksTrigger$TriggerInstance(Optional<ContextAwarePredicate> player) {
	// Fields
	public static final Codec<SubtleTweaksTrigger$TriggerInstance> CODEC;

	public static Criterion<SubtleTweaksTrigger$TriggerInstance> create() {
		return (Criterion<SubtleTweaksTrigger$TriggerInstance>) (Criterion) (((SubtleTweaksTrigger)(ModAdvancements.SUBTLE_TWEAKS.get())).createCriterion((CriterionTriggerInstance)(new SubtleTweaksTrigger$TriggerInstance(Optional.empty()))));
	}
	static { /* yak-decompiler: undecompilable <clinit>: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}