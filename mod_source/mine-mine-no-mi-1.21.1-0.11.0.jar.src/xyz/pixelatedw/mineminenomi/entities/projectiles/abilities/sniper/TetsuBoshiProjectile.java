package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.entities.SpikeEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class TetsuBoshiProjectile extends NuProjectileEntity {
	// Fields
	public static final float DAMAGE = 4F;

	public TetsuBoshiProjectile(EntityType<? extends TetsuBoshiProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public TetsuBoshiProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.TETSU_BOSHI.get())),var1,var2,var3);
		this.setDamage(4F);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		int var2 = 0;
		do{
			if ((var2) < (10)){
				double var3 = ((double)(WyHelper.randomWithRange(-2,2))) + (WyHelper.randomDouble());
				double var4 = ((double)(WyHelper.randomWithRange(-2,2))) + (WyHelper.randomDouble());
				SpikeEntity var5 = new SpikeEntity(this.level());
				var5.moveTo(((double)(var1.getBlockPos().getX())) + (var3),(double)((var1.getBlockPos().getY()) + (1)),((double)(var1.getBlockPos().getZ())) + (var4),180F,0F);
				this.level().addFreshEntity((Entity)(var5));
				var2++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}