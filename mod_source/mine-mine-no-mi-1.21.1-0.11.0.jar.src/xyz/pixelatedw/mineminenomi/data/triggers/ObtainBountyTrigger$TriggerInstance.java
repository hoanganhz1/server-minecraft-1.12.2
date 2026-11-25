package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

public final record ObtainBountyTrigger$TriggerInstance(Optional<ContextAwarePredicate> player, MinMaxBounds.Ints bounty) {
	// Fields
	public static final Codec<ObtainBountyTrigger$TriggerInstance> CODEC;

	public boolean matches(ServerPlayer var1, int var2) {
		return this.bounty.matches(var2);
	}
	static { /* yak-decompiler: undecompilable <clinit>: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}