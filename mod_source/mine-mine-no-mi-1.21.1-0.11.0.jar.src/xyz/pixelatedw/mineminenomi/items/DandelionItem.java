package xyz.pixelatedw.mineminenomi.items;

import java.util.function.Supplier;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public class DandelionItem extends Item {
	public DandelionItem() {
		super(new Item.Properties().food(Foods.DRIED_KELP));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		var2.startUsingItem(var3);
		return InteractionResultHolder.success(var4);
	}
	public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
		if ((!(var2.isClientSide)) && (var3 instanceof Player)){
			((Player)(var3)).getCooldowns().addCooldown(this.asItem(),200);
			var3.stopUsingItem();
			var3.level().broadcastEntityEvent((Entity)(var3),(byte)(30));
			var3.heal(getHealAmount(var1));
			var1.shrink(1);
		}
		return var1;
	}
	public UseAnim getUseAnimation(ItemStack var1) {
		return UseAnim.EAT;
	}
	public static float getHealAmount(ItemStack var0) {
		return ((Float)(var0.get(ModDataComponents.STORED_HEALTH))).floatValue();
	}
	public static void setHealAmount(ItemStack var0, float var1) {
		float var2 = (float)((ServerConfig.getDorikiLimit()) / (ServerConfig.getHealthGainFrequency()));
		var0.set(ModDataComponents.STORED_HEALTH,Float.valueOf(Mth.clamp(var1,0F,var2)));
	}
}