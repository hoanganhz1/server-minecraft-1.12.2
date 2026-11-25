package xyz.pixelatedw.mineminenomi.abilities;

import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;

public class CommandAbility$1 {
	// Fields
	static final int[] $SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$NPCCommand = new int[NPCCommand.values().length];

	static  {
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$NPCCommand[NPCCommand.ATTACK.ordinal()] = 1;
		}catch(NoSuchFieldError var0){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$NPCCommand[NPCCommand.FOLLOW.ordinal()] = 2;
		}catch(NoSuchFieldError var1){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$NPCCommand[NPCCommand.GUARD.ordinal()] = 3;
		}catch(NoSuchFieldError var2){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$NPCCommand[NPCCommand.IDLE.ordinal()] = 4;
		}catch(NoSuchFieldError var3){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$NPCCommand[NPCCommand.STAY.ordinal()] = 5;
		}catch(NoSuchFieldError var4){

		}
	}
}