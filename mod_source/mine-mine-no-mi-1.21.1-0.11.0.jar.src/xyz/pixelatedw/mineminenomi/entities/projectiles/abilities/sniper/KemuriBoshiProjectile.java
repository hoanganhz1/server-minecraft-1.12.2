package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.clouds.KemuriBoshiCloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class KemuriBoshiProjectile extends NuProjectileEntity {
	// Fields
	public static final float DAMAGE = 3F;
	public static final int SMOKE_TIME = 100;

	public KemuriBoshiProjectile(EntityType<? extends KemuriBoshiProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public KemuriBoshiProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.KEMURI_BOSHI.get())),var1,var2,var3);
		this.setDamage(3F);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		if ((this.getOwner()) == (null)){
			return;
		}else{
			KemuriBoshiCloudEntity var2 = new KemuriBoshiCloudEntity((Level)(this.level()),(LivingEntity)(this.getOwner()));
			var2.setLife(100);
			var2.moveTo((double)(var1.getBlockPos().getX()),(double)((var1.getBlockPos().getY()) + (1)),(double)(var1.getBlockPos().getZ()),0F,0F);
			AbilityHelper.setDeltaMovement(var2,0D,0D,0D);
			this.level().addFreshEntity((Entity)(var2));
			return;
		}
	}
}