package xyz.pixelatedw.mineminenomi.items;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class BellyPouchItem extends Item {
	public BellyPouchItem() {
		super(new Item.Properties().stacksTo(1));
	}
	public void inventoryTick(ItemStack var1, Level var2, Entity var3, int var4, boolean var5) {
		if ((!(var2.isClientSide)) && (!(var1.has(ModDataComponents.BELLY)))){
			var1.set(ModDataComponents.BELLY,Long.valueOf((long)(WyHelper.randomWithRange(5,250))));
		}
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		EntityStatsData var4 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		ItemStack var5 = var2.getItemInHand(var3);
		if ((var4) == (null)){
			return InteractionResultHolder.fail(var5);
		}else{
			if (!(var1.isClientSide)){
				long var6 = ((Long)(var5.get(ModDataComponents.BELLY))).longValue();
				if (((((var4.getBelly()) + (var6)) <= (999999999L)) ? (1) : (0)) != (0)){
					var4.alterBelly(var6,StatChangeSource.NATURAL);
					ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var2)),var2);
					var2.getInventory().removeItem(var5);
					Object[] var7 = new Object[]{Long.valueOf(var6)};
					var2.sendSystemMessage((Component)(Component.translatable(ModI18n.ITEM_MESSAGE_POUCH_BELLY_GAINED,var7)));
					return InteractionResultHolder.success(var5);
				}else{
					if ((var4.getBelly()) >= (999999999L)){
						var2.sendSystemMessage(ModI18n.ITEM_MESSAGE_BELLY_FULL);
						return InteractionResultHolder.fail(var5);
					}else{
						long var7_1 = (999999999L) - (var4.getBelly());
						long var8 = (var6) - (var7_1);
						if ((var8) < (0L)){
							return InteractionResultHolder.fail(var5);
						}else{
							var4.alterBelly(var7_1,StatChangeSource.NATURAL);
							ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var2)),var2);
							var5.set(ModDataComponents.BELLY,Long.valueOf(var8));
							Object[] var9 = new Object[]{Long.valueOf(var6)};
							var2.sendSystemMessage((Component)(Component.translatable(ModI18n.ITEM_MESSAGE_POUCH_BELLY_GAINED,var9)));
							return InteractionResultHolder.success(var5);
						}
					}
				}
			}else{
				return InteractionResultHolder.success(var5);
			}
		}
	}
	public void appendHoverText(ItemStack var1, Item.TooltipContext var2, List<Component> var3, TooltipFlag var4) {
		if (var1.has(ModDataComponents.BELLY)){
			Object[] var5 = new Object[]{var1.get(ModDataComponents.BELLY)};
			((java.util.List)(var3)).add((Object)(Component.translatable(ModI18n.ITEM_MESSAGE_BELLY_POUCH_AMOUNT,var5).withStyle(ChatFormatting.GRAY)));
		}
	}
}