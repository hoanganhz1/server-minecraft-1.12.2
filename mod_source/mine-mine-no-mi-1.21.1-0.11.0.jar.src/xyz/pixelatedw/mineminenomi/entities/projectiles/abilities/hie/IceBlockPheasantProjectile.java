package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hie;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.effects.FrostbiteEffect;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class IceBlockPheasantProjectile extends NuProjectileEntity {
	public IceBlockPheasantProjectile(EntityType<? extends IceBlockPheasantProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public IceBlockPheasantProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.ICE_BLOCK_PHEASANT.get())),var1,var2,var3);
		this.setDamage(45F);
		this.setArmorPiercing(1F);
		this.setPassThroughEntities();
		this.setMaxLife(60);
		super.setPhysical();
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			FrostbiteEffect.addFrostbiteStacks(((LivingEntity)(var2)),6);
		}
	}
	private void onTickEvent() {
		int var3 = 0;
		BlockPos var1 = null;
		int var2 = 1;
		do{
			if ((var1) == (null)){
				if (this.level().getBlockState(this.blockPosition().below(var2)).canOcclude()){
					var1 = this.blockPosition().below(var2);
					break;
				}else{
					if ((var2) > (2)){
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
		if ((var1) != (null)){
			var3 = (2) + (((4) * ((this.getMaxLife()) - (this.getLife()))) / (this.getMaxLife()));
			new SimpleBlockPlacer().setBlock(Blocks.BLUE_ICE.defaultBlockState()).setSize(var3).setRule(DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID).generate(this.level(),var1,BlockGenerators.SPHERE);
		}
		if (!(this.level().isClientSide)){
			var3 = 0;
			do{
				if ((var3) < (5)){
					double var4 = WyHelper.randomDouble();
					double var5 = WyHelper.randomDouble();
					double var6 = WyHelper.randomDouble();
					SimpleParticleData var7 = new SimpleParticleData(((ParticleType)(ModParticleTypes.HIE.get())));
					var7.setLife(14);
					var7.setAnimationSpeed(2);
					var7.setRotation(new Vec3(0D,0D,1D));
					var7.setRotationSpeed((((var3) % (2)) == (0)) ? (0.07F) : (-0.07F));
					var7.setSize(1.5F);
					WyHelper.spawnParticles(var7,((ServerLevel)(this.level())),(this.getX()) + (var4),(this.getY()) + (var5),(this.getZ()) + (var6));
					var3++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}