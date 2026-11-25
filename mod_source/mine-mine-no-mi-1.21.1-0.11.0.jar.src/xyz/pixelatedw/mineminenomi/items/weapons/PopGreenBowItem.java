package xyz.pixelatedw.mineminenomi.items.weapons;

import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.entities.projectiles.PopGreenProjectile;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class PopGreenBowItem extends BowItem {
	public PopGreenBowItem(int var1) {
		super(new Item.Properties().stacksTo(1).durability(var1));
	}
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		Predicate<ItemStack> var1 = (l0) -> {
			return (l0.getItem()) == (ModItems.POP_GREEN.get());
		};
		return var1;
	}
	public AbstractArrow customArrow(AbstractArrow var1, ItemStack var2, ItemStack var3) {
		return new PopGreenProjectile(((LivingEntity)(var1.getOwner())));
	}
	public int getDefaultProjectileRange() {
		return 32;
	}
}