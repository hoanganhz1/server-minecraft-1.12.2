package xyz.pixelatedw.mineminenomi.items;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class WateringCanItem extends Item {
	// Fields
	private static final int MAX_TEARS = 250;

	public WateringCanItem() {
		super(new Item.Properties().stacksTo(1));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		float var5 = var2.getHealth();
		float var6 = var2.getMaxHealth();
		if ((var5) >= (var6)){
			return InteractionResultHolder.fail(var4);
		}else{
			int var7 = 0;
			if ((var5) > ((var6) - ((var6) * (0.05F)))){
				var7 = (int)((var6) - (var5));
			}else{
				var7 = (int)((var6) * (0.05F));
			}
			int var8 = getTearAmount(var4);
			if (((var8) > (0)) && ((var8) >= (var7))){
				var2.startUsingItem(var3);
			}
			return InteractionResultHolder.success(var4);
		}
	}
	public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
		float var4 = var3.getHealth();
		float var5 = var3.getMaxHealth();
		float var7 = 0.0F;
		if ((var4) > ((var5) - ((var5) * (0.05F)))){
			var7 = (var5) - (var4);
		}else{
			var7 = (var5) * (0.05F);
		}
		var3.heal(var7);
		alterTearAmount(var1,(int)(-(var7)));
		return var1;
	}
	public UseAnim getUseAnimation(ItemStack var1) {
		return UseAnim.DRINK;
	}
	public int getUseDuration(ItemStack var1, LivingEntity var2) {
		return 32;
	}
	public void appendHoverText(ItemStack var1, Item.TooltipContext var2, List<Component> var3, TooltipFlag var4) {
		int var5 = getTearAmount(var1);
		Object[] var6 = new Object[]{Integer.valueOf(var5)};
		((java.util.List)(var3)).add((Object)(Component.translatable(ModI18n.ITEM_WATERING_CAN_TEARS,var6).withStyle(ChatFormatting.GRAY)));
	}
	public static int getTearAmount(ItemStack var0) {
		return ((Integer)(var0.getOrDefault(ModDataComponents.TEARS,Integer.valueOf(0)))).intValue();
	}
	public static void alterTearAmount(ItemStack var0, int var1) {
		int var2 = getTearAmount(var0);
		var0.set(ModDataComponents.TEARS,Integer.valueOf(Mth.clamp((var2) + (var1),0,250)));
	}
}