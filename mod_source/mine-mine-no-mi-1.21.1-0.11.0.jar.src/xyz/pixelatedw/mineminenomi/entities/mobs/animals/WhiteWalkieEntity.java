package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Position;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
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
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.event.EventHooks;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.GoalMemories;
import xyz.pixelatedw.mineminenomi.api.entities.IGoalMemoriesEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.containers.WhiteWalkieStorageContainer;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.whitewalkie.WhiteWalkieSleepGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class WhiteWalkieEntity extends TamableAnimal implements ContainerListener, HasCustomInventoryScreen, IGoalMemoriesEntity {
	// Fields
	private static final EntityDataAccessor<Byte> FLAGS = SynchedEntityData.defineId(WhiteWalkieEntity.class,EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Integer> CHESTS = SynchedEntityData.defineId(WhiteWalkieEntity.class,EntityDataSerializers.INT);
	private static final double TAME_CHANCE = 0.35D;
	private static final Item[] SADDLES;
	private static final byte BITE_EVENT = 100;
	private static final byte SHAKE_EVENT = 101;
	private final GoalMemories goalMemories;
	private SimpleContainer inventory;
	private int currentInvPage;
	private long lastMountTime;
	private long lastBiteTime;
	private long lastShakeTime;
	private int clientBiteTime;
	private int clientShakeTime;

	public WhiteWalkieEntity(EntityType<? extends WhiteWalkieEntity> var1, Level var2) {
		super(var1,var2);
		this.goalMemories = new GoalMemories();
		this.createInventory();
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,40D).add(Attributes.STEP_HEIGHT,1D);
	}
	protected void registerGoals() {
		this.goalSelector.addGoal(0,(Goal)(new FloatGoal((Mob)(this))));
		this.goalSelector.addGoal(0,(Goal)(new WhiteWalkieSleepGoal(this)));
		this.goalSelector.addGoal(2,(Goal)(new BreedGoal((Animal)(this),1D)));
		this.goalSelector.addGoal(3,(Goal)(new SitWhenOrderedToGoal((TamableAnimal)(this))));
		this.goalSelector.addGoal(3,(Goal)(new FollowParentGoal((Animal)(this),1.15D)));
		this.goalSelector.addGoal(4,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,false)));
		this.goalSelector.addGoal(5,(Goal)(new RandomStrollGoal((PathfinderMob)(this),0.8D)));
		this.goalSelector.addGoal(6,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
		this.goalSelector.addGoal(6,(Goal)(new RandomLookAroundGoal((Mob)(this))));
		this.targetSelector.addGoal(1,(Goal)(new OwnerHurtByTargetGoal((TamableAnimal)(this))));
		this.targetSelector.addGoal(2,(Goal)(new OwnerHurtTargetGoal((TamableAnimal)(this))));
		this.targetSelector.addGoal(3,(Goal)(new HurtByTargetGoal((PathfinderMob)(this),new Class[0])));
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		super.defineSynchedData(var1);
		var1.define(FLAGS,Byte.valueOf((byte)(0)));
		var1.define(CHESTS,Integer.valueOf(0));
	}
	public void openCustomInventoryScreen(Player var1) {
	}
	protected void createInventory() {
		SimpleContainer var1 = this.inventory;
		this.inventory = new SimpleContainer(this.getInventorySize());
		if ((var1) != (null)){
			var1.removeListener((ContainerListener)(this));
			int var2 = Math.min(var1.getContainerSize(),this.inventory.getContainerSize());
			int var3 = 0;
			do{
				if ((var3) < (var2)){
					ItemStack var4 = var1.getItem(var3);
					if (!(var4.isEmpty())){
						this.inventory.setItem(var3,var4.copy());
					}
					var3++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		this.inventory.addListener((ContainerListener)(this));
		this.updateContainerEquipment();
	}
	protected void updateContainerEquipment() {
		if (!(this.level().isClientSide)){
			this.setFlag(4,!(this.inventory.getItem(0).isEmpty()));
		}
	}
	public void openInventory(Player var1) {
		if (((!(this.level().isClientSide)) && (!((this.isVehicle()) && (!(this.hasPassenger((Entity)(var1))))))) && (this.isTame())){
			WhiteWalkieEntity var2 = this;
			MenuConstructor var3 = (l0, l1, l2) -> {
			return new WhiteWalkieStorageContainer(l0,l1,this.inventory,this);
		};
			SimpleMenuProvider var4 = new SimpleMenuProvider(var3,ModI18n.CONTAINER_WHITE_WALKIE_STORAGE);
			ServerPlayer var5 = ((ServerPlayer)(var1));
			WhiteWalkieEntity var6 = this;
			Consumer<RegistryFriendlyByteBuf> var7 = (l0) -> {
			CompoundTag lv2_2 = new CompoundTag();
			lv2_2.putInt("entity",this.getId());
			lv2_2.putInt("inventorySize",this.inventory.getContainerSize());
			lv2_2.putInt("pageSize",this.getInventoryPageSize());
			l0.writeNbt((Tag)(lv2_2));
		};
			var5.openMenu((MenuProvider)(var4),var7);
		}
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		var1.putBoolean("IsSleeping",this.isSleeping());
		var1.putInt("Chests",this.getChests());
		if (!(this.inventory.getItem(0).isEmpty())){
			var1.put("SaddleItem",this.inventory.getItem(0).save((HolderLookup.Provider)(this.registryAccess()),(Tag)(new CompoundTag())));
		}
		if (this.hasChest()){
			ListTag var2 = new ListTag();
			int var3 = 1;
			do{
				if ((var3) < (this.inventory.getContainerSize())){
					ItemStack var4 = this.inventory.getItem(var3);
					if (!(var4.isEmpty())){
						CompoundTag var5 = new CompoundTag();
						var5.putByte("Slot",(byte)(var3));
						var2.add(var4.save((HolderLookup.Provider)(this.registryAccess()),(Tag)(var5)));
					}
					var3++;
					continue;
				}else{
					break;
				}
			} while (true);
			var1.put("Items",(Tag)(var2));
		}
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		this.setSleeping(var1.getBoolean("IsSleeping"));
		this.setChest(var1.getInt("Chests"));
		if (var1.contains("SaddleItem",10)){
			ItemStack var2 = ((ItemStack)(ItemStack.parse((HolderLookup.Provider)(this.registryAccess()),(Tag)(var1.getCompound("SaddleItem"))).orElse(ItemStack.EMPTY)));
			if (((var2) != (null)) && ((!(var2.isEmpty())) && ((var2.getItem()) == (Items.SADDLE)))){
				this.setSaddled(var2);
			}
		}
		if (this.hasChest()){
			ListTag var2_1 = var1.getList("Items",10);
			this.createInventory();
			int var3 = 0;
			do{
				if ((var3) < (var2_1.size())){
					CompoundTag var4 = var2_1.getCompound(var3);
					int var5 = (var4.getByte("Slot")) & (255);
					if (((var5) >= (1)) && ((var5) < (this.inventory.getContainerSize()))){
						this.inventory.setItem(var5,((ItemStack)(ItemStack.parse((HolderLookup.Provider)(this.registryAccess()),(Tag)(var4)).orElse(ItemStack.EMPTY))));
					}
					var3++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		this.updateContainerEquipment();
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
					if (((this.random.nextDouble()) < (0.35D)) && (!(EventHooks.onAnimalTame((Animal)(this),var1)))){
						this.level().broadcastEntityEvent((Entity)(this),(byte)(7));
						this.tame(var1);
					}
					return InteractionResult.SUCCESS;
				}
			}else{
				if (var1.isSecondaryUseActive()){
					this.openInventory(var1);
					return InteractionResult.sidedSuccess(this.level().isClientSide);
				}else{
					if ((this.isFood(var3)) && ((this.getHealth()) < (this.getMaxHealth()))){
						this.usePlayerItem(var1,var2,var3);
						this.heal(4F);
						this.level().broadcastEntityEvent((Entity)(this),(byte)(7));
						return InteractionResult.SUCCESS;
					}else{
						if ((var5) && ((!(this.isSaddled())) && (!(this.isBaby())))){
							this.setSaddled(var3.copy());
							this.usePlayerItem(var1,var2,var3);
							this.level().playSound(((Player)(null)),(Entity)(this),SoundEvents.HORSE_SADDLE,SoundSource.PLAYERS,0.5F,1F);
							return InteractionResult.SUCCESS;
						}else{
							if (((var3.getItem()) == (Blocks.CHEST.asItem())) && ((!(this.isBaby())) && ((this.getChests()) < (2)))){
								this.setChest((this.getChests()) + (1));
								this.createInventory();
								this.usePlayerItem(var1,var2,var3);
								return InteractionResult.SUCCESS;
							}else{
								if (((this.isFood(var3)) && (!((this.isFood(var3)) && (!(this.canFallInLove()))))) || (!(((var1) == (this.getOwner())) && (!(this.isBaby()))))){
									return null;

								}else{
									var1.startRiding((Entity)(this));
									this.lastMountTime = this.level().getGameTime();
									return InteractionResult.SUCCESS;
								}
							}
						}
					}
				}
			}
		}
		return super.mobInteract(var1,var2);
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
				float var3 = (var2.xxa) * (0.3F);
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
		if ((this.clientShakeTime) > (0)){
			this.clientShakeTime = (this.clientShakeTime) - (1);
		}
		if ((this.clientBiteTime) > (0)){
			this.clientBiteTime = (this.clientBiteTime) - (1);
		}
		if (!(this.level().isClientSide)){
			if ((this.isTame()) && ((this.isVehicle()) && ((this.getPassengers().contains(this.getOwner())) && (((this.getOwner()) != (null)) && ((this.getOwner().swinging) && (((this.level().getGameTime()) >= ((this.lastMountTime) + (20L))) && ((this.level().getGameTime()) >= ((this.lastBiteTime) + (20L))))))))){
				this.swing(InteractionHand.MAIN_HAND);
				Vec3 var1 = this.position().add(this.getOwner().getLookAngle().multiply(4D,1D,4D));
				Vec3 var2 = new Vec3(var1.x(),this.getY(),var1.z());
				List var3 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),this.getOwner(),BlockPos.containing((Position)(var2)),1D,TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class);
				var3.remove(this);
				var3.remove(this.getOwner());
				this.lastBiteTime = this.level().getGameTime();
				this.level().broadcastEntityEvent((Entity)(this),(byte)(100));
				Iterator var4 = var3.iterator();
				do{
					if (var4.hasNext()){
						((Entity)(var4.next())).hurt(this.damageSources().mobAttack((LivingEntity)(this)),(float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
						continue;
					}else{
						break;
					}
				} while (true);
			}
			if ((this.lastShakeTime) <= (0L)){
				this.lastShakeTime = this.level().getGameTime();
			}
			if (((this.level().getGameTime()) >= ((this.lastShakeTime) + (1200L))) && (!(this.isVehicle()))){
				this.level().broadcastEntityEvent((Entity)(this),(byte)(101));
				this.lastShakeTime = this.level().getGameTime();
			}else{
				return;
			}
		}
	}
	public void tick() {
		super.tick();
	}
	public void customServerAiStep() {
		this.goalMemories.tick();
	}
	public void swing(InteractionHand var1, boolean var2) {
		if ((this.getTarget()) != (null)){
			this.level().broadcastEntityEvent((Entity)(this),(byte)(100));
		}
		super.swing(var1,var2);
	}
	protected SoundEvent getAmbientSound() {
		return SoundEvents.POLAR_BEAR_AMBIENT;
	}
	protected SoundEvent getHurtSound(DamageSource var1) {
		return SoundEvents.POLAR_BEAR_HURT;
	}
	protected SoundEvent getDeathSound() {
		return SoundEvents.POLAR_BEAR_DEATH;
	}
	protected void playStepSound(BlockPos var1, BlockState var2) {
		this.playSound(SoundEvents.POLAR_BEAR_STEP,0.15F,1.5F);
	}
	protected float getSoundVolume() {
		return 0.4F;
	}
	public float getVoicePitch() {
		return (this.isBaby()) ? ((1.5F) + ((this.random.nextFloat()) / (2F))) : ((1.25F) + ((this.random.nextFloat()) / (2F)));
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
		this.setHealth((float)(this.getAttribute(Attributes.MAX_HEALTH).getValue()));
		return super.finalizeSpawn(var1,var2,var3,var4);
	}
	public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
		WhiteWalkieEntity var3 = ((WhiteWalkieEntity)(((EntityType)(ModMobs.WHITE_WALKIE.get())).create((Level)(var1))));
		this.setOffspringAttributes(var2,var3);
		return var3;
	}
	protected void setOffspringAttributes(AgeableMob var1, WhiteWalkieEntity var2) {
		double var3 = ((this.getAttributeBaseValue(Attributes.MAX_HEALTH)) + (var1.getAttributeBaseValue(Attributes.MAX_HEALTH))) + (this.generateRandomHealth());
		var2.getAttribute(Attributes.MAX_HEALTH).setBaseValue((var3) / (3D));
		double var4 = ((this.getAttributeBaseValue(Attributes.ARMOR)) + (var1.getAttributeBaseValue(Attributes.ARMOR))) + (this.generateRandomArmor());
		var2.getAttribute(Attributes.ARMOR).setBaseValue((var4) / (3D));
		double var5 = ((this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED)) + (var1.getAttributeBaseValue(Attributes.MOVEMENT_SPEED))) + (this.generateRandomSpeed());
		var2.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((var5) / (3D));
		double var6 = ((this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE)) + (var1.getAttributeBaseValue(Attributes.ATTACK_DAMAGE))) + (this.generateRandomAttack());
		var2.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((var6) / (3D));
	}
	protected double generateRandomArmor() {
		return (1D) + ((this.random.nextDouble()) * (2.5D));
	}
	protected double generateRandomAttack() {
		return (5D) + ((this.random.nextDouble()) * (2D));
	}
	protected double generateRandomHealth() {
		return (50D) + ((this.random.nextDouble()) * (10D));
	}
	protected double generateRandomSpeed() {
		return (0.23000000417232513D) + ((this.random.nextDouble()) * (0.05D));
	}
	public boolean isFood(ItemStack var1) {
		return var1.is(ModTags$Items.WHITE_WALKIE_FOOD);
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
			break;
		case 101:
			this.clientShakeTime = 40;
			break;
		}
		super.handleEntityEvent(var1);
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
		return new Vec3(-0.4D,1.45D,0D);
	}
	public void containerChanged(Container var1) {
		ItemStack var2 = this.inventory.getItem(0);
		int var3 = (((var2) != (null)) && ((!(var2.isEmpty())) && ((var2.getItem()) == (Items.SADDLE)))) ? (1) : (0);
		this.setFlag(0,(var3) != (0));
		this.updateContainerEquipment();
		if (((this.tickCount) > (20)) && (((var3) == (0)) && (this.isSaddled()))){
			this.playSound(SoundEvents.HORSE_SADDLE,0.5F,1F);
		}
	}
	protected int getInventorySize() {
		return (3) + ((this.hasChest()) ? ((this.getInventoryPageSize()) * (this.getInventoryMaxPage())) : (0));
	}
	public int getInventoryColumns() {
		return 5;
	}
	public int getInventoryPageSize() {
		return 15;
	}
	public void setInventoryPage(int var1) {
		this.currentInvPage = var1;
	}
	public int getInventoryPage() {
		return this.currentInvPage;
	}
	public int getInventoryMaxPage() {
		return 2;
	}
	protected void dropEquipment() {
		super.dropEquipment();
		if ((this.inventory) != (null)){
			int var1 = 0;
			do{
				if ((var1) < (this.inventory.getContainerSize())){
					ItemStack var2 = this.inventory.getItem(var1);
					int var3 = ((var2.getEnchantmentLevel((Holder)(this.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.VANISHING_CURSE)))) > (0)) ? (1) : (0);
					if ((!(var2.isEmpty())) && ((var3) == (0))){
						this.spawnAtLocation(var2);
					}
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		if (this.hasChest()){
			if (!(this.level().isClientSide)){
				this.spawnAtLocation((ItemLike)(Blocks.CHEST));
			}
			this.setChest(0);
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public float getBiteAnimationProgress(float var1) {
		return (((float)((20) - (this.clientBiteTime))) + (var1)) / (20F);
	}
	@OnlyIn(value=Dist.CLIENT)
	public float getShakeAnimationTime() {
		return (float)(this.clientShakeTime);
	}
	public void setSaddled(@Nullable() ItemStack var1) {
		int var2 = 1;
		if ((var1) == (null)){
			var1 = ItemStack.EMPTY;
			var2 = 0;
		}
		this.inventory.setItem(0,var1);
		this.setFlag(0,(var2) != (0));
	}
	public void setSleeping(boolean var1) {
		this.setFlag(1,var1);
	}
	public void setChest(int var1) {
		this.getEntityData().set(CHESTS,Integer.valueOf(var1));
	}
	private void setFlag(int var1, boolean var2) {
		byte var3 = ((Byte)(this.entityData.get(FLAGS))).byteValue();
		if (var2){
			this.entityData.set(FLAGS,Byte.valueOf((byte)((var3) | ((1) << (var1)))));
		}else{
			this.entityData.set(FLAGS,Byte.valueOf((byte)((var3) & (((1) << (var1)) ^ (-1)))));
		}
	}
	private boolean getFlag(int var1) {
		return ((((Byte)(this.entityData.get(FLAGS))).byteValue()) & ((1) << (var1))) != (0);
	}
	public boolean isIdling() {
		return (!(this.isSleeping())) && (!(this.isVehicle()));
	}
	public boolean isSaddled() {
		ItemStack var1 = this.inventory.getItem(0);
		return (((var1) != (null)) && ((!(var1.isEmpty())) && ((var1.getItem()) == (Items.SADDLE)))) || (this.getFlag(0));
	}
	public boolean isSleeping() {
		return this.getFlag(1);
	}
	public boolean hasChest() {
		return (this.getChests()) > (0);
	}
	public int getChests() {
		return ((Integer)(this.getEntityData().get(CHESTS))).intValue();
	}
	public GoalMemories getGoalMemories() {
		return this.goalMemories;
	}
	static  {
		Item[] var0 = new Item[]{Items.SADDLE};
		SADDLES = var0;
	}
}