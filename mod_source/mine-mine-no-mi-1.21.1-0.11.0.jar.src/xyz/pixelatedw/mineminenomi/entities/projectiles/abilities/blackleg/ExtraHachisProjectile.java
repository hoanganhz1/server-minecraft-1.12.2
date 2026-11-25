package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.blackleg;

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

public class ExtraHachisProjectile extends NuProjectileEntity {
	// Fields
	public static final float DAMAGE = 8F;

	public ExtraHachisProjectile(EntityType<? extends ExtraHachisProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public ExtraHachisProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.EXTRA_HACHIS.get())),var1,var2,(IAbility)(null),SourceElement.NONE,SourceHakiNature.HARDENING,new SourceType[]{SourceType.FIST});
		this.setDamage(8F);
		this.setMaxLife(5);
		this.setEntityCollisionSize(1D);
	}
}