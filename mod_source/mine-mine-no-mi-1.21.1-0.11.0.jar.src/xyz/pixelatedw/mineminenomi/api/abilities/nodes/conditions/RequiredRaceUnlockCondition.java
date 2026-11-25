package xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nNodes;

public class RequiredRaceUnlockCondition implements NodeUnlockCondition {
	// Fields
	private final Race race;

	public static RequiredRaceUnlockCondition requires(Race var0) {
		return new RequiredRaceUnlockCondition(var0);
	}
	public RequiredRaceUnlockCondition(Race var1) {
		this.race = var1;
	}
	public boolean test(LivingEntity var1) {
		Function<EntityStatsData, Race> var2 = (l0) -> {
			return ((Race)(((java.util.Optional)(l0.getRace())).orElse((Object)(null))));
		};
		RequiredRaceUnlockCondition var3 = this;
		Function<Race, Boolean> var4 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals((Object)(this.race)));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(EntityStatsData.get(var1))).map((Function)(var2)).filter((Predicate)(((Predicate<Race>)(Objects::nonNull)))))).map((Function)(var4)).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public MutableComponent getTooltip() {
		Object[] var1 = new Object[]{this.race.getLabel().getString()};
		return Component.translatable(ModI18nNodes.RACE_CHECK,var1);
	}
}