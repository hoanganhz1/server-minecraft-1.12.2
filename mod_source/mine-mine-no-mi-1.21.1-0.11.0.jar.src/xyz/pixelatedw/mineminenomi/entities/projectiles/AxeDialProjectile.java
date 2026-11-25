package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class AxeDialProjectile extends NuProjectileEntity {
	public AxeDialProjectile(EntityType<AxeDialProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public AxeDialProjectile(Level var1, LivingEntity var2) {
		super(((EntityType)(ModProjectiles.AXE_DIAL_PROJECTILE.get())),var1,var2,(IAbility)(null),SourceElement.AIR,SourceHakiNature.IMBUING,new SourceType[]{SourceType.SLASH,SourceType.PROJECTILE,SourceType.TECHNOLOGY});
		this.setDamage(20F);
		this.setMaxLife(20);
		this.setPhysical();
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
	}
	private void blockHitEvent(BlockHitResult var1) {
		AbilityExplosion var2 = new AbilityExplosion((Entity)(this),(IAbility)(null),this.getX(),this.getY(),this.getZ(),4F);
		var2.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION4));
		var2.explode();
	}
}