package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hitodaibutsu;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ImpactBlastProjectile extends NuProjectileEntity {
	public ImpactBlastProjectile(EntityType<? extends ImpactBlastProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public ImpactBlastProjectile(Level var1, LivingEntity var2, Ability var3) {
		super(((EntityType)(ModProjectiles.IMPACT_BLAST.get())),var1,var2,(IAbility)(var3));
		this.setDamage(60F);
		this.setMaxLife(20);
		this.setEntityCollisionSize(5D);
		this.setPassThroughBlocks();
		this.setPassThroughEntities();
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onTickEvent() {
		if ((this.tickCount) > (0)){
			if (((this.tickCount) % (2)) == (0)){
				IAbility var1 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
				ImpactBlastProjectile var2 = this;
				Consumer<ExplosionComponent> var3 = (l0) -> {
			AbilityExplosion lv1_2 = l0.createExplosion((Entity)(this),this.getOwner(),this.getX(),this.getY(),this.getZ(),5F);
			lv1_2.setStaticDamage((40F) * ((float)((this.getLife()) / (this.getMaxLife()))));
			lv1_2.explode();
		};
				ExplosionComponent.createExplosion(var1,var3);
			}
			if (((this.tickCount) % (5)) == (0)){
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.IMPACT_WAVE.get())),(Entity)(this),this.getX(),this.getY(),this.getZ());
			}else{
				return;
			}
		}
	}
}