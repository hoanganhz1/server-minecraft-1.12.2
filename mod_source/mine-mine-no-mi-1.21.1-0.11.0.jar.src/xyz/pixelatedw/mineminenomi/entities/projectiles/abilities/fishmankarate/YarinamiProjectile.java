package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate;

import java.util.Optional;
import java.util.function.Consumer;
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
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class YarinamiProjectile extends NuProjectileEntity {
	// Fields
	public static final float DAMAGE = 60F;
	public static final int EXPLOSION_RADIUS = 4;
	public static final int EXPLOSION_DAMAGE = 15;

	public YarinamiProjectile(EntityType<? extends YarinamiProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public YarinamiProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.YARINAMI.get())),var1,var2,var3);
		this.setDamage(60F);
		this.setPassThroughEntities();
		this.setMaxLife(30);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void blockHitEvent(BlockHitResult var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		YarinamiProjectile var3 = this;
		BlockHitResult var4 = var1;
		final BlockHitResult var4_f1 = var4;
		Consumer<ExplosionComponent> var5 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var4_f1.getBlockPos().getX()),(double)(var4_f1.getBlockPos().getY()),(double)(var4_f1.getBlockPos().getZ()),4F);
			lv1_3.setStaticDamage(15F);
			lv1_3.setExplosionSound(false);
			lv1_3.setDamageOwner(false);
			lv1_3.setDestroyBlocks(true);
			lv1_3.setFireAfterExplosion(false);
			lv1_3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.WATER_EXPLOSION.get())));
			lv1_3.setDamageEntities(false);
			lv1_3.explode();
		};
		ExplosionComponent.createExplosion(var2,var5);
	}
	private void tickEvent() {
		if (!(this.level().isClientSide)){
			int var1 = 0;
			do{
				if ((var1) < (15)){
					double var2 = (WyHelper.randomDouble()) / (2D);
					double var3 = (WyHelper.randomDouble()) / (2D);
					double var4 = (WyHelper.randomDouble()) / (2D);
					((ServerLevel)(this.level())).sendParticles((ParticleOptions)(ParticleTypes.FISHING),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4),1,0D,0D,0D,-0.1D);
					((ServerLevel)(this.level())).sendParticles((ParticleOptions)(ParticleTypes.BUBBLE),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4),1,0D,0D,0D,0.1D);
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}