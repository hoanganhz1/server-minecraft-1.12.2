package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;

public class RangeComponentTooltip$1 {
	// Fields
	static final int[] $SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$components$RangeComponent$RangeType = new int[RangeComponent$RangeType.values().length];

	static  {
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$components$RangeComponent$RangeType[RangeComponent$RangeType.AOE.ordinal()] = 1;
		}catch(NoSuchFieldError var0){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$components$RangeComponent$RangeType[RangeComponent$RangeType.CONE.ordinal()] = 2;
		}catch(NoSuchFieldError var1){

		}
		try{
			$SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$components$RangeComponent$RangeType[RangeComponent$RangeType.LINE.ordinal()] = 3;
		}catch(NoSuchFieldError var2){

		}
	}
}