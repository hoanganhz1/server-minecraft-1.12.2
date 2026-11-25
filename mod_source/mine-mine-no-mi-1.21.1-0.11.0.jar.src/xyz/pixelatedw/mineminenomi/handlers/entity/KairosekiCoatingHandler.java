package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Optional;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.data.entity.KairosekiCoatingData;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class KairosekiCoatingHandler {
	public static void applyExistingKairosekiCoatingFromItem(Player var0, InteractionHand var1, Entity var2) {
		int var3 = ((Integer)(var0.getItemInHand(var1).get(ModDataComponents.KAIROSEKI_COATING_LEVEL))).intValue();
		if ((var3) > (0)){
			KairosekiCoatingData var4 = ((KairosekiCoatingData)(((java.util.Optional)(KairosekiCoatingData.get(var2))).orElse((Object)(null))));
			if ((var4) == (null)){
				return;
			}else{
				var4.setCoatingLevel(var3);
			}
		}
	}
	public static boolean applyKairosekiCoat(Player var0, Entity var1, ItemStack var2) {
		KairosekiCoatingData var3 = ((KairosekiCoatingData)(((java.util.Optional)(KairosekiCoatingData.get(var1))).orElse((Object)(null))));
		if (((var3) != (null)) && ((!(var3.isFullyCoated())) && ((!(var2.isEmpty())) && (((java.lang.Object)(var2.getItem())).equals(ModItems.DENSE_KAIROSEKI.get()))))){
			int var4 = Math.min(var2.getCount(),5);
			if ((var3.addCoatingLevel(var4)) && (!(var0.getAbilities().instabuild))){
				var2.shrink(var4);
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	@Nullable()
	public static ItemEntity dropCoatedItem(Entity var0, ItemStack var1) {
		KairosekiCoatingData var2 = ((KairosekiCoatingData)(((java.util.Optional)(KairosekiCoatingData.get(var0))).orElse((Object)(null))));
		if (((var2) != (null)) && ((var2.getCoatingLevel()) > (0))){
			var1.set(ModDataComponents.KAIROSEKI_COATING_LEVEL,Integer.valueOf(var2.getCoatingLevel()));
			return var0.spawnAtLocation(var1,0F);
		}else{
			return null;
		}
	}
}