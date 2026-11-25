package xyz.pixelatedw.mineminenomi.handlers.ui;

import net.neoforged.neoforge.client.settings.KeyModifier;

public class CombatModeHandler$1 {
	// Fields
	static final int[] $SwitchMap$net$neoforged$neoforge$client$settings$KeyModifier = new int[KeyModifier.values().length];

	static  {
		try{
			$SwitchMap$net$neoforged$neoforge$client$settings$KeyModifier[KeyModifier.ALT.ordinal()] = 1;
		}catch(NoSuchFieldError var0){

		}
		try{
			$SwitchMap$net$neoforged$neoforge$client$settings$KeyModifier[KeyModifier.CONTROL.ordinal()] = 2;
		}catch(NoSuchFieldError var1){

		}
		try{
			$SwitchMap$net$neoforged$neoforge$client$settings$KeyModifier[KeyModifier.SHIFT.ordinal()] = 3;
		}catch(NoSuchFieldError var2){

		}
	}
}