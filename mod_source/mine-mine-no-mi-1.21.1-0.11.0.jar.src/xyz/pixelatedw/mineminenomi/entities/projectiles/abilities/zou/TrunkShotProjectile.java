package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zou;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class TrunkShotProjectile extends NuHorizontalLightningEntity {
	public TrunkShotProjectile(EntityType<? extends TrunkShotProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public TrunkShotProjectile(Level var1, LivingEntity var2, Ability var3) {
		super(((EntityType)(ModProjectiles.TRUNK_SHOT.get())),var2,10F,2F,(IAbility)(var3));
		this.setDamage(24F);
		this.setFadeTime(20);
		this.setSize(0.05F);
		this.setOneTimeHit();
		this.setFollowOwner();
		this.setPassThroughEntities();
		this.setRetracting();
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			((LivingEntity)(var2)).addEffect(new MobEffectInstance(MobEffects.WEAKNESS,100,1));
		}
	}
}