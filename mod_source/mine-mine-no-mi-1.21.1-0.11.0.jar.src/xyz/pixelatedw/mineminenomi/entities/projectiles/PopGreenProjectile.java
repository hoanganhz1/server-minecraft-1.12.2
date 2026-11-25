package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class PopGreenProjectile extends AbstractArrow {
	public PopGreenProjectile(EntityType<? extends PopGreenProjectile> var1, Level var2) {
		super(var1,var2);
	}
	public PopGreenProjectile(LivingEntity var1) {
		super(((EntityType)(ModProjectiles.POP_GREEN.get())),var1,var1.level(),((Item)(ModItems.POP_GREEN.get())).getDefaultInstance(),(ItemStack)(null));
	}
	protected ItemStack getDefaultPickupItem() {
		return ((Item)(ModItems.POP_GREEN.get())).getDefaultInstance();
	}
	public boolean isOnGround() {
		return this.inGround;
	}
}