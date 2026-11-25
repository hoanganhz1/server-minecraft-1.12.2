package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.neoforged.neoforge.entity.PartEntity;
import xyz.pixelatedw.mineminenomi.abilities.BellyFlopAbility;
import xyz.pixelatedw.mineminenomi.abilities.TailSpinAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.TackleAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.FishSwimMoveController;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.LandFishLookController;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.AvoidBlockGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.AvoidCoatedBoatGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.EatNearbyFishGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;

public class SeaCowEntity extends TamableAnimal implements IEntityWithComplexSpawn {
	// Fields
	private static final UUID TAIL_SPIN_RANGE_BONUS = UUID.fromString("3da76e8f-b539-4a87-b764-8edab430cd77");
	private static final ResourceLocation SWIM_SPEED_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","sea_cow.swim_speed_bonus");
	private static final AttributeModifier SWIM_SPEED_MOD = new AttributeModifier(SWIM_SPEED_ID,1D,AttributeModifier.Operation.ADD_VALUE);
	private TargetPredicate tailSpinAOECheck;
	private float size;
	private static final float MIN_SIZE = 2F;
	private static final float MAX_SIZE = 3.5F;
	private final SeaCowEntity$SeaCowPartEntity head;
	private final SeaCowEntity$SeaCowPartEntity tail;
	private final SeaCowEntity$SeaCowPartEntity[] subEntities;
	private MoveControl groundMovementController;
	private MoveControl waterMovementController;
	private LookControl groundLookControl;
	private LookControl waterLookControl;
	private PathNavigation groundNavigator;
	private PathNavigation waterNavigator;
	private final NPCPhaseManager phaseManager;
	private NPCPhase<SeaCowEntity> groundPhase;
	private NPCPhase<SeaCowEntity> waterPhase;
	private DashAbilityWrapperGoal<TackleAbility> tackleWrapper;

	public SeaCowEntity(EntityType<? extends SeaCowEntity> var1, Level var2) {
		super(var1,var2);
		this.phaseManager = new NPCPhaseManager((Mob)(this));
		this.head = new SeaCowEntity$SeaCowPartEntity(this,"head",2.5F,2.5F);
		this.tail = new SeaCowEntity$SeaCowPartEntity(this,"tail",3F,3F);
		SeaCowEntity$SeaCowPartEntity[] var3 = new SeaCowEntity$SeaCowPartEntity[]{this.head,this.tail};
		this.subEntities = var3;
		SeaCowEntity var4 = this;
		Predicate<Entity> var5 = (l0) -> {
			if (((this.getTarget()) != (null)) && ((this.getTarget()) == (l0))){
				return true;
			}else{
				if ((!(l0 instanceof YagaraBullEntity)) && ((!(l0 instanceof SeaCowEntity)) && ((!(l0 instanceof PandaSharkEntity)) && (!(l0 instanceof Dolphin))))){
					return true;
				}else{
					return false;
				}
			}
		};
		this.tailSpinAOECheck = TargetPredicate.DEFAULT_AREA_CHECK.selector(var5);
		this.groundMovementController = new MoveControl((Mob)(this));
		this.waterMovementController = new FishSwimMoveController(this);
		this.groundLookControl = new LookControl((Mob)(this));
		this.waterLookControl = new LandFishLookController(this,10);
		this.groundNavigator = new GroundPathNavigation((Mob)(this),var2);
		this.waterNavigator = new WaterBoundPathNavigation((Mob)(this),var2);
		this.setId((ENTITY_COUNTER.getAndAdd((this.subEntities.length) + (1))) + (1));
		if (((var2) != (null)) && (!(var2.isClientSide))){
			this.size = Math.min((2F) + (this.random.nextFloat()),3.5F);
			this.groundPhase = new SimplePhase("Ground Phase",this,(SimplePhase$IStartPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent)(this::startGroundPhaseEvent))));
			this.waterPhase = new SimplePhase("Water Phase",this,(SimplePhase$IStartPhaseEvent)(((xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase$IStartPhaseEvent)(this::startWaterPhaseEvent))));
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(2D);
			this.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).setBaseValue(4D);
			CloseMeleeAbilityWrapperGoal var6 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(TailSpinAbility.INSTANCE.get())));
			TailSpinAbility var7 = ((TailSpinAbility)(var6.getAbility()));
			SeaCowEntity var8 = this;
			Consumer<RangeComponent> var9 = (l0) -> {
			l0.setAreaCheck(this.tailSpinAOECheck);
			l0.getBonusManager().addBonus(TAIL_SPIN_RANGE_BONUS,"Tail Spin Range Bonus",BonusOperation.MUL,4F);
		};
			((java.util.Optional)(var7.getComponent(((AbilityComponentKey)(ModAbilityComponents.RANGE.get()))))).ifPresent((Consumer)(var9));
			this.tackleWrapper = (DashAbilityWrapperGoal) (new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(TackleAbility.INSTANCE.get()))));
			this.goalSelector.addGoal(0,(Goal)(new AvoidBlockGoal((PathfinderMob)(this),ModTags$Blocks.KAIROSEKI)));
			this.goalSelector.addGoal(0,(Goal)(new AvoidCoatedBoatGoal((PathfinderMob)(this))));
			this.goalSelector.addGoal(0,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1.25D,true)));
			this.goalSelector.addGoal(0,(Goal)(new EatNearbyFishGoal((Mob)(this))));
			this.goalSelector.addGoal(1,(Goal)(new BreedGoal((Animal)(this),1D)));
			this.goalSelector.addGoal(2,(Goal)(var6));
			this.goalSelector.addGoal(2,(Goal)(this.tackleWrapper));
			this.goalSelector.addGoal(5,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
			this.goalSelector.addGoal(5,(Goal)(new RandomLookAroundGoal((Mob)(this))));
			this.groundPhase.addGoal(2,new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(BellyFlopAbility.INSTANCE.get()))));
			this.groundPhase.addGoal(4,(Goal)(new RandomStrollGoal((PathfinderMob)(this),0.8D)));
			this.waterPhase.addGoal(0,(Goal)(new TryFindWaterGoal((PathfinderMob)(this))));
			this.waterPhase.addGoal(4,(Goal)(new RandomSwimmingGoal((PathfinderMob)(this),1D,10)));
			this.targetSelector.addGoal(2,(Goal)(new HurtByTargetGoal((PathfinderMob)(this),new Class[0])));
			this.phaseManager.setPhase(this.groundPhase);
		}
	}
	public void setId(int var1) {
		super.setId(var1);
		int var2 = 0;
		do{
			if ((var2) < (this.subEntities.length)){
				this.subEntities[var2].setId(((var1) + (var2)) + (1));
				var2++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.ENTITY_INTERACTION_RANGE).add(Attributes.ATTACK_DAMAGE,4D).add(Attributes.KNOCKBACK_RESISTANCE,0.699999988079071D).add(Attributes.MOVEMENT_SPEED,0.27000001072883606D).add(Attributes.FOLLOW_RANGE,70D).add(Attributes.MAX_HEALTH,100D);
	}
	public void aiStep() {
		super.aiStep();
		Vec3[] var1 = new Vec3[this.subEntities.length];
		int var2 = 0;
		do{
			if ((var2) < (this.subEntities.length)){
				var1[var2] = new Vec3(this.subEntities[var2].getX(),this.subEntities[var2].getY(),this.subEntities[var2].getZ());
				var2++;
				continue;
			}else{
				break;
			}
		} while (true);
		float var3 = (Mth.lerp(1F,this.yHeadRot,this.yBodyRot)) * (0.017453292F);
		float var4 = Mth.sin(var3);
		float var5 = Mth.cos(var3);
		this.tickPart(this.head,(double)((-(var4)) * (3.5F)),1.5D,(double)((var5) * (3.5F)));
		this.tickPart(this.tail,(double)((var4) * (4F)),-0.25D,(double)((-(var5)) * (4F)));
		int var6 = 0;
		do{
			if ((var6) < (this.subEntities.length)){
				this.subEntities[var6].xo = var1[var6].x;
				this.subEntities[var6].yo = var1[var6].y;
				this.subEntities[var6].zo = var1[var6].z;
				this.subEntities[var6].xOld = var1[var6].x;
				this.subEntities[var6].yOld = var1[var6].y;
				this.subEntities[var6].zOld = var1[var6].z;
				var6++;
				continue;
			}else{
				break;
			}
		} while (true);
		if ((!(this.level().isClientSide)) && (this.isAlive())){
			if (this.isInWater()){
				this.phaseManager.setPhase(this.waterPhase);
			}else{
				this.phaseManager.setPhase(this.groundPhase);
			}
		}
	}
	public void customServerAiStep() {
		this.phaseManager.tick();
	}
	protected SoundEvent getAmbientSound() {
		return SoundEvents.COW_AMBIENT;
	}
	protected SoundEvent getHurtSound(DamageSource var1) {
		return SoundEvents.COW_HURT;
	}
	protected SoundEvent getDeathSound() {
		return SoundEvents.COW_DEATH;
	}
	protected float getSoundVolume() {
		return 0.4F;
	}
	public float getVoicePitch() {
		return (this.isBaby()) ? ((((this.random.nextFloat()) - (this.random.nextFloat())) * (0.2F)) + (1F)) : ((((this.random.nextFloat()) - (this.random.nextFloat())) * (0.2F)) + (0.5F));
	}
	protected PathNavigation createNavigation(Level var1) {
		return new WaterBoundPathNavigation((Mob)(this),var1);
	}
	public void travel(Vec3 var1) {
		if ((this.isEffectiveAi()) && (this.isInWater())){
			this.moveRelative(this.getSpeed(),var1);
			this.move(MoverType.SELF,this.getDeltaMovement());
			AbilityHelper.setDeltaMovement((Entity)(this),this.getDeltaMovement().scale(0.9D));
			if ((this.getTarget()) == (null)){
				AbilityHelper.setDeltaMovement((Entity)(this),this.getDeltaMovement().add(0D,-0.005D,0D));
			}else{
				return;
			}
		}else{
			super.travel(var1);
		}
	}
	public int getMaxHeadXRot() {
		if (((this.phaseManager.getCurrentPhase()) != (null)) && (((java.lang.Object)(this.phaseManager.getCurrentPhase())).equals((Object)(this.waterPhase)))){
			return 1;
		}else{
			return super.getMaxHeadXRot();
		}
	}
	public int getMaxHeadYRot() {
		if (((this.phaseManager.getCurrentPhase()) != (null)) && (((java.lang.Object)(this.phaseManager.getCurrentPhase())).equals((Object)(this.waterPhase)))){
			return 1;
		}else{
			return super.getMaxHeadYRot();
		}
	}
	public boolean isPushedByFluid() {
		return false;
	}
	public boolean hurt(DamageSource var1, float var2) {
		return super.hurt(var1,var2);
	}
	public boolean hurt(SeaCowEntity$SeaCowPartEntity var1, DamageSource var2, float var3) {
		if ((!(this.level().isClientSide)) && ((var1.equals(this.head)) && (((TackleAbility)(this.tackleWrapper.getAbility())).isContinuous()))){
			var3 = (var3) * (1.5F);
			TackleAbility var4 = ((TackleAbility)(this.tackleWrapper.getAbility()));
			SeaCowEntity var5 = this;
			Consumer<ContinuousComponent> var6 = (l0) -> {
			l0.stopContinuity((LivingEntity)(this));
		};
			((java.util.Optional)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))))).ifPresent((Consumer)(var6));
		}
		return this.hurt(var2,var3);
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		super.defineSynchedData(var1);
	}
	public boolean isFood(ItemStack var1) {
		return false;
	}
	public EntityDimensions getDefaultDimensions(Pose var1) {
		float var2 = (this.getSize()) * (((this.getAge()) < (0)) ? (0.5F) : (1F));
		return this.getType().getDimensions().scale(var2);
	}
	private void tickPart(SeaCowEntity$SeaCowPartEntity var1, double var2, double var3, double var4) {
		var1.setPos((this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4));
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		var1.putFloat("size",this.size);
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		this.size = var1.getFloat("size");
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		var1.writeFloat(this.size);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		this.size = var1.readFloat();
		this.refreshDimensions();
	}
	public float getSize() {
		return this.size;
	}
	public boolean isMultipartEntity() {
		return true;
	}
	@Nullable()
	public PartEntity<?>[] getParts() {
		return this.subEntities;
	}
	public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
		return ((SeaCowEntity)(((EntityType)(ModMobs.SEA_COW.get())).create((Level)(var1))));
	}
	private void startWaterPhaseEvent(SeaCowEntity var1) {
		this.moveControl = this.waterMovementController;
		this.lookControl = this.waterLookControl;
		this.navigation.stop();
		this.navigation = this.waterNavigator;
		AttributeInstance var2 = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if (((var2) != (null)) && (!(var2.hasModifier(SWIM_SPEED_ID)))){
			var2.addTransientModifier(SWIM_SPEED_MOD);
		}
	}
	private void startGroundPhaseEvent(SeaCowEntity var1) {
		this.moveControl = this.groundMovementController;
		this.lookControl = this.groundLookControl;
		this.navigation.stop();
		this.navigation = this.groundNavigator;
		AttributeInstance var2 = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if ((var2) != (null)){
			var2.removeModifier(SWIM_SPEED_MOD);
		}
	}
	public static boolean checkSpawnRules(EntityType<SeaCowEntity> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, RandomSource var4) {
		return var1.getBlockState(var3.below()).getFluidState().is(FluidTags.WATER);
	}
}