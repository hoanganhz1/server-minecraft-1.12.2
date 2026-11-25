package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gasu;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.abilities.gasu.ShinokuniAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GasRobeProjectile extends NuProjectileEntity {
	// Fields
	private boolean hasShinokuniEnabled;
	private ShinokuniAbility shinokuniAbility;

	public GasRobeProjectile(EntityType<? extends GasRobeProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.hasShinokuniEnabled = false;
	}
	public GasRobeProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.GAS_ROBE.get())),var1,var2,var3);
		this.hasShinokuniEnabled = false;
		this.setDamage(0.1F);
		this.setMaxLife(30);
		this.setPassThroughEntities();
		this.setArmorPiercing(0.5F);
		Function<AbilityData, ShinokuniAbility> var4 = (l0) -> {
			return ((ShinokuniAbility)(l0.getEquippedAbility(((AbilityCore)(ShinokuniAbility.INSTANCE.get())))));
		};
		this.shinokuniAbility = ((ShinokuniAbility)(((java.util.Optional)(AbilityData.get(var2))).map((Function)(var4)).orElse(null)));
		this.hasShinokuniEnabled = ((this.shinokuniAbility) != (null)) && (this.shinokuniAbility.isContinuous());
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::entityImpactEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void entityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			LivingEntity var3 = ((LivingEntity)(var2));
			if (this.hasShinokuniEnabled){
				this.shinokuniAbility.applyEffects(this.getOwner(),var3);
			}else{
				var3.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,200,2));
				var3.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,40,0));
				var3.addEffect(new MobEffectInstance(MobEffects.POISON,200,6));
			}
		}
	}
	private void tickEvent() {
		if (!(this.level().isClientSide)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.GAS_ROBE.get())),(Entity)(this),this.getX(),this.getY(),this.getZ());
		}
	}
}