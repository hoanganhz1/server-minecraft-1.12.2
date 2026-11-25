package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
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
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.brawler.BrawlerPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.GoalMemories;
import xyz.pixelatedw.mineminenomi.api.entities.IGoalMemoriesEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong.DugongCheerGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong.DugongHappyGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong.DugongRageGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong.DugongRestGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong.DugongTrainGoal;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMemoryModuleTypes;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;

public abstract class AbstractDugongEntity extends TamableAnimal implements IGoalMemoriesEntity {
	// Fields
	private static final EntityDataAccessor<Byte> FLAGS = SynchedEntityData.defineId(AbstractDugongEntity.class,EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Integer> TRAINING_MODE = SynchedEntityData.defineId(AbstractDugongEntity.class,EntityDataSerializers.INT);
	private final GoalMemories goalMemories;
	private LivingEntity cheerTarget;
	private LivingEntity rageTarget;

	protected AbstractDugongEntity(EntityType<? extends TamableAnimal> var1, Level var2) {
		super(var1,var2);
		this.goalMemories = new GoalMemories();
		if (((var2) != (null)) && (!(var2.isClientSide))){
			EntityStatsData.getOrCreate((LivingEntity)(this)).setFightingStyle(((FightingStyle)(ModFightingStyles.BRAWLER.get())));
		}
	}
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(this),((AbilityCore)(BrawlerPassiveBonusesAbility.INSTANCE.get())))));
		this.goalSelector.addGoal(0,(Goal)(new FloatGoal((Mob)(this))));
		this.goalSelector.addGoal(0,(Goal)(new SitWhenOrderedToGoal((TamableAnimal)(this))));
		this.goalSelector.addGoal(0,(Goal)(new DugongRageGoal(this)));
		this.goalSelector.addGoal(0,(Goal)(new DugongTrainGoal(this)));
		if (!(this instanceof WanderingDugongEntity)){
			this.goalSelector.addGoal(0,(Goal)(new DugongHappyGoal(this)));
			this.goalSelector.addGoal(0,(Goal)(new DugongCheerGoal(this)));
			this.goalSelector.addGoal(0,(Goal)(new DugongRestGoal(this)));
		}
		this.goalSelector.addGoal(3,(Goal)(new FollowOwnerGoal((TamableAnimal)(this),1D,6F,2F)));
		this.goalSelector.addGoal(4,(Goal)(new WaterAvoidingRandomStrollGoal((PathfinderMob)(this),0.8D)));
		this.goalSelector.addGoal(5,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
		this.goalSelector.addGoal(5,(Goal)(new LookAtPlayerGoal((Mob)(this),AbstractDugongEntity.class,8F)));
		this.goalSelector.addGoal(6,(Goal)(new RandomLookAroundGoal((Mob)(this))));
		this.targetSelector.addGoal(1,(Goal)(new OwnerHurtByTargetGoal((TamableAnimal)(this))));
		this.targetSelector.addGoal(2,(Goal)(new HurtByTargetGoal((PathfinderMob)(this),new Class[0])));
		this.targetSelector.addGoal(2,(Goal)(new OwnerHurtTargetGoal((TamableAnimal)(this))));
	}
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.ATTACK_SPEED).add(Attributes.FOLLOW_RANGE,40D);
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		super.defineSynchedData(var1);
		var1.define(FLAGS,Byte.valueOf((byte)(0)));
		var1.define(TRAINING_MODE,Integer.valueOf(0));
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
	}
	public SoundEvent getHurtSound(DamageSource var1) {
		return SoundEvents.TURTLE_HURT_BABY;
	}
	public SoundEvent getDeathSound() {
		return SoundEvents.TURTLE_DEATH_BABY;
	}
	public void playStepSound(BlockPos var1, BlockState var2) {
		this.playSound(SoundEvents.TURTLE_SHAMBLE_BABY,0.2F,0.5F);
	}
	public float getSoundVolume() {
		return 0.4F;
	}
	public float getVoicePitch() {
		return (this.isBaby()) ? ((0.6F) + ((this.random.nextFloat()) / (4F))) : ((0.4F) + ((this.random.nextFloat()) / (4F)));
	}
	private void broadcastAngerTarget(LivingEntity var1) {
		LivingEntity var2 = var1;
		final LivingEntity var2_f1 = var2;
		Consumer<? super AbstractDugongEntity> var3 = (l0) -> {
			if (l0.isOwnedBy(var2_f1)){
				return;
			}else{
				l0.setEnraged(var2_f1);
				return;
			}
		};
		((java.lang.Iterable)(this.getNearbyDugongs())).forEach((Consumer)(var3));
	}
	private void broadcastCheeringTarget(LivingEntity var1) {
		AbstractDugongEntity var2 = this;
		Consumer<? super AbstractDugongEntity> var3 = (l0) -> {
			if ((!(l0.isEnraged())) && (!(GoalHelper.hasAliveTarget((Mob)(l0))))){
				GoalHelper.lookAtEntity((Mob)(l0),(Entity)(this));
				l0.setCheering((LivingEntity)(this));
				return;
			}else{
				return;
			}
		};
		((java.lang.Iterable)(this.getNearbyDugongs())).forEach((Consumer)(var3));
	}
	public List<AbstractDugongEntity> getNearbyDugongs() {
		if (this.hasMemoryValue(((MemoryModuleType)(ModMemoryModuleTypes.NEARBY_ADULT_DUGONGS.get())))){
			return ((List)(this.getMemory(((MemoryModuleType)(ModMemoryModuleTypes.NEARBY_ADULT_DUGONGS.get()))).get()));
		}else{
			List var1 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),(LivingEntity)(this),16D,(TargetPredicate)(null),AbstractDugongEntity.class);
			var1.remove(this);
			this.setMemoryWithExpiry(((MemoryModuleType)(ModMemoryModuleTypes.NEARBY_ADULT_DUGONGS.get())),var1,600L);
			return var1;
		}
	}
	public void customServerAiStep() {
		this.goalMemories.tick();
	}
	public InteractionResult mobInteract(Player var1, InteractionHand var2) {
		if ((this.isTame()) && ((this.isOwnedBy((LivingEntity)(var1))) && ((!(var1.level().isClientSide)) && ((var2) == (InteractionHand.MAIN_HAND))))){
			ItemStack var3 = var1.getItemInHand(((var1.getUsedItemHand()) != (null)) ? (var1.getUsedItemHand()) : (InteractionHand.MAIN_HAND));
			if ((!(var3.isEmpty())) && (((this.getHealth()) < (this.getMaxHealth())) && (this.isFood(var3)))){
				var3.shrink(1);
				this.heal(4F);
				this.level().broadcastEntityEvent((Entity)(this),(byte)(7));
				return InteractionResult.SUCCESS;
			}else{
				if (var3.isEmpty()){
					this.setOrderedToSit(!(this.isOrderedToSit()));
					this.jumping = false;
					this.navigation.stop();
					this.setTarget(((LivingEntity)(null)));
					return InteractionResult.SUCCESS;
				}
			}
		}
		return super.mobInteract(var1,var2);
	}
	public void tick() {
		super.tick();
	}
	public void aiStep() {
		this.updateSwingTime();
		super.aiStep();
	}
	public boolean hurt(DamageSource var1, float var2) {
		if (!(super.hurt(var1,var2))){
			return false;
		}else{
			Entity var3 = var1.getEntity();
			if ((!(this.level().isClientSide)) && ((!(this.isTame())) && (var3 instanceof LivingEntity))){
				LivingEntity var4 = ((LivingEntity)(var3));
				if (var4.getItemInHand(var4.getUsedItemHand()).isEmpty()){
					if (var4 instanceof Player){
						Player var5 = ((Player)(var4));
						this.broadcastCheeringTarget(var4);
						if ((!(this.isEnraged())) && (((this.getHealth()) < ((this.getMaxHealth()) / (2F))) && (!(EventHooks.onAnimalTame((Animal)(this),var5))))){
							this.tame(var5);
						}else{
							return true;
						}
					}else{
						return true;
					}
				}else{
					this.setEnraged(var4);
					this.broadcastAngerTarget(var4);
				}
			}
			return true;
		}
	}
	public boolean isOrderedToSit() {
		return super.isOrderedToSit();
	}
	public boolean wantsToAttack(LivingEntity var1, LivingEntity var2) {
		if ((!(var1 instanceof Ghast)) && (!(var1 instanceof Blaze))){
			LivingEntity var3 = MobsHelper.getTamer(var1);
			if (((var3) != (null)) && (MobsHelper.getTamer(var1).equals(var3))){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	public void tame(Player var1) {
		super.tame(var1);
		this.navigation.stop();
		this.setOrderedToSit(true);
		this.setTarget((LivingEntity)(null));
		this.broadcastCheeringTarget((LivingEntity)(null));
		this.level().broadcastEntityEvent((Entity)(this),(byte)(7));
	}
	public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
		return null;
	}
	public boolean canDrownInFluidType(FluidType var1) {
		if ((var1) == (NeoForgeMod.WATER_TYPE.value())){
			return false;
		}else{
			return true;
		}
	}
	public boolean isFood(ItemStack var1) {
		return var1.is(ModTags$Items.DUGONG_FOOD);
	}
	public boolean hasHakiLearned() {
		AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(this)))).orElse((Object)(null))));
		if ((var1) == (null)){
			return false;
		}else{
			boolean var2 = var1.hasUnlockedAbility(((AbilityCore)(BusoshokuHakiHardeningAbility.INSTANCE.get())));
			boolean var3 = var1.hasUnlockedAbility(((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get())));
			return (var2) || (var3);
		}
	}
	public boolean removeWhenFarAway(double var1) {
		return false;
	}
	public boolean isTraining() {
		return (((Integer)(this.entityData.get(TRAINING_MODE))).intValue()) > (0);
	}
	public boolean isIdling() {
		return (!(this.isTraining())) && ((!(this.isResting())) && (!(this.isCheering())));
	}
	public AbstractDugongEntity$TrainingMethod getTrainingMode() {
		return AbstractDugongEntity$TrainingMethod.values()[(((Integer)(this.entityData.get(TRAINING_MODE))).intValue()) - (1)];
	}
	public void stopTraining() {
		this.setTraining(0);
	}
	public void setTraining(int var1) {
		this.entityData.set(TRAINING_MODE,Integer.valueOf(var1));
	}
	public void setCheering(@Nullable() LivingEntity var1) {
		int var2 = 1;
		if (((var1) != (null)) && (!(this.isOwnedBy(var1)))){

		}else{
			var2 = 0;
			var1 = null;
		}
		this.setFlag(0,(var2) != (0));
		this.cheerTarget = var1;
	}
	public void setHappy(boolean var1) {
		this.setFlag(1,var1);
	}
	public void setResting(boolean var1) {
		this.setFlag(2,var1);
	}
	public void setEnraged(@Nullable() LivingEntity var1) {
		int var2 = 1;
		if (((var1) != (null)) && (!(this.isOwnedBy(var1)))){

		}else{
			var2 = 0;
			var1 = null;
		}
		this.setFlag(3,(var2) != (0));
		this.rageTarget = var1;
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
	public boolean isCheering() {
		return this.getFlag(0);
	}
	public boolean isHappy() {
		return this.getFlag(1);
	}
	public boolean isResting() {
		return this.getFlag(2);
	}
	public boolean isEnraged() {
		return this.getFlag(3);
	}
	public boolean isWandering() {
		return this.getFlag(4);
	}
	@Nullable()
	public @Nullable() LivingEntity getCheerTarget() {
		return this.cheerTarget;
	}
	@Nullable()
	public @Nullable() LivingEntity getRageTarget() {
		return this.rageTarget;
	}
	public GoalMemories getGoalMemories() {
		return this.goalMemories;
	}
}