package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class BubblyCoralItem extends Item {
	// Fields
	public static final int EFFECT_DURATION = 3600;

	public BubblyCoralItem() {
		super(new Item.Properties().durability(3));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		if (var2.hasEffect(ModEffects.BUBBLY_CORAL)){
			return InteractionResultHolder.pass(var4);
		}else{
			var2.addEffect(new MobEffectInstance(ModEffects.BUBBLY_CORAL,3600,0));
			var4.hurtAndBreak(1,(LivingEntity)(var2),EquipmentSlot.MAINHAND);
			return InteractionResultHolder.success(var4);
		}
	}
	public boolean isBookEnchantable(ItemStack var1, ItemStack var2) {
		return false;
	}
}