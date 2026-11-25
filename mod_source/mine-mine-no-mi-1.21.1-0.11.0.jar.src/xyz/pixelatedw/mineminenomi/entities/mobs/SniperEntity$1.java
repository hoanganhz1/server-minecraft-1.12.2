package xyz.pixelatedw.mineminenomi.entities.mobs;

import net.minecraft.world.Difficulty;

public class SniperEntity$1 {
	// Fields
	static final int[] $SwitchMap$net$minecraft$world$Difficulty = new int[Difficulty.values().length];

	static  {
		try{
			$SwitchMap$net$minecraft$world$Difficulty[Difficulty.EASY.ordinal()] = 1;
		}catch(NoSuchFieldError var0){

		}
		try{
			$SwitchMap$net$minecraft$world$Difficulty[Difficulty.NORMAL.ordinal()] = 2;
		}catch(NoSuchFieldError var1){

		}
	}
}