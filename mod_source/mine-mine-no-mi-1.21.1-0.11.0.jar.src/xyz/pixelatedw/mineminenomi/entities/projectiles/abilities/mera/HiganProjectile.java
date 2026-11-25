package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class HiganProjectile extends NuProjectileEntity {
	public HiganProjectile(EntityType<? extends HiganProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public HiganProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.HIGAN.get())),var1,var2,var3);
		this.setDamage(10F);
		this.setArmorPiercing(0.75F);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::entityHitEvent))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void blockHitEvent(BlockHitResult var1) {
		BlockState var2 = super.level().getBlockState(var1.getBlockPos());
		if ((var2.getBlock()) == (Blocks.CAMPFIRE)){
			super.level().setBlock(var1.getBlockPos(),((BlockState)(var2.setValue((Property)(BlockStateProperties.LIT),(Comparable)(Boolean.valueOf(true))))),11);
		}else{
			NuWorld.setBlockState((Entity)(this.getOwner()),var1.getBlockPos().offset(0,1,0),Blocks.FIRE.defaultBlockState(),2,DefaultProtectionRules.AIR);
		}
	}
	private void entityHitEvent(EntityHitResult var1) {
		if (((var1.getEntity()) != (null)) && (var1.getEntity().isAlive())){
			Entity var2 = var1.getEntity();
			if (var2 instanceof LivingEntity){
				((LivingEntity)(var2)).igniteForSeconds(4F);
			}else{
				return;
			}
		}
	}
	private void tickEvent() {
		if (super.isEyeInFluidType(((FluidType)(NeoForgeMod.WATER_TYPE.value())))){
			super.remove(Entity.RemovalReason.KILLED);
			super.getCommandSenderWorld().addParticle((ParticleOptions)(ParticleTypes.SMOKE),super.getX(),(super.getY()) + (1.1D),super.getZ(),0D,0D,0D);
		}
		if (!(super.level().isClientSide)){
			int var1 = 0;
			do{
				if ((var1) < (2)){
					double var2 = (WyHelper.randomDouble()) / (5D);
					double var3 = (WyHelper.randomDouble()) / (5D);
					double var4 = (WyHelper.randomDouble()) / (5D);
					SimpleParticleData var5 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
					var5.setLife(10);
					var5.setSize(1.3F);
					WyHelper.spawnParticles(var5,((ServerLevel)(super.level())),(super.getX()) + (var2),((super.getY()) + (0.25D)) + (var3),(super.getZ()) + (var4));
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}