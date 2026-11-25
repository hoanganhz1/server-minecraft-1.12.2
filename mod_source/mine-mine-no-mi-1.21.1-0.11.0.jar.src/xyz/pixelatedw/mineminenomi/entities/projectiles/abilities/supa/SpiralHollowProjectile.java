package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.supa;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
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

public class SpiralHollowProjectile extends NuProjectileEntity {
	public SpiralHollowProjectile(EntityType<? extends SpiralHollowProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public SpiralHollowProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.SPIRAL_HOLLOW.get())),var1,var2,var3);
		this.setDamage(30F);
		this.setMaxLife(10);
		this.setPassThroughEntities();
		this.setEntityCollisionSize(2.5D,1D,2.5D);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			((LivingEntity)(var2)).addEffect(new MobEffectInstance((Holder)(ModEffects.BLEEDING),100,0));
		}
	}
}