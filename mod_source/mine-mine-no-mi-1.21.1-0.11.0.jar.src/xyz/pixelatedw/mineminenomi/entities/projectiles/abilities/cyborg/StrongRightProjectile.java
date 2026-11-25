package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class StrongRightProjectile extends NuProjectileEntity {
	public StrongRightProjectile(EntityType<? extends StrongRightProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public StrongRightProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.STRONG_RIGHT.get())),var1,var2,var3);
		this.setDamage(20F);
		this.setMaxLife(15);
		this.setFist();
		this.setEntityCollisionSize(1D);
	}
}