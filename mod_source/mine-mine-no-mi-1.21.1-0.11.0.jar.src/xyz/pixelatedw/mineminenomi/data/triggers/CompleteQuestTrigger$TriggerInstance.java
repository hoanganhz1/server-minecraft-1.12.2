package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final record CompleteQuestTrigger$TriggerInstance(Optional<ContextAwarePredicate> player, ResourceLocation quest) {
	// Fields
	public static final Codec<CompleteQuestTrigger$TriggerInstance> CODEC;

	public boolean matches(ServerPlayer var1, ResourceLocation var2) {
		return ((this.quest) == (null)) || (this.quest.equals(var2));
	}
	static { /* yak-decompiler: undecompilable <clinit>: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}