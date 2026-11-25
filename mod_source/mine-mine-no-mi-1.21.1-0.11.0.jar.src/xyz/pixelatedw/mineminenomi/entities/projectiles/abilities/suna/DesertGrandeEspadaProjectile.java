package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suna;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class DesertGrandeEspadaProjectile extends NuProjectileEntity {
	public DesertGrandeEspadaProjectile(EntityType<? extends DesertGrandeEspadaProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public DesertGrandeEspadaProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.DESERT_GRANDE_ESPADA.get())),var1,var2,var3);
		this.setDamage(60F);
		this.setMaxLife(8);
		this.setPassThroughBlocks();
		this.setPassThroughEntities();
		this.setEntityCollisionSize(1.75D,5D,1.75D);
	}
}