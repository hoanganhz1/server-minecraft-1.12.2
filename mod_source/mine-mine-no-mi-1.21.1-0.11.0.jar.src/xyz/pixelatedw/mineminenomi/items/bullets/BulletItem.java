package xyz.pixelatedw.mineminenomi.items.bullets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;

public abstract class BulletItem extends Item {
	public BulletItem(Item.Properties var1) {
		super(var1);
	}
	public abstract NuProjectileEntity createProjectile(Level var0, LivingEntity var1);
}