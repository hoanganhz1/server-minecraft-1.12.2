package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.carnivaltricks;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class TopEntity extends NuProjectileEntity {
	public TopEntity(EntityType<? extends TopEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public TopEntity(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.TOP.get())),var1,var2,var3);
		this.setDamage(2F);
		this.setArmorPiercing(0.25F);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockImpactEvent))));
	}
	private void blockImpactEvent(BlockHitResult var1) {
		if (((this.hashCode()) % (3)) == (0)){
			AbilityExplosion var2 = new AbilityExplosion((Entity)(this.getOwner()),((IAbility)(this.getParent().get())),(double)(var1.getBlockPos().getX()),(double)(var1.getBlockPos().getY()),(double)(var1.getBlockPos().getZ()),1F);
			var2.setStaticDamage(2F);
			var2.disableExplosionKnockback();
			var2.setFireAfterExplosion(false);
			var2.setDestroyBlocks(false);
			var2.setExplosionSound(false);
			var2.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION2));
			var2.explode();
		}
	}
}