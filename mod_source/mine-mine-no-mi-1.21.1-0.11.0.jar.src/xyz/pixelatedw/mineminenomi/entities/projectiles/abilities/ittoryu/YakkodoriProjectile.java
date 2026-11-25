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

public class YakkodoriProjectile extends NuProjectileEntity {
	// Fields
	public static final float DAMAGE = 15F;
	public static final float EXPLOSION_RADIUS = 1F;
	public static final float EXPLOSION_DAMAGE = 5F;

	public YakkodoriProjectile(EntityType<? extends YakkodoriProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public YakkodoriProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.YAKKODORI.get())),var1,var2,var3);
		this.setDamage(15F);
		this.setMaxLife(40);
		this.setEntityCollisionSize(0.5D,3.5D,0.5D);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
	}
	private void blockHitEvent(BlockHitResult var1) {
		BlockPos var2 = var1.getBlockPos();
		IAbility var3 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		YakkodoriProjectile var4 = this;
		BlockPos var5 = var2;
		final BlockPos var5_f1 = var5;
		Consumer<ExplosionComponent> var6 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var5_f1.getX()),(double)(var5_f1.getY()),(double)(var5_f1.getZ()),1F);
			lv1_3.setStaticDamage(5F);
			lv1_3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(new CommonExplosionParticleEffect$Details(2D)));
			lv1_3.explode();
		};
		ExplosionComponent.createExplosion(var3,var6);
	}
}