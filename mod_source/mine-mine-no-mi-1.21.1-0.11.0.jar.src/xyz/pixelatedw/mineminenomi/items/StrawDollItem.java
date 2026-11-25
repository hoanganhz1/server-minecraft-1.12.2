package xyz.pixelatedw.mineminenomi.items;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.enums.SoulboundMark;
import xyz.pixelatedw.mineminenomi.api.helpers.SoulboundItemHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class StrawDollItem extends Item {
	public StrawDollItem() {
		super(new Item.Properties().stacksTo(1));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		if (var1.isClientSide){
			return InteractionResultHolder.fail(var4);
		}else{
			LivingEntity var5 = ((LivingEntity)(SoulboundItemHelper.getOwner(var1,var4).getValue()));
			if ((var5) == (null)){
				return InteractionResultHolder.fail(var4);
			}else{
				EntityStatsData var6 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var5))).orElse((Object)(null))));
				if ((var6) == (null)){
					return InteractionResultHolder.fail(var4);
				}else{
					if (var6.hasStrawDoll()){
						var2.getInventory().removeItem(var4);
						return InteractionResultHolder.fail(var4);
					}else{
						if ((var5) == (var2)){
							var6.setStrawDoll(true);
							var2.getInventory().removeItem(var4);
							return InteractionResultHolder.success(var4);
						}else{
							return InteractionResultHolder.fail(var4);
						}
					}
				}
			}
		}
	}
	public boolean onEntityItemUpdate(ItemStack var1, ItemEntity var2) {
		if (var2.level().isClientSide){
			return false;
		}else{
			Pair var3 = SoulboundItemHelper.getOwner(var2.level(),var1);
			boolean var4 = var2.isOnFire();
			int var5 = ((var2.blockPosition().getY()) < (-1)) ? (1) : (0);
			if ((!(var4)) && ((var5) == (0))){

			}else{
				if ((var3.getValue()) != (null)){
					Iterator var6 = ((LivingEntity)(var3.getValue())).getHandSlots().iterator();
					do{
						if (var6.hasNext()){
							ItemStack var7 = ((ItemStack)(var6.next()));
							if ((var7.getItem()) == (Items.TOTEM_OF_UNDYING)){
								var7.shrink(var7.getCount());
								continue;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
					Consumer<EntityStatsData> var7_1 = (l0) -> {
			l0.setStrawDoll(true);
		};
					((java.util.Optional)(EntityStatsData.get(((LivingEntity)(var3.getValue()))))).ifPresent((Consumer)(var7_1));
				}else{
					ExtendedWorldData.get().markRestore(((UUID)(var3.getKey())),SoulboundMark.RESTORE_DOLL);
				}
			}
			return false;
		}
	}
	public static ItemStack createDollStack(LivingEntity var0) {
		ItemStack var1 = new ItemStack(((ItemLike)(ModItems.STRAW_DOLL.get())));
		SoulboundItemHelper.setOwner(var1,var0);
		float var2 = var0.getRandom().nextFloat();
		float var3 = var0.getRandom().nextFloat();
		float var4 = var0.getRandom().nextFloat();
		MultiChannelItemColor.dyeArmor(var1,0,new Color(var2,var3,var4).getRGB());
		setStrawDollHealth(var1,var0.getHealth());
		Object[] var5 = new Object[]{var0.getDisplayName().getString()};
		MutableComponent var6 = Component.translatable(ModI18n.ITEM_STRAW_DOLL,var5);
		var1.set(DataComponents.CUSTOM_NAME,(Object)(var6));
		return var1;
	}
	public static float getStrawDollHealth(ItemStack var0) {
		return ((Float)(var0.getOrDefault(ModDataComponents.STORED_HEALTH,Float.valueOf(0F)))).floatValue();
	}
	public static void setStrawDollHealth(ItemStack var0, float var1) {
		float var2 = (float)((ServerConfig.getDorikiLimit()) / (ServerConfig.getHealthGainFrequency()));
		var0.set(ModDataComponents.STORED_HEALTH,(Object)(Float.valueOf(Mth.clamp(var1,0F,var2))));
	}
	public static void alterStrawDollHealth(ItemStack var0, float var1) {
		float var2 = (float)((ServerConfig.getDorikiLimit()) / (ServerConfig.getHealthGainFrequency()));
		float var3 = ((Float)(var0.get(ModDataComponents.STORED_HEALTH))).floatValue();
		var0.set(ModDataComponents.STORED_HEALTH,(Object)(Float.valueOf(Mth.clamp((var3) + (var1),0F,var2))));
	}
}