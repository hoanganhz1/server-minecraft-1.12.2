package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ittoryu;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class SanbyakurokujuPoundHoProjectile extends NuProjectileEntity {
	// Fields
	public static final float DAMAGE = 25F;
	public static final float EXPLOSION_RADIUS = 2F;
	public static final float EXPLOSION_DAMAGE = 10F;

	public SanbyakurokujuPoundHoProjectile(EntityType<? extends SanbyakurokujuPoundHoProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public SanbyakurokujuPoundHoProjectile(Level var1, LivingEntity var2, Ability var3) {
		super(((EntityType)(ModProjectiles.SANBYAKUROKUJU_POUND_HO.get())),var1,var2,(IAbility)(var3));
		this.setDamage(25F);
		this.setMaxLife(40);
		this.setPassThroughEntities();
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
	}
	private void blockHitEvent(BlockHitResult var1) {
		BlockPos var2 = var1.getBlockPos();
		IAbility var3 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		SanbyakurokujuPoundHoProjectile var4 = this;
		BlockPos var5 = var2;
		final BlockPos var5_f1 = var5;
		Consumer<ExplosionComponent> var6 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var5_f1.getX()),(double)(var5_f1.getY()),(double)(var5_f1.getZ()),2F);
			lv1_3.setStaticDamage(10F);
			lv1_3.setExplosionSound(true);
			lv1_3.setDamageOwner(false);
			lv1_3.setDestroyBlocks(true);
			lv1_3.setFireAfterExplosion(false);
			lv1_3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(new CommonExplosionParticleEffect$Details(2D)));
			lv1_3.setDamageEntities(true);
			lv1_3.explode();
		};
		ExplosionComponent.createExplosion(var3,var6);
	}
}