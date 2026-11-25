package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BaraBaraHoProjectile extends NuProjectileEntity {
	public BaraBaraHoProjectile(EntityType<? extends BaraBaraHoProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public BaraBaraHoProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.BARA_BARA_HO.get())),var1,var2,var3);
		this.setDamage(6F);
		this.setMaxLife(12);
		this.setFist();
	}
}