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
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import xyz.pixelatedw.mineminenomi.api.entities.IFlexibleSizeProjectile;
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
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class DaiEnkaiEnteiProjectile extends NuProjectileEntity implements IFlexibleSizeProjectile {
	// Fields
	private static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(DaiEnkaiEnteiProjectile.class,EntityDataSerializers.FLOAT);
	private static final BlockProtectionRule GRIEF_RULE;

	public DaiEnkaiEnteiProjectile(EntityType<? extends DaiEnkaiEnteiProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public DaiEnkaiEnteiProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.DAI_ENKAI_ENTEI.get())),var1,var2,var3);
		this.setArmorPiercing(0.75F);
		this.setUnavoidable();
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void blockHitEvent(BlockHitResult var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		DaiEnkaiEnteiProjectile var3 = this;
		BlockHitResult var4 = var1;
		final BlockHitResult var4_f1 = var4;
		Consumer<ExplosionComponent> var5 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var4_f1.getBlockPos().getX()),(double)(var4_f1.getBlockPos().getY()),(double)(var4_f1.getBlockPos().getZ()),(0.6F) * (this.getSize()));
			lv1_3.setStaticDamage((2F) * (this.getSize()));
			lv1_3.setStaticBlockResistance(0.25F);
			lv1_3.setFireAfterExplosion(true);
			lv1_3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(new CommonExplosionParticleEffect$Details((double)((0.6F) * (this.getSize())))));
			lv1_3.explode();
		};
		ExplosionComponent.createExplosion(var2,var5);
	}
	private void tickEvent() {
		double var2 = 0.0;
		double var3 = 0.0;
		this.setDamage((2F) * (this.getSize()));
		if ((this.isEyeInFluidType(((FluidType)(NeoForgeMod.WATER_TYPE.value())))) && (ServerConfig.getDestroyWater())){
			SimpleBlockPlacer var1 = new SimpleBlockPlacer().setBlock(Blocks.AIR.defaultBlockState()).setRule(GRIEF_RULE).setSize(9);
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
		if (!(this.level().isClientSide)){
			double var4 = 0.0;
			int var1_1 = 0;
			do{
				if ((var1_1) < (20)){
					var2 = WyHelper.randomDouble();
					var3 = WyHelper.randomDouble();
					var4 = WyHelper.randomDouble();
					SimpleParticleData var5 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
					var5.setLife(6);
					var5.setSize(1.3F);
					WyHelper.spawnParticles(var5,((ServerLevel)(this.level())),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4));
					var1_1++;
					continue;
				}else{
					break;
				}
			} while (true);
			var1_1 = 0;
			do{
				if ((var1_1) < (2)){
					var2 = WyHelper.randomDouble();
					var3 = WyHelper.randomDouble();
					var4 = WyHelper.randomDouble();
					SimpleParticleData var5_1 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU.get())));
					var5_1.setLife(4);
					var5_1.setSize(1.2F);
					WyHelper.spawnParticles(var5_1,((ServerLevel)(this.level())),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4));
					var1_1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	public void defineSynchedData(SynchedEntityData.Builder var1) {
		super.defineSynchedData(var1);
		var1.define(SIZE,Float.valueOf(0F));
	}
	public void setSize(float var1) {
		this.entityData.set(SIZE,Float.valueOf(var1));
	}
	public float getSize() {
		return ((Float)(this.entityData.get(SIZE))).floatValue();
	}
	public void increaseSize() {
		float var1 = this.getSize();
		if ((this.getSize()) <= (0F)){
			var1 = 10F;
		}
		float var2 = (var1) + (0.1875F);
		this.setSize(var2);
		this.setEntityCollisionSizeAndUpdate((double)((var2) / (4F)));
	}
	static  {
		TagKey[] var0 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_LIQUIDS,ModTags$Blocks.BLOCK_PROT_SNOW};
		GRIEF_RULE = new BlockProtectionRule$Builder(new BlockProtectionRule[0]).addApprovedTags(var0).build();
	}
}