package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModFoods;

public class SeaKingMeatItem extends Item {
	public SeaKingMeatItem() {
		super(new Item.Properties().food(ModFoods.SEA_KING_MEAT));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		var2.startUsingItem(var3);
		return InteractionResultHolder.success(var4);
	}
	public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
		if ((!(var2.isClientSide)) && (var3 instanceof Player)){
			((Player)(var3)).eat(var2,var1);
		}
		return var1;
	}
}