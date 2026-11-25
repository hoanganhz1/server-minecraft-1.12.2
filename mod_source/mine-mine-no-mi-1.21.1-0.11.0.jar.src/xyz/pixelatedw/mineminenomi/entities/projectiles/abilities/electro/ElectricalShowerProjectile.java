package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElectricalShowerProjectile extends NuProjectileEntity {
	public ElectricalShowerProjectile(EntityType<? extends ElectricalShowerProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public ElectricalShowerProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.ELECTRICAL_SHOWER.get())),var1,var2,var3);
		this.setMaxLife(40);
		this.setPassThroughEntities();
		this.setEntityCollisionSize(1.5D,1.5D,1.5D);
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onTickEvent() {
		if ((this.tickCount) < (0)){
			return;
		}else{
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.ELECTRICAL_LUNA.get())),(Entity)(this),this.getX(),this.getY(),this.getZ());
			return;
		}
	}
}