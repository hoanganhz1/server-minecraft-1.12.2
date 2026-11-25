package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mero;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class PistolKissProjectile extends NuProjectileEntity {
	public PistolKissProjectile(EntityType<? extends PistolKissProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public PistolKissProjectile(Level var1, LivingEntity var2, Ability var3) {
		super(((EntityType)(ModProjectiles.PISTOL_KISS.get())),var1,var2,(IAbility)(var3));
		this.setDamage(4F);
		this.setMaxLife(30);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityHitEvent))));
	}
	private void onEntityHitEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			((LivingEntity)(var2)).addEffect(new MobEffectInstance((Holder)(ModEffects.LOVESTRUCK),20,0));
		}
	}
}