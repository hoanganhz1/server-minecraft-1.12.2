package xyz.pixelatedw.mineminenomi.abilities.artofweather;

import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;

public class ArtOfWeatherHelper$1 {
	// Fields
	static final int[] $SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$WeatherBallKind = new int[WeatherBallKind.values().length];

	static  {
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$WeatherBallKind[WeatherBallKind.COOL.ordinal()] = 1;
		}catch(NoSuchFieldError var0){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$WeatherBallKind[WeatherBallKind.HEAT.ordinal()] = 2;
		}catch(NoSuchFieldError var1){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$WeatherBallKind[WeatherBallKind.THUNDER.ordinal()] = 3;
		}catch(NoSuchFieldError var2){

		}
	}
}