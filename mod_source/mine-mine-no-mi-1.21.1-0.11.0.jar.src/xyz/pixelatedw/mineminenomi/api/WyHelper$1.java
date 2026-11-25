package xyz.pixelatedw.mineminenomi.api;

import net.minecraft.core.Direction8;

public class WyHelper$1 {
	// Fields
	static final int[] $SwitchMap$net$minecraft$core$Direction8 = new int[Direction8.values().length];

	static  {
		try{
			$SwitchMap$net$minecraft$core$Direction8[Direction8.NORTH.ordinal()] = 1;
		}catch(NoSuchFieldError var0){

		}
		try{
			$SwitchMap$net$minecraft$core$Direction8[Direction8.EAST.ordinal()] = 2;
		}catch(NoSuchFieldError var1){

		}
		try{
			$SwitchMap$net$minecraft$core$Direction8[Direction8.SOUTH.ordinal()] = 3;
		}catch(NoSuchFieldError var2){

		}
		try{
			$SwitchMap$net$minecraft$core$Direction8[Direction8.WEST.ordinal()] = 4;
		}catch(NoSuchFieldError var3){

		}
		try{
			$SwitchMap$net$minecraft$core$Direction8[Direction8.NORTH_EAST.ordinal()] = 5;
		}catch(NoSuchFieldError var4){

		}
		try{
			$SwitchMap$net$minecraft$core$Direction8[Direction8.NORTH_WEST.ordinal()] = 6;
		}catch(NoSuchFieldError var5){

		}
		try{
			$SwitchMap$net$minecraft$core$Direction8[Direction8.SOUTH_EAST.ordinal()] = 7;
		}catch(NoSuchFieldError var6){

		}
		try{
			$SwitchMap$net$minecraft$core$Direction8[Direction8.SOUTH_WEST.ordinal()] = 8;
		}catch(NoSuchFieldError var7){

		}
	}
}