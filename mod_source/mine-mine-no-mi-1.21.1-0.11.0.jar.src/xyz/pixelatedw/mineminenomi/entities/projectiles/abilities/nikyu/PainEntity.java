package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class PainEntity extends NuProjectileEntity {
	public PainEntity(EntityType<? extends PainEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public PainEntity(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.PAIN.get())),var1,var2,var3);
		super.setArmorPiercing(1F);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		float var2 = Math.min(super.getDamage(),20F);
		IAbility var3 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		PainEntity var4 = this;
		BlockHitResult var5 = var1;
		float var6 = var2;
		final BlockHitResult var5_f1 = var5;
		final float var6_f2 = var6;
		Consumer<ExplosionComponent> var7 = (l0) -> {
			AbilityExplosion lv1_4 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var5_f1.getBlockPos().getX()),(double)(var5_f1.getBlockPos().getY()),(double)(var5_f1.getBlockPos().getZ()),Math.max(1F,(var6_f2) / (2F)));
			lv1_4.setExplosionSound(true);
			lv1_4.setDamageEntities(false);
			lv1_4.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION2));
			lv1_4.setDamageEntities(false);
			lv1_4.explode();
		};
		ExplosionComponent.createExplosion(var3,var7);
	}
}