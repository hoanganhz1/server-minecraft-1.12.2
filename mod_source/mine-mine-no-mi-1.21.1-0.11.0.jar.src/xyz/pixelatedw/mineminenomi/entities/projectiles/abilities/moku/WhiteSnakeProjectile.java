package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.moku;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class WhiteSnakeProjectile extends NuProjectileEntity {
	// Fields
	private Optional<LivingEntity> target;

	public WhiteSnakeProjectile(EntityType<? extends WhiteSnakeProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.target = (Optional) (((java.util.Optional)(Optional.empty())));
	}
	public WhiteSnakeProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.WHITE_SNAKE.get())),var1,var2,var3);
		this.target = (Optional) (((java.util.Optional)(Optional.empty())));
		this.setDamage(25F);
		this.setMaxLife(40);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityHit))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onEntityHit(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			((LivingEntity)(var2)).addEffect(new MobEffectInstance(MobEffects.POISON,300,0));
		}
	}
	private void onTickEvent() {
		if ((this.getOwner()) == (null)){
			return;
		}else{
			double var2 = 0.0;
			double var3 = 0.0;
			double var4 = 0.0;
			if ((this.target.isPresent()) && ((((LivingEntity)(this.target.get())).isAlive()) && (!((((LivingEntity)(this.target.get())).position().distanceToSqr(this.position())) > (15D))))){
				Vec3 var1 = this.position().subtract(((LivingEntity)(this.target.get())).position()).add(0D,-1D,0D);
				var2 = 12D;
				var3 = 0.6D;
				var4 = Math.min(var3,(-(var1.x)) / (var2));
				double var5 = Math.min(var3,(-(var1.y)) / (var2));
				AbilityHelper.setDeltaMovement((Entity)(this),var4,var5,Math.min(var3,(-(var1.z)) / (var2)));
			}else{
				List var1_1 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),this.getOwner(),this.position(),5D,TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class);
				var1_1.remove(this.getOwner());
				var1_1.sort(MobsHelper.ENTITY_THREAT);
				if ((var1_1.size()) > (0)){
					this.target = (Optional) (var1_1.stream().findAny());
				}
			}
			if (!(this.level().isClientSide)){
				int var1_2 = 0;
				do{
					if ((var1_2) < (40)){
						var2 = (WyHelper.randomDouble()) / (2D);
						var3 = (WyHelper.randomDouble()) / (2D);
						var4 = (WyHelper.randomDouble()) / (2D);
						SimpleParticleData var5_1 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU2.get())));
						var5_1.setLife(10);
						var5_1.setSize((float)((WyHelper.randomDouble()) * (4D)));
						var5_1.setColor(0.8F,0.8F,0.8F);
						WyHelper.spawnParticles(var5_1,((ServerLevel)(this.level())),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4));
						var1_2++;
						continue;
					}else{
						break;
					}
				} while (true);
			}
			return;
		}
	}
}