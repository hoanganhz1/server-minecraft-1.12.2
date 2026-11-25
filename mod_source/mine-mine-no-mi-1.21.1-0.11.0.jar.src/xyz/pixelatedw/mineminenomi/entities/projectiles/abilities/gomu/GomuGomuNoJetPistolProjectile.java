package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GomuGomuNoJetPistolProjectile extends NuHorizontalLightningEntity {
	public GomuGomuNoJetPistolProjectile(EntityType<? extends GomuGomuNoJetPistolProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public GomuGomuNoJetPistolProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.GOMU_GOMU_NO_JET_PISTOL.get())),var2,80F,16F,var3);
		this.setRetracting();
		this.setSize(0.025F);
		this.setOneTimeHit();
		this.setDepth(1);
		this.setPassThroughEntities(false);
		this.setDamage(10F);
		this.setFist();
		this.setAlwaysDamage(false);
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onTickEvent() {
		if (((this.tickCount) % (2)) == (0)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.GEAR_SECOND.get())),(Entity)(this),this.getX(),this.getY(),this.getZ());
		}
	}
}