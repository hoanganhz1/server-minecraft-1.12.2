package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.horo;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class NegativeHollowProjectile extends NuProjectileEntity {
	public NegativeHollowProjectile(EntityType<? extends NegativeHollowProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public NegativeHollowProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.NEGATIVE_HOLLOW.get())),var1,var2,var3);
		this.setDamage(10F);
		this.setPassThroughEntities();
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			LivingEntity var3 = ((LivingEntity)(var2));
			var3.addEffect(new MobEffectInstance(MobEffects.CONFUSION,200,0));
			var3.addEffect(new MobEffectInstance((Holder)(ModEffects.NEGATIVE),40,0));
		}
	}
}