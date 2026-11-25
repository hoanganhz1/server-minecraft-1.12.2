package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.kage;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class TsunoTokagePillarEntity extends NuProjectileEntity {
	public TsunoTokagePillarEntity(EntityType<TsunoTokagePillarEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public TsunoTokagePillarEntity(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.TSUNO_TOKAGE.get())),var1,var2,var3);
		this.setDamage(30F);
		this.setMaxLife(8);
		this.setPassThroughEntities();
		this.setPassThroughBlocks();
		this.setEntityCollisionSize(3D,5D,3D);
	}
}