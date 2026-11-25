package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.magu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.abilities.magu.MaguHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class MeigoProjectile extends NuHorizontalLightningEntity {
	public MeigoProjectile(EntityType<? extends MeigoProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public MeigoProjectile(Level var1, LivingEntity var2, Ability var3) {
		super(((EntityType)(ModProjectiles.MEIGO.get())),var2,20F,2F,(IAbility)(var3));
		this.setFadeTime(20);
		this.setSize(0.05F);
		this.setOneTimeHit();
		this.setDamage(100F);
		this.setPassThroughEntities();
		this.setArmorPiercing(1F);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::entityHitEvent))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void entityHitEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			AbilityHelper.setSecondsOnFireBy((Entity)(((LivingEntity)(var2))),20,this.getOwner());
		}
	}
	private void blockHitEvent(BlockHitResult var1) {
		MaguHelper.generateLavaPool(this.level(),var1.getBlockPos(),2);
	}
	private void tickEvent() {
		if (!(this.level().isClientSide)){
			double var5 = 0.0;
			double var6 = 0.0;
			double var7 = 0.0;
			double var1 = this.getCurrentX();
			double var2 = this.getCurrentY();
			double var3 = this.getCurrentZ();
			int var4 = 0;
			do{
				if ((var4) < (3)){
					var5 = (WyHelper.randomDouble()) / (2D);
					var6 = (WyHelper.randomDouble()) / (2D);
					var7 = (WyHelper.randomDouble()) / (2D);
					SimpleParticleData var8 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
					var8.setLife(5);
					var8.setSize(1.3F);
					WyHelper.spawnParticles(var8,((ServerLevel)(this.level())),(var1) + (var5),(var2) + (var6),(var3) + (var7));
					var4++;
					continue;
				}else{
					break;
				}
			} while (true);
			var4 = 0;
			do{
				if ((var4) < (10)){
					var5 = (WyHelper.randomDouble()) / (2D);
					var6 = (WyHelper.randomDouble()) / (2D);
					var7 = (WyHelper.randomDouble()) / (2D);
					((ServerLevel)(this.level())).sendParticles((ParticleOptions)(ParticleTypes.LAVA),(var1) + (var5),(var2) + (var6),(var3) + (var7),1,0D,0D,0D,0.5D);
					var4++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}