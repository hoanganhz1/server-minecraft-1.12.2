package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bari;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BarrierCrashProjectile extends NuProjectileEntity {
	public BarrierCrashProjectile(EntityType<BarrierCrashProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public BarrierCrashProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.BARRIER_CRASH.get())),var1,var2,var3);
		this.setDamage(20F);
		this.setMaxLife(10);
		this.setPassThroughEntities();
		this.setPhysical();
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::entityHitEvent))));
	}
	private void entityHitEvent(EntityHitResult var1) {
		AbilityHelper.setDeltaMovement(var1.getEntity(),this.getDeltaMovement());
	}
}