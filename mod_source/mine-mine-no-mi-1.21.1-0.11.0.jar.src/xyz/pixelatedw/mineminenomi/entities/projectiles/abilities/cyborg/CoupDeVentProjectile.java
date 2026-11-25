package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class CoupDeVentProjectile extends NuProjectileEntity {
	public CoupDeVentProjectile(EntityType<? extends CoupDeVentProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public CoupDeVentProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.COUP_DE_VENT.get())),var1,var2,var3);
		this.setDamage(50F);
		this.setPassThroughEntities();
		this.setMaxLife(15);
		this.setEntityCollisionSize(5.5D);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			LivingEntity var3 = ((LivingEntity)(var2));
			double var4 = (WyHelper.randomDouble()) * (400D);
			double var5 = 0.0;
			if ((var4) >= (0D)){
				var5 = Mth.clamp(var4,200D,400D);
			}else{
				var5 = Mth.clamp(var4,-400D,-200D);
			}
			double var6 = (WyHelper.randomDouble()) * (400D);
			double var7 = 0.0;
			if ((var6) >= (0D)){
				var7 = Mth.clamp(var6,200D,400D);
			}else{
				var7 = Mth.clamp(var6,-400D,-200D);
			}
			var3.addEffect(new MobEffectInstance((Holder)(ModEffects.LAUNCHED),1200,0));
			AbilityHelper.setDeltaMovement((Entity)(var3),var5,10D,var7);
			var3.fallDistance = 0F;
			return;
		}else{
			return;
		}
	}
	private void onTickEvent() {
		int var1 = 0;
		do{
			if ((var1) < (25)){
				double var2 = (WyHelper.randomDouble()) * (1.2D);
				double var3 = (WyHelper.randomDouble()) * (1.2D);
				double var4 = (WyHelper.randomDouble()) * (1.2D);
				((ServerLevel)(this.level())).sendParticles((ParticleOptions)(ParticleTypes.END_ROD),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4),1,0D,0D,0D,-0.1D);
				if (((var1) % (5)) == (0)){
					((ServerLevel)(this.level())).sendParticles((ParticleOptions)(ParticleTypes.FLASH),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4),1,0D,0D,0D,-0.1D);
				}
				var1++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}