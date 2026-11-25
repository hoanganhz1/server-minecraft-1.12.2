package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.supa;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class SparklingDaisyAftereffectProjectile extends NuProjectileEntity {
	public SparklingDaisyAftereffectProjectile(EntityType<? extends SparklingDaisyAftereffectProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public SparklingDaisyAftereffectProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.SPARKLING_DAISY_AFTER.get())),var1,var2,var3);
		this.setDamage(10F);
		this.setMaxLife(20);
		this.setPassThroughEntities();
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	public void tickEvent() {
		if ((!(this.level().isClientSide)) && ((this.tickCount) > (5))){
			IAbility var1 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
			SparklingDaisyAftereffectProjectile var2 = this;
			Consumer<ExplosionComponent> var3 = (l0) -> {
			AbilityExplosion lv1_2 = l0.createExplosion((Entity)(this),this.getOwner(),this.getX(),this.getY(),this.getZ(),2F);
			lv1_2.setStaticDamage(5F);
			lv1_2.setFireAfterExplosion(false);
			lv1_2.setExplosionSound(false);
			lv1_2.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION4));
			lv1_2.explode();
		};
			ExplosionComponent.createExplosion(var1,var3);
		}
	}
}