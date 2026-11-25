package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
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

public class CannonBallProjectile extends NuProjectileEntity {
	// Fields
	public static final float DAMAGE = 14F;

	public CannonBallProjectile(EntityType<? extends CannonBallProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public CannonBallProjectile(Level var1, LivingEntity var2) {
		this(var1,var2,SourceElement.NONE,SourceHakiNature.IMBUING,new SourceType[]{SourceType.PROJECTILE,SourceType.PHYSICAL,SourceType.BLUNT});
	}
	public CannonBallProjectile(Level var1, LivingEntity var2, SourceElement var3, SourceHakiNature var4, SourceType... var5) {
		super(((EntityType)(ModProjectiles.CANNON_BALL.get())),var1,var2,(IAbility)(null),var3,var4,var5);
		this.setDamage(14F);
		this.setMaxLife(40);
		this.setGravity(0.01F);
		this.setPhysical();
		this.setEntityCollisionSize(1.5D,1.5D,1.5D);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
	}
	public CannonBallProjectile(Level var1, LivingEntity var2, IAbility var3) {
		this(var1,var2,var3.getCore().getSourceElement(),var3.getCore().getSourceHakiNature(),var3.getCore().getSourceTypesArray());
	}
	private void blockHitEvent(BlockHitResult var1) {
		AbilityExplosion var2 = new AbilityExplosion((Entity)(this.getOwner()),(IAbility)(null),this.getX(),this.getY(),this.getZ(),2F);
		var2.setStaticDamage(8F);
		var2.setDestroyBlocks(true);
		var2.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION2));
		var2.explode();
	}
}