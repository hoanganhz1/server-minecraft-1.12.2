package xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nNodes;

public class RequiredFactionUnlockCondition implements NodeUnlockCondition {
	// Fields
	private final Faction faction;

	public static RequiredFactionUnlockCondition requires(Faction var0) {
		return new RequiredFactionUnlockCondition(var0);
	}
	public RequiredFactionUnlockCondition(Faction var1) {
		this.faction = var1;
	}
	public boolean test(LivingEntity var1) {
		Function<EntityStatsData, Faction> var2 = (l0) -> {
			return ((Faction)(((java.util.Optional)(l0.getFaction())).orElse((Object)(null))));
		};
		RequiredFactionUnlockCondition var3 = this;
		Function<Faction, Boolean> var4 = (l0) -> {
			return Boolean.valueOf(l0.equals(this.faction));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(EntityStatsData.get(var1))).map((Function)(var2)).filter((Predicate)(((Predicate<Faction>)(Objects::nonNull)))))).map((Function)(var4)).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public MutableComponent getTooltip() {
		Object[] var1 = new Object[]{this.faction.getLabel().getString()};
		return Component.translatable(ModI18nNodes.FACTION_CHECK,var1);
	}
}