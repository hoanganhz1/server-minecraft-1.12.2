package xyz.pixelatedw.mineminenomi.items;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.ColaFuelAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModFoods;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateColaAmountPacket;

public class UltraColaItem extends Item {
	// Fields
	public static final int ULTRA_COLA_BONUS = 20;
	public static final int COLA_REFILL = 100;

	public UltraColaItem() {
		super(new Item.Properties().stacksTo(16).food(ModFoods.COLA));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		var2.startUsingItem(var3);
		return InteractionResultHolder.success(var4);
	}
	public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
		if ((!(var2.isClientSide)) && (var3 instanceof ServerPlayer)){
			ServerPlayer var4 = ((ServerPlayer)(var3));
			AbilityData var5 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var4)))).orElse((Object)(null))));
			EntityStatsData var6 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var4)))).orElse((Object)(null))));
			if (((var5) != (null)) && ((var6) != (null))){
				if (var5.hasPassiveAbility(((AbilityCore)(ColaFuelAbility.INSTANCE.get())))){
					if ((var6.getUltraCola()) <= (19)){
						var6.addUltraCola(1);
						var6.alterCola(50);
					}else{
						var4.addEffect(new MobEffectInstance(MobEffects.REGENERATION,100,0,false,false));
						var4.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,200,0,false,false));
						var4.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,0,false,false));
						var6.alterCola(100);
					}
					ModNetwork.sendTo(new SUpdateColaAmountPacket(var3),(Player)(var4));
				}else{
					var4.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,250,0));
				}
				var4.eat(var2,var1);
				if (!(var4.getAbilities().instabuild)){
					var4.addItem(new ItemStack(((ItemLike)(ModItems.EMPTY_ULTRA_COLA.get()))));
				}
			}else{
				return var1;
			}
		}
		return var1;
	}
	public UseAnim getUseAnimation(ItemStack var1) {
		return UseAnim.DRINK;
	}
}