package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hie;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.effects.FrostbiteEffect;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class IceBallProjectile extends NuProjectileEntity {
	public IceBallProjectile(EntityType<? extends IceBallProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public IceBallProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.ICE_BALL.get())),var1,var2,var3);
		this.setDamage(25F);
		this.setMaxLife(32);
		super.setPhysical();
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			LivingEntity var3 = ((LivingEntity)(var2));
			FrostbiteEffect.addFrostbiteStacks(var3,6);
			var3.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,100,0));
			var3.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,100,0));
		}
		this.createSphere(var1.getEntity().blockPosition());
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		this.createSphere(var1.getBlockPos());
	}
	private void createSphere(BlockPos var1) {
		new SimpleBlockPlacer().setBlock(Blocks.BLUE_ICE.defaultBlockState()).setSize(5).setHollow().setThickness(3).setRule(DefaultProtectionRules.AIR_FOLIAGE_LIQUID).generate(this.level(),var1,BlockGenerators.SPHERE);
	}
	private void onTickEvent() {
		if (!(this.level().isClientSide)){
			int var1 = 0;
			do{
				if ((var1) < (5)){
					double var2 = (WyHelper.randomDouble()) / (1.5D);
					double var3 = (WyHelper.randomDouble()) / (1.5D);
					double var4 = (WyHelper.randomDouble()) / (1.5D);
					SimpleParticleData var5 = new SimpleParticleData(((ParticleType)(ModParticleTypes.HIE.get())));
					var5.setLife(14);
					var5.setAnimationSpeed(2);
					var5.setRotation(new Vec3(0D,0D,1D));
					var5.setRotationSpeed((((var1) % (2)) == (0)) ? (0.07F) : (-0.07F));
					var5.setSize(1.5F);
					WyHelper.spawnParticles(var5,((ServerLevel)(this.level())),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4));
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
}