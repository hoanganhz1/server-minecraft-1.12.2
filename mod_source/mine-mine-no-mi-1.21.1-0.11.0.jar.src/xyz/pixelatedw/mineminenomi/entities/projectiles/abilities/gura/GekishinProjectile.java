package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gura;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion$RemovedBlock;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GekishinProjectile extends NuProjectileEntity {
	// Fields
	private Interval explosionInterval;

	public GekishinProjectile(EntityType<? extends GekishinProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.explosionInterval = new Interval(2);
	}
	public GekishinProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.GEKISHIN.get())),var1,var2,var3);
		this.explosionInterval = new Interval(2);
		this.setDamage(70F);
		this.setMaxLife(50);
		this.setEntityCollisionSize(5D);
		this.setPassThroughEntities();
		this.setPassThroughBlocks();
		this.setEntityCollisionSize(3D);
		this.setArmorPiercing(0.75F);
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onTickEvent() {
		int var1 = 0;
		do{
			if ((var1) < (32)){
				if (this.explosionInterval.canTick()){
					((ServerLevel)(this.level())).sendParticles((ParticleOptions)(ParticleTypes.EXPLOSION),(this.getX()) + ((WyHelper.randomDouble()) * (1.5D)),(this.getY()) + ((WyHelper.randomDouble()) * (1.5D)),(this.getZ()) + ((WyHelper.randomDouble()) * (1.5D)),1,0D,0D,0D,0D);
				}else{
					double var2 = (WyHelper.randomDouble()) * (5D);
					double var3 = (WyHelper.randomDouble()) * (5D);
					double var4 = (WyHelper.randomDouble()) * (5D);
					SimpleParticleData var5 = new SimpleParticleData(((this.random.nextDouble()) > (0.5D)) ? (((ParticleType)(ModParticleTypes.MOKU.get()))) : (((ParticleType)(ModParticleTypes.MOKU2.get()))));
					var5.setLife(10);
					var5.setSize(10F);
					WyHelper.spawnParticles(var5,((ServerLevel)(this.level())),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4));
				}
				var1++;
				continue;
			}else{
				break;
			}
		} while (true);
		IAbility var2_1 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		GekishinProjectile var3_1 = this;
		Consumer<ExplosionComponent> var4_1 = (l0) -> {
			int lv1_2 = 0;
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),this.getX(),this.getY(),this.getZ(),8F);
			lv1_3.setStaticBlockResistance(1.35F);
			lv1_3.setProtectOwnerFromFalling(true);
			lv1_3.setExplosionSound(false);
			lv1_3.setSmokeParticles((ParticleEffect)(null));
			lv1_3.setStaticDamage(15F);
			lv1_3.addRemovedBlocksToList();
			lv1_3.explode();
			Iterator lv1_4 = lv1_3.getBlownStates().iterator();
			do{
				if (lv1_4.hasNext()){
					AbilityExplosion$RemovedBlock lv1_5 = ((AbilityExplosion$RemovedBlock)(lv1_4.next()));
					FallingBlockEntity lv1_6 = FallingBlockEntity.fall(this.level(),lv1_5.pos(),lv1_5.state());
					lv1_6.dropItem = false;
					lv1_6.time = 1;
					Vec3 lv1_7 = lv1_6.position().subtract(this.position()).normalize().multiply(2D,2D,2D);
					AbilityHelper.setDeltaMovement((Entity)(lv1_6),-(lv1_7.x),(1.5D) + (lv1_7.y),-(lv1_7.z));
					if ((++(lv1_2)) > (50)){
						break;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		};
		ExplosionComponent.createExplosion(var2_1,var4_1);
	}
}