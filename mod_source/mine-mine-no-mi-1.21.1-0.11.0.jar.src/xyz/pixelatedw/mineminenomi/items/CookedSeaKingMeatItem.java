package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModFoods;

public class CookedSeaKingMeatItem extends Item {
	public CookedSeaKingMeatItem() {
		super(new Item.Properties().food(ModFoods.COOKED_SEA_KING_MEAT));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		var2.startUsingItem(var3);
		return InteractionResultHolder.success(var4);
	}
	public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
		if ((!(var2.isClientSide)) && (var3 instanceof Player)){
			Player var4 = ((Player)(var3));
			var4.addEffect(new MobEffectInstance(MobEffects.REGENERATION,100,0));
			var4.heal((10F) + ((var4.getMaxHealth()) / (10F)));
			var4.eat(var2,var1);
		}
		return var1;
	}
}