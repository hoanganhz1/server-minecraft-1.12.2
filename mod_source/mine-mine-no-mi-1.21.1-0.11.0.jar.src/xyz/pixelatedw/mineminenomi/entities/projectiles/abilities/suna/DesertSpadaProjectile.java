package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suna;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DesertSpadaProjectile extends NuProjectileEntity {
	// Fields
	private SimpleBlockPlacer placer;

	public DesertSpadaProjectile(EntityType<? extends DesertSpadaProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public DesertSpadaProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.DESERT_SPADA.get())),var1,var2,var3);
		this.setDamage(30F);
		this.setMaxLife(45);
		this.setPassThroughEntities();
		this.setPassThroughBlocks();
		this.setEntityCollisionSize(2D);
		this.placer = new SimpleBlockPlacer().setBlock(Blocks.AIR.defaultBlockState()).setRule(DefaultProtectionRules.CORE).setSize(2);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::entityHitEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void entityHitEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			((LivingEntity)(var2)).addEffect(new MobEffectInstance((Holder)(ModEffects.DEHYDRATION),200,1));
		}
	}
	private void tickEvent() {
		BlockPos var1 = null;
		int var2 = 1;
		do{
			if (!(this.level().getBlockState(this.blockPosition().below(var2)).isAir())){
				var1 = this.blockPosition().below(var2);
				break;
			}else{
				if ((var2) > (5)){
					break;
				}else{
					var2++;
					continue;
				}
			}
		} while (true);
		if ((var1) == (null)){
			return;
		}else{
			if ((this.placer) != (null)){
				this.placer.generate(this.level(),var1.below(),BlockGenerators.SPHERE);
			}
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.DESERT_SPADA.get())),(Entity)(this),(double)(var1.getX()),(double)(var1.getY()),(double)(var1.getZ()));
			return;
		}
	}
}