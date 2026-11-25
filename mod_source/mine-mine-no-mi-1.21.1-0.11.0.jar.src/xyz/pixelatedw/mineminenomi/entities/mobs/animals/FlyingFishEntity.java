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
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.FishSwimMoveController;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.LandFishLookController;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.AvoidBlockGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.AvoidCoatedBoatGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.EatNearbyFishGoal;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;

public class FlyingFishEntity extends TamableAnimal {
	// Fields
	private static final EntityDataAccessor<Boolean> IS_SADDLED = SynchedEntityData.defineId(FlyingFishEntity.class,EntityDataSerializers.BOOLEAN);
	private static final String SADDLED_TAG = "isSaddled";
	private static final double TAME_CHANCE = 0.25D;
	private static final Item[] SADDLES;

	public FlyingFishEntity(EntityType<? extends FlyingFishEntity> var1, Level var2) {
		super(var1,var2);
		this.moveControl = new FishSwimMoveController(this);
		this.lookControl = new LandFishLookController(this,10);
		this.setPathfindingMalus(PathType.WATER,0F);
		this.getNavigation().setCanFloat(true);
	}
	protected void registerGoals() {
		this.goalSelector.addGoal(0,(Goal)(new TryFindWaterGoal((PathfinderMob)(this))));
		this.goalSelector.addGoal(0,(Goal)(new AvoidBlockGoal((PathfinderMob)(this),ModTags$Blocks.KAIROSEKI)));
		this.goalSelector.addGoal(0,(Goal)(new AvoidCoatedBoatGoal((PathfinderMob)(this))));
		this.goalSelector.addGoal(0,(Goal)(new EatNearbyFishGoal((Mob)(this))));
		this.goalSelector.addGoal(1,(Goal)(new RandomSwimmingGoal((PathfinderMob)(this),1D,10)));
		this.goalSelector.addGoal(1,(Goal)(new RandomLookAroundGoal((Mob)(this))));
		this.goalSelector.addGoal(2,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,12F)));
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		super.defineSynchedData(var1);
		var1.define(IS_SADDLED,Boolean.valueOf(false));
	}
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED,1.2999999523162842D).add(Attributes.FOLLOW_RANGE,55D).add(Attributes.ATTACK_DAMAGE,0D).add(Attributes.MAX_HEALTH,20D);
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
		return new Vec3(0.5D,0.8D,0D);
	}
	public InteractionResult mobInteract(Player var1, InteractionHand var2) {
		if ((!(var1.level().isClientSide)) && ((var2) == (InteractionHand.MAIN_HAND))){
			ItemStack var3 = var1.getItemInHand(((var1.getUsedItemHand()) != (null)) ? (var1.getUsedItemHand()) : (InteractionHand.MAIN_HAND));
			Item var4 = var3.getItem();
			Objects.requireNonNull(var4);
			boolean var5 = Arrays.stream(SADDLES).anyMatch((Predicate)(((Predicate<Item>)(var4::equals))));
			if (!(this.isTame())){
				if ((!(var3.isEmpty())) && (this.isFood(var3))){
					this.usePlayerItem(var1,var2,var3);
					if (((this.random.nextDouble()) < (0.25D)) && (!(EventHooks.onAnimalTame((Animal)(this),var1)))){
						this.level().broadcastEntityEvent((Entity)(this),(byte)(7));
						this.tame(var1);
					}
					return InteractionResult.SUCCESS;
				}
			}else{
				if ((this.isFood(var3)) && ((this.getHealth()) < (this.getMaxHealth()))){
					this.heal(4F);
					this.usePlayerItem(var1,var2,var3);
					this.level().broadcastEntityEvent((Entity)(this),(byte)(7));
					return InteractionResult.SUCCESS;
				}else{
					if ((var5) && ((!(this.isSaddled())) && (!(this.isBaby())))){
						this.setSaddled(true);
						this.level().playSound(((Player)(null)),(Entity)(this),SoundEvents.HORSE_SADDLE,SoundSource.PLAYERS,0.5F,1F);
						this.usePlayerItem(var1,var2,var3);
						return InteractionResult.SUCCESS;
					}else{
						if (((this.isSaddled()) && (!((this.isFood(var3)) && (!((this.isFood(var3)) && (!(this.canFallInLove()))))))) && (((var1) == (this.getOwner())) && (!(this.isBaby())))){
							var1.startRiding((Entity)(this));
							return InteractionResult.SUCCESS;
						}
					}
				}
			}
		}
		return super.mobInteract(var1,var2);
	}
	public boolean isFood(ItemStack var1) {
		return var1.is(ModTags$Items.FLYING_FISH_FOOD);
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
				if (this.isControlledByLocalInstance()){
					this.setSpeed(((float)(this.getAttributeValue(Attributes.MOVEMENT_SPEED))) * (0.7F));
					if (((!((var2.zza) > (0F))) ? (1) : (0)) == (0)){
						float var3 = 0.5F;
						Vec3 var4 = var2.getLookAngle();
						double var5 = (var4.x) * ((double)(var3));
						double var6 = (var4.y) * ((double)(var3));
						AbilityHelper.setDeltaMovement((Entity)(this),var5,var6,(var4.z) * ((double)(var3)));
					}else{
						double var3_1 = 0D;
						if (!(this.isUnderWater())){
							double var4_1 = (this.getDeltaMovement().y) * (0.99D);
							var3_1 = Math.max(var4_1,-1D);
						}
						AbilityHelper.setDeltaMovement((Entity)(this),this.getDeltaMovement().x,var3_1,this.getDeltaMovement().z);
					}
					super.travel(this.getDeltaMovement());
				}else{
					if (var2 instanceof Player){
						AbilityHelper.setDeltaMovement((Entity)(this),Vec3.ZERO);
					}
				}
				this.calculateEntityAnimation(false);
			}else{
				super.travel(var1);
			}
		}
	}
	public int getMaxHeadXRot() {
		return 1;
	}
	public int getMaxHeadYRot() {
		return 1;
	}
	public boolean checkSpawnObstruction(LevelReader var1) {
		return var1.isUnobstructed((Entity)(this));
	}
	protected void handleAirSupply(int var1) {
		if ((this.isAlive()) && (!(this.isInWaterOrBubble()))){
			this.setAirSupply((var1) - (1));
			if ((this.getAirSupply()) == (-20)){
				this.setAirSupply(0);
				this.hurt(this.damageSources().drown(),2F);
			}else{
				return;
			}
		}else{
			this.setAirSupply(300);
		}
	}
	public void baseTick() {
		int var1 = this.getAirSupply();
		super.baseTick();
		this.handleAirSupply(var1);
	}
	public boolean isPushedByFluid() {
		return false;
	}
	public boolean canBeLeashed() {
		return true;
	}
	public boolean removeWhenFarAway(double var1) {
		return (!(this.isTame())) && ((var1) > (1024D));
	}
	protected PathNavigation createNavigation(Level var1) {
		return new WaterBoundPathNavigation((Mob)(this),var1);
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		var1.putBoolean("isSaddled",((Boolean)(this.entityData.get(IS_SADDLED))).booleanValue());
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		this.entityData.set(IS_SADDLED,Boolean.valueOf(var1.getBoolean("isSaddled")));
	}
	public void setSaddled(boolean var1) {
		this.entityData.set(IS_SADDLED,Boolean.valueOf(var1));
	}
	public boolean isSaddled() {
		return ((Boolean)(this.entityData.get(IS_SADDLED))).booleanValue();
	}
	public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
		return null;
	}
	public static boolean checkSpawnRules(EntityType<FlyingFishEntity> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, RandomSource var4) {
		return var1.getBlockState(var3.below()).getFluidState().is(FluidTags.WATER);
	}
	static  {
		Item[] var0 = new Item[]{Items.SADDLE};
		SADDLES = var0;
	}
}