package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bomu;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.bomu.BreezeBreathBombAbility;
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

public class BreezeBreathBombProjectile extends NuProjectileEntity {
	// Fields
	public static final float DAMAGE = 15F;
	public static final float EXPLOSION_RADIUS = 2F;
	public static final float EXPLOSION_DAMAGE = 12F;

	public BreezeBreathBombProjectile(EntityType<BreezeBreathBombProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public BreezeBreathBombProjectile(Level var1, LivingEntity var2, BreezeBreathBombAbility var3) {
		super(((EntityType)(ModProjectiles.BREEZE_BREATH_BOMB.get())),var1,var2,(IAbility)(var3));
		this.setPhysical();
		this.setDamage(15F);
		this.setMaxLife(26);
		this.setPassThroughEntities();
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void tickEvent() {
		if ((this.tickCount) < (3)){
			return;
		}else{
			BlockPos var1 = this.blockPosition();
			IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
			BreezeBreathBombProjectile var3 = this;
			BlockPos var4 = var1;
			final BlockPos var4_f1 = var4;
			Consumer<ExplosionComponent> var5 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var4_f1.getX()),(double)(var4_f1.getY()),(double)(var4_f1.getZ()),2F);
			lv1_3.setStaticDamage(12F);
			lv1_3.setExplosionSound(true);
			lv1_3.setDamageOwner(false);
			lv1_3.setDestroyBlocks(true);
			lv1_3.setFireAfterExplosion(false);
			lv1_3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION4));
			lv1_3.setDamageEntities(true);
			lv1_3.explode();
		};
			ExplosionComponent.createExplosion(var2,var5);
			return;
		}
	}
}