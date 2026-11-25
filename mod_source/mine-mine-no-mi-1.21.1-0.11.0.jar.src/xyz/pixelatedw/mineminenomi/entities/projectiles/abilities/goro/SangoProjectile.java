package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class SangoProjectile extends NuHorizontalLightningEntity {
	// Fields
	private float multiplier;

	public SangoProjectile(EntityType<? extends SangoProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.multiplier = 1F;
	}
	public SangoProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.SANGO.get())),var2,80F,40F,var3);
		this.multiplier = 1F;
		float var4 = 80F;
		float var5 = 20F;
		if (((MorphInfo)(ModMorphs.VOLT_AMARU.get())).isActive(var2)){
			this.multiplier = (this.multiplier) + (0.25F);
			var4 = 128F;
			var5 = 30F;
		}
		float var6 = (0.28F) * (this.multiplier);
		super.offsetToHand(var2);
		this.setFollowOwner();
		this.setFadeTime(40);
		this.setMaxLife(40);
		this.setMaxTravelDistance(var4);
		this.setDamage((var5) * (this.multiplier));
		this.setSize((var6) * (this.multiplier));
		this.setAngle(50);
		this.setTargetTimeToReset(20);
		this.setBranches((int)((5D) + (((double)(var4)) / (100D))));
		int var7 = (int)(((double)(var4)) * (0.5D));
		this.setSegments((var7) + (WyHelper.randomWithRange((-(var7)) / (4),(var7) / (4))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
	}
	private void blockHitEvent(BlockHitResult var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		SangoProjectile var3 = this;
		BlockHitResult var4 = var1;
		final BlockHitResult var4_f1 = var4;
		Consumer<ExplosionComponent> var5 = (l0) -> {
			float lv1_3 = (float)((int)((5F) * (this.multiplier)));
			AbilityExplosion lv1_4 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var4_f1.getBlockPos().getX()),(double)(var4_f1.getBlockPos().getY()),(double)(var4_f1.getBlockPos().getZ()),lv1_3);
			lv1_4.disableExplosionKnockback();
			lv1_4.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(new CommonExplosionParticleEffect$Details((double)(lv1_3))));
			lv1_4.explode();
		};
		ExplosionComponent.createExplosion(var2,var5);
	}
}