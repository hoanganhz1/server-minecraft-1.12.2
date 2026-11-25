package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.items.DFEncyclopediaItem;

public final record DFEncyclopediaCompletionTrigger$TriggerInstance(Optional<ContextAwarePredicate> player, float completion) {
	// Fields
	public static final Codec<DFEncyclopediaCompletionTrigger$TriggerInstance> CODEC;

	public boolean matches(ServerPlayer var1, ItemStack var2) {
		return (DFEncyclopediaItem.getCompletion(var2)) >= (this.completion);
	}
	public static Criterion<DFEncyclopediaCompletionTrigger$TriggerInstance> completion(float var0) {
		return (Criterion<DFEncyclopediaCompletionTrigger$TriggerInstance>) (Criterion) (((DFEncyclopediaCompletionTrigger)(ModAdvancements.ENCYCLOPEDIA_COMPLETION.get())).createCriterion((CriterionTriggerInstance)(new DFEncyclopediaCompletionTrigger$TriggerInstance(Optional.empty(),var0))));
	}
	static { /* yak-decompiler: undecompilable <clinit>: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}