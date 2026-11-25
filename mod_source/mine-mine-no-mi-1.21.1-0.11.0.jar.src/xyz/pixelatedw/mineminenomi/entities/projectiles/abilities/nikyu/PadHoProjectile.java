package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu;

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
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class PadHoProjectile extends NuProjectileEntity {
	public PadHoProjectile(EntityType<? extends PadHoProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public PadHoProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.PAD_HO.get())),var1,var2,var3);
		this.setDamage(15F);
		this.setArmorPiercing(1F);
		this.setPassThroughEntities();
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		if ((this.getDamage()) > (10F)){
			Vec3 var2 = var1.getEntity().getLookAngle().multiply(-1D,-1D,-1D).multiply((double)(WyHelper.randomWithRange(4,6)),(double)(WyHelper.randomWithRange(1,3)),(double)(WyHelper.randomWithRange(4,6)));
			AbilityHelper.setDeltaMovement(var1.getEntity(),var2.x,var2.y,var2.z);
			var1.getEntity().fallDistance = 0F;
		}
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		PadHoProjectile var3 = this;
		BlockHitResult var4 = var1;
		final BlockHitResult var4_f1 = var4;
		Consumer<ExplosionComponent> var5 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var4_f1.getBlockPos().getX()),(double)(var4_f1.getBlockPos().getY()),(double)(var4_f1.getBlockPos().getZ()),(this.getDamage()) / (5F));
			lv1_3.setStaticDamage((this.getDamage()) / (3F));
			lv1_3.setExplosionSound(true);
			lv1_3.setDamageOwner(false);
			lv1_3.setDestroyBlocks(true);
			lv1_3.setFireAfterExplosion(false);
			lv1_3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION2));
			lv1_3.setDamageEntities(false);
			lv1_3.explode();
		};
		ExplosionComponent.createExplosion(var2,var5);
	}
}