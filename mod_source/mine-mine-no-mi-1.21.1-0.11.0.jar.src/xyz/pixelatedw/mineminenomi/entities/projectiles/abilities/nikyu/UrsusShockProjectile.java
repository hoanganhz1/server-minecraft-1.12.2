package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.abilities.nikyu.UrsusShockAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.IFlexibleSizeProjectile;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class UrsusShockProjectile extends NuProjectileEntity implements IFlexibleSizeProjectile {
	// Fields
	private static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(UrsusShockProjectile.class,EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Boolean> FINISHED = SynchedEntityData.defineId(UrsusShockProjectile.class,EntityDataSerializers.BOOLEAN);
	private static final double DAMAGE_RADIUS = 13.75D;
	private static final double KNOCKBACK_RADIUS = 22D;
	public float multiplier;

	public UrsusShockProjectile(EntityType<? extends UrsusShockProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.multiplier = 0F;
	}
	public UrsusShockProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.URSUS_SHOCK.get())),var1,var2,var3);
		this.multiplier = 0F;
		this.setDamage(15F);
		this.setMaxLife(64);
		this.setArmorPiercing(1F);
		this.setPassThroughBlocks();
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	public void tick() {
		super.tick();
		this.noCulling = true;
		if (this.isFinished()){
			this.setSize(Math.min((this.getSize()) + (0.75F),50F));
			AbilityHelper.setDeltaMovement((Entity)(this),0D,0D,0D);
			this.teleportTo(this.position().x,this.position().y,this.position().z);
			this.setRot(90F,0F);
			this.xRotO = this.getXRot();
			this.yRotO = this.getYRot();
		}
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		if (this.isFinished()){
			return;
		}else{
			BlockPos var2 = var1.getBlockPos();
			this.level().playSound((Player)(null),this.blockPosition(),((SoundEvent)(ModSounds.PAD_HO_SFX.get())),SoundSource.PLAYERS,10F,0.25F);
			new SimpleBlockPlacer().setBlock(Blocks.AIR.defaultBlockState()).setSize(55,5).setRule(DefaultProtectionRules.CORE_FOLIAGE_ORE).generate(this.level(),var2,BlockGenerators.SPHERE);
			if ((this.getOwner()) != (null)){
				List var3 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),this.getOwner(),this.blockPosition(),22D,TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class);
				AbilityDamageSource var4 = ModDamageSources.getInstance((LevelAccessor)(this.level())).ability((Entity)(this),(Entity)(this.getOwner()),((AbilityCore)(UrsusShockAbility.INSTANCE.get())));
				IDamageSourceHandler var5 = IDamageSourceHandler.getHandler(var4);
				SourceType[] var6 = new SourceType[]{SourceType.INTERNAL};
				var5.addDamage(85F,SourceElement.SHOCKWAVE,SourceHakiNature.IMBUING,var6);
				var5.bypassLogia();
				var5.setUnavoidable();
				var5.addAbilityPiercing(1F,((AbilityCore)(this.getParent().map((Function)(((Function<IAbility, AbilityCore>)(IAbility::getCore)))).orElse(null))));
				Iterator var7 = var3.iterator();
				do{
					if (var7.hasNext()){
						LivingEntity var8 = ((LivingEntity)(var7.next()));
						if ((var8.distanceToSqr((Entity)(this))) < (189.0625D)){
							int var9 = 0;
							var8.invulnerableTime = var9;
							var8.hurtTime = var9;
							var8.hurt((DamageSource)(var4),var5.getTotalDamage());
						}
						Vec3 var9_1 = var8.getLookAngle().multiply(-1D,-1D,-1D).multiply(1D,0D,1D);
						AbilityHelper.setDeltaMovement((Entity)(var8),var9_1.x,0.25D,var9_1.z);
						continue;
					}else{
						break;
					}
				} while (true);
			}
			this.setFinished();
			return;
		}
	}
	public void defineSynchedData(SynchedEntityData.Builder var1) {
		var1.define(SIZE,Float.valueOf(0F));
		var1.define(FINISHED,Boolean.valueOf(false));
	}
	public void setSize(float var1) {
		this.entityData.set(SIZE,Float.valueOf(var1));
	}
	public float getSize() {
		return ((Float)(this.entityData.get(SIZE))).floatValue();
	}
	public boolean isFinished() {
		return ((Boolean)(this.entityData.get(FINISHED))).booleanValue();
	}
	public void setFinished() {
		this.entityData.set(FINISHED,Boolean.valueOf(true));
		this.setMaxLife(100);
		this.setLife(this.getMaxLife());
	}
}