package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.baku;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BeroCannonProjectile extends NuProjectileEntity {
	public BeroCannonProjectile(EntityType<BeroCannonProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public BeroCannonProjectile(Level var1, LivingEntity var2, Ability var3) {
		super(((EntityType)(ModProjectiles.BERO_CANNON.get())),var1,var2,(IAbility)(var3));
		this.setDamage(20F);
		this.setMaxLife(50);
		this.setGravity(0.01F);
		this.setPhysical();
	}
}