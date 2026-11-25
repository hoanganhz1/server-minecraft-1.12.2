package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.noro;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;

public class NoroNoroBeamProjectile extends NuProjectileEntity {
	public NoroNoroBeamProjectile(EntityType<? extends NoroNoroBeamProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public NoroNoroBeamProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.NORO_NORO_BEAM.get())),var1,var2,var3);
		this.setMaxLife(10);
		this.setEntityCollisionSize(1.25D);
		this.setPassThroughEntities();
		this.setUnavoidable();
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			LivingEntity var3 = ((LivingEntity)(var2));
			MobEffectInstance var4 = var3.getEffect((Holder)(ModEffects.NORO_SLOWNESS));
			if ((var4) == (null)){
				var3.addEffect(new MobEffectInstance((Holder)(ModEffects.NORO_SLOWNESS),240,0));
			}else{
				((IMobEffectInstanceMixin)(var4)).setDuration((var4.getDuration()) + (240));
				WyHelper.sendApplyEffectToAllNearby(var3,var3.position(),100,var4);
			}
		}
	}
}