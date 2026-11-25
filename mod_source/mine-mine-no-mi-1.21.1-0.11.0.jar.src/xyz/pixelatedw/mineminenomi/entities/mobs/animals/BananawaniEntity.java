package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
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
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
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
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.PersonalSpaceTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;

public class BananawaniEntity extends TamableAnimal {
	// Fields
	private static final EntityDataAccessor<Boolean> IS_SADDLED = SynchedEntityData.defineId(BananawaniEntity.class,EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SADDLE_COLOR = SynchedEntityData.defineId(BananawaniEntity.class,EntityDataSerializers.INT);
	private static final String SADDLED_TAG = "isSaddled";
	private static final String SADDLE_COLOR_TAG = "saddleColor";
	private static final double TAME_CHANCE = 0.25D;
	private static final Item[] SADDLES;
	private static final byte BITE_EVENT = 100;
	private long lastMountTime;
	private long lastBiteTime;
	private int clientBiteTime;

	public BananawaniEntity(EntityType<? extends BananawaniEntity> var1, Level var2) {
		super(var1,var2);
		this.getNavigation().setCanFloat(true);
		if (((var2) != (null)) && (!(var2.isClientSide))){
			this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1D);
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,40D);
	}
	protected void registerGoals() {
		this.goalSelector.addGoal(1,(Goal)(new FloatGoal((Mob)(this))));
		this.goalSelector.addGoal(2,(Goal)(new BreedGoal((Animal)(this),1D)));
		this.goalSelector.addGoal(3,(Goal)(new SitWhenOrderedToGoal((TamableAnimal)(this))));
		this.goalSelector.addGoal(3,(Goal)(new FollowParentGoal((Animal)(this),1.15D)));
		this.goalSelector.addGoal(4,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,false)));
		this.goalSelector.addGoal(5,(Goal)(new RandomStrollGoal((PathfinderMob)(this),0.8D)));
		this.goalSelector.addGoal(6,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
		this.goalSelector.addGoal(6,(Goal)(new LookAtPlayerGoal((Mob)(this),Chicken.class,8F)));
		this.goalSelector.addGoal(6,(Goal)(new RandomLookAroundGoal((Mob)(this))));
		this.targetSelector.addGoal(1,(Goal)(new OwnerHurtByTargetGoal((TamableAnimal)(this))));
		this.targetSelector.addGoal(1,(Goal)(new HurtByTargetGoal((PathfinderMob)(this),new Class[0])));
		this.targetSelector.addGoal(1,(Goal)(new OwnerHurtTargetGoal((TamableAnimal)(this))));
		this.targetSelector.addGoal(1,(Goal)(new PersonalSpaceTargetGoal((Mob)(this))));
		this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Chicken.class,true)));
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		super.defineSynchedData(var1);
		var1.define(IS_SADDLED,Boolean.valueOf(false));
		var1.define(SADDLE_COLOR,Integer.valueOf(DyeColor.RED.getId()));
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		var1.putBoolean("isSaddled",((Boolean)(this.entityData.get(IS_SADDLED))).booleanValue());
		var1.putInt("saddleColor",((Integer)(this.entityData.get(SADDLE_COLOR))).intValue());
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		this.entityData.set(IS_SADDLED,Boolean.valueOf(var1.getBoolean("isSaddled")));
		this.entityData.set(SADDLE_COLOR,Integer.valueOf(var1.getInt("saddleColor")));
	}
	protected void playStepSound(BlockPos var1, BlockState var2) {
		this.playSound(SoundEvents.PIG_STEP,0.15F,1.5F);
	}
	protected float getSoundVolume() {
		return 0.4F;
	}
	public float getVoicePitch() {
		return (this.isBaby()) ? ((1.5F) + ((this.random.nextFloat()) / (2F))) : ((1.25F) + ((this.random.nextFloat()) / (2F)));
	}
	public void tick() {
		super.tick();
		if ((this.isControlledByLocalInstance()) && (this.isInWater())){
			double var1 = (this.getY()) - (this.getFluidTypeHeight(Fluids.WATER.getFluidType()));
			double var2 = (((this.getY()) - (var1)) / ((double)(this.getBbHeight()))) - (0.35D);
			Vec3 var3 = this.getLookAngle().multiply(2D,1D,2D);
			BlockPos var4 = this.blockPosition().offset((int)(var3.x),0,(int)(var3.z));
			BlockState var5 = this.level().getBlockState(var4);
			if ((!(var5.isAir())) && (var5.getFluidState().isEmpty())){
				var2 = (var2) + (0.35D);
			}
			if ((var2) > (0D)){
				double var6 = this.getAttributeValue(NeoForgeMod.SWIM_SPEED);
				Vec3 var7 = this.getDeltaMovement();
				AbilityHelper.setDeltaMovement((Entity)(this),(var7.x) * (var6),((var7.y) + ((var2) * (0.061D))) * (0.75D),(var7.z) * (var6));
			}
			this.move(MoverType.SELF,this.getDeltaMovement());
		}
	}
	public InteractionResult mobInteract(Player var1, InteractionHand var2) {
		Level var3 = super.level();
		if ((var3) == (null)){
			return super.mobInteract(var1,var2);
		}else{
			if ((!(var3.isClientSide)) && ((var2) == (InteractionHand.MAIN_HAND))){
				ItemStack var4 = var1.getItemInHand(((var1.getUsedItemHand()) != (null)) ? (var1.getUsedItemHand()) : (InteractionHand.MAIN_HAND));
				Item var5 = var4.getItem();
				Objects.requireNonNull(var5);
				boolean var6 = Arrays.stream(SADDLES).anyMatch((Predicate)(((Predicate<Item>)(var5::equals))));
				int var7 = ((!(var4.isEmpty())) && (var4.getItem() instanceof DyeItem)) ? (1) : (0);
				if (!(this.isTame())){
					if ((!(var4.isEmpty())) && (this.isFood(var4))){
						this.usePlayerItem(var1,var2,var4);
						if (((this.random.nextDouble()) < (0.25D)) && (!(EventHooks.onAnimalTame((Animal)(this),var1)))){
							var3.broadcastEntityEvent((Entity)(this),(byte)(7));
							this.tame(var1);
						}
						return InteractionResult.SUCCESS;
					}
				}else{
					if ((var7) != (0)){
						DyeColor var8 = ((DyeItem)(var4.getItem())).getDyeColor();
						if ((var8) != (this.getSaddleColor())){
							this.setSaddleColor(var8);
							this.usePlayerItem(var1,var2,var4);
							return InteractionResult.SUCCESS;
						}
					}else{
						if ((this.isFood(var4)) && ((this.getHealth()) < (this.getMaxHealth()))){
							this.heal(4F);
							this.usePlayerItem(var1,var2,var4);
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
									this.lastMountTime = var3.getGameTime();
									return InteractionResult.SUCCESS;
								}
							}
						}
					}
				}
			}
			return super.mobInteract(var1,var2);
		}
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
				float var3 = (var2.xxa) * (0.1F);
				float var4 = var2.zza;
				if ((var4) <= (0F)){
					var4 = (var4) * (0.25F);
				}
				if (this.isControlledByLocalInstance()){
					this.setSpeed(((float)(this.getAttributeValue(Attributes.MOVEMENT_SPEED))) * (0.7F));
					super.travel(new Vec3((double)(var3),var1.y,(double)(var4)));
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
	public void aiStep() {
		super.aiStep();
		Level var1 = super.level();
		if ((var1) == (null)){
			return;
		}else{
			if ((this.clientBiteTime) > (0)){
				this.clientBiteTime = (this.clientBiteTime) - (1);
			}
			if ((!(var1.isClientSide)) && ((this.isTame()) && ((this.isVehicle()) && ((this.getPassengers().contains(this.getOwner())) && (((this.getOwner()) != (null)) && ((this.getOwner().swinging) && (((var1.getGameTime()) >= ((this.lastMountTime) + (20L))) && ((var1.getGameTime()) >= ((this.lastBiteTime) + (20L)))))))))){
				this.swing(InteractionHand.MAIN_HAND);
				Vec3 var2 = this.position().add(this.getOwner().getLookAngle().multiply(4D,1D,4D));
				Vec3 var3 = new Vec3(var2.x(),this.getY(),var2.z());
				List var4 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1),this.getOwner(),BlockPos.containing((Position)(var3)),1D,TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class);
				var4.remove(this);
				var4.remove(this.getOwner());
				this.lastBiteTime = var1.getGameTime();
				var1.broadcastEntityEvent((Entity)(this),(byte)(100));
				Iterator var5 = var4.iterator();
				do{
					if (var5.hasNext()){
						((Entity)(var5.next())).hurt(this.damageSources().mobAttack((LivingEntity)(this)),(float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
						continue;
					}else{
						break;
					}
				} while (true);
			}
			return;
		}
	}
	protected void checkInsideBlocks() {
		Level var1 = super.level();
		if ((var1) == (null)){
			return;
		}else{
			AABB var2 = this.getBoundingBox();
			BlockPos var3 = BlockPos.containing((var2.minX) + (0.001D),(var2.minY) + (0.001D),(var2.minZ) + (0.001D));
			BlockPos var4 = BlockPos.containing((var2.maxX) - (0.001D),(var2.maxY) - (0.001D),(var2.maxZ) - (0.001D));
			BlockPos.MutableBlockPos var5 = new BlockPos.MutableBlockPos();
			if (var1.hasChunksAt(var3,var4)){
				int var6 = var3.getX();
				LOOP_2:
				do{
					if ((var6) <= (var4.getX())){
						int var7 = var3.getY();
						LOOP_1:
						do{
							if ((var7) <= (var4.getY())){
								int var8 = var3.getZ();
								do{
									if ((var8) <= (var4.getZ())){
										var5.set(var6,var7,var8);
										if ((var1.getBlockState((BlockPos)(var5)).getBlock()) == (Blocks.LILY_PAD)){
											var1.destroyBlock((BlockPos)(var5),true,(Entity)(this));
										}
										var8++;
										continue;
									}else{
										break;
									}
								} while (true);
								var7++;
								continue LOOP_1;
							}else{
								break;
							}
						} while (true);
						var6++;
						continue LOOP_2;
					}else{
						break;
					}
				} while (true);
			}
			return;
		}
	}
	public void swing(InteractionHand var1, boolean var2) {
		if (((this.level()) != (null)) && ((this.getTarget()) != (null))){
			this.level().broadcastEntityEvent((Entity)(this),(byte)(100));
		}
		super.swing(var1,var2);
	}
	@Nullable()
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable() SpawnGroupData var4) {
		if ((var4) == (null)){
			var4 = new AgeableMob.AgeableMobGroupData(0.2F);
		}
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.generateRandomHealth());
		this.getAttribute(Attributes.ARMOR).setBaseValue(this.generateRandomArmor());
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.generateRandomAttack());
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateRandomSpeed());
		this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(this.generateRandomSwimSpeed());
		this.setHealth((float)(this.getAttribute(Attributes.MAX_HEALTH).getValue()));
		return super.finalizeSpawn(var1,var2,var3,var4);
	}
	public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
		BananawaniEntity var3 = ((BananawaniEntity)(((EntityType)(ModMobs.BANANAWANI.get())).create((Level)(var1))));
		this.setOffspringAttributes(var2,var3);
		return var3;
	}
	protected void setOffspringAttributes(AgeableMob var1, BananawaniEntity var2) {
		double var3 = ((this.getAttributeBaseValue(Attributes.MAX_HEALTH)) + (var1.getAttributeBaseValue(Attributes.MAX_HEALTH))) + (this.generateRandomHealth());
		var2.getAttribute(Attributes.MAX_HEALTH).setBaseValue((var3) / (3D));
		double var4 = ((this.getAttributeBaseValue(Attributes.ARMOR)) + (var1.getAttributeBaseValue(Attributes.ARMOR))) + (this.generateRandomArmor());
		var2.getAttribute(Attributes.ARMOR).setBaseValue((var4) / (3D));
		double var5 = ((this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED)) + (var1.getAttributeBaseValue(Attributes.MOVEMENT_SPEED))) + (this.generateRandomSpeed());
		var2.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((var5) / (3D));
		double var6 = ((this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE)) + (var1.getAttributeBaseValue(Attributes.ATTACK_DAMAGE))) + (this.generateRandomAttack());
		var2.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((var6) / (3D));
		double var7 = ((this.getAttributeBaseValue(NeoForgeMod.SWIM_SPEED)) + (var1.getAttributeBaseValue(NeoForgeMod.SWIM_SPEED))) + (this.generateRandomSwimSpeed());
		var2.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue((var7) / (3D));
	}
	protected double generateRandomArmor() {
		return (2D) + ((this.random.nextDouble()) * (2D));
	}
	protected double generateRandomAttack() {
		return (7D) + ((this.random.nextDouble()) * (2D));
	}
	protected double generateRandomHealth() {
		return (50D) + ((this.random.nextDouble()) * (10D));
	}
	protected double generateRandomSpeed() {
		return (0.25D) + ((this.random.nextDouble()) * (0.07D));
	}
	protected double generateRandomSwimSpeed() {
		return (1D) + ((this.random.nextDouble()) * (0.15D));
	}
	public boolean isFood(ItemStack var1) {
		return var1.is(ModTags$Items.BANANAWANI_FOOD);
	}
	protected boolean isImmobile() {
		return (super.isImmobile()) && ((this.isVehicle()) && (this.isSaddled()));
	}
	public boolean isPushedByFluid() {
		return false;
	}
	@OnlyIn(value=Dist.CLIENT)
	public void handleEntityEvent(byte var1) {
		switch (var1){
		case 100:
			this.clientBiteTime = 20;
		default:
			super.handleEntityEvent(var1);
			return;
		}
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
		return new Vec3(-0.2D,1.1D,0D);
	}
	public void setSaddled(boolean var1) {
		this.entityData.set(IS_SADDLED,Boolean.valueOf(var1));
	}
	public boolean isSaddled() {
		return ((Boolean)(this.entityData.get(IS_SADDLED))).booleanValue();
	}
	public DyeColor getSaddleColor() {
		return DyeColor.byId(((Integer)(this.entityData.get(SADDLE_COLOR))).intValue());
	}
	public void setSaddleColor(DyeColor var1) {
		this.entityData.set(SADDLE_COLOR,Integer.valueOf(var1.getId()));
	}
	@OnlyIn(value=Dist.CLIENT)
	public float getBiteAnimationProgress(float var1) {
		return (((float)((20) - (this.clientBiteTime))) + (var1)) / (20F);
	}
	static  {
		Item[] var0 = new Item[]{Items.SADDLE};
		SADDLES = var0;
	}
}