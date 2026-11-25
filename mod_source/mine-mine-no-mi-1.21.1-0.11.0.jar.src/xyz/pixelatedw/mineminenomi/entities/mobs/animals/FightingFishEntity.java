package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FollowBoatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.FishSwimMoveController;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.LandFishLookController;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.AvoidBlockGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.AvoidCoatedBoatGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.BreakBoatGoal;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;

public class FightingFishEntity extends WaterAnimal implements IEntityWithComplexSpawn {
	// Fields
	private float size;

	public FightingFishEntity(EntityType<? extends FightingFishEntity> var1, Level var2) {
		super(var1,var2);
		this.size = 1F;
		this.moveControl = new FishSwimMoveController(this);
		this.lookControl = new LandFishLookController(this,10);
		if (((var2) != (null)) && (!(var2.isClientSide))){
			this.size = (0.2F) + (this.random.nextFloat());
		}
	}
	protected void registerGoals() {
		this.goalSelector.addGoal(0,(Goal)(new TryFindWaterGoal((PathfinderMob)(this))));
		this.goalSelector.addGoal(0,(Goal)(new AvoidBlockGoal((PathfinderMob)(this),ModTags$Blocks.KAIROSEKI)));
		this.goalSelector.addGoal(0,(Goal)(new AvoidCoatedBoatGoal((PathfinderMob)(this))));
		this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1.7999999523162842D,true)));
		this.goalSelector.addGoal(1,(Goal)(new RandomSwimmingGoal((PathfinderMob)(this),1D,10)));
		this.goalSelector.addGoal(1,(Goal)(new RandomLookAroundGoal((Mob)(this))));
		this.goalSelector.addGoal(2,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,12F)));
		this.goalSelector.addGoal(4,(Goal)(new FollowBoatGoal((PathfinderMob)(this))));
		this.goalSelector.addGoal(5,(Goal)(new BreakBoatGoal((PathfinderMob)(this))));
		this.targetSelector.addGoal(1,(Goal)(new HurtByTargetGoal((PathfinderMob)(this),new Class[0])));
		this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Player.class,true)));
		this.targetSelector.addGoal(3,(Goal)(new NearestAttackableTargetGoal((Mob)(this),YagaraBullEntity.class,false)));
	}
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.ENTITY_INTERACTION_RANGE).add(Attributes.MOVEMENT_SPEED,1.2000000476837158D).add(Attributes.FOLLOW_RANGE,55D).add(Attributes.ATTACK_DAMAGE,7D).add(Attributes.MAX_HEALTH,40D);
	}
	@Nullable()
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable() SpawnGroupData var4) {
		this.setXRot(0F);
		if (((double)(WyHelper.randomWithRange(this.random,0,64))) == (0D)){
			this.setCustomName((Component)(Component.literal("Timmy")));
		}
		double var5 = (double)(((1F) - (this.getSize())) * (30F));
		this.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).setBaseValue(-(var5));
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((this.getAttributeValue(Attributes.MAX_HEALTH)) * (Math.max((double)(this.getSize()),0.5D)));
		return super.finalizeSpawn(var1,var2,var3,var4);
	}
	protected void handleAirSupply(int var1) {
		super.handleAirSupply(var1);
		if (WyHelper.isAprilFirst()){
			this.setAirSupply(300);
		}
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
	public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
		super.onSyncedDataUpdated(var1);
	}
	public EntityDimensions getDefaultDimensions(Pose var1) {
		float var2 = this.getSize();
		return this.getType().getDimensions().scale(var2);
	}
	public float getSize() {
		return this.size;
	}
	public int getMaxHeadXRot() {
		return 1;
	}
	public int getMaxHeadYRot() {
		return 1;
	}
	protected PathNavigation createNavigation(Level var1) {
		if (WyHelper.isAprilFirst()){
			return super.createNavigation(var1);
		}else{
			return new WaterBoundPathNavigation((Mob)(this),var1);
		}
	}
	public void tick() {
		super.tick();
		if ((!(this.getCommandSenderWorld().isClientSide)) && (!(this.isEyeInFluid(FluidTags.WATER)))){
			AbilityHelper.setDeltaMovement((Entity)(this),this.getDeltaMovement().add(0D,-0.01D,0D));
		}
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
	public static boolean checkSpawnRules(EntityType<FightingFishEntity> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, RandomSource var4) {
		if (WyHelper.isAprilFirst()){
			return Mob.checkMobSpawnRules(var0,var1,var2,var3,var4);
		}else{
			if ((var3.getY()) >= ((var1.getSeaLevel()) - (10))){
				return false;
			}else{
				return var1.getFluidState(var3).is(FluidTags.WATER);
			}
		}
	}
	public boolean canBeLeashed() {
		return true;
	}
}