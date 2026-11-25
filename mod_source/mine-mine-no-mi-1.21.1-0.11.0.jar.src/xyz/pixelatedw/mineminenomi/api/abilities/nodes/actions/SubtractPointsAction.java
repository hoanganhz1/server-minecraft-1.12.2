package xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.config.GeneralConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

public class SubtractPointsAction implements NodeUnlockAction {
	// Fields
	private final TrainingPointType type;
	private final int amount;

	public static SubtractPointsAction accuracy(float var0) {
		return new SubtractPointsAction(TrainingPointType.ACCURACY,Math.round(var0));
	}
	public static SubtractPointsAction martialArts(float var0) {
		return new SubtractPointsAction(TrainingPointType.MARTIAL_ARTS,Math.round(var0));
	}
	public static SubtractPointsAction technology(float var0) {
		return new SubtractPointsAction(TrainingPointType.TECHNOLOGY,Math.round(var0));
	}
	public static SubtractPointsAction weaponMastery(float var0) {
		return new SubtractPointsAction(TrainingPointType.WEAPON_MASTERY,Math.round(var0));
	}
	public SubtractPointsAction(TrainingPointType var1, int var2) {
		this.type = var1;
		this.amount = var2;
	}
	public void onUnlock(LivingEntity var1) {
		SubtractPointsAction var2 = this;
		Consumer<EntityStatsData> var3 = (l0) -> {
			l0.alterTrainingPoints(this.type,-(this.amount));
			l0.alterSpentTrainingPoints(this.amount);
		};
		((java.util.Optional)(EntityStatsData.get(var1))).ifPresent((Consumer)(var3));
	}
	public void onLock(LivingEntity var1) {
		SubtractPointsAction var2 = this;
		Consumer<EntityStatsData> var3 = (l0) -> {
			float lv2_2 = (float)((((Double)(GeneralConfig.TRAINING_POINTS_KEEP_PERCENTAGE.get())).doubleValue()) / (100D));
			l0.alterTrainingPoints(this.type,Math.round(((float)(this.amount)) * (lv2_2)));
			l0.alterSpentTrainingPoints(-(this.amount));
		};
		((java.util.Optional)(EntityStatsData.get(var1))).ifPresent((Consumer)(var3));
	}
}