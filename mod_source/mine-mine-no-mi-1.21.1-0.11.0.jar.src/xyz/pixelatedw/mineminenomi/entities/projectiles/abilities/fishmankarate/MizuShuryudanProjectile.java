package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
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
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MizuShuryudanProjectile extends NuProjectileEntity {
	// Fields
	private Optional<LivingEntity> target;

	public MizuShuryudanProjectile(EntityType<? extends MizuShuryudanProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public MizuShuryudanProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.MIZU_SHURYUDAN.get())),var1,var2,var3);
		this.setDamage(7F);
		this.setMaxLife(400);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::entityHitEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void entityHitEvent(EntityHitResult var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		MizuShuryudanProjectile var3 = this;
		Consumer<ExplosionComponent> var4 = (l0) -> {
			AbilityExplosion lv1_2 = l0.createExplosion((Entity)(this),this.getOwner(),this.getX(),this.getY(),this.getZ(),3F);
			lv1_2.setExplosionSound(false);
			lv1_2.setDamageOwner(false);
			lv1_2.setDestroyBlocks(false);
			lv1_2.setFireAfterExplosion(false);
			lv1_2.setSmokeParticles(((ParticleEffect)(ModParticleEffects.WATER_EXPLOSION.get())));
			lv1_2.setDamageEntities(false);
			lv1_2.explode();
		};
		ExplosionComponent.createExplosion(var2,var4);
	}
	public void setTarget(Optional<LivingEntity> var1) {
		this.target = var1;
	}
	private void tickEvent() {
		if (((this.target) != (null)) && ((this.target.isPresent()) && (ModEntityPredicates.IS_ALIVE_AND_SURVIVAL.test(((Entity)(this.target.get())))))){
			Vec3 var1 = this.position().subtract(((LivingEntity)(this.target.get())).position().add(0D,1D,0D)).normalize().multiply(0.4D,1D,0.4D);
			AbilityHelper.setDeltaMovement((Entity)(this),-(var1.x),-(var1.y),-(var1.z));
		}else{
			if ((this.getOwner()) != (null)){
				List var1_1 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),this.getOwner(),this.blockPosition(),8D,(TargetPredicate)(null),LivingEntity.class);
				var1_1.remove(this.getOwner());
				var1_1.sort(MobsHelper.ENTITY_THREAT);
				if ((var1_1.size()) > (0)){
					this.target = (Optional) (var1_1.stream().findAny());
				}
			}
			if (((this.tickCount) % (5)) == (0)){
				AbilityHelper.setDeltaMovement((Entity)(this),this.getDeltaMovement().scale(0.7D));
			}
		}
		if (!(this.level().isClientSide)){
			int var1_2 = 0;
			do{
				if ((var1_2) < (1)){
					double var2 = (WyHelper.randomDouble()) / (2D);
					double var3 = (WyHelper.randomDouble()) / (2D);
					double var4 = (WyHelper.randomDouble()) / (2D);
					WyHelper.spawnParticles((ParticleOptions)(ParticleTypes.FISHING),((ServerLevel)(this.level())),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4));
					var1_2++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}