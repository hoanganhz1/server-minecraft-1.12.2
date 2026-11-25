package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;

public final record UnlockAbilityTrigger$TriggerInstance(Optional<ContextAwarePredicate> player, ResourceLocation abilityId) {
	// Fields
	public static final Codec<UnlockAbilityTrigger$TriggerInstance> CODEC;

	public boolean matches(ServerPlayer var1, AbilityCore<?> var2) {
		return this.abilityId.equals(var2.getRegistryKey());
	}
	public static Criterion<UnlockAbilityTrigger$TriggerInstance> ability(Holder<AbilityCore<?>> var0) {
		return ability(((AbilityCore)(var0.value())));
	}
	public static Criterion<UnlockAbilityTrigger$TriggerInstance> ability(AbilityCore<?> var0) {
		return (Criterion<UnlockAbilityTrigger$TriggerInstance>) (Criterion) (((UnlockAbilityTrigger)(ModAdvancements.UNLOCK_ABILITY.get())).createCriterion((CriterionTriggerInstance)(new UnlockAbilityTrigger$TriggerInstance(Optional.empty(),var0.getRegistryKey()))));
	}
	static { /* yak-decompiler: undecompilable <clinit>: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}