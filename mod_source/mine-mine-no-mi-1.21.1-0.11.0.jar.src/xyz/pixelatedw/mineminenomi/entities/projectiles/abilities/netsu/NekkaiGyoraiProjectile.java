package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.netsu;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.abilities.netsu.NetsuEnhancementAbility;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class NekkaiGyoraiProjectile extends NuProjectileEntity {
	// Fields
	private int damage;

	public NekkaiGyoraiProjectile(EntityType<? extends NekkaiGyoraiProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.damage = 10;
	}
	public NekkaiGyoraiProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.NEKKAI_GYORAI.get())),var1,var2,var3);
		this.damage = 10;
		NetsuEnhancementAbility var4 = ((NetsuEnhancementAbility)(((AbilityData)(((java.util.Optional)(AbilityData.get(var2))).orElse((Object)(null)))).getEquippedAbility(((AbilityCore)(NetsuEnhancementAbility.INSTANCE.get())))));
		if (((var4) != (null)) && (var4.isContinuous())){
			this.damage = (this.damage) + (5);
		}
		this.setDamage((float)(this.damage));
		this.setMaxLife(30);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::entityHitEvent))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void entityHitEvent(EntityHitResult var1) {
		var1.getEntity().igniteForSeconds(4F);
	}
	private void blockHitEvent(BlockHitResult var1) {
		BlockPos var2 = var1.getBlockPos();
		NuWorld.setBlockState((Entity)(this.getOwner()),var2.offset(0,1,0),Blocks.FIRE.defaultBlockState(),2,DefaultProtectionRules.AIR);
	}
	private void tickEvent() {
		if (!(this.level().isClientSide)){
			int var1 = 0;
			do{
				if ((var1) < (20)){
					double var2 = (WyHelper.randomDouble()) / (2D);
					double var3 = (WyHelper.randomDouble()) / (2D);
					double var4 = (WyHelper.randomDouble()) / (2D);
					ParticleType var5 = ((ParticleType)(ModParticleTypes.NETSU.get()));
					if (((var1) % (3)) == (0)){
						var5 = ((ParticleType)(ModParticleTypes.NETSU2.get()));
					}
					if (((var1) % (7)) == (0)){
						var5 = ((ParticleType)(ModParticleTypes.MERA.get()));
					}
					SimpleParticleData var6 = new SimpleParticleData(var5);
					var6.setLife(10);
					var6.setSize(1.3F);
					WyHelper.spawnParticles(var6,((ServerLevel)(this.level())),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4));
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}