package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ito;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class TamaitoProjectile extends NuProjectileEntity {
	public TamaitoProjectile(EntityType<? extends TamaitoProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public TamaitoProjectile(Level var1, LivingEntity var2, Ability var3) {
		super(((EntityType)(ModProjectiles.TAMAITO.get())),var1,var2,(IAbility)(var3));
		this.setDamage(15F);
		this.setMaxLife(30);
		this.setArmorPiercing(0.25F);
	}
}