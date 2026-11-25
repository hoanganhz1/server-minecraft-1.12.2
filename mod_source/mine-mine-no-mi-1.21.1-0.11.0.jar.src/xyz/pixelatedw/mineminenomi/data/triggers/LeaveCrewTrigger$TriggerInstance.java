package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;

public final record LeaveCrewTrigger$TriggerInstance(Optional<ContextAwarePredicate> player) {
	// Fields
	public static final Codec<LeaveCrewTrigger$TriggerInstance> CODEC;

	static { /* yak-decompiler: undecompilable <clinit>: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}