package xyz.pixelatedw.mineminenomi.items.bullets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.KairosekiBulletProjectile;

public class KairosekiBulletItem extends BulletItem {
	public KairosekiBulletItem() {
		super(new Item.Properties());
	}
	public NuProjectileEntity createProjectile(Level var1, LivingEntity var2) {
		return new KairosekiBulletProjectile((Level)(var1),(LivingEntity)(var2));
	}
}