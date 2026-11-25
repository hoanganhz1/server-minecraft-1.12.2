package xyz.pixelatedw.mineminenomi.entities.projectiles;

import java.util.Optional;
import java.util.function.Supplier;
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
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class HookEntity extends NuProjectileEntity {
	// Fields
	private DevilFruitData props;

	public HookEntity(EntityType<? extends HookEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public HookEntity(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.HOOK.get())),var1,var2,var3);
		this.setMaxLife(50);
		this.setEntityCollisionSize(1D);
		this.props = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var2))).orElse((Object)(null))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void tickEvent() {
		if (!(this.level().isClientSide)){
			int var1 = 0;
			do{
				if ((var1) < (10)){
					double var2 = (WyHelper.randomDouble()) / (2D);
					double var3 = (WyHelper.randomDouble()) / (2D);
					double var4 = (WyHelper.randomDouble()) / (2D);
					SimpleParticleData var5 = null;
					if (this.props.hasDevilFruit(ModFruits.SUNA_SUNA_NO_MI)){
						var5 = new SimpleParticleData(((ParticleType)(ModParticleTypes.SUNA2.get())));
						var5.setSize(1.5F);
					}else{
						if (this.props.hasDevilFruit(ModFruits.MOKU_MOKU_NO_MI)){
							var5 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU.get())));
							var5.setSize(1.3F);
						}
					}
					if ((var5) != (null)){
						var5.setLife(20);
						var5.setRotation(0F,0F,1F);
						WyHelper.spawnParticles(var5,((ServerLevel)(this.level())),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4));
					}
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}