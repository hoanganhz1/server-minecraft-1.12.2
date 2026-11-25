package xyz.pixelatedw.mineminenomi.renderers.entities;

import xyz.pixelatedw.mineminenomi.api.enums.NetType;

public class NetRenderer$1 {
	// Fields
	static final int[] $SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$NetType = new int[NetType.values().length];

	static  {
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$NetType[NetType.KAIROSEKI.ordinal()] = 1;
		}catch(NoSuchFieldError var0){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$NetType[NetType.NORMAL.ordinal()] = 2;
		}catch(NoSuchFieldError var1){

		}
	}
}