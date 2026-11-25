package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.EventHooks;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;

public class BigDuckEntity extends TamableAnimal implements PlayerRideableJumping, Saddleable {
	// Fields
	private static final EntityDataAccessor<Boolean> IS_SADDLED = SynchedEntityData.defineId(BigDuckEntity.class,EntityDataSerializers.BOOLEAN);
	private static final String SADDLED_TAG = "isSaddled";
	private static final double TAME_CHANCE = 0.2D;
	private static final Item[] SADDLES;
	private static final int QUACK_ANIM_CYCLE = 20;
	private static final byte QUACK_EVENT = 100;
	private float playerJumpPendingScale;
	private boolean isJumping;
	private double floating;
	private int clientQuackTick;

	public BigDuckEntity(EntityType<? extends BigDuckEntity> var1, Level var2) {
		super(var1,var2);
		this.clientQuackTick = 0;
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(ModAttributes.FLOATING_TIME).add(Attributes.JUMP_STRENGTH).add(Attributes.ATTACK_DAMAGE,1D).add(Attributes.FOLLOW_RANGE,40D).add(Attributes.MOVEMENT_SPEED,0.27D).add(Attributes.MAX_HEALTH,25D).add(Attributes.STEP_HEIGHT,1D);
	}
	protected void registerGoals() {
		this.goalSelector.addGoal(0,(Goal)(new JumpOutOfHoleGoal((Mob)(this))));
		this.goalSelector.addGoal(1,(Goal)(new FloatGoal((Mob)(this))));
		this.goalSelector.addGoal(2,(Goal)(new BreedGoal((Animal)(this),1D)));
		ItemLike[] var1 = new ItemLike[]{Items.SWEET_BERRIES};
		this.goalSelector.addGoal(3,(Goal)(new TemptGoal((PathfinderMob)(this),1.2D,(Predicate)(Ingredient.of(var1)),false)));
		this.goalSelector.addGoal(3,(Goal)(new SitWhenOrderedToGoal((TamableAnimal)(this))));
		this.goalSelector.addGoal(3,(Goal)(new FollowParentGoal((Animal)(this),1.15D)));
		this.goalSelector.addGoal(4,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,false)));
		this.goalSelector.addGoal(5,(Goal)(new RandomStrollGoal((PathfinderMob)(this),0.6D)));
		this.goalSelector.addGoal(6,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
		this.goalSelector.addGoal(6,(Goal)(new RandomLookAroundGoal((Mob)(this))));
		this.targetSelector.addGoal(1,(Goal)(new OwnerHurtByTargetGoal((TamableAnimal)(this))));
		this.targetSelector.addGoal(2,(Goal)(new OwnerHurtTargetGoal((TamableAnimal)(this))));
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		super.defineSynchedData(var1);
		var1.define(IS_SADDLED,Boolean.valueOf(false));
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		var1.putBoolean("isSaddled",((Boolean)(this.entityData.get(IS_SADDLED))).booleanValue());
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		this.entityData.set(IS_SADDLED,Boolean.valueOf(var1.getBoolean("isSaddled")));
	}
	protected SoundEvent getAmbientSound() {
		return ((SoundEvent)(ModSounds.BIG_DUCK_QUACK.get()));
	}
	protected SoundEvent getHurtSound(DamageSource var1) {
		return ((SoundEvent)(ModSounds.BIG_DUCK_HURT.get()));
	}
	protected SoundEvent getDeathSound() {
		return ((SoundEvent)(ModSounds.BIG_DUCK_HURT.get()));
	}
	protected void playStepSound(BlockPos var1, BlockState var2) {
		this.playSound(SoundEvents.CHICKEN_STEP,0.2F,0.5F);
	}
	protected float getSoundVolume() {
		return 0.8F;
	}
	public float getVoicePitch() {
		return (this.isBaby()) ? ((1.5F) + ((this.random.nextFloat()) / (3F))) : ((1F) + ((this.random.nextFloat()) / (3F)));
	}
	public void playAmbientSound() {
		this.level().broadcastEntityEvent((Entity)(this),(byte)(100));
		super.playAmbientSound();
	}
	public void aiStep() {
		super.aiStep();
		if (((this.level()) != (null)) && ((this.level().isClientSide) && ((this.clientQuackTick) > (0)))){
			this.clientQuackTick = (this.clientQuackTick) - (1);
		}
		Vec3 var1 = this.getDeltaMovement();
		if ((!(this.onGround())) && ((this.floating) > (0D))){
			if ((var1.y) < (0D)){
				AbilityHelper.setDeltaMovement((Entity)(this),var1.multiply(1D,0.6D,1D));
			}
			this.fallDistance = 0F;
			this.floating = (this.floating) - (1D);
		}else{
			if ((this.onGround()) && (!(this.isJumping()))){
				this.floating = this.getAttributeValue(ModAttributes.FLOATING_TIME);
			}else{
				return;
			}
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public void handleEntityEvent(byte var1) {
		switch (var1){
		case 100:
			this.clientQuackTick = 20;
		default:
			super.handleEntityEvent(var1);
			return;
		}
	}
	public boolean causeFallDamage(float var1, float var2, DamageSource var3) {
		int var4 = (((var2) > (0F)) && ((this.floating) <= (0D))) ? (1) : (0);
		this.floating = this.getAttributeValue(ModAttributes.FLOATING_TIME);
		if ((var4) != (0)){
			return super.causeFallDamage(var1,var2,var3);
		}else{
			return false;
		}
	}
	protected void actuallyHurt(DamageSource var1, float var2) {
		this.level().broadcastEntityEvent((Entity)(this),(byte)(100));
		super.actuallyHurt(var1,var2);
	}
	public InteractionResult mobInteract(Player var1, InteractionHand var2) {
		Level var3 = var1.level();
		if (((var3) != (null)) && ((!(var3.isClientSide)) && ((var2) == (InteractionHand.MAIN_HAND)))){
			ItemStack var4 = var1.getItemInHand(((var1.getUsedItemHand()) != (null)) ? (var1.getUsedItemHand()) : (InteractionHand.MAIN_HAND));
			Item var5 = var4.getItem();
			Objects.requireNonNull(var5);
			boolean var6 = Arrays.stream(SADDLES).anyMatch((Predicate)(((Predicate<Item>)(var5::equals))));
			if (!(this.isTame())){
				if ((!(var4.isEmpty())) && (this.isFood(var4))){
					this.usePlayerItem(var1,var2,var4);
					if (((this.random.nextDouble()) < (0.2D)) && (!(EventHooks.onAnimalTame((Animal)(this),var1)))){
						var3.broadcastEntityEvent((Entity)(this),(byte)(7));
						this.tame(var1);
					}
					return InteractionResult.SUCCESS;
				}
			}else{
				if ((this.isFood(var4)) && ((this.getHealth()) < (this.getMaxHealth()))){
					this.usePlayerItem(var1,var2,var4);
					this.heal(4F);
					var3.broadcastEntityEvent((Entity)(this),(byte)(7));
					return InteractionResult.SUCCESS;
				}else{
					if ((var6) && ((!(this.isSaddled())) && (!(this.isBaby())))){
						this.setSaddled(true);
						var3.playSound(((Player)(null)),(Entity)(this),SoundEvents.HORSE_SADDLE,SoundSource.PLAYERS,0.5F,1F);
						this.usePlayerItem(var1,var2,var4);
						return InteractionResult.SUCCESS;
					}else{
						if (((this.isFood(var4)) && (!((this.isFood(var4)) && (!(this.canFallInLove()))))) || (!(((var1) == (this.getOwner())) && (!(this.isBaby()))))){
							return null;

						}else{
							var1.startRiding((Entity)(this));
							return InteractionResult.SUCCESS;
						}
					}
				}
			}
			return super.mobInteract(var1,var2);
		}else{
			return super.mobInteract(var1,var2);
		}
	}
	@Nullable()
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable() SpawnGroupData var4) {
		if ((var4) == (null)){
			var4 = new AgeableMob.AgeableMobGroupData(0.2F);
		}
		this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.generateRandomJumpStrength());
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateRandomSpeed());
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.generateRandomHealth());
		double var5 = this.generateRandomFloatingTime();
		this.getAttribute(ModAttributes.FLOATING_TIME).setBaseValue(var5);
		this.floating = var5;
		this.setHealth((float)(this.getAttribute(Attributes.MAX_HEALTH).getValue()));
		return super.finalizeSpawn(var1,var2,var3,var4);
	}
	public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
		BigDuckEntity var3 = ((BigDuckEntity)(((EntityType)(ModMobs.BIG_DUCK.get())).create((Level)(var1))));
		this.setOffspringAttributes(var2,var3);
		return var3;
	}
	protected void setOffspringAttributes(AgeableMob var1, BigDuckEntity var2) {
		double var3 = ((this.getAttributeBaseValue(Attributes.MAX_HEALTH)) + (var1.getAttributeBaseValue(Attributes.MAX_HEALTH))) + (this.generateRandomHealth());
		var2.getAttribute(Attributes.MAX_HEALTH).setBaseValue((var3) / (3D));
		double var4 = ((this.getAttributeBaseValue(Attributes.JUMP_STRENGTH)) + (var1.getAttributeBaseValue(Attributes.JUMP_STRENGTH))) + (this.generateRandomJumpStrength());
		var2.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue((var4) / (3D));
		double var5 = ((this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED)) + (var1.getAttributeBaseValue(Attributes.MOVEMENT_SPEED))) + (this.generateRandomSpeed());
		var2.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((var5) / (3D));
		double var6 = ((this.getAttributeBaseValue(ModAttributes.FLOATING_TIME)) + (var1.getAttributeBaseValue(ModAttributes.FLOATING_TIME))) + (this.generateRandomFloatingTime());
		var2.getAttribute(ModAttributes.FLOATING_TIME).setBaseValue((var6) / (3D));
	}
	protected double generateRandomJumpStrength() {
		return (0.6D) + ((this.random.nextDouble()) * (0.4D));
	}
	protected double generateRandomSpeed() {
		return (0.28D) + ((this.random.nextDouble()) * (0.08D));
	}
	protected double generateRandomHealth() {
		return (20D) + ((this.random.nextDouble()) * (10D));
	}
	protected double generateRandomFloatingTime() {
		return (50D) + (((WyHelper.randomDouble(this.random)) * (0.5D)) * (30D));
	}
	public void travel(Vec3 var1) {
		if (this.isAlive()){
			if ((this.isVehicle()) && (this.isSaddled())){
				LivingEntity var2 = this.getControllingPassenger();
				this.setYRot(var2.getYRot());
				this.yRotO = this.getYRot();
				this.setXRot((var2.getXRot()) * (0.5F));
				this.setRot(this.getYRot(),this.getXRot());
				this.yBodyRot = this.getYRot();
				this.yHeadRot = this.yBodyRot;
				float var3 = (var2.xxa) * (0.5F);
				float var4 = var2.zza;
				if ((var4) <= (0F)){
					var4 = (var4) * (0.25F);
				}
				if (((this.playerJumpPendingScale) > (0F)) && ((!(this.isJumping())) && (this.onGround()))){
					double var5 = ((this.getAttributeValue(Attributes.JUMP_STRENGTH)) * ((double)(this.playerJumpPendingScale))) * ((double)(this.getBlockJumpFactor()));
					double var6 = 0.0;
					if (this.hasEffect(MobEffects.JUMP)){
						var6 = (var5) + ((double)(((float)((this.getEffect(MobEffects.JUMP).getAmplifier()) + (1))) * (0.1F)));
					}else{
						var6 = var5;
					}
					Vec3 var7 = this.getDeltaMovement();
					AbilityHelper.setDeltaMovement((Entity)(this),var7.x,var6,var7.z);
					this.setIsJumping(true);
					this.hasImpulse = true;
					CommonHooks.onLivingJump((LivingEntity)(this));
					if ((var4) > (0F)){
						float var8 = Mth.sin((this.getYRot()) * (0.017453292F));
						float var9 = Mth.cos((this.getYRot()) * (0.017453292F));
						AbilityHelper.setDeltaMovement((Entity)(this),this.getDeltaMovement().add((double)(((-0.4F) * (var8)) * (this.playerJumpPendingScale)),0D,(double)(((0.4F) * (var9)) * (this.playerJumpPendingScale))));
					}
					this.playerJumpPendingScale = 0F;
				}
				if (this.isControlledByLocalInstance()){
					this.setSpeed((float)(this.getAttributeValue(Attributes.MOVEMENT_SPEED)));
					super.travel(new Vec3((double)(var3),var1.y,(double)(var4)));
				}else{
					if (var2 instanceof Player){
						AbilityHelper.setDeltaMovement((Entity)(this),Vec3.ZERO);
					}
				}
				if (this.onGround()){
					this.playerJumpPendingScale = 0F;
					this.setIsJumping(false);
				}
				this.calculateEntityAnimation(false);
			}else{
				super.travel(var1);
			}
		}
	}
	public boolean isFood(ItemStack var1) {
		return var1.is(ModTags$Items.BIG_DUCK_FOOD);
	}
	protected boolean isImmobile() {
		return (super.isImmobile()) && ((this.isVehicle()) && (this.isSaddled()));
	}
	@Nullable()
	public LivingEntity getControllingPassenger() {
		if (this.isSaddled()){
			Entity var1 = this.getFirstPassenger();
			if (var1 instanceof LivingEntity){
				return ((LivingEntity)(var1));
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	public void positionRider(Entity var1, Entity.MoveFunction var2) {
		if (this.hasPassenger(var1)){
			Vec3 var3 = this.getPassengerRidingPosition(var1).yRot(((-(this.getYRot())) * (0.017453292F)) - (1.5707964F));
			var1.setPos((this.getX()) + (var3.x),(this.getY()) + (var3.y()),(this.getZ()) + (var3.z));
		}
	}
	public Vec3 getPassengerRidingPosition(Entity var1) {
		return new Vec3(-0.4D,0.85D,0D);
	}
	public void setSaddled(boolean var1) {
		this.entityData.set(IS_SADDLED,Boolean.valueOf(var1));
	}
	public boolean isSaddled() {
		return ((Boolean)(this.entityData.get(IS_SADDLED))).booleanValue();
	}
	public void onPlayerJump(int var1) {
		if (this.isSaddled()){
			if ((var1) < (0)){
				var1 = 0;
			}
			if ((var1) >= (90)){
				this.playerJumpPendingScale = 1F;
			}else{
				this.playerJumpPendingScale = (0.4F) + (((0.4F) * ((float)(var1))) / (90F));
			}
		}
	}
	public void setIsJumping(boolean var1) {
		this.isJumping = var1;
	}
	public boolean isJumping() {
		return this.isJumping;
	}
	public boolean canJump() {
		return this.isSaddled();
	}
	public void handleStartJump(int var1) {
	}
	public void handleStopJump() {
	}
	@OnlyIn(value=Dist.CLIENT)
	public float getQuackAnimationProgress(float var1) {
		return (((float)((20) - (this.clientQuackTick))) + (var1)) / (20F);
	}
	public boolean isSaddleable() {
		return (this.isAlive()) && ((!(this.isBaby())) && (this.isTame()));
	}
	public void equipSaddle(ItemStack var1, SoundSource var2) {
	}
	static  {
		Item[] var0 = new Item[]{Items.SADDLE};
		SADDLES = var0;
	}
}