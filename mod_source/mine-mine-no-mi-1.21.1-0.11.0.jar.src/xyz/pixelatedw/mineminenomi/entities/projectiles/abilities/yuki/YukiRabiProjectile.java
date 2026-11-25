package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yuki;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.effects.FrostbiteEffect;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class YukiRabiProjectile extends NuProjectileEntity {
	public YukiRabiProjectile(EntityType<? extends YukiRabiProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public YukiRabiProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.YUKI_RABI.get())),var1,var2,var3);
		this.setDamage(3.5F);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			FrostbiteEffect.addFrostbiteStacks(((LivingEntity)(var2)),1);
		}
	}
	private void onTickEvent() {
		if (!(this.level().isClientSide)){
			int var1 = 0;
			do{
				if ((var1) < (2)){
					double var2 = (WyHelper.randomDouble()) / (5D);
					double var3 = (WyHelper.randomDouble()) / (5D);
					double var4 = (WyHelper.randomDouble()) / (5D);
					ParticleType var5 = null;
					if (((var1) % (2)) == (0)){
						var5 = ((ParticleType)(ModParticleTypes.YUKI2.get()));
					}else{
						var5 = ((ParticleType)(ModParticleTypes.YUKI.get()));
					}
					SimpleParticleData var6 = new SimpleParticleData(var5);
					var6.setLife(20);
					var6.setSize(1.3F);
					var6.setMotion(0D,-0.02D,0D);
					WyHelper.spawnParticles(var6,((ServerLevel)(this.level())),(this.getX()) + (var2),((this.getY()) + (0.25D)) + (var3),(this.getZ()) + (var4));
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}