package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr3Entity;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class DoruHelper {
	public static boolean hasColorPalette(LivingEntity var0) {
		if ((ItemsHelper.countItemInInventory(var0,((Item)(ModItems.COLOR_PALETTE.get())))) > (0)){
			return true;
		}else{
			if ((var0 instanceof Mr3Entity) && (((Mr3Entity)(var0)).hasColorPaletteBonus())){
				return true;
			}else{
				return false;
			}
		}
	}
}