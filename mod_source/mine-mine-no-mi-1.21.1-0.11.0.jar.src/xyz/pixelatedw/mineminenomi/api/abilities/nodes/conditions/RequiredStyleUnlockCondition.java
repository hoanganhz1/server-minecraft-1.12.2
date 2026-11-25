package xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nNodes;

public class RequiredStyleUnlockCondition implements NodeUnlockCondition {
	// Fields
	private final FightingStyle style;

	public static RequiredStyleUnlockCondition requires(FightingStyle var0) {
		return new RequiredStyleUnlockCondition(var0);
	}
	public RequiredStyleUnlockCondition(FightingStyle var1) {
		this.style = var1;
	}
	public boolean test(LivingEntity var1) {
		Function<EntityStatsData, FightingStyle> var2 = (l0) -> {
			return ((FightingStyle)(((java.util.Optional)(l0.getFightingStyle())).orElse((Object)(null))));
		};
		RequiredStyleUnlockCondition var3 = this;
		Function<FightingStyle, Boolean> var4 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals((Object)(this.style)));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(EntityStatsData.get(var1))).map((Function)(var2)).filter((Predicate)(((Predicate<FightingStyle>)(Objects::nonNull)))))).map((Function)(var4)).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public MutableComponent getTooltip() {
		Object[] var1 = new Object[]{this.style.getLabel().getString()};
		return Component.translatable(ModI18nNodes.STYLE_CHECK,var1);
	}
}