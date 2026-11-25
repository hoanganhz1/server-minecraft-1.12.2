package xyz.pixelatedw.mineminenomi.particles;

import xyz.pixelatedw.mineminenomi.api.math.EasingDirection;

public class SimpleParticle$1 {
	// Fields
	static final int[] $SwitchMap$xyz$pixelatedw$mineminenomi$api$math$EasingDirection = new int[EasingDirection.values().length];

	static  {
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$math$EasingDirection[EasingDirection.POSITIVE.ordinal()] = 1;
		}catch(NoSuchFieldError var0){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$math$EasingDirection[EasingDirection.NEGATIVE.ordinal()] = 2;
		}catch(NoSuchFieldError var1){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$math$EasingDirection[EasingDirection.HALF_HALF.ordinal()] = 3;
		}catch(NoSuchFieldError var2){

		}
	}
}