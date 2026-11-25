package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFoods;

public class BottleOfRumItem extends Item {
	public BottleOfRumItem() {
		super(new Item.Properties().durability(5).food(ModFoods.ALCOHOL));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		var2.startUsingItem(var3);
		return InteractionResultHolder.success(var4);
	}
	public boolean isBookEnchantable(ItemStack var1, ItemStack var2) {
		return false;
	}
	public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
		if ((!(var2.isClientSide)) && (var3 instanceof Player)){
			Player var4 = ((Player)(var3));
			var4.eat(var2,var1,var1.getFoodProperties(var3));
			var4.awardStat(Stats.ITEM_USED.get(var1.getItem()));
			var2.playSound(((Player)(null)),var4.getX(),var4.getY(),var4.getZ(),SoundEvents.PLAYER_BURP,SoundSource.PLAYERS,0.5F,((var2.random.nextFloat()) * (0.1F)) + (0.9F));
			if (var4 instanceof ServerPlayer){
				ServerPlayer var5 = ((ServerPlayer)(var4));
				CriteriaTriggers.CONSUME_ITEM.trigger(var5,var1);
			}
			if (var3.hasEffect((Holder)(ModEffects.DRUNK))){
				MobEffectInstance var5_1 = var3.getEffect((Holder)(ModEffects.DRUNK));
				int var6 = var5_1.getAmplifier();
				int var7 = var5_1.getDuration();
				if ((var6) < (4)){
					var6++;
				}
				var3.removeEffect((Holder)(ModEffects.DRUNK));
				var3.addEffect(new MobEffectInstance((Holder)(ModEffects.DRUNK),(var7) + (200),var6));
			}else{
				var3.addEffect(new MobEffectInstance((Holder)(ModEffects.DRUNK),400,0));
			}
			var1.hurtAndBreak(1,var3,var4.getEquipmentSlotForItem(var1));
		}
		return var1;
	}
	public UseAnim getUseAnimation(ItemStack var1) {
		return UseAnim.DRINK;
	}
}