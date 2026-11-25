package xyz.pixelatedw.mineminenomi.items.dials;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class FlameDialItem extends BlockItem {
	public FlameDialItem(Block var1) {
		super(var1,new Item.Properties().stacksTo(16));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		if ((!(var1.isClientSide)) && (!(var2.isCrouching()))){
			Vec3 var5 = var2.getLookAngle().multiply(0.2D,0.2D,0.2D);
			var1.addFreshEntity((Entity)(new SmallFireball(var1,var2.getX(),(var2.getY()) + (1.5D),var2.getZ(),var5)));
			var2.getCooldowns().addCooldown((Item)(this),10);
			var4.shrink(1);
		}
		return InteractionResultHolder.success(var4);
	}
}