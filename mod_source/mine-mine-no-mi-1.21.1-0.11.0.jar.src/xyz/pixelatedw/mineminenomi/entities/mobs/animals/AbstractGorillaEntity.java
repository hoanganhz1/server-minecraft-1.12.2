package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import xyz.pixelatedw.mineminenomi.abilities.BellyFlopAbility;
import xyz.pixelatedw.mineminenomi.abilities.SlamAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;

public abstract class AbstractGorillaEntity extends PathfinderMob implements IEntityWithComplexSpawn {
	// Fields
	private float size;
	private static final float MIN_SIZE = 2.5F;
	private static final float MAX_SIZE = 3.5F;

	protected AbstractGorillaEntity(EntityType<? extends AbstractGorillaEntity> var1, Level var2) {
		super(var1,var2);
		if (((var2) != (null)) && (!(var2.isClientSide))){
			this.setItemSlot(EquipmentSlot.MAINHAND,this.getRandomAxe((List)(MobsHelper.GORILLA_AXES)));
			double var3 = this.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
			double var4 = this.getAttribute(Attributes.MAX_HEALTH).getBaseValue();
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((var3) + ((double)(((this.getSize()) > (1F)) ? (this.getSize()) : (0F))));
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((var4) + ((double)((this.getSize()) * (10F))));
			this.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).setBaseValue((double)((0F) + ((this.getSize()) * (0.5F))));
			this.setHealth((float)(this.getAttribute(Attributes.MAX_HEALTH).getValue()));
			EntityStatsData var5 = EntityStatsData.getOrCreate((LivingEntity)(this));
			var5.setDoriki((double)((1500) + (WyHelper.randomWithRange(var2.getRandom(),0,500))));
			var5.setBelly(0L);
			this.goalSelector.addGoal(0,(Goal)(new FloatGoal((Mob)(this))));
			this.goalSelector.addGoal(0,(Goal)(new JumpOutOfHoleGoal((Mob)(this))));
			if ((this.random.nextFloat()) < (0.6F)){
				this.goalSelector.addGoal(0,(Goal)(new SprintTowardsTargetGoal((Mob)(this))));
			}
			this.goalSelector.addGoal(0,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1.25D,true)));
			if (this.random.nextBoolean()){
				this.goalSelector.addGoal(2,(Goal)(new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(SlamAbility.INSTANCE.get())))));
			}else{
				this.goalSelector.addGoal(2,(Goal)(new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(BellyFlopAbility.INSTANCE.get())))));
			}
			this.goalSelector.addGoal(3,(Goal)(new WaterAvoidingRandomStrollGoal((PathfinderMob)(this),0.8D)));
			this.goalSelector.addGoal(4,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
			this.goalSelector.addGoal(4,(Goal)(new RandomLookAroundGoal((Mob)(this))));
			this.targetSelector.addGoal(1,(Goal)(new HurtByTargetGoal((PathfinderMob)(this),new Class[0])));
			this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Player.class,true)));
			this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),OPEntity.class,true)));
			this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Villager.class,true)));
			this.targetSelector.addGoal(3,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Monster.class,true)));
			this.targetSelector.addGoal(3,(Goal)(new NearestAttackableTargetGoal((Mob)(this),HumandrillEntity.class,true)));
		}
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		super.defineSynchedData(var1);
		this.size = Math.min((2.5F) + (this.random.nextFloat()),3.5F);
	}
	public EntityDimensions getDefaultDimensions(Pose var1) {
		float var2 = (this.getSize()) / (1.3F);
		return this.getType().getDimensions().scale(var2);
	}
	public void aiStep() {
		this.updateSwingTime();
		super.aiStep();
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		var1.putFloat("size",this.size);
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		this.size = var1.getFloat("size");
	}
	public ItemStack getRandomAxe(List<Supplier<? extends Item>> var1) {
		return new ItemStack(((ItemLike)(((Supplier)(var1.get(this.random.nextInt(var1.size())))).get())));
	}
	public float getSize() {
		return this.size;
	}
	protected SoundEvent getAmbientSound() {
		return SoundEvents.POLAR_BEAR_AMBIENT;
	}
	protected SoundEvent getHurtSound(DamageSource var1) {
		return SoundEvents.PANDA_BITE;
	}
	protected SoundEvent getDeathSound() {
		return SoundEvents.PANDA_DEATH;
	}
	protected void playStepSound(BlockPos var1, BlockState var2) {
		this.playSound(SoundEvents.POLAR_BEAR_STEP,0.15F,1.5F);
	}
	protected float getSoundVolume() {
		return 0.4F;
	}
	public boolean removeWhenFarAway(double var1) {
		return false;
	}
	public float getVoicePitch() {
		return (this.isBaby()) ? ((0.5F) + ((this.random.nextFloat()) / (2F))) : ((0.2F) + ((this.random.nextFloat()) / (3F)));
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		var1.writeFloat(this.size);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		this.size = var1.readFloat();
		this.refreshDimensions();
	}
}