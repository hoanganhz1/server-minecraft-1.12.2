package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;

public final record SetCrewCaptainTrigger$TriggerInstance(Optional<ContextAwarePredicate> player) {
	// Fields
	public static final Codec<SetCrewCaptainTrigger$TriggerInstance> CODEC;

	static { /* yak-decompiler: undecompilable <clinit>: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}