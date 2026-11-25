package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.beta;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
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
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class StickyProjectile extends NuProjectileEntity {
	// Fields
	private boolean causeExplosion;

	public StickyProjectile(EntityType<? extends StickyProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.causeExplosion = false;
	}
	public StickyProjectile(Level var1, LivingEntity var2, Ability var3) {
		super(((EntityType)(ModProjectiles.STICKY_PROJECTILE.get())),var1,var2,(IAbility)(var3));
		this.causeExplosion = false;
		this.setDamage(2F);
		this.setMaxLife(20);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::entityHitEvent))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void tickEvent() {
		if (!(this.level().isClientSide)){
			double var2 = 0.0;
			double var3 = 0.0;
			double var4 = 0.0;
			int var1 = 0;
			do{
				if ((var1) < (4)){
					var2 = (WyHelper.randomDouble()) / (5D);
					var3 = (WyHelper.randomDouble()) / (5D);
					var4 = (WyHelper.randomDouble()) / (5D);
					SimpleParticleData var5 = new SimpleParticleData(((ParticleType)(ModParticleTypes.BETA.get())));
					var5.setLife(10);
					var5.setSize(1.3F);
					WyHelper.spawnParticles(var5,((ServerLevel)(this.level())),(this.getX()) + (var2),((this.getY()) + (0.25D)) + (var3),(this.getZ()) + (var4));
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
			if (this.causeExplosion){
				var1 = 0;
				do{
					if ((var1) < (2)){
						var2 = (WyHelper.randomDouble()) / (2D);
						var3 = (WyHelper.randomDouble()) / (2D);
						var4 = (WyHelper.randomDouble()) / (2D);
						SimpleParticleData var5_1 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
						var5_1.setLife(7);
						var5_1.setSize(1.2F);
						WyHelper.spawnParticles(var5_1,((ServerLevel)(this.level())),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4));
						var1++;
						continue;
					}else{
						break;
					}
				} while (true);
			}else{
				return;
			}
		}
	}
	private void entityHitEvent(EntityHitResult var1) {
		if (!(this.causeExplosion)){
			Entity var2 = var1.getEntity();
			if (var2 instanceof LivingEntity){
				((LivingEntity)(var2)).addEffect(new MobEffectInstance((Holder)(ModEffects.STICKY),300,0,false,false));
			}
		}
		this.defaultOnHitBlock(var1);
	}
	private void blockHitEvent(BlockHitResult var1) {
		if (this.causeExplosion){
			IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
			StickyProjectile var3 = this;
			BlockHitResult var4 = var1;
			final BlockHitResult var4_f1 = var4;
			Consumer<ExplosionComponent> var5 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var4_f1.getBlockPos().getX()),(double)(var4_f1.getBlockPos().getY()),(double)(var4_f1.getBlockPos().getZ()),2F);
			lv1_3.setStaticDamage(10F);
			lv1_3.setExplosionSound(true);
			lv1_3.setDamageOwner(false);
			lv1_3.setDestroyBlocks(true);
			lv1_3.setFireAfterExplosion(true);
			lv1_3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION2));
			lv1_3.setDamageEntities(true);
			lv1_3.explode();
		};
			ExplosionComponent.createExplosion(var2,var5);
		}else{
			int var2_1 = 0;
			do{
				if ((var2_1) < (20)){
					int var3_1 = WyHelper.randomWithRange(-2,2);
					int var4_1 = WyHelper.randomWithRange(-2,2);
					BlockPos var5_1 = this.blockPosition().offset(var3_1,0,var4_1);
					if (this.level().getBlockState(var5_1.below()).canOcclude()){
						NuWorld.setBlockState((Entity)(this.getOwner()),var5_1,((Block)(ModBlocks.MUCUS.get())).defaultBlockState(),2,DefaultProtectionRules.AIR_FOLIAGE);
					}
					var2_1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	public void setCauseExplosion() {
		this.causeExplosion = true;
	}
}