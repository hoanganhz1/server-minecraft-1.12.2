package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule$Builder;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class HikenProjectile extends NuProjectileEntity {
	// Fields
	private static final BlockProtectionRule GRIEF_RULE;

	public HikenProjectile(EntityType<? extends HikenProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public HikenProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.HIKEN.get())),var1,var2,var3);
		this.setDamage(50F);
		this.setPassThroughBlocks();
		this.setPassThroughEntities();
		this.setMaxLife(32);
		this.setArmorPiercing(0.75F);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void blockHitEvent(BlockHitResult var1) {
		this.explode((double)(var1.getBlockPos().getX()),(double)(var1.getBlockPos().getY()),(double)(var1.getBlockPos().getZ()));
	}
	private void tickEvent() {
		double var2 = 0.0;
		double var3 = 0.0;
		if ((this.tickCount) > (2)){
			this.explode(this.getX(),this.getY(),this.getZ());
		}
		if ((this.isEyeInFluidType(((FluidType)(NeoForgeMod.WATER_TYPE.value())))) && (ServerConfig.getDestroyWater())){
			SimpleBlockPlacer var1 = new SimpleBlockPlacer().setBlock(Blocks.AIR.defaultBlockState()).setRule(GRIEF_RULE).setSize(2);
			var1.generate(this.level(),this.blockPosition(),BlockGenerators.SPHERE);
			Iterator var2_1 = var1.getPlacedPositions().iterator();
			do{
				if (var2_1.hasNext()){
					BlockPos var3_1 = ((BlockPos)(var2_1.next()));
					WyHelper.spawnParticles((ParticleOptions)(ParticleTypes.BUBBLE),((ServerLevel)(this.getCommandSenderWorld())),((double)(var3_1.getX())) + ((WyHelper.randomDouble()) / (2D)),((double)(var3_1.getY())) + (0.8D),((double)(var3_1.getZ())) + ((WyHelper.randomDouble()) / (2D)));
					this.getCommandSenderWorld().addParticle((ParticleOptions)(ParticleTypes.SMOKE),(double)(var3_1.getX()),((double)(var3_1.getY())) + (1.1D),(double)(var3_1.getZ()),0D,0D,0D);
					continue;
				}else{
					break;
				}
			} while (true);
		}
		if (!(super.level().isClientSide)){
			double var4 = 0.0;
			int var1_1 = 0;
			do{
				if ((var1_1) < (20)){
					var2 = (WyHelper.randomDouble()) * (2D);
					var3 = (WyHelper.randomDouble()) * (2D);
					var4 = (WyHelper.randomDouble()) * (2D);
					SimpleParticleData var5 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
					var5.setLife(30);
					var5.setSize(3F);
					WyHelper.spawnParticles(var5,((ServerLevel)(this.level())),(super.getX()) + (var2),(super.getY()) + (var3),(super.getZ()) + (var4));
					var1_1++;
					continue;
				}else{
					break;
				}
			} while (true);
			var1_1 = 0;
			do{
				if ((var1_1) < (10)){
					var2 = (WyHelper.randomDouble()) / (2D);
					var3 = (WyHelper.randomDouble()) / (2D);
					var4 = (WyHelper.randomDouble()) / (2D);
					SimpleParticleData var5_1 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU.get())));
					var5_1.setLife(7);
					var5_1.setSize(1.2F);
					WyHelper.spawnParticles(var5_1,((ServerLevel)(this.level())),(super.getX()) + (var2),(super.getY()) + (var3),(super.getZ()) + (var4));
					var1_1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	private void explode(double var1, double var2, double var3) {
		IAbility var4 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		HikenProjectile var5 = this;
		double var6 = var1;
		double var7 = var2;
		double var8 = var3;
		final double var6_f1 = var6;
		final double var7_f2 = var7;
		final double var8_f3 = var8;
		Consumer<ExplosionComponent> var9 = (l0) -> {
			AbilityExplosion lv1_5 = l0.createExplosion((Entity)(this),this.getOwner(),var6_f1,var7_f2,var8_f3,4F);
			lv1_5.setStaticDamage(25F);
			lv1_5.disableExplosionKnockback();
			lv1_5.setDamageOwner(false);
			lv1_5.setFireAfterExplosion(true);
			lv1_5.setExplosionSound(false);
			lv1_5.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION2));
			lv1_5.explode();
		};
		ExplosionComponent.createExplosion(var4,var9);
	}
	static  {
		TagKey[] var0 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_LIQUIDS,ModTags$Blocks.BLOCK_PROT_SNOW};
		GRIEF_RULE = new BlockProtectionRule$Builder(new BlockProtectionRule[0]).addApprovedTags(var0).build();
	}
}