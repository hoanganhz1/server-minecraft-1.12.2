package xyz.pixelatedw.mineminenomi.abilities.kage;

import java.util.Iterator;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class KageHelper {
	public static Result hasEnoughShadows(LivingEntity var0, IAbility var1, int var2) {
		if ((ItemsHelper.countItemInInventory(var0,((Item)(ModItems.SHADOW.get())))) < (var2)){
			return Result.fail(ModI18nAbilities.MESSAGE_NOT_ENOUGH_SHADOWS);
		}else{
			return Result.success();
		}
	}
	public static void removeShadows(LivingEntity var0, int var1) {
		Iterator var2 = ItemsHelper.getAllInventoryItems(var0).iterator();
		do{
			if (var2.hasNext()){
				ItemStack var3 = ((ItemStack)(var2.next()));
				if ((var1) <= (0)){
					break;
				}else{
					if (((java.lang.Object)(var3.getItem())).equals(ModItems.SHADOW.get())){
						if ((var3.getCount()) >= (var1)){
							var3.shrink(var1);
							break;
						}else{
							var1 = (var1) - (var3.getCount());
							var3.shrink(var1);
							continue;
						}
					}else{
						continue;
					}
				}
			}else{
				break;
			}
		} while (true);
	}
	@Deprecated()
	public static int getAvailableShadows(LivingEntity var0) {
		return ItemsHelper.countItemInInventory(var0,((Item)(ModItems.SHADOW.get())));
	}
}