package xyz.pixelatedw.mineminenomi.entities.projectiles;

import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class KairosekiBulletProjectile extends NuProjectileEntity {
	public KairosekiBulletProjectile(EntityType<? extends KairosekiBulletProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public KairosekiBulletProjectile(Level var1, LivingEntity var2) {
		super(((EntityType)(ModProjectiles.KAIROSEKI_BULLET.get())),var1,var2,(IAbility)(null),SourceElement.NONE,SourceHakiNature.IMBUING,new SourceType[]{SourceType.PROJECTILE,SourceType.PHYSICAL,SourceType.BLUNT});
		this.setDamage(8F);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::entityHitEvent))));
	}
	private void entityHitEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			AbilityHelper.disableAbilities(((LivingEntity)(var2)),20,AbilityCategory.DEVIL_FRUITS.isAbilityPartofCategory());
		}
	}
}