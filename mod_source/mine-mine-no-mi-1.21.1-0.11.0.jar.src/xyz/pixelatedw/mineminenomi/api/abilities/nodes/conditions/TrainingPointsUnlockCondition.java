package xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

public class TrainingPointsUnlockCondition implements NodeUnlockCondition {
	// Fields
	private final TrainingPointType type;
	private final float pointsNeeded;

	public static TrainingPointsUnlockCondition accuracy(float var0) {
		return new TrainingPointsUnlockCondition(TrainingPointType.ACCURACY,Math.round(var0));
	}
	public static TrainingPointsUnlockCondition martialArts(float var0) {
		return new TrainingPointsUnlockCondition(TrainingPointType.MARTIAL_ARTS,Math.round(var0));
	}
	public static TrainingPointsUnlockCondition technology(float var0) {
		return new TrainingPointsUnlockCondition(TrainingPointType.TECHNOLOGY,Math.round(var0));
	}
	public static TrainingPointsUnlockCondition weaponMastery(float var0) {
		return new TrainingPointsUnlockCondition(TrainingPointType.WEAPON_MASTERY,Math.round(var0));
	}
	public TrainingPointsUnlockCondition(TrainingPointType var1, int var2) {
		this.type = var1;
		this.pointsNeeded = (float)(var2);
	}
	public boolean test(LivingEntity var1) {
		if (!(ServerConfig.isQuestProgressionEnabled())){
			return true;
		}else{
			TrainingPointsUnlockCondition var2 = this;
			Function<EntityStatsData, Integer> var3 = (l0) -> {
			return Integer.valueOf(l0.getTrainingPoints(this.type));
		};
			return ((float)(((Integer)(((java.util.Optional)(EntityStatsData.get(var1))).map((Function)(var3)).orElse(Integer.valueOf(0)))).intValue())) >= (this.pointsNeeded);
		}
	}
	public MutableComponent getTooltip() {
		switch (TrainingPointsUnlockCondition$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$TrainingPointType[this.type.ordinal()]){
		case 1:

		case 2:

		case 3:

		case 4:

		default:
			Object var1 = null;
			if ((null) == (null)){
				return Component.empty();
			}else{
				Object[] var2 = new Object[]{Float.valueOf(this.pointsNeeded)};
				return Component.translatable((String)(null),var2);
			}
		}
	}
}