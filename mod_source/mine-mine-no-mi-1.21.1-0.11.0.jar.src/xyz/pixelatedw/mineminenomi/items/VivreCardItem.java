package xyz.pixelatedw.mineminenomi.items;

import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.items.components.Soulbound;
import xyz.pixelatedw.mineminenomi.entities.VivreCardEntity;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public class VivreCardItem extends Item {
	public VivreCardItem() {
		super(new Item.Properties().stacksTo(1));
	}
	public boolean onDroppedByPlayer(ItemStack var1, Player var2) {
		VivreCardEntity var3 = new VivreCardEntity(var2.level());
		if (!(var1.has(ModDataComponents.SOULBOUND))){
			return true;
		}else{
			Soulbound var4 = ((Soulbound)(var1.get(ModDataComponents.SOULBOUND)));
			LivingEntity var5 = ((LivingEntity)(((ServerLevel)(var2.level())).getEntity(var4.id())));
			if ((var5) == (null)){
				return true;
			}else{
				var3.moveTo(var2.getX(),var2.getY(),var2.getZ(),var2.getYRot(),var2.getXRot());
				float var6 = Mth.sin((var2.getXRot()) * (0.017453292F));
				float var7 = Mth.cos((var2.getXRot()) * (0.017453292F));
				float var8 = Mth.sin((var2.getYRot()) * (0.017453292F));
				float var9 = Mth.cos((var2.getYRot()) * (0.017453292F));
				float var10 = (var2.getRandom().nextFloat()) * (6.2831855F);
				float var11 = (0.02F) * (var2.getRandom().nextFloat());
				var3.setDeltaMovement(((double)(((-(var8)) * (var7)) * (0.3F))) + ((Math.cos((double)(var10))) * ((double)(var11))),(double)((((-(var6)) * (0.3F)) + (0.1F)) + (((var2.getRandom().nextFloat()) - (var2.getRandom().nextFloat())) * (0.1F))),((double)(((var9) * (var7)) * (0.3F))) + ((Math.sin((double)(var10))) * ((double)(var11))));
				var3.setOwner(var5.getUUID());
				var2.level().addFreshEntity((Entity)(var3));
				var1.setCount(0);
				return false;
			}
		}
	}
	public void inventoryTick(ItemStack var1, Level var2, Entity var3, int var4, boolean var5) {
		if (var2.isClientSide){
			return;
		}else{
			Soulbound var6 = ((Soulbound)(var1.get(ModDataComponents.SOULBOUND)));
			if ((var6) != (null)){
				LivingEntity var7 = ((LivingEntity)(((ServerLevel)(var2)).getEntity(var6.id())));
				if (((var7) != (null)) && ((var7.getHealth()) <= (0F))){
					var1.setCount(0);
				}else{
					return;
				}
			}else{
				if (var3 instanceof LivingEntity){
					this.setOwner(var1,((LivingEntity)(var3)));
				}else{
					return;
				}
			}
			return;
		}
	}
	public void setOwner(ItemStack var1, LivingEntity var2) {
		var1.set(ModDataComponents.SOULBOUND,(Object)(new Soulbound(var2)));
		String var3 = var1.getHoverName().getString();
		String var4 = String.valueOf((Object)(ChatFormatting.RESET));
		String var5 = var2.getDisplayName().getString();
		String var6 = var3;
		String var7 = var4 + var5 + "'s " + var6;
		var1.set(DataComponents.CUSTOM_NAME,(Object)(Component.literal(var7)));
	}
}