package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class HanaFeetEntity extends NuProjectileEntity {
	// Fields
	private static final TargetPredicate TARGET_PREDICATE = new TargetPredicate().testEnemyFaction();

	public HanaFeetEntity(EntityType<? extends HanaFeetEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public HanaFeetEntity(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.HANA_FEET.get())),var1,var2,var3);
		this.setMaxLife(35);
		this.setDamage(10F);
		this.setPassThroughEntities();
		super.setFist();
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockImpactEvent))));
	}
	public void blockImpactEvent(BlockHitResult var1) {
		if ((this.getOwner()) == (null)){
			return;
		}else{
			List var2 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),this.getOwner(),var1.getBlockPos(),5D,TARGET_PREDICATE,LivingEntity.class);
			var2.remove(this.getOwner());
			Iterator var3 = var2.iterator();
			do{
				if (var3.hasNext()){
					LivingEntity var4 = ((LivingEntity)(var3.next()));
					if (var4.hurt(this.getDamageSource(),this.getDamage())){
						AbilityHelper.setDeltaMovement((Entity)(var4),var4.getDeltaMovement().add(0D,1D,0D));
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			HanaFeetEntity var4_1 = this;
			Predicate<BlockPos> var5 = (l0) -> {
			return DefaultProtectionRules.FOLIAGE.isApproved(this.level().getBlockState(l0));
		};
			Iterator var6 = WyHelper.getNearbyBlocks(this.blockPosition(),(LevelAccessor)(this.level()),5,var5,(List)(ImmutableList.of(Blocks.AIR))).iterator();
			LOOP_1:
			do{
				if (var6.hasNext()){
					BlockPos var7 = ((BlockPos)(var6.next()));
					BlockState var8 = this.level().getBlockState(var7);
					int var9 = 0;
					do{
						if ((var9) < (150)){
							double var10 = WyHelper.randomDouble();
							double var11 = WyHelper.randomDouble();
							double var12 = WyHelper.randomDouble();
							((ServerLevel)(this.level())).sendParticles((ParticleOptions)(new BlockParticleOption(ParticleTypes.BLOCK,var8)),((double)(var7.getX())) + (var10),((double)(var7.getY())) + (var11),((double)(var7.getZ())) + (var12),1,0D,0D,0D,0D);
							var9++;
							continue;
						}else{
							break;
						}
					} while (true);
					NuWorld.setBlockState((Entity)(this.getOwner()),var7,Blocks.AIR.defaultBlockState(),3,DefaultProtectionRules.FOLIAGE);
					continue LOOP_1;
				}else{
					break;
				}
			} while (true);
			BlockState var7_1 = this.level().getBlockState(var1.getBlockPos());
			int var8_1 = 0;
			do{
				if ((var8_1) < (150)){
					double var9_1 = WyHelper.randomDouble();
					double var10 = WyHelper.randomDouble();
					((ServerLevel)(this.level())).sendParticles((ParticleOptions)(new BlockParticleOption(ParticleTypes.BLOCK,var7_1)),((double)((var1.getBlockPos().getX()) + (WyHelper.randomWithRange(-3,3)))) + (var9_1),(double)((var1.getBlockPos().getY()) + (1)),((double)((var1.getBlockPos().getZ()) + (WyHelper.randomWithRange(-3,3)))) + (var10),1,0D,0D,0D,0D);
					var8_1++;
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
}