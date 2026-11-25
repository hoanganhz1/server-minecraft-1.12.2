package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hie;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.IFlexibleSizeProjectile;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class IceBlockAvalancheProjectile extends NuProjectileEntity implements IFlexibleSizeProjectile {
	// Fields
	private static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(IceBlockAvalancheProjectile.class,EntityDataSerializers.FLOAT);
	public boolean finalized;

	public IceBlockAvalancheProjectile(EntityType<? extends IceBlockAvalancheProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.finalized = false;
	}
	public IceBlockAvalancheProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.ICE_BLOCK_AVALANCHE.get())),var1,var2,var3);
		this.finalized = false;
		this.setDamage(50F);
		this.setMaxLife(150);
		this.setPassThroughEntities();
		super.setPhysical();
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onTickEvent() {
		float var1 = (this.getSize()) / (6F);
		this.setBoundingBox(this.getBoundingBox().inflate((double)(var1)));
		this.setEntityCollisionSize((double)(var1));
		if (!(this.finalized)){
			this.setSize((this.getSize()) + (0.4F));
		}
	}
	public void onBlockImpactEvent(BlockHitResult var1) {
		if (!(this.level().isClientSide)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.ICE_BLOCK_AVALANCHE.get())),(Entity)(this),this.getX(),this.getY(),this.getZ());
		}
	}
	public void defineSynchedData(SynchedEntityData.Builder var1) {
		super.defineSynchedData(var1);
		var1.define(SIZE,Float.valueOf(1F));
	}
	public void setSize(float var1) {
		this.getEntityData().set(SIZE,Float.valueOf(Math.min(var1,50F)));
	}
	public float getSize() {
		return ((Float)(this.entityData.get(SIZE))).floatValue();
	}
}