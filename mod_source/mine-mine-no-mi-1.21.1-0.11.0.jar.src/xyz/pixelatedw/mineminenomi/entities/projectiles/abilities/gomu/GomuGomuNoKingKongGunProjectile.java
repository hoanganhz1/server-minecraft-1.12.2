package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GomuGomuNoKingKongGunProjectile extends NuHorizontalLightningEntity {
	public GomuGomuNoKingKongGunProjectile(EntityType<? extends GomuGomuNoKingKongGunProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public GomuGomuNoKingKongGunProjectile(Level var1, LivingEntity var2, Ability var3) {
		super(((EntityType)(ModProjectiles.GOMU_GOMU_NO_KING_KONG_GUN.get())),var2,80F,10F,(IAbility)(var3));
		this.setRetracting();
		this.setSize(0.25F);
		this.setOneTimeHit();
		this.setDepth(1);
		this.setDamage(20F);
		this.setPassThroughBlocks();
		this.setPassThroughEntities();
		this.setFist();
		this.setAlwaysDamage(false);
		this.setEntityCollisionSize(5D,3D,5D);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		GomuGomuNoKingKongGunProjectile var3 = this;
		Consumer<ExplosionComponent> var4 = (l0) -> {
			AbilityExplosion lv1_2 = l0.createExplosion((Entity)(this),this.getOwner(),this.getX(),this.getY(),this.getZ(),3F);
			lv1_2.setStaticDamage(12F);
			lv1_2.setExplosionSound(true);
			lv1_2.setDamageOwner(false);
			lv1_2.setDestroyBlocks(true);
			lv1_2.setFireAfterExplosion(false);
			lv1_2.setSmokeParticles((ParticleEffect)(null));
			lv1_2.setDamageEntities(false);
			lv1_2.explode();
		};
		ExplosionComponent.createExplosion(var2,var4);
	}
}