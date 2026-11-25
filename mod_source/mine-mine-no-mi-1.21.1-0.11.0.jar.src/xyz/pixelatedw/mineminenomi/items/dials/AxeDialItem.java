package xyz.pixelatedw.mineminenomi.items.dials;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import xyz.pixelatedw.mineminenomi.entities.projectiles.AxeDialProjectile;

public class AxeDialItem extends BlockItem {
	public AxeDialItem(Block var1) {
		super(var1,new Item.Properties().stacksTo(16));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		if (!(var1.isClientSide)){
			AxeDialProjectile var5 = new AxeDialProjectile((Level)(var2.level()),(LivingEntity)(var2));
			var1.addFreshEntity((Entity)(var5));
			var5.shootFromRotation((Entity)(var2),var2.getXRot(),var2.getYRot(),0F,2F,1F);
			var2.getCooldowns().addCooldown((Item)(this),10);
			var4.shrink(1);
		}
		return InteractionResultHolder.success(var4);
	}
}