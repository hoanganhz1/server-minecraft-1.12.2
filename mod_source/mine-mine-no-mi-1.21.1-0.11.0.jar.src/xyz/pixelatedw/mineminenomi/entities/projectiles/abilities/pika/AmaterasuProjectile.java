package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.pika;

import java.awt.Color;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.abilities.goro.ElThorAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class AmaterasuProjectile extends NuHorizontalLightningEntity {
	public AmaterasuProjectile(EntityType<? extends AmaterasuProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public AmaterasuProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.AMATERASU.get())),var2,128F,12F,var3);
		this.setMaxLife(20);
		this.setDamage(70F);
		this.setSize(0.25F);
		this.setColor(ElThorAbility.YELLOW_THUNDER,100);
		this.setAngle(100);
		this.setOneTimeHit();
		this.setBranches(1);
		this.setSegments(1);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
	}
	private void blockHitEvent(BlockHitResult var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		AmaterasuProjectile var3 = this;
		BlockHitResult var4 = var1;
		final BlockHitResult var4_f1 = var4;
		Consumer<ExplosionComponent> var5 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var4_f1.getBlockPos().getX()),(double)(var4_f1.getBlockPos().getY()),(double)(var4_f1.getBlockPos().getZ()),5F);
			lv1_3.disableExplosionKnockback();
			lv1_3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION5));
			lv1_3.explode();
		};
		ExplosionComponent.createExplosion(var2,var5);
	}
}