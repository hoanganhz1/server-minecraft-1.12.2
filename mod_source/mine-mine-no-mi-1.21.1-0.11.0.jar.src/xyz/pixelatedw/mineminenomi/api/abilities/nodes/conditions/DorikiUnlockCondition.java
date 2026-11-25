package xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nNodes;

public class DorikiUnlockCondition implements NodeUnlockCondition {
	// Fields
	private final double dorikiNeeded;

	public static DorikiUnlockCondition atLeast(double var0) {
		return new DorikiUnlockCondition(var0);
	}
	public DorikiUnlockCondition(double var1) {
		this.dorikiNeeded = var1;
	}
	public boolean test(LivingEntity var1) {
		return (((Double)(EntityStatsData.get(var1).map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDoriki)))).orElse(Double.valueOf(0D)))).doubleValue()) >= (this.dorikiNeeded);
	}
	public MutableComponent getTooltip() {
		Object[] var1 = new Object[]{Double.valueOf(this.dorikiNeeded)};
		return Component.translatable(ModI18nNodes.DORIKI_CHECK,var1);
	}
}