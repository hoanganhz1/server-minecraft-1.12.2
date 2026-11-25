package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class NormalBulletProjectile extends NuProjectileEntity {
	public NormalBulletProjectile(EntityType<? extends NormalBulletProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public NormalBulletProjectile(Level var1, LivingEntity var2) {
		super(((EntityType)(ModProjectiles.NORMAL_BULLET.get())),var1,var2,(IAbility)(null),SourceElement.NONE,SourceHakiNature.IMBUING,new SourceType[]{SourceType.PROJECTILE,SourceType.PHYSICAL,SourceType.BLUNT});
		this.setDamage(5F);
	}
}