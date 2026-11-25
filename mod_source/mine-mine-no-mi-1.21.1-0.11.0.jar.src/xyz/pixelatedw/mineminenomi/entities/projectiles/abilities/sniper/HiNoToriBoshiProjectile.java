package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class HiNoToriBoshiProjectile extends NuProjectileEntity {
	// Fields
	public static final float DAMAGE = 25F;
	public static final float EXPLOSION_RADIUS = 4F;
	public static final float EXPLOSION_DAMAGE = 10F;

	public HiNoToriBoshiProjectile(EntityType<? extends HiNoToriBoshiProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public HiNoToriBoshiProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.HI_NO_TORI_BOSHI.get())),var1,var2,var3);
		this.setDamage(25F);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof Creeper){
			((Creeper)(var2)).ignite();
			return;
		}else{
			var1.getEntity().igniteForSeconds(5F);
			return;
		}
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		if ((this.level().getBlockState(var1.getBlockPos()).getBlock()) == (ModBlocks.OIL_SPILL.get())){
			AbilityExplosion var2 = new AbilityExplosion((Entity)(this.getOwner()),((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null)))),(double)(var1.getBlockPos().getX()),(double)(var1.getBlockPos().getY()),(double)(var1.getBlockPos().getZ()),4F);
			var2.setStaticDamage(10F);
			var2.setFireAfterExplosion(true);
			var2.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION4));
			var2.explode();
			return;
		}else{
			return;
		}
	}
	private void onTickEvent() {
		if (!(this.level().isClientSide)){
			int var1 = 0;
			do{
				if ((var1) < (2)){
					double var2 = (WyHelper.randomDouble()) / (2D);
					double var3 = (WyHelper.randomDouble()) / (2D);
					double var4 = (WyHelper.randomDouble()) / (2D);
					SimpleParticleData var5 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
					var5.setLife(10);
					var5.setSize(1F);
					WyHelper.spawnParticles(var5,((ServerLevel)(this.level())),(this.getX()) + (var2),((this.getY()) + (0.2D)) + (var3),(this.getZ()) + (var4));
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}