package xyz.pixelatedw.mineminenomi.items.bullets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.NormalBulletProjectile;

public class NormalBulletItem extends BulletItem {
	public NormalBulletItem() {
		super(new Item.Properties());
	}
	public NuProjectileEntity createProjectile(Level var1, LivingEntity var2) {
		return new NormalBulletProjectile((Level)(var1),(LivingEntity)(var2));
	}
}