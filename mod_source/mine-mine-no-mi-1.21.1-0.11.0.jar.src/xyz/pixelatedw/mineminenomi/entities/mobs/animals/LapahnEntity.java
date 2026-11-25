package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.LeapAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.PersonalSpaceTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.lapahn.LapahnChaseGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.lapahn.LapahnRageGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.lapahn.LapahnRestGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class LapahnEntity extends AgeableMob {
	// Fields
	private static final UUID LEAP_COOLDOWN_BONUS_UUID = UUID.fromString("5711f1b8-d776-44fb-9cd7-afdbd51a9837");
	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(LapahnEntity.class,EntityDataSerializers.BYTE);
	private static final int JUMP_ANIM_CYCLE = 20;
	private static final byte JUMP_EVENT = 100;
	private int clientJumpTick;
	private int jumpTick;

	public LapahnEntity(EntityType<? extends LapahnEntity> var1, Level var2) {
		super(var1,var2);
		this.clientJumpTick = 0;
		this.jumpTick = 0;
		if (((var2) != (null)) && (!(var2.isClientSide))){
			Consumer<EntityStatsData> var3 = (l0) -> {
			l0.setDoriki((double)((1000) + (WyHelper.randomWithRange(0,500))));
			l0.setBelly(0L);
		};
			((java.util.Optional)(EntityStatsData.get((LivingEntity)(this)))).ifPresent((Consumer)(var3));
			CloseMeleeAbilityWrapperGoal var4 = new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(LeapAbility.INSTANCE.get())));
			((LeapAbility)(var4.getAbility())).setLeapHeight(0.4D);
			LeapAbility var5 = ((LeapAbility)(var4.getAbility()));
			Consumer<CooldownComponent> var6 = (l0) -> {
			l0.getBonusManager().addBonus(LEAP_COOLDOWN_BONUS_UUID,"Leap Cooldown Bonus",BonusOperation.MUL,0.5F);
		};
			((java.util.Optional)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var6));
			this.goalSelector.addGoal(0,(Goal)(new JumpOutOfHoleGoal((Mob)(this))));
			this.goalSelector.addGoal(0,(Goal)(new FloatGoal((Mob)(this))));
			this.goalSelector.addGoal(0,(Goal)(new LapahnChaseGoal(this)));
			this.goalSelector.addGoal(0,(Goal)(new LapahnRageGoal(this)));
			this.goalSelector.addGoal(0,(Goal)(new LapahnRestGoal(this)));
			this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
			this.goalSelector.addGoal(1,(Goal)(var4));
			this.goalSelector.addGoal(3,(Goal)(new WaterAvoidingRandomStrollGoal((PathfinderMob)(this),0.8D)));
			this.goalSelector.addGoal(4,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
			this.goalSelector.addGoal(4,(Goal)(new LookAtPlayerGoal((Mob)(this),Rabbit.class,8F)));
			this.goalSelector.addGoal(4,(Goal)(new RandomLookAroundGoal((Mob)(this))));
			this.targetSelector.addGoal(1,(Goal)(new HurtByTargetGoal((PathfinderMob)(this),new Class[0])));
			this.targetSelector.addGoal(1,(Goal)(new PersonalSpaceTargetGoal((Mob)(this))));
			this.targetSelector.addGoal(3,(Goal)(new NearestAttackableTargetGoal((Mob)(this),HumandrillEntity.class,true)));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,55D).add(Attributes.MOVEMENT_SPEED,0.2D).add(Attributes.ATTACK_DAMAGE,5D).add(Attributes.MAX_HEALTH,60D);
	}
	public void aiStep() {
		super.aiStep();
		if (this.level().isClientSide){
			if ((this.clientJumpTick) > (0)){
				this.clientJumpTick = (this.clientJumpTick) - (1);
			}else{
				return;
			}
		}else{
			if (this.isIdling()){
				int var1 = (this.jumpTick) - (1);
				this.jumpTick = var1;
				if (((var1) <= (0)) && (this.onGround())){
					this.level().broadcastEntityEvent((Entity)(this),(byte)(100));
					this.getJumpControl().jump();
					this.playSound(SoundEvents.POLAR_BEAR_STEP,0.2F,0.5F);
					this.jumpTick = 20;
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	public List<LapahnEntity> getNearbyLapahns() {
		List var1 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),(LivingEntity)(this),16D,TargetPredicate.EVERYTHING,LapahnEntity.class);
		var1.remove(this);
		return var1;
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		super.defineSynchedData(var1);
		var1.define(DATA_FLAGS_ID,Byte.valueOf((byte)(0)));
	}
	@OnlyIn(value=Dist.CLIENT)
	public void handleEntityEvent(byte var1) {
		switch (var1){
		case 100:
			this.clientJumpTick = 20;
		default:
			super.handleEntityEvent(var1);
			return;
		}
	}
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable() SpawnGroupData var4) {
		var4 = super.finalizeSpawn(var1,var2,var3,var4);
		return var4;
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		var1.putBoolean("isEnraged",this.isEnraged());
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		this.setEnraged(var1.getBoolean("isEnraged"));
	}
	public void setEnraged(boolean var1) {
		this.setFlag(0,var1);
	}
	public void setChasing(boolean var1) {
		this.setFlag(1,var1);
		if (var1){
			this.setResting(false);
		}
	}
	public void setResting(boolean var1) {
		this.setFlag(2,var1);
	}
	private void setFlag(int var1, boolean var2) {
		byte var3 = ((Byte)(this.entityData.get(DATA_FLAGS_ID))).byteValue();
		if (var2){
			this.entityData.set(DATA_FLAGS_ID,Byte.valueOf((byte)((var3) | ((1) << (var1)))));
		}else{
			this.entityData.set(DATA_FLAGS_ID,Byte.valueOf((byte)((var3) & (((1) << (var1)) ^ (-1)))));
		}
	}
	private boolean getFlag(int var1) {
		return ((((Byte)(this.entityData.get(DATA_FLAGS_ID))).byteValue()) & ((1) << (var1))) != (0);
	}
	public boolean isIdling() {
		return (!(this.isResting())) && (!(this.isChasing()));
	}
	public boolean isEnraged() {
		return this.getFlag(0);
	}
	public boolean isChasing() {
		return this.getFlag(1);
	}
	public boolean isResting() {
		return this.getFlag(2);
	}
	@OnlyIn(value=Dist.CLIENT)
	public float getJumpAnimationProgress(float var1) {
		return (((float)((20) - (this.clientJumpTick))) + (var1)) / (20F);
	}
	public boolean removeWhenFarAway(double var1) {
		return false;
	}
	public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
		return null;
	}
}