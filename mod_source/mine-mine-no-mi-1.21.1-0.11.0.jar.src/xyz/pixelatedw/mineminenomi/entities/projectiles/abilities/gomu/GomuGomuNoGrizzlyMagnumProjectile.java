package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class GomuGomuNoGrizzlyMagnumProjectile extends NuHorizontalLightningEntity {
	// Fields
	private static final double KNOCKBACK = 14D;
	private Vec3 lookVec;

	public GomuGomuNoGrizzlyMagnumProjectile(EntityType<? extends GomuGomuNoGrizzlyMagnumProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.lookVec = Vec3.ZERO;
	}
	public GomuGomuNoGrizzlyMagnumProjectile(Level var1, LivingEntity var2, Ability var3) {
		super(((EntityType)(ModProjectiles.GOMU_GOMU_NO_GRIZZLY_MAGNUM.get())),var2,40F,8F,(IAbility)(var3));
		this.lookVec = Vec3.ZERO;
		this.setRetracting();
		this.setSize(0.2F);
		this.setOneTimeHit();
		this.setDepth(1);
		this.setDamage(40F);
		this.setPassThroughEntities();
		this.setFist();
		this.setAlwaysDamage(false);
		this.setEntityCollisionSize(5D,3D,5D);
		this.lookVec = var2.getLookAngle();
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			LivingEntity var3 = ((LivingEntity)(var2));
			AbilityHelper.setDeltaMovement((Entity)(var3),this.lookVec.normalize().multiply(14D,1D,14D).add(0D,0.15D,0D));
		}
	}
}