package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ryupteranodon;

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

public class TempuraudonProjectile extends NuProjectileEntity {
	public TempuraudonProjectile(EntityType<? extends TempuraudonProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public TempuraudonProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.TEMPURAUDON.get())),var1,var2,var3);
		this.setDamage(50F);
		this.setPassThroughEntities();
		this.setMaxLife(32);
		this.setArmorPiercing(1F);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		TempuraudonProjectile var2 = this;
		BlockHitResult var3 = var1;
		final BlockHitResult var3_f1 = var3;
		Consumer<ExplosionComponent> var4 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var3_f1.getBlockPos().getX()),(double)(var3_f1.getBlockPos().getY()),(double)(var3_f1.getBlockPos().getZ()),6F);
			lv1_3.setStaticDamage(15F);
			lv1_3.disableExplosionKnockback();
			lv1_3.setDestroyBlocks(true);
			lv1_3.setFireAfterExplosion(false);
			lv1_3.setExplosionSound(false);
			lv1_3.setDamageEntities(false);
			lv1_3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION6));
			lv1_3.explode();
		};
		ExplosionComponent.createExplosion((NuProjectileEntity)(this),var4);
	}
}