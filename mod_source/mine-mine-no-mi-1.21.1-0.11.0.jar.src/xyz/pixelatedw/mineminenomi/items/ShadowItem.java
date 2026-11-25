package xyz.pixelatedw.mineminenomi.items;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class ShadowItem extends Item {
	public ShadowItem() {
		super(new Item.Properties().food(Foods.DRIED_KELP));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		var2.startUsingItem(var3);
		return InteractionResultHolder.success(var4);
	}
	public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
		if ((!(var2.isClientSide)) && (var3 instanceof Player)){
			EntityStatsData var4 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var3))).orElse((Object)(null))));
			if ((var4) == (null)){
				return var1;
			}else{
				if (!(var4.hasShadow())){
					var4.setShadow(true);
					ModNetwork.sendToAllTrackingAndSelf(new SSyncEntityStatsPacket(var3),var3);
				}else{
					if (((var3.getEffect(MobEffects.DAMAGE_BOOST)) != (null)) && ((var3.getEffect(MobEffects.DAMAGE_RESISTANCE)) != (null))){
						int var5 = var3.getEffect(MobEffects.DAMAGE_BOOST).getDuration();
						int var6 = 0;
						if ((var5 = (var5) + (100)) > (500)){
							var6 = 1;
						}
						if ((var5) > (1200)){
							var5 = 1200;
						}
						var3.removeEffect(MobEffects.DAMAGE_BOOST);
						var3.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,var5,var6));
						var5 = var3.getEffect(MobEffects.DAMAGE_RESISTANCE).getDuration();
						var6 = 0;
						if ((var5 = (var5) + (100)) > (500)){
							var6 = 1;
						}
						if ((var5) > (1200)){
							var5 = 1200;
						}
						var3.removeEffect(MobEffects.DAMAGE_RESISTANCE);
						var3.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,var5,var6));
					}else{
						var3.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,100,0,false,false));
						var3.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,100,0,false,false));
					}
				}
				var1.shrink(1);
			}
		}
		return var1;
	}
	public UseAnim getUseAnimation(ItemStack var1) {
		return UseAnim.EAT;
	}
}