package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class HandcuffsItem extends Item {
	// Fields
	private static final int HANDCUFF_TIME = 2400;
	private Holder<MobEffect> handcuffEffect;

	public HandcuffsItem(Holder<MobEffect> var1) {
		super(new Item.Properties().stacksTo(1));
		this.handcuffEffect = var1;
	}
	public Holder<MobEffect> getEffect() {
		return this.handcuffEffect;
	}
	public boolean hurtEnemy(ItemStack var1, LivingEntity var2, LivingEntity var3) {
		MobEffectInstance var4 = var2.getEffect((Holder)(ModEffects.UNCONSCIOUS));
		if (((var4) != (null)) && ((var4.getAmplifier()) >= (0))){
			handleHandcuffActivation(var1,var2,2F);
			return false;
		}else{
			return true;
		}
	}
	public static boolean handleHandcuffActivation(ItemStack var0, LivingEntity var1, float var2) {
		if ((!((var2) <= (0F))) && (!(((var1.getHealth()) - (var2)) > (0F)))){
			if (!(var0.isEmpty())){
				Item var3 = var0.getItem();
				if (var3 instanceof HandcuffsItem){
					HandcuffsItem var4 = ((HandcuffsItem)(var3));
					var1.setHealth(2F);
					var1.addEffect(new MobEffectInstance(var4.handcuffEffect,2400,0));
					var1.invulnerableTime = 100;
					var0.shrink(1);
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}