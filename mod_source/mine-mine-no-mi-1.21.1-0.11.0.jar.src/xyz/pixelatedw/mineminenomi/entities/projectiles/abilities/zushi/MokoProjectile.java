package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi;

import java.util.function.Consumer;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MokoProjectile extends NuProjectileEntity {
	public MokoProjectile(EntityType<? extends MokoProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public MokoProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.MOKO.get())),var1,var2,var3);
		this.setDamage(10F);
		this.setPassThroughEntities();
		this.setArmorPiercing(1F);
		this.setUnavoidable();
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			LivingEntity var3 = ((LivingEntity)(var2));
			AbilityHelper.setDeltaMovement((Entity)(var3),0D,-5D,0D);
			var3.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,100,10));
		}
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		MokoProjectile var2 = this;
		Consumer<ExplosionComponent> var3 = (l0) -> {
			AbilityExplosion lv1_2 = l0.createExplosion((Entity)(this),this.getOwner(),this.getX(),this.getY(),this.getZ(),3F);
			lv1_2.setStaticDamage(15F);
			lv1_2.setExplosionSound(true);
			lv1_2.setDamageOwner(false);
			lv1_2.setDestroyBlocks(true);
			lv1_2.setFireAfterExplosion(false);
			lv1_2.setExplosionSound(false);
			lv1_2.setSmokeParticles((ParticleEffect)(null));
			lv1_2.setDamageEntities(true);
			lv1_2.explode();
		};
		ExplosionComponent.createExplosion((NuProjectileEntity)(this),var3);
	}
	private void onTickEvent() {
		if (!(this.level().isClientSide)){
			int var1 = 0;
			do{
				if ((var1) < (25)){
					double var2 = (WyHelper.randomDouble()) / (2D);
					double var3 = (WyHelper.randomDouble()) / (2D);
					double var4 = (WyHelper.randomDouble()) / (2D);
					SimpleParticleData var5 = new SimpleParticleData(((ParticleType)(ModParticleTypes.GASU.get())));
					var5.setLife(10);
					var5.setSize(1F);
					WyHelper.spawnParticles(var5,((ServerLevel)(this.level())),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4));
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}