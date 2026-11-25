package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.blackleg;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class PoeleAFrireProjectile extends NuProjectileEntity {
	// Fields
	public static final float DAMAGE = 10F;

	public PoeleAFrireProjectile(EntityType<? extends PoeleAFrireProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public PoeleAFrireProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.POELE_A_FRIRE.get())),var1,var2,(IAbility)(null),SourceElement.FIRE,SourceHakiNature.HARDENING,new SourceType[]{SourceType.FIST});
		super.setDamage(10F);
		super.setMaxLife(10);
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
	}
	private void onTickEvent() {
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.EXTRA_HACHI_DIABLE.get())),(Entity)(this),super.getX(),super.getY(),super.getZ());
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		var1.getEntity().igniteForSeconds(2F);
	}
}