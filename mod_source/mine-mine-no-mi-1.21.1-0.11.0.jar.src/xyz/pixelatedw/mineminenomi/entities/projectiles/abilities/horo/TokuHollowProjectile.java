package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.horo;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
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

public class TokuHollowProjectile extends NuProjectileEntity {
	public TokuHollowProjectile(EntityType<? extends TokuHollowProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public TokuHollowProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.TOKU_HOLLOW.get())),var1,var2,var3);
		this.setDamage(25F);
		this.setPassThroughEntities();
		this.setEntityCollisionSize(5D);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			LivingEntity var3 = ((LivingEntity)(var2));
			var3.addEffect(new MobEffectInstance(MobEffects.CONFUSION,350,0));
			var3.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,350,0));
		}
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		TokuHollowProjectile var3 = this;
		BlockHitResult var4 = var1;
		final BlockHitResult var4_f1 = var4;
		Consumer<ExplosionComponent> var5 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var4_f1.getBlockPos().getX()),(double)(var4_f1.getBlockPos().getY()),(double)(var4_f1.getBlockPos().getZ()),7F);
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
}