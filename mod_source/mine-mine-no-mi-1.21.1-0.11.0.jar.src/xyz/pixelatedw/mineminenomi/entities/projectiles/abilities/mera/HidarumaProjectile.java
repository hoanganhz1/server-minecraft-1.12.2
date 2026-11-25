package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class HidarumaProjectile extends NuProjectileEntity {
	// Fields
	private Optional<LivingEntity> target;

	public HidarumaProjectile(EntityType<? extends HidarumaProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public HidarumaProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.HIDARUMA.get())),var1,var2,var3);
		this.setDamage(3.5F);
		this.setArmorPiercing(0.75F);
		this.setGravity(0F);
		this.setMaxLife(120);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::entityHitEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void entityHitEvent(EntityHitResult var1) {
		if (((var1.getEntity()) != (null)) && (var1.getEntity().isAlive())){
			AbilityHelper.setSecondsOnFireBy(var1.getEntity(),3,this.getOwner());
		}
	}
	private void tickEvent() {
		double var2 = 0.0;
		double var3 = 0.0;
		double var4 = 0.0;
		if (((this.target) != (null)) && ((this.target.isPresent()) && (((LivingEntity)(this.target.get())).isAlive()))){
			Vec3 var1 = super.position().subtract(((LivingEntity)(this.target.get())).position()).add(0D,-1D,0D);
			var2 = 12D;
			var3 = 0.5D;
			var4 = Math.min(var3,(-(var1.x)) / (var2));
			double var5 = Math.min(var3,(-(var1.y)) / (var2));
			AbilityHelper.setDeltaMovement((Entity)(this),var4,var5,Math.min(var3,(-(var1.z)) / (var2)));
		}else{
			if ((this.getOwner()) != (null)){
				List var1_1 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),this.getOwner(),this.position(),16D,TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class);
				var1_1.remove(this.getOwner());
				var1_1.sort(MobsHelper.ENTITY_THREAT);
				if ((var1_1.size()) > (0)){
					this.target = (Optional) (var1_1.stream().findAny());
				}
			}
		}
		if (super.isEyeInFluidType(((FluidType)(NeoForgeMod.WATER_TYPE.value())))){
			super.remove(Entity.RemovalReason.KILLED);
			super.getCommandSenderWorld().addParticle((ParticleOptions)(ParticleTypes.SMOKE),super.getX(),(super.getY()) + (1.1D),super.getZ(),0D,0D,0D);
		}
		if (!(super.level().isClientSide)){
			int var1_2 = 0;
			do{
				if ((var1_2) < (1)){
					var2 = (WyHelper.randomDouble()) / (2D);
					var3 = (WyHelper.randomDouble()) / (2D);
					var4 = (WyHelper.randomDouble()) / (2D);
					WyHelper.spawnParticles((ParticleOptions)(ParticleTypes.HAPPY_VILLAGER),((ServerLevel)(super.level())),(super.getX()) + (var2),(super.getY()) + (var3),(super.getZ()) + (var4));
					var1_2++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}