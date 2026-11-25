package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yami;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class LiberationProjectile extends NuProjectileEntity {
	// Fields
	private BlockState block;

	public LiberationProjectile(EntityType<? extends LiberationProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public LiberationProjectile(Level var1, LivingEntity var2, BlockState var3, IAbility var4) {
		super(((EntityType)(ModProjectiles.LIBERATION.get())),var1,var2,var4);
		this.block = var3;
		this.setDamage(25F);
		this.setEntityCollisionSize(2D);
		this.setBlockCollisionSize(0.5D);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		NuWorld.setBlockState(this.level(),var1.getBlockPos().offset(0,1,0),this.block,3,DefaultProtectionRules.AIR);
	}
}