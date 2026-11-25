package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ChangeStatsComponentTooltip$1 {
	// Fields
	static final int[] $SwitchMap$net$minecraft$world$entity$ai$attributes$AttributeModifier$Operation = new int[AttributeModifier.Operation.values().length];

	static  {
		try{
			$SwitchMap$net$minecraft$world$entity$ai$attributes$AttributeModifier$Operation[AttributeModifier.Operation.ADD_VALUE.ordinal()] = 1;
		}catch(NoSuchFieldError var0){

		}
		try{
			$SwitchMap$net$minecraft$world$entity$ai$attributes$AttributeModifier$Operation[AttributeModifier.Operation.ADD_MULTIPLIED_BASE.ordinal()] = 2;
		}catch(NoSuchFieldError var1){

		}
		try{
			$SwitchMap$net$minecraft$world$entity$ai$attributes$AttributeModifier$Operation[AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL.ordinal()] = 3;
		}catch(NoSuchFieldError var2){

		}
	}
}