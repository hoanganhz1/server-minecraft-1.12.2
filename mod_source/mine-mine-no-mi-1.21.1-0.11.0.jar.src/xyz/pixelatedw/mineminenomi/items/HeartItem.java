package xyz.pixelatedw.mineminenomi.items;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.IDestroyableItem;
import xyz.pixelatedw.mineminenomi.api.helpers.SoulboundItemHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class HeartItem extends Item implements IDestroyableItem {
	public HeartItem() {
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
					if (var6.hasHeart()){
						var2.getInventory().removeItem(var4);
						return InteractionResultHolder.fail(var4);
					}else{
						if ((var5) == (var2)){
							var6.setHeart(true);
							var2.getInventory().removeItem(var4);
						}else{
							var5.hurt(ModDamageSources.getInstance((LevelAccessor)(var1)).heartAttack((LivingEntity)(var2)),5F);
							var5.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,1));
							var5.addEffect(new MobEffectInstance(MobEffects.CONFUSION,40,1));
							if ((var5.getHealth()) <= (0F)){
								var2.getInventory().removeItem(var4);
							}
						}
						return InteractionResultHolder.success(var4);
					}
				}
			}
		}
	}
	public void onEntityDestroyed(ItemEntity var1, ItemStack var2, Entity.RemovalReason var3) {
		if (var1.level().isClientSide()){
			return;
		}else{
			if (!(SoulboundItemHelper.hasOwner(var2))){
				return;
			}else{
				Pair var4 = SoulboundItemHelper.getOwner(var1.level(),var2);
				if ((var4.getValue()) == (null)){
					ExtendedWorldData.get().markDead(((UUID)(var4.getKey())));
					return;
				}else{
					Iterator var5 = ((LivingEntity)(var4.getValue())).getHandSlots().iterator();
					do{
						if (var5.hasNext()){
							ItemStack var6 = ((ItemStack)(var5.next()));
							if ((var6.getItem()) == (Items.TOTEM_OF_UNDYING)){
								var6.shrink(var6.getCount());
								continue;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
					((LivingEntity)(var4.getValue())).hurt(ModDamageSources.getInstance((LevelAccessor)(var1.level())).heartAttack((LivingEntity)(null)),3.4028235e+38F);
					return;
				}
			}
		}
	}
	public static ItemStack createHeartStack(LivingEntity var0) {
		ItemStack var1 = new ItemStack(((ItemLike)(ModItems.HEART.get())));
		SoulboundItemHelper.setOwner(var1,var0);
		Object[] var2 = new Object[]{var0.getDisplayName().getString()};
		MutableComponent var3 = Component.translatable(ModI18n.ITEM_HEART,var2);
		var1.set(DataComponents.CUSTOM_NAME,var3);
		return var1;
	}
}