package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion$RemovedBlock;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class SagariNoRyuseiProjectile extends NuProjectileEntity {
	// Fields
	private float size;

	public SagariNoRyuseiProjectile(EntityType<? extends SagariNoRyuseiProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public SagariNoRyuseiProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.SAGARI_NO_RYUSEI.get())),var1,var2,var3);
		this.setDamage(200F);
		this.setArmorPiercing(0.25F);
		this.setMaxLife(256);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		float var2 = (this.size) / (30F);
		IAbility var3 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		SagariNoRyuseiProjectile var4 = this;
		float var5 = var2;
		final float var5_f1 = var5;
		Consumer<ExplosionComponent> var6 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),this.getX(),this.getY(),this.getZ(),(20F) * (var5_f1));
			lv1_3.setStaticDamage((90F) * (var5_f1));
			lv1_3.addRemovedBlocksToList();
			lv1_3.setDamageOwner(true);
			lv1_3.setFireAfterExplosion(true);
			lv1_3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(new CommonExplosionParticleEffect$Details((double)((int)((20F) * (var5_f1))))));
			lv1_3.explode();
			int lv1_4 = 0;
			Iterator lv1_5 = lv1_3.getBlownStates().iterator();
			do{
				if (lv1_5.hasNext()){
					AbilityExplosion$RemovedBlock lv1_6 = ((AbilityExplosion$RemovedBlock)(lv1_5.next()));
					FallingBlockEntity lv1_7 = FallingBlockEntity.fall(this.level(),lv1_6.pos(),lv1_6.state());
					AbilityHelper.setDeltaMovement((Entity)(lv1_7),(double)(((float)((WyHelper.randomWithRange(-1,1)) / (2))) * (var5_f1)),((0.75D) + (WyHelper.randomDouble())) * ((double)(var5_f1)),(double)(((float)((WyHelper.randomWithRange(-1,1)) / (2))) * (var5_f1)));
					lv1_7.dropItem = false;
					lv1_7.time = 1;
					if ((++(lv1_4)) > (256)){
						break;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		};
		ExplosionComponent.createExplosion(var3,var6);
	}
	private void onTickEvent() {
		float var1 = (this.size) / (30F);
		if ((1) > (this.tickCount)){
			this.setBoundingBox(this.getBoundingBox().inflate((double)(var1)));
		}
		if (!(this.level().isClientSide)){
			int var2 = 0;
			do{
				if ((var2) < (25)){
					ParticleType var3 = (((this.tickCount) % (2)) == (0)) ? (((ParticleType)(ModParticleTypes.MOKU.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.MERA.get()))));
					double var4 = ((WyHelper.randomDouble()) * (5D)) * ((double)(var1));
					double var5 = WyHelper.randomDouble();
					double var6 = ((WyHelper.randomDouble()) * (5D)) * ((double)(var1));
					SimpleParticleData var7 = new SimpleParticleData(var3);
					var7.setLife(20);
					var7.setSize((7F) * (var1));
					WyHelper.spawnParticles(var7,((ServerLevel)(this.level())),(this.getX()) + (var4),(this.getY()) + (var5),(this.getZ()) + (var6));
					var2++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	public void setSize(float var1) {
		this.size = var1;
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		super.writeSpawnData(var1);
		var1.writeFloat(this.size);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		super.readSpawnData(var1);
		this.size = var1.readFloat();
	}
}