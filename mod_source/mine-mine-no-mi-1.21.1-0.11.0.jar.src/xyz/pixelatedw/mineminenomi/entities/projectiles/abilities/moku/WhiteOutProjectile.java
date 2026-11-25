package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.moku;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class WhiteOutProjectile extends NuProjectileEntity {
	public WhiteOutProjectile(EntityType<? extends WhiteOutProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public WhiteOutProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.WHITE_OUT.get())),var1,var2,var3);
		super.setDamage(0.1F);
		super.setMaxLife(30);
		super.setEntityCollisionSize(1D);
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onTickEvent() {
		if (!(super.level().isClientSide)){
			int var1 = 0;
			do{
				if ((var1) < (10)){
					double var2 = (WyHelper.randomDouble()) / (2D);
					double var3 = (WyHelper.randomDouble()) / (2D);
					double var4 = (WyHelper.randomDouble()) / (2D);
					SimpleParticleData var5 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU.get())));
					var5.setLife(10);
					var5.setSize(1.3F);
					WyHelper.spawnParticles(var5,((ServerLevel)(super.level())),(super.getX()) + (var2),(super.getY()) + (var3),(super.getZ()) + (var4));
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}