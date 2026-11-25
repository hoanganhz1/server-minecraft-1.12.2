package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.pero;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class CandyWaveProjectile extends NuProjectileEntity {
	// Fields
	private final SimpleBlockPlacer placer;

	public CandyWaveProjectile(EntityType<? extends CandyWaveProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.placer = new SimpleBlockPlacer().setBlock(((Block)(ModBlocks.CANDY.get())).defaultBlockState()).setRule(DefaultProtectionRules.CORE);
	}
	public CandyWaveProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.CANDY_WAVE.get())),var1,var2,var3);
		this.setDamage(24F);
		this.setMaxLife(12);
		this.setPassThroughEntities();
		this.setPassThroughBlocks();
		this.placer = new SimpleBlockPlacer().setBlock(((Block)(ModBlocks.CANDY.get())).defaultBlockState()).setRule(DefaultProtectionRules.CORE);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityHit))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onEntityHit(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			((LivingEntity)(var2)).addEffect(new MobEffectInstance((Holder)(ModEffects.CANDY_STUCK),100,0,false,false,false));
		}
	}
	private void onTickEvent() {
		BlockPos var1 = null;
		int var2 = 1;
		do{
			if ((var1) == (null)){
				if (this.level().getBlockState(this.blockPosition().below(var2)).canOcclude()){
					var1 = this.blockPosition().below(var2);
					break;
				}else{
					if ((var2) > (3)){
						break;
					}else{
						var2++;
						continue;
					}
				}
			}else{
				break;
			}
		} while (true);
		if ((var1) == (null)){
			return;
		}else{
			int var3 = (2) + (((4) * ((this.getMaxLife()) - (this.getLife()))) / (this.getMaxLife()));
			if ((this.placer) != (null)){
				this.placer.setSize(var3);
				this.placer.generate(this.level(),var1,BlockGenerators.SPHERE);
			}
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.CANDY_WAVE.get())),(Entity)(this),(double)(var1.getX()),(double)(var1.getY()),(double)(var1.getZ()));
			return;
		}
	}
}