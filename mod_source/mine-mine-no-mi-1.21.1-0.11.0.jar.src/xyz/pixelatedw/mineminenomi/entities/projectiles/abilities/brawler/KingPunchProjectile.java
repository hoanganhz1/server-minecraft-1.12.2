package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.brawler;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class KingPunchProjectile extends NuProjectileEntity {
	public KingPunchProjectile(EntityType<? extends KingPunchProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public KingPunchProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.KING_PUNCH.get())),var1,var2,var3);
		super.setEntityCollisionSize(4D);
		super.setMaxLife(30);
		super.setPassThroughEntities();
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onTickEvent() {
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