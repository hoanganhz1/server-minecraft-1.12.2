package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doru;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class DoruDoruArtsMoriProjectile extends NuProjectileEntity {
	public DoruDoruArtsMoriProjectile(EntityType<? extends DoruDoruArtsMoriProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public DoruDoruArtsMoriProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.DORU_DORU_ARTS_MORI.get())),var1,var2,var3);
		this.setDamage(20F);
		this.setMaxLife(40);
		super.setPhysical();
	}
}