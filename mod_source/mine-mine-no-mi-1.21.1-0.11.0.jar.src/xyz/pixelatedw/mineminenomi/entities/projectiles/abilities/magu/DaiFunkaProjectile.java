package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.magu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.abilities.magu.MaguHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class DaiFunkaProjectile extends NuProjectileEntity {
	// Fields
	private boolean changeLifeTime;

	public DaiFunkaProjectile(EntityType<? extends DaiFunkaProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.changeLifeTime = true;
	}
	public DaiFunkaProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.DAI_FUNKA.get())),var1,var2,var3);
		this.changeLifeTime = true;
		this.setDamage(80F);
		this.setMaxLife(35);
		this.setPassThroughEntities();
		this.setArmorPiercing(1F);
		this.setEntityCollisionSize(7D,7D,7D);
		this.setBlockCollisionSize(6D,2D,6D);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::entityHitEvent))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void entityHitEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			AbilityHelper.setSecondsOnFireBy((Entity)(((LivingEntity)(var2))),15,this.getOwner());
		}
	}
	private void blockHitEvent(BlockHitResult var1) {
		MaguHelper.generateLavaPool(this.level(),var1.getBlockPos(),3);
		if (this.changeLifeTime){
			this.setLife(3);
			this.changeLifeTime = false;
		}
	}
	private void tickEvent() {
		if (!(this.level().isClientSide)){
			double var2 = 0.0;
			double var3 = 0.0;
			double var4 = 0.0;
			int var1 = 0;
			do{
				if ((var1) < (2)){
					var2 = (WyHelper.randomDouble()) / (2D);
					var3 = (WyHelper.randomDouble()) / (2D);
					var4 = (WyHelper.randomDouble()) / (2D);
					SimpleParticleData var5 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
					var5.setLife(3);
					var5.setSize(2.3F);
					WyHelper.spawnParticles(var5,((ServerLevel)(this.level())),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4));
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
			var1 = 0;
			do{
				if ((var1) < (2)){
					var2 = (WyHelper.randomDouble()) / (2D);
					var3 = (WyHelper.randomDouble()) / (2D);
					var4 = (WyHelper.randomDouble()) / (2D);
					SimpleParticleData var5_1 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MAGU.get())));
					var5_1.setLife(3);
					var5_1.setSize(2.3F);
					WyHelper.spawnParticles(var5_1,((ServerLevel)(this.level())),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4));
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}