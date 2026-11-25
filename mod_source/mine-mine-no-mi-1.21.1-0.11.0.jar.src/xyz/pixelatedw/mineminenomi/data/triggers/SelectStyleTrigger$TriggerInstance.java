package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final record SelectStyleTrigger$TriggerInstance(Optional<ContextAwarePredicate> player, ResourceLocation style) {
	// Fields
	public static final Codec<SelectStyleTrigger$TriggerInstance> CODEC;

	public boolean matches(ServerPlayer var1, ResourceLocation var2) {
		return this.style.equals(var2);
	}
	static { /* yak-decompiler: undecompilable <clinit>: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}