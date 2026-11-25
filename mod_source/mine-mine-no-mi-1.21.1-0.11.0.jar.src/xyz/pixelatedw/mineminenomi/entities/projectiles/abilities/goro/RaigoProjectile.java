package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.MineMineNoMi;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.IFlexibleSizeProjectile;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hie.IceBlockAvalancheProjectile;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class RaigoProjectile extends NuProjectileEntity implements IFlexibleSizeProjectile {
	// Fields
	private static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(IceBlockAvalancheProjectile.class,EntityDataSerializers.FLOAT);
	public static final float EXPLOSION_RADIUS = 0.7F;
	public static final float MAX_SIZE = 80F;
	public static final float SHOCKWAVE_RADIUS = 0.85F;
	private boolean dealtAOE;
	private boolean closeToFloor;
	private LivingEntity owner;

	public RaigoProjectile(EntityType<? extends RaigoProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.dealtAOE = false;
		this.closeToFloor = false;
	}
	public RaigoProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.RAIGO.get())),var1,var2,var3);
		this.dealtAOE = false;
		this.closeToFloor = false;
		this.setDamage(100F);
		this.setMaxLife(256);
		this.setEntityCollisionSize(4D);
		this.setPassThroughEntities();
		this.setPassThroughBlocks();
		this.setArmorPiercing(0.75F);
		this.setUnavoidable();
		this.owner = this.getOwner();
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	public void onBlockImpactEvent(BlockHitResult var1) {
		int var2 = (int)((0.7F) * (this.getSize()));
		int var3 = (int)((0.85F) * (this.getSize()));
		new SimpleBlockPlacer().setBlock(Blocks.AIR.defaultBlockState()).setRule(DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID).setSize(var2).generate(this.level(),this.blockPosition().below((var2) / (8)),BlockGenerators.SPHERE);
		if (!(this.dealtAOE)){
			List var4 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),this.getOwner(),this.position(),(double)(var3),TargetPredicate.EVERYTHING,Entity.class);
			var4.remove(this.getOwner());
			Iterator var5 = var4.iterator();
			do{
				if (var5.hasNext()){
					Entity var6 = ((Entity)(var5.next()));
					if ((!(var6 instanceof Projectile)) && (!(var6 instanceof AbstractArrow))){

					}else{
						var6.discard();
					}
					if (var6 instanceof LivingEntity){
						var6.hurt((DamageSource)(ModDamageSources.getInstance((LevelAccessor)(this.level())).ability((Entity)(this.getOwner()),((IAbility)(this.getParent().get())).getCore())),this.getDamage());
						Vec3 var7 = var6.getLookAngle().scale(-1D).multiply(5D,0D,5D);
						AbilityHelper.setDeltaMovement(var6,var7.x,1D,var7.z);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			this.dealtAOE = true;
		}
	}
	private void onTickEvent() {
		this.setDamage((2F) * (this.getSize()));
		if (!(this.level().isClientSide)){
			int var1 = 0;
			do{
				if ((var1) < (25)){
					ParticleType var2 = (MineMineNoMi.hasBlueGoroPackSelected()) ? (((ParticleType)(ModParticleTypes.GORO.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.GORO_YELLOW.get()))));
					ParticleType var3 = (MineMineNoMi.hasBlueGoroPackSelected()) ? (((ParticleType)(ModParticleTypes.GORO2.get()))) : ((ParticleType)(((ParticleType)(ModParticleTypes.GORO2_YELLOW.get()))));
					ParticleType var4 = (((this.tickCount) % (2)) == (0)) ? (var3) : (var2);
					double var5 = (WyHelper.randomDouble()) * (5D);
					double var6 = WyHelper.randomDouble();
					double var7 = (WyHelper.randomDouble()) * (5D);
					SimpleParticleData var8 = new SimpleParticleData(var4);
					var8.setLife(20);
					var8.setSize(7F);
					WyHelper.spawnParticles(var8,((ServerLevel)(this.level())),(this.getX()) + (var5),(this.getY()) + (var6),(this.getZ()) + (var7));
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
			this.setRot(0F,90F);
			if ((!(this.closeToFloor)) && (WyHelper.rayTraceBlocks((Entity)(this),30D).getType().equals(HitResult.Type.BLOCK))){
				this.setMaxLife(16);
				this.closeToFloor = true;
			}
			if (((this.owner) != (null)) && (((this.tickCount) % (5)) == (0))){
				int var2_1 = 0;
				do{
					if ((var2_1) < (10)){
						float var3_1 = (float)(WyHelper.randomWithRange(36,50));
						RaigoLightningProjectile var4_1 = new RaigoLightningProjectile(this.level(),this.owner,this.getX(),this.getY(),this.getZ(),(float)(WyHelper.randomWithRange(0,360)),(float)(WyHelper.randomWithRange(-90,90)),var3_1,((IAbility)(this.getParent().get())));
						this.level().addFreshEntity((Entity)(var4_1));
						var2_1++;
						continue;
					}else{
						break;
					}
				} while (true);
			}else{
				return;
			}
		}
	}
	public void defineSynchedData(SynchedEntityData.Builder var1) {
		super.defineSynchedData(var1);
		var1.define(SIZE,Float.valueOf(1F));
	}
	public float getSize() {
		return ((Float)(this.getEntityData().get(SIZE))).floatValue();
	}
	public void setSize(float var1) {
		this.getEntityData().set(SIZE,Float.valueOf(Math.min(var1,80F)));
	}
	public void increaseSize(float var1) {
		float var2 = (this.getSize()) + ((var1) * (10F));
		this.setSize(var2);
		this.setEntityCollisionSize((double)(var2));
		this.setBlockCollisionSize((double)(var2));
	}
}