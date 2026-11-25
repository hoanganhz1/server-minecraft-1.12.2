package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.toriphoenix;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class PhoenixGoenProjectile extends NuProjectileEntity {
	// Fields
	private Vec3 lookVec;

	public PhoenixGoenProjectile(EntityType<? extends PhoenixGoenProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public PhoenixGoenProjectile(Level var1, LivingEntity var2, IAbility var3, Vec3 var4) {
		super(((EntityType)(ModProjectiles.PHOENIX_GOEN.get())),var1,var2,var3);
		this.setDamage(10F);
		this.setMaxLife(30);
		this.lookVec = var4;
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			LivingEntity var3 = ((LivingEntity)(var2));
			Vec3 var4 = this.lookVec.multiply(1.5D,0D,1.5D);
			AbilityHelper.setDeltaMovement((Entity)(var3),var4.x,0.15D,var4.z);
			var3.fallDistance = 0F;
		}
	}
	private void onTickEvent() {
		if (!(this.level().isClientSide)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.PHOENIX_GOEN.get())),(Entity)(this),this.getX(),this.getY(),this.getZ());
		}
	}
}