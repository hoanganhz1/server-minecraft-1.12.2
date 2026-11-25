package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.world.entity.EquipmentSlot;

public class RendererHelper$1 {
	// Fields
	static final int[] $SwitchMap$net$minecraft$world$entity$EquipmentSlot = new int[EquipmentSlot.values().length];

	static  {
		try{
			$SwitchMap$net$minecraft$world$entity$EquipmentSlot[EquipmentSlot.HEAD.ordinal()] = 1;
		}catch(NoSuchFieldError var0){

		}
		try{
			$SwitchMap$net$minecraft$world$entity$EquipmentSlot[EquipmentSlot.CHEST.ordinal()] = 2;
		}catch(NoSuchFieldError var1){

		}
		try{
			$SwitchMap$net$minecraft$world$entity$EquipmentSlot[EquipmentSlot.LEGS.ordinal()] = 3;
		}catch(NoSuchFieldError var2){

		}
		try{
			$SwitchMap$net$minecraft$world$entity$EquipmentSlot[EquipmentSlot.FEET.ordinal()] = 4;
		}catch(NoSuchFieldError var3){

		}
	}
}