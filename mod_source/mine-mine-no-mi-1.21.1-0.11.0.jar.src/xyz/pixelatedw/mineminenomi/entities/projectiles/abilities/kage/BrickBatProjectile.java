package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.kage;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BrickBatProjectile extends NuProjectileEntity {
	public BrickBatProjectile(EntityType<BrickBatProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public BrickBatProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.BRICK_BAT.get())),var1,var2,var3);
		this.setDamage(5F);
		this.setPassThroughEntities();
	}
}