package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class DaiCircusProjectile extends NuProjectileEntity {
	public DaiCircusProjectile(EntityType<? extends DaiCircusProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public DaiCircusProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.DAI_CIRCUS.get())),var1,var2,var3);
		this.setDamage(4F);
		this.setMaxLife(30);
		this.setEntityCollisionSize(2D);
		this.setPhysical();
	}
}