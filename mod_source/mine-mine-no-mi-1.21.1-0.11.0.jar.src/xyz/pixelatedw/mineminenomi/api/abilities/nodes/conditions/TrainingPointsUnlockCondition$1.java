package xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions;

import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;

public class TrainingPointsUnlockCondition$1 {
	// Fields
	static final int[] $SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$TrainingPointType = new int[TrainingPointType.values().length];

	static  {
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$TrainingPointType[TrainingPointType.ACCURACY.ordinal()] = 1;
		}catch(NoSuchFieldError var0){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$TrainingPointType[TrainingPointType.MARTIAL_ARTS.ordinal()] = 2;
		}catch(NoSuchFieldError var1){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$TrainingPointType[TrainingPointType.TECHNOLOGY.ordinal()] = 3;
		}catch(NoSuchFieldError var2){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$TrainingPointType[TrainingPointType.WEAPON_MASTERY.ordinal()] = 4;
		}catch(NoSuchFieldError var3){

		}
	}
}