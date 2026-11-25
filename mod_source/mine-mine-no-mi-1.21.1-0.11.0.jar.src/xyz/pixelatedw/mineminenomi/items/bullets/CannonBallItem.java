package xyz.pixelatedw.mineminenomi.items.bullets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.CannonBallProjectile;

public class CannonBallItem extends BulletItem {
	public CannonBallItem() {
		super(new Item.Properties());
	}
	public NuProjectileEntity createProjectile(Level var1, LivingEntity var2) {
		return new CannonBallProjectile((Level)(var1),(LivingEntity)(var2));
	}
}