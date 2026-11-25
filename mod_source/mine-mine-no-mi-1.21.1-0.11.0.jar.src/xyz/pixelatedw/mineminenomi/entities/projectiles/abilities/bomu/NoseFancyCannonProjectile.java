package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bomu;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.abilities.bomu.NoseFancyCannonAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class NoseFancyCannonProjectile extends NuProjectileEntity {
	// Fields
	public static final float DAMAGE = 10F;
	public static final float EXPLOSION_RANGE = 3F;
	public static final float EXPLOSION_DAMAGE = 15F;

	public NoseFancyCannonProjectile(EntityType<? extends NoseFancyCannonProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public NoseFancyCannonProjectile(Level var1, LivingEntity var2, NoseFancyCannonAbility var3) {
		super(((EntityType)(ModProjectiles.NOSE_FANCY_CANNON.get())),var1,var2,(IAbility)(var3));
		this.setDamage(10F);
		this.setMaxLife(32);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::entityHitEvent))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
	}
	private void entityHitEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			this.explode(((LivingEntity)(var2)).blockPosition());
		}
	}
	private void blockHitEvent(BlockHitResult var1) {
		this.explode(var1.getBlockPos());
	}
	private void explode(BlockPos var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		NoseFancyCannonProjectile var3 = this;
		BlockPos var4 = var1;
		final BlockPos var4_f1 = var4;
		Consumer<ExplosionComponent> var5 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var4_f1.getX()),(double)(var4_f1.getY()),(double)(var4_f1.getZ()),3F);
			lv1_3.setStaticDamage(15F);
			lv1_3.setExplosionSound(true);
			lv1_3.setDamageOwner(false);
			lv1_3.setDestroyBlocks(true);
			lv1_3.setFireAfterExplosion(false);
			lv1_3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION4));
			lv1_3.setDamageEntities(true);
			lv1_3.explode();
		};
		ExplosionComponent.createExplosion(var2,var5);
	}
}