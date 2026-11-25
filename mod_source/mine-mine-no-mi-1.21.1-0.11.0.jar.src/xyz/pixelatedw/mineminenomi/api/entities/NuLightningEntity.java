package xyz.pixelatedw.mineminenomi.api.entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent$ICheckLimitEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateLightningEntity;

public abstract class NuLightningEntity extends NuProjectileEntity {
	// Fields
	private static final int DEFAULT_FADE_TIME = 20;
	private final LightningBolt bolt;
	public long seed;
	protected ArrayList<Integer> targets;
	protected ArrayList<Entity> explosionTargets;
	protected Set<Long> firstContactBlocks;
	protected Vec3 hitPosition;
	protected Vec3 lookVec;
	protected RandomSource spazzingRandom;
	private float length;
	private float size;
	private float maxTravelDistance;
	private float travelSpeed;
	private float rotation;
	private float previousLength;
	private int branches;
	private int segments;
	private int segmentLength;
	private int angle;
	private int targetTimeToReset;
	private int startFadeTime;
	private int fadeTime;
	private int depth;
	private int lengthChecks;
	private boolean alwaysDamage;
	private boolean visualOnly;
	private boolean lightningMovement;
	private boolean hasToCheckForTarget;
	private boolean fadeTrail;
	private boolean canRetract;
	private boolean isRetracting;
	private boolean hasFinishedExtending;
	private boolean hasLengthChanged;
	private boolean mimicVanilla;
	private boolean fadeAtMaxLength;
	private boolean discardOnRetract;
	private int red;
	private int green;
	private int blue;
	private int alpha;
	private Optional<ExplosionComponent> explosionComponent;

	public NuLightningEntity(EntityType<? extends NuLightningEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.bolt = new LightningBolt(EntityType.LIGHTNING_BOLT,this.level());
		this.targets = (ArrayList) (new ArrayList());
		this.explosionTargets = (ArrayList) (new ArrayList());
		this.firstContactBlocks = new LinkedHashSet();
		this.hitPosition = Vec3.ZERO;
		this.lookVec = Vec3.ZERO;
		this.spazzingRandom = RandomSource.create();
		this.length = 0F;
		this.size = 1F;
		this.maxTravelDistance = 0F;
		this.travelSpeed = 12F;
		this.rotation = 0F;
		this.branches = 1;
		this.segments = 7;
		this.segmentLength = 0;
		this.angle = 0;
		this.targetTimeToReset = 20;
		this.startFadeTime = 20;
		this.fadeTime = -1;
		this.depth = 4;
		this.alwaysDamage = true;
		this.visualOnly = false;
		this.lightningMovement = true;
		this.hasToCheckForTarget = true;
		this.fadeTrail = false;
		this.canRetract = false;
		this.isRetracting = false;
		this.hasFinishedExtending = false;
		this.hasLengthChanged = false;
		this.mimicVanilla = false;
		this.fadeAtMaxLength = true;
		this.discardOnRetract = true;
		this.red = 255;
		this.green = 255;
		this.blue = 255;
		this.alpha = 255;
		this.explosionComponent = (Optional) (((java.util.Optional)(Optional.empty())));
		this.noCulling = true;
		this.seed = this.random.nextLong();
	}
	public NuLightningEntity(EntityType<? extends NuLightningEntity> var1, LivingEntity var2, float var3, float var4, IAbility var5) {
		this(var1,var2,var2.getX(),var2.getY(),var2.getZ(),var2.getYRot(),var2.getXRot(),var3,var4,var5,var5.getCore().getSourceElement(),var5.getCore().getSourceHakiNature(),var5.getCore().getSourceTypesArray());
	}
	public NuLightningEntity(EntityType<? extends NuLightningEntity> var1, LivingEntity var2, double var3, double var4, double var5, float var6, float var7, float var8, float var9, IAbility var10) {
		this(var1,var2,var3,var4,var5,var6,var7,var8,var9,var10,var10.getCore().getSourceElement(),var10.getCore().getSourceHakiNature(),var10.getCore().getSourceTypesArray());
	}
	public NuLightningEntity(EntityType<? extends NuLightningEntity> var1, LivingEntity var2, double var3, double var4, double var5, float var6, float var7, float var8, float var9, @Nullable() IAbility var10, SourceElement var11, SourceHakiNature var12, SourceType... var13) {
		super((EntityType)(var1),var2.level(),var2,var10,var11,var12,var13);
		this.bolt = new LightningBolt(EntityType.LIGHTNING_BOLT,this.level());
		this.targets = (ArrayList) (new ArrayList());
		this.explosionTargets = (ArrayList) (new ArrayList());
		this.firstContactBlocks = new LinkedHashSet();
		this.hitPosition = Vec3.ZERO;
		this.lookVec = Vec3.ZERO;
		this.spazzingRandom = RandomSource.create();
		this.length = 0F;
		this.size = 1F;
		this.maxTravelDistance = 0F;
		this.travelSpeed = 12F;
		this.rotation = 0F;
		this.branches = 1;
		this.segments = 7;
		this.segmentLength = 0;
		this.angle = 0;
		this.targetTimeToReset = 20;
		this.startFadeTime = 20;
		this.fadeTime = -1;
		this.depth = 4;
		this.alwaysDamage = true;
		this.visualOnly = false;
		this.lightningMovement = true;
		this.hasToCheckForTarget = true;
		this.fadeTrail = false;
		this.canRetract = false;
		this.isRetracting = false;
		this.hasFinishedExtending = false;
		this.hasLengthChanged = false;
		this.mimicVanilla = false;
		this.fadeAtMaxLength = true;
		this.discardOnRetract = true;
		this.red = 255;
		this.green = 255;
		this.blue = 255;
		this.alpha = 255;
		this.explosionComponent = (Optional) (((java.util.Optional)(Optional.empty())));
		this.setMaxLife(-1);
		this.moveTo(var3,var4,var5,var6,var7);
		if ((var10) != (null)){
			this.explosionComponent = (Optional) (var10.getComponent(((AbilityComponentKey)(ModAbilityComponents.EXPLOSION.get()))));
			NuLightningEntity var14 = this;
			Consumer<? super ExplosionComponent> var15 = (l0) -> {
			l0.setKillOnBlockLimit(false);
			NuLightningEntity lv2_2 = this;
			ExplosionComponent lv2_3 = l0;
			final ExplosionComponent lv2_3_f1 = lv2_3;
			ExplosionComponent$ICheckLimitEvent lv2_4 = (l2_0, l2_1) -> {
				if (lv2_3_f1.hasReachedLimit()){
					this.setPassThroughBlocks(false);
				}
				return true;
			};
			l0.addLimitCheckEvent(100,lv2_4);
		};
			((java.util.Optional)(this.explosionComponent)).ifPresent((Consumer)(var15));
		}
		this.maxTravelDistance = var8;
		this.travelSpeed = var9;
		this.noCulling = true;
		this.noPhysics = true;
		this.setNoGravity(true);
		this.setPassThroughEntities();
		this.lookVec = this.getLookAngle().normalize();
		this.setLength(this.getReachableTravelDistance(Math.min(2F,this.maxTravelDistance)));
		this.setArmorPiercing(0.75F);
	}
	public void tick() {
		if (((this.getOwner()) != (null)) && (this.getOwner().isAlive())){
			if (this.getLightningMovement()){
				this.seed = this.random.nextLong();
			}
			if (!(this.level().isClientSide())){
				float var2 = 0.0F;
				if (!(this.isRetracting)){
					float var1 = Math.min((this.getLength()) + (this.travelSpeed),this.maxTravelDistance);
					var2 = this.getReachableTravelDistance(var1);
					int var3 = ((var2) < (var1)) ? (1) : (0);
					if ((((this.tickCount) % (20)) == (0)) && ((var2) == (this.previousLength))){
						this.lengthChecks = (this.lengthChecks) + (1);
						if ((this.lengthChecks) >= (2)){
							var2 = this.maxTravelDistance;
							this.hasLengthChanged = true;
						}
					}
					this.setLength(var2);
					if (((var3) != (0)) && (this.canRetract)){
						this.isRetracting = true;
					}else{
						if ((!(this.hasFinishedExtending)) && ((var2) >= (this.maxTravelDistance))){
							this.hasFinishedExtending = true;
							if (this.canRetract){
								this.isRetracting = true;
							}else{
								if (this.fadeAtMaxLength()){
									this.startFade(this.startFadeTime,this.startFadeTime);
									this.setMaxLife(this.startFadeTime);
								}
							}
						}
					}
					this.previousLength = var2;
				}else{
					this.setLength(Math.max((this.getLength()) - (this.travelSpeed),0F));
					if ((this.discardOnRetract()) && ((this.getLength()) <= (0F))){
						this.discard();
						return;
					}
				}
				if ((((this.tickCount) % (this.targetTimeToReset)) == (0)) && (this.hasToCheckForTarget)){
					this.targets.clear();
					this.explosionTargets.clear();
				}
				this.hitPosition = this.getBeamPosition(this.getLength());
				int var1_1 = 0;
				do{
					if (((float)(var1_1)) < (this.getLength())){
						double var2_1 = this.getPreciseSegmentX((float)(var1_1));
						double var3_1 = this.getPreciseSegmentY((float)(var1_1));
						double var4 = this.getPreciseSegmentZ((float)(var1_1));
						if (this.isVisualOnly()){
							var1_1++;
							continue;
						}else{
							AABB var5 = this.getCollisionBox(var2_1,var3_1,var4);
							if (((((this.fadeTime) < (0)) || (((double)(this.fadeTime)) > (((double)(this.startFadeTime)) * (0.5D)))) ? (1) : (0)) != (0)){
								TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),this.getOwner(),var5,this.getTargetCheck(),Entity.class).forEach((Consumer)(((Consumer<Entity>)(this::onEntityImpact))));
								var1_1++;
								continue;
							}else{
								var1_1++;
								continue;
							}
						}
					}else{
						break;
					}
				} while (true);
				if (this.hasLengthChanged){
					this.updateClient();
					this.hasLengthChanged = false;
				}
			}
			if ((this.fadeTime) > (0)){
				this.fadeTime = (this.fadeTime) - (1);
			}
			if (this.getMimicVanilla()){
				super.level().setSkyFlashTime(4);
			}
			super.tick();
			return;
		}else{
			this.discard();
			return;
		}
	}
	public void onEntityImpact(Entity var1) {
		if ((!(this.targets.contains(Integer.valueOf(var1.getId())))) && ((var1.getId()) != (this.getId()))){
			if (this.hasToCheckForTarget){
				this.targets.add(Integer.valueOf(var1.getId()));
			}
			this.triggerEntityHitEvents(new EntityHitResult(var1));
			if (((this.getDamage()) > (0F)) && ((var1.isAlive()) && (var1 instanceof LivingEntity))){
				LivingEntity var2 = ((LivingEntity)(var1));
				if (this.getMimicVanilla()){
					var2.igniteForSeconds((float)((2) + ((int)(((float)((this.getRemainingFireTicks()) / (20))) + ((this.getDamage()) / (5F))))));
					var2.thunderHit(((ServerLevel)(this.getCommandSenderWorld())),this.bolt);
				}
				if (this.alwaysDamage){
					var1.invulnerableTime = 0;
				}
				var1.hurt(this.getDamageSource(),this.getDamage());
				if (!(this.canPassThroughEntities())){
					this.setEarlyLength(var1.position());
					if (!(this.canRetract)){
						this.setVisualOnly();
						this.setPassThroughBlocks(false);
					}
					this.hasLengthChanged = true;
				}else{
					return;
				}
			}
			return;
		}else{
			return;
		}
	}
	public void onBlockImpact(BlockPos var1) {
		Direction var2 = Direction.getNearest((float)(var1.getX()),(float)(var1.getY()),(float)(var1.getZ()));
		BlockHitResult var3 = new BlockHitResult(Vec3.atCenterOf((Vec3i)(var1)),var2,var1,false);
		BlockState var4 = this.level().getBlockState(var1);
		super.onHitBlock(var3);
		BlockState var5 = this.level().getBlockState(var1);
		if (((((!(var4.isAir())) && (((java.lang.Object)(var4)).equals((Object)(var5)))) ? (1) : (0)) != (0)) && ((this.getLength()) != (this.maxTravelDistance))){
			this.setEarlyLength(Vec3.atCenterOf((Vec3i)(var1)));
		}
	}
	private float getReachableTravelDistance(float var1) {
		Vec3 var2 = this.getBeamOrigin();
		Vec3 var3 = this.getBeamPosition(var1);
		HitResult var4 = this.getBlockDetectionHit(var2,var3);
		if (var4 instanceof BlockHitResult){
			BlockHitResult var5 = ((BlockHitResult)(var4));
			BlockPos var6 = var5.getBlockPos();
			BlockState var7 = this.level().getBlockState(var6);
			if ((((((var7.getBlock()) == (Blocks.BARRIER)) && (!(NuWorld.isChallengeDimension(this.level())))) ? (1) : (0)) == (0)) && (((var7.getBlock()) != (ModBlocks.BARRIER.get())) && (!(var7.is(ModTags$Blocks.KAIROSEKI))))){
				if ((!(this.isVisualOnly())) && (!(var7.isAir()))){
					super.onHitBlock(var5);
				}
				HitResult var8 = this.getBlockDetectionHit(var2,var3);
				if (var8 instanceof BlockHitResult){
					BlockHitResult var9 = ((BlockHitResult)(var8));
					if (this.canPassThroughBlocks()){
						return var1;
					}else{
						Vec3 var10 = var3.subtract(var2).normalize();
						return Mth.clamp((float)(var9.getLocation().subtract(var2).dot(var10)),0F,var1);
					}
				}else{
					return var1;
				}
			}else{
				this.discard();
				return this.getLength();
			}
		}else{
			return var1;
		}
	}
	protected void setEarlyLength(Vec3 var1) {
		if (this.canPassThroughBlocks()){
			return;
		}else{
			this.maxTravelDistance = this.getLength();
			this.setLength(this.maxTravelDistance);
			this.hitPosition = var1;
			return;
		}
	}
	public HitResult getBlockDetectionHit(Vec3 var1, Vec3 var2) {
		return this.level().clip(new ClipContext(var1,var2,ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,(Entity)(this)));
	}
	public TargetPredicate getTargetCheck() {
		return this.targetsCheck;
	}
	protected Vec3 getBeamOrigin() {
		return super.getEyePosition();
	}
	protected Vec3 getBeamPosition(float var1) {
		return this.getBeamOrigin().add(this.lookVec.scale((double)(var1)));
	}
	public double getPreciseCurrentX() {
		return this.getPreciseSegmentX(this.length);
	}
	public double getPreciseCurrentY() {
		return this.getPreciseSegmentY(this.length);
	}
	public double getPreciseCurrentZ() {
		return this.getPreciseSegmentZ(this.length);
	}
	public double getCurrentX() {
		return this.getSegmentX(Math.round(this.length));
	}
	public double getCurrentY() {
		return this.getSegmentY(Math.round(this.length));
	}
	public double getCurrentZ() {
		return this.getSegmentZ(Math.round(this.length));
	}
	public double getPreciseSegmentX(float var1) {
		return this.getBeamPosition(var1).x;
	}
	public double getPreciseSegmentY(float var1) {
		return this.getBeamPosition(var1).y;
	}
	public double getPreciseSegmentZ(float var1) {
		return this.getBeamPosition(var1).z;
	}
	public double getSegmentX(int var1) {
		return Math.floor(this.getPreciseSegmentX((float)(var1)));
	}
	public double getSegmentY(int var1) {
		return Math.floor(this.getPreciseSegmentY((float)(var1)));
	}
	public double getSegmentZ(int var1) {
		return Math.floor(this.getPreciseSegmentZ((float)(var1)));
	}
	public AABB getCollisionBox(double var1, double var2, double var3) {
		double var4 = (Math.max((double)(this.getSize()),0.1D)) * (7D);
		return new AABB((var1) - (var4),(var2) - (var4),(var3) - (var4),(var1) + (var4),(var2) + (var4),(var3) + (var4));
	}
	public boolean hasLineOfSight(Entity var1) {
		if ((var1.level()) != (this.level())){
			return false;
		}else{
			Vec3 var2 = this.hitPosition;
			Vec3 var3 = new Vec3(var1.getX(),var1.getEyeY(),var1.getZ());
			if ((var3.distanceTo(var2)) > (128D)){
				return false;
			}else{
				return (this.level().clip(new ClipContext(var2,var3,ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,(Entity)(this))).getType()) == (HitResult.Type.MISS);
			}
		}
	}
	public void updateRotation() {
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean shouldRenderAtSqrDistance(double var1) {
		double var2 = (512D) * (Entity.getViewScale());
		return (var1) < ((var2) * (var2));
	}
	public void setTargetTimeToReset(int var1) {
		this.targetTimeToReset = var1;
	}
	public void setOneTimeHit() {
		this.targetTimeToReset = 9999;
	}
	public void setLength(float var1) {
		this.length = var1;
		this.hasLengthChanged = true;
	}
	public float getLength() {
		return this.length;
	}
	public void setSize(float var1) {
		this.size = var1;
	}
	public float getSize() {
		return this.size;
	}
	public void setDepth(int var1) {
		this.depth = var1;
	}
	public int getDepth() {
		return this.depth;
	}
	public void setBranches(int var1) {
		this.branches = var1;
	}
	public int getBranches() {
		return this.branches;
	}
	public void setSegments(int var1) {
		this.segments = var1;
	}
	public int getSegments() {
		return this.segments;
	}
	public void setSegmentLength(int var1) {
		this.segmentLength = var1;
	}
	public int getSegmentLength() {
		return this.segmentLength;
	}
	public void setColor(Color var1) {
		this.setColor(var1.getRed(),var1.getGreen(),var1.getBlue(),var1.getAlpha());
	}
	public void setColor(Color var1, int var2) {
		this.setColor(var1.getRed(),var1.getGreen(),var1.getBlue(),var2);
	}
	public void setColor(int var1, int var2, int var3, int var4) {
		this.red = Mth.clamp(var1,0,255);
		this.green = Mth.clamp(var2,0,255);
		this.blue = Mth.clamp(var3,0,255);
		this.alpha = Mth.clamp(var4,0,255);
	}
	public int getRed() {
		return this.red;
	}
	public int getGreen() {
		return this.green;
	}
	public int getBlue() {
		return this.blue;
	}
	public int getAlpha() {
		return this.alpha;
	}
	public void setAngle(int var1) {
		this.angle = Math.round((float)(Mth.clamp(var1,0,180)));
	}
	public int getAngle() {
		return this.angle;
	}
	public void setLightningMimic(boolean var1) {
		this.mimicVanilla = var1;
		this.updateClient();
	}
	public boolean getMimicVanilla() {
		return this.mimicVanilla;
	}
	public void setVisualOnly() {
		this.visualOnly = true;
	}
	public boolean isVisualOnly() {
		return this.visualOnly;
	}
	public void setFadeTime(int var1) {
		this.startFadeTime = var1;
	}
	public void startFade(int var1, int var2) {
		this.startFadeTime = var2;
		this.fadeTime = var1;
	}
	public int getStartFadeTime() {
		return this.startFadeTime;
	}
	public int getFadeTime() {
		return this.fadeTime;
	}
	public boolean canRetract() {
		return this.canRetract;
	}
	public void setRetracting() {
		this.canRetract = true;
	}
	public boolean isRetracting() {
		return this.isRetracting;
	}
	public void setRetractingState(boolean var1) {
		this.isRetracting = var1;
	}
	public boolean discardOnRetract() {
		return this.discardOnRetract;
	}
	public void discardOnRetract(boolean var1) {
		this.discardOnRetract = var1;
	}
	public void setTravelSpeed(float var1) {
		this.travelSpeed = var1;
	}
	public boolean fadeAtMaxLength() {
		return this.fadeAtMaxLength;
	}
	public void fadeAtMaxLength(boolean var1) {
		this.fadeAtMaxLength = var1;
	}
	public float getMaxTravelDistance() {
		return this.maxTravelDistance;
	}
	public void setMaxTravelDistance(float var1) {
		this.maxTravelDistance = var1;
	}
	public void setLightningMovement(boolean var1) {
		this.lightningMovement = var1;
	}
	public boolean getLightningMovement() {
		return this.lightningMovement;
	}
	public void setBeamLookVector(Vec3 var1) {
		this.lookVec = var1;
	}
	public void setRotationSpeed(float var1) {
		this.rotation = var1;
	}
	public float getRotation() {
		return this.rotation;
	}
	public void setTrailFade() {
		this.fadeTrail = true;
	}
	public boolean isTrailFading() {
		return this.fadeTrail;
	}
	public boolean shouldAlwaysDamage() {
		return this.alwaysDamage;
	}
	public void setAlwaysDamage(boolean var1) {
		this.alwaysDamage = var1;
	}
	public void updateClient() {
		if ((!(this.level().isClientSide)) && ((this.getOwner()) != (null))){
			ModNetwork.sendToAllAround(new SUpdateLightningEntity(this),(Entity)(this.getOwner()));
		}
	}
	public RandomSource getRandom() {
		return this.spazzingRandom;
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		super.writeSpawnData(var1);
		var1.writeFloat(this.length);
		var1.writeFloat(this.size);
		var1.writeFloat(this.rotation);
		var1.writeFloat(this.maxTravelDistance);
		var1.writeFloat(this.getXRot());
		var1.writeFloat(this.getYRot());
		var1.writeInt(this.red);
		var1.writeInt(this.green);
		var1.writeInt(this.blue);
		var1.writeInt(this.alpha);
		var1.writeInt(this.branches);
		var1.writeInt(this.segments);
		var1.writeInt(this.angle);
		var1.writeInt(this.depth);
		var1.writeInt(this.segmentLength);
		var1.writeBoolean(this.visualOnly);
		var1.writeBoolean(this.lightningMovement);
		var1.writeBoolean(this.fadeTrail);
		var1.writeBoolean(this.alwaysDamage);
		var1.writeBoolean(this.mimicVanilla);
		var1.writeBoolean(this.canRetract);
		var1.writeBoolean(this.isRetracting);
		var1.writeBoolean(this.hasFinishedExtending);
		var1.writeBoolean(this.fadeAtMaxLength);
		var1.writeBoolean(this.discardOnRetract);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		super.readSpawnData(var1);
		this.length = var1.readFloat();
		this.size = var1.readFloat();
		this.rotation = var1.readFloat();
		this.maxTravelDistance = var1.readFloat();
		float var2 = var1.readFloat();
		this.setRot(var1.readFloat(),var2);
		this.red = var1.readInt();
		this.green = var1.readInt();
		this.blue = var1.readInt();
		this.alpha = var1.readInt();
		this.branches = var1.readInt();
		this.segments = var1.readInt();
		this.angle = var1.readInt();
		this.depth = var1.readInt();
		this.segmentLength = var1.readInt();
		this.visualOnly = var1.readBoolean();
		this.lightningMovement = var1.readBoolean();
		this.fadeTrail = var1.readBoolean();
		this.alwaysDamage = var1.readBoolean();
		this.mimicVanilla = var1.readBoolean();
		this.canRetract = var1.readBoolean();
		this.isRetracting = var1.readBoolean();
		this.hasFinishedExtending = var1.readBoolean();
		this.fadeAtMaxLength = var1.readBoolean();
		this.discardOnRetract = var1.readBoolean();
	}
}