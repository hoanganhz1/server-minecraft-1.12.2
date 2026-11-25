package xyz.pixelatedw.mineminenomi.api.quests.components;

import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$DamageState;

public class DamageTakenQuestComponent$1 {
	// Fields
	static final int[] $SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$components$DamageTakenComponent$DamageState = new int[DamageTakenComponent$DamageState.values().length];

	static  {
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$components$DamageTakenComponent$DamageState[DamageTakenComponent$DamageState.ATTACK.ordinal()] = 1;
		}catch(NoSuchFieldError var0){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$components$DamageTakenComponent$DamageState[DamageTakenComponent$DamageState.HURT.ordinal()] = 2;
		}catch(NoSuchFieldError var1){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$components$DamageTakenComponent$DamageState[DamageTakenComponent$DamageState.DAMAGE.ordinal()] = 3;
		}catch(NoSuchFieldError var2){

		}
	}
}