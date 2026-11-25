package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GomuGomuNoElephantGunProjectile extends NuHorizontalLightningEntity {
	// Fields
	private static final double KNOCKBACK = 7D;
	private Vec3 lookVec;

	public GomuGomuNoElephantGunProjectile(EntityType<? extends GomuGomuNoElephantGunProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.lookVec = Vec3.ZERO;
	}
	public GomuGomuNoElephantGunProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.GOMU_GOMU_NO_ELEPHANT_GUN.get())),var2,80F,7F,var3);
		this.lookVec = Vec3.ZERO;
		this.setRetracting();
		this.setSize(0.25F);
		this.setOneTimeHit();
		this.setDepth(1);
		this.setDamage(15F);
		this.setFist();
		this.setAlwaysDamage(false);
		this.setEntityCollisionSize(5D,3D,5D);
		this.lookVec = var2.getLookAngle();
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			LivingEntity var3 = ((LivingEntity)(var2));
			AbilityHelper.setDeltaMovement((Entity)(var3),this.lookVec.normalize().multiply(7D,1D,7D).add(0D,0.15D,0D));
		}
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		GomuGomuNoElephantGunProjectile var3 = this;
		BlockHitResult var4 = var1;
		final BlockHitResult var4_f1 = var4;
		Consumer<ExplosionComponent> var5 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var4_f1.getBlockPos().getX()),(double)(var4_f1.getBlockPos().getY()),(double)(var4_f1.getBlockPos().getZ()),2F);
			lv1_3.setExplosionSound(true);
			lv1_3.setDamageOwner(false);
			lv1_3.setDestroyBlocks(true);
			lv1_3.setFireAfterExplosion(false);
			lv1_3.setSmokeParticles((ParticleEffect)(null));
			lv1_3.setDamageEntities(false);
			lv1_3.explode();
			this.setEarlyLength(this.position());
		};
		ExplosionComponent.createExplosion(var2,var5);
	}
}