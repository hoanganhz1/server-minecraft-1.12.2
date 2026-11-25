package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.moku;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class WhiteBlowProjectile extends NuProjectileEntity {
	public WhiteBlowProjectile(EntityType<? extends WhiteBlowProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public WhiteBlowProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.WHITE_OUT.get())),var1,var2,var3);
		this.setDamage(10F);
		this.setMaxLife(128);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityHit))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onEntityHit(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			((LivingEntity)(var2)).addEffect(new MobEffectInstance((Holder)(ModEffects.SMOKE),500,0));
		}
	}
	private void onTickEvent() {
		if (!(this.level().isClientSide)){
			int var1 = 0;
			do{
				if ((var1) < (10)){
					double var2 = (WyHelper.randomDouble()) / (2D);
					double var3 = (WyHelper.randomDouble()) / (2D);
					double var4 = (WyHelper.randomDouble()) / (2D);
					SimpleParticleData var5 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU.get())));
					var5.setLife(10);
					var5.setSize(1.3F);
					WyHelper.spawnParticles(var5,((ServerLevel)(this.level())),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4));
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}