package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class GomuGomuNoRocketProjectile extends NuHorizontalLightningEntity {
	public GomuGomuNoRocketProjectile(EntityType<? extends GomuGomuNoRocketProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public GomuGomuNoRocketProjectile(Level var1, LivingEntity var2, Ability var3) {
		super(((EntityType)(ModProjectiles.GOMU_GOMU_NO_ROCKET.get())),var2,100F,8F,(IAbility)(var3));
		this.setRetracting();
		this.offsetToHand(var2);
		this.setFollowOwner();
		this.setSize(0.025F);
		this.setOneTimeHit();
		this.setDepth(1);
		this.setPassThroughEntities(false);
		this.setDamage(4F);
		this.setFist();
		this.setAlwaysDamage(false);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		LivingEntity var2 = this.getOwner();
		if ((var2) != (null)){
			var2.removeEffect((Holder)(ModEffects.REDUCED_FALL));
			BlockPos var3 = var1.getBlockPos().subtract((Vec3i)(var2.blockPosition()));
			AbilityHelper.setDeltaMovement((Entity)(var2),((double)(var3.getX())) * (0.35D),(0.3D) + (((double)(var3.getY())) * (0.35D)),((double)(var3.getZ())) * (0.35D));
		}
	}
}