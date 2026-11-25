package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.beta;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BetaBetaChainProjectile extends NuHorizontalLightningEntity {
	public BetaBetaChainProjectile(EntityType<? extends BetaBetaChainProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public BetaBetaChainProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.BETA_BETA_CHAIN.get())),var2,80F,6F,var3);
		this.setFadeTime(20);
		this.setSize(0.07F);
		this.setOneTimeHit();
		this.setDamage(4F);
		this.setDepth(1);
		this.setPhysical();
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
	}
	private void blockHitEvent(BlockHitResult var1) {
		LivingEntity var2 = this.getOwner();
		if (var2 instanceof LivingEntity){
			LivingEntity var3 = ((LivingEntity)(var2));
			BlockPos var4 = var1.getBlockPos().subtract((Vec3i)(var3.blockPosition()));
			AbilityHelper.setDeltaMovement((Entity)(var3),((double)(var4.getX())) * (0.15D),(0.3D) + ((double)((var4.getY()) / (5))),((double)(var4.getZ())) * (0.15D));
		}
	}
}