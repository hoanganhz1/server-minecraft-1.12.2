package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class JujikaProjectile extends NuProjectileEntity {
	public JujikaProjectile(EntityType<? extends JujikaProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public JujikaProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.JUJIKA.get())),var1,var2,var3);
		this.setDamage(25F);
		this.setArmorPiercing(0.75F);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void tickEvent() {
		if (super.isEyeInFluidType(((FluidType)(NeoForgeMod.WATER_TYPE.value())))){
			super.remove(Entity.RemovalReason.KILLED);
			super.getCommandSenderWorld().addParticle((ParticleOptions)(ParticleTypes.SMOKE),super.getX(),(super.getY()) + (1.1D),super.getZ(),0D,0D,0D);
		}
		if ((!(super.level().isClientSide)) && ((this.tickCount) > (0))){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.JUJIKA.get())),(Entity)(this),super.getX(),super.getY(),super.getZ());
		}
	}
	private void blockHitEvent(BlockHitResult var1) {
		BlockPos var2 = var1.getBlockPos();
		int var3 = -2;
		LOOP_1:
		do{
			if ((var3) <= (2)){
				int var4 = -5;
				do{
					if ((var4) <= (5)){
						if (super.level().isEmptyBlock(new BlockPos((var2.getX()) + (var4),(var2.getY()) + (var3),var2.getZ()))){
							NuWorld.setBlockState((Entity)(this.getOwner()),new BlockPos((var2.getX()) + (var4),(var2.getY()) + (var3),(var2.getZ()) - (var4)),Blocks.FIRE.defaultBlockState(),2,(BlockProtectionRule)(null));
						}
						if (super.level().isEmptyBlock(new BlockPos(var2.getX(),(var2.getY()) + (var3),(var2.getZ()) + (var4)))){
							NuWorld.setBlockState((Entity)(this.getOwner()),new BlockPos(var2.getX(),(var2.getY()) + (var3),(var2.getZ()) + (var4)),Blocks.FIRE.defaultBlockState(),2,(BlockProtectionRule)(null));
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