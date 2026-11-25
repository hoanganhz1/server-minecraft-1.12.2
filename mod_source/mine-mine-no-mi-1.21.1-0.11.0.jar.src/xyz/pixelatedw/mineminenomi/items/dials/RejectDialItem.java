package xyz.pixelatedw.mineminenomi.items.dials;

import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;

public class RejectDialItem extends BlockItem {
	public RejectDialItem(Block var1) {
		super(var1,new Item.Properties().stacksTo(16));
	}
	public boolean hurtEnemy(ItemStack var1, LivingEntity var2, LivingEntity var3) {
		if ((!(var3.level().isClientSide)) && (var3 instanceof Player)){
			Player var4 = ((Player)(var3));
			if ((var4.getCooldowns().getCooldownPercent((Item)(this),0F)) > (0F)){
				return false;
			}else{
				var4.getCooldowns().addCooldown((Item)(this),400);
				var4.getItemInHand(var4.getUsedItemHand()).shrink(1);
				var4.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,600,2,false,false));
				var4.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,600,1,false,false));
				var4.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,600,1,false,false));
				DamageSource var5 = ModDamageSources.getInstance((LevelAccessor)(var2.level())).rejectDial((Entity)(var4));
				if (!(var4.isCreative())){
					var3.hurt(var5,(var3.getMaxHealth()) - (1F));
				}
				var2.hurt(var5,(var2.getMaxHealth()) + (1F));
				return true;
			}
		}else{
			return false;
		}
	}
}