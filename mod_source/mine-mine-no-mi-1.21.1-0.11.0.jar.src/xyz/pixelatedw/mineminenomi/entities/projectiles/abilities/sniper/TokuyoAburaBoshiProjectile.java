package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class TokuyoAburaBoshiProjectile extends NuProjectileEntity {
	// Fields
	public static final float DAMAGE = 4F;
	public static final int EFFECT_TIME = 300;

	public TokuyoAburaBoshiProjectile(EntityType<? extends TokuyoAburaBoshiProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public TokuyoAburaBoshiProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.TOKUYO_ABURA_BOSHI.get())),var1,var2,var3);
		this.setDamage(4F);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			((LivingEntity)(var2)).addEffect(new MobEffectInstance((Holder)(ModEffects.OIL_COVERED),300,0));
		}
		this.defaultOnHitBlock(var1);
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		BlockPos.MutableBlockPos var2 = new BlockPos.MutableBlockPos();
		int var3 = -3;
		LOOP_1:
		do{
			if ((var3) < (3)){
				int var4 = 0;
				do{
					if ((var4) < (20)){
						double var5 = (double)(WyHelper.randomWithRange(-3,3));
						double var6 = (double)(WyHelper.randomWithRange(-3,3));
						var2.set(((double)(var1.getBlockPos().getX())) + (var5),(double)((var1.getBlockPos().getY()) + (var3)),((double)(var1.getBlockPos().getZ())) + (var6));
						BlockPos var7 = var2.below();
						if ((this.level().getBlockState(var7).getBlock()) != (ModBlocks.OIL_SPILL.get())){
							NuWorld.setBlockState((Entity)(this.getOwner()),var2.immutable(),((Block)(ModBlocks.OIL_SPILL.get())).defaultBlockState(),3,DefaultProtectionRules.AIR_FOLIAGE);
						}
						var4++;
						continue;
					}else{
						break;
					}
				} while (true);
				var3++;
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
	}
}