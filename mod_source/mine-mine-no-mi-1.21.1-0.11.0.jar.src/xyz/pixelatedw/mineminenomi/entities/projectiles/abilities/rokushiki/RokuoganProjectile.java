package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.rokushiki;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class RokuoganProjectile extends NuProjectileEntity {
	public RokuoganProjectile(EntityType<? extends RokuoganProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public RokuoganProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.ROKUOGAN.get())),var1,var2,var3);
		this.setMaxLife(5);
		this.setDamage(0F);
		this.setPassThroughBlocks();
		this.setPassThroughEntities();
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void blockHitEvent(BlockHitResult var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		RokuoganProjectile var3 = this;
		Consumer<ExplosionComponent> var4 = (l0) -> {
			AbilityExplosion lv1_2 = l0.createExplosion((Entity)(this),this.getOwner(),this.getX(),this.getY(),this.getZ(),7F);
			lv1_2.setExplosionSound(true);
			lv1_2.setDamageOwner(false);
			lv1_2.setDestroyBlocks(false);
			lv1_2.setDamageEntities(false);
			lv1_2.setFireAfterExplosion(false);
			lv1_2.setDamageEntities(false);
			lv1_2.explode();
		};
		ExplosionComponent.createExplosion(var2,var4);
	}
	private void tickEvent() {
		if (!(this.level().isClientSide)){
			int var1 = 0;
			do{
				if ((var1) < (20)){
					double var2 = (WyHelper.randomDouble()) / (1.25D);
					double var3 = (WyHelper.randomDouble()) / (1.25D);
					double var4 = (WyHelper.randomDouble()) / (1.25D);
					((ServerLevel)(this.level())).sendParticles((ParticleOptions)(ParticleTypes.POOF),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4),1,0D,0D,0D,-0.1D);
					((ServerLevel)(this.level())).sendParticles((ParticleOptions)(ParticleTypes.EXPLOSION_EMITTER),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4),1,0D,0D,0D,0.1D);
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}