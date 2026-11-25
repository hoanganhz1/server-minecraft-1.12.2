package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suna;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class SablesPesadoProjectile extends NuProjectileEntity {
	public SablesPesadoProjectile(EntityType<? extends SablesPesadoProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public SablesPesadoProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.SABLES_PESADO.get())),var1,var2,var3);
		this.setDamage(50F);
		this.setMaxLife(50);
		this.setPhysical();
		this.setArmorPiercing(0.5F);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockImpactEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void blockImpactEvent(BlockHitResult var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		SablesPesadoProjectile var3 = this;
		BlockHitResult var4 = var1;
		final BlockHitResult var4_f1 = var4;
		Consumer<ExplosionComponent> var5 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),var4_f1.getLocation().x(),var4_f1.getLocation().y(),var4_f1.getLocation().z(),6F);
			lv1_3.setStaticDamage(35F);
			lv1_3.setExplosionSound(true);
			lv1_3.setDamageOwner(false);
			lv1_3.setDestroyBlocks(true);
			lv1_3.setFireAfterExplosion(false);
			lv1_3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION8));
			lv1_3.setDamageEntities(true);
			lv1_3.explode();
		};
		ExplosionComponent.createExplosion(var2,var5);
	}
	private void tickEvent() {
		if (!(this.level().isClientSide)){
			double var2 = 0.0;
			double var3 = 0.0;
			double var4 = 0.0;
			int var1 = 0;
			do{
				if ((var1) < (20)){
					var2 = WyHelper.randomDouble();
					var3 = WyHelper.randomDouble();
					var4 = WyHelper.randomDouble();
					SimpleParticleData var5 = new SimpleParticleData(((ParticleType)(ModParticleTypes.SUNA.get())));
					var5.setLife(6);
					var5.setSize(1.3F);
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
					var2 = WyHelper.randomDouble();
					var3 = WyHelper.randomDouble();
					var4 = WyHelper.randomDouble();
					SimpleParticleData var5_1 = new SimpleParticleData(((ParticleType)(ModParticleTypes.SUNA2.get())));
					var5_1.setLife(4);
					var5_1.setSize(1.2F);
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