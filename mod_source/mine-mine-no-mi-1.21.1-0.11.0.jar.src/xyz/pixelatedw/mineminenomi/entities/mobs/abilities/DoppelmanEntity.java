package xyz.pixelatedw.mineminenomi.entities.mobs.abilities;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.kage.BlackBoxAbility;
import xyz.pixelatedw.mineminenomi.abilities.kage.BrickBatAbility;
import xyz.pixelatedw.mineminenomi.abilities.kage.DoppelmanAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class DoppelmanEntity extends CloneEntity {
	// Fields
	private static final UUID BLACK_BOX_COOLDOWN_BONUS_UUID = UUID.fromString("8459d1b9-2362-4c63-b89c-af761a6c9f18");
	private static final EntityDataAccessor<Integer> SHADOWS = SynchedEntityData.defineId(DoppelmanEntity.class,EntityDataSerializers.INT);

	public DoppelmanEntity(EntityType<DoppelmanEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public DoppelmanEntity(Level var1, LivingEntity var2) {
		super(((EntityType)(ModMobs.DOPPELMAN.get())),var1,var2);
		if (((var1) != (null)) && (!(var1.isClientSide))){
			this.setSprinting(false);
			this.setMaxAliveTicks(-1);
			this.disablePermaPanic();
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(4D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(10D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
			this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(2D);
			((GroundPathNavigation)(this.getNavigation())).setCanOpenDoors(true);
			ProjectileAbilityWrapperGoal var3 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(BlackBoxAbility.INSTANCE.get())));
			BlackBoxAbility var4 = ((BlackBoxAbility)(var3.getAbility()));
			Consumer<CooldownComponent> var5 = (l0) -> {
			l0.getBonusManager().addBonus(BLACK_BOX_COOLDOWN_BONUS_UUID,"Black Box Cooldown Bonus",BonusOperation.ADD,80F);
		};
			((java.util.Optional)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var5));
			this.goalSelector.addGoal(2,(Goal)(new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(BrickBatAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(var3));
			AbilityHelper.forceCooldownOnAllAbilities((LivingEntity)(this),40);
		}
	}
	protected void registerGoals() {
		this.goalSelector.addGoal(0,(Goal)(new FloatGoal((Mob)(this))));
		this.goalSelector.addGoal(0,(Goal)(new OpenDoorGoal((Mob)(this),false)));
		this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.goalSelector.addGoal(3,(Goal)(new WaterAvoidingRandomStrollGoal((PathfinderMob)(this),0.8D)));
		this.goalSelector.addGoal(5,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
		this.goalSelector.addGoal(5,(Goal)(new RandomLookAroundGoal((Mob)(this))));
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.2800000011920929D).add(Attributes.ATTACK_DAMAGE,6D).add(Attributes.MAX_HEALTH,200D).add(Attributes.KNOCKBACK_RESISTANCE,1D).add(Attributes.ARMOR,2D);
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		var1.define(SHADOWS,Integer.valueOf(0));
	}
	public void remove(Entity.RemovalReason var1) {
		if (((this.getOwner()) != (null)) && ((this.getHealth()) <= (0F))){
			DoppelmanEntity var2 = this;
			Consumer<AbilityData> var3 = (l0) -> {
			DoppelmanAbility lv2_2 = ((DoppelmanAbility)(l0.getEquippedAbility(((AbilityCore)(DoppelmanAbility.INSTANCE.get())))));
			if ((lv2_2) != (null)){
				lv2_2.doppelmanDeathTrigger(this.getOwner());
			}
		};
			((java.util.Optional)(AbilityData.get(this.getOwner()))).ifPresent((Consumer)(var3));
		}
		super.remove(var1);
	}
	public InteractionResult mobInteract(Player var1, InteractionHand var2) {
		if ((var1) == (this.getOwner())){
			ItemStack var3 = var1.getItemInHand(var2);
			if (((var3) != (null)) && ((!(var3.isEmpty())) && (((var3.getItem()) == (ModItems.SHADOW.get())) && ((this.getShadows()) < (15))))){
				var3.shrink(1);
				this.addShadow();
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
	public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
		if (var1.equals(SHADOWS)){
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(var1);
	}
	public EntityDimensions getDefaultDimensions(Pose var1) {
		float var2 = (float)(this.getShadows());
		if ((var2) > (0F)){
			return this.getType().getDimensions().scale((1F) + ((var2) / (6F)));
		}else{
			return super.getDefaultDimensions(var1);
		}
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		var1.putInt("shadows",((Integer)(this.entityData.get(SHADOWS))).intValue());
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		this.entityData.set(SHADOWS,Integer.valueOf(var1.getInt("shadows")));
	}
	public void setOwner(LivingEntity var1) {
		super.setOwner(var1);
		this.unlockHakiOfOwner(var1);
	}
	public void addShadow() {
		this.entityData.set(SHADOWS,Integer.valueOf((((Integer)(this.entityData.get(SHADOWS))).intValue()) + (1)));
	}
	public void setShadow(int var1) {
		this.entityData.set(SHADOWS,Integer.valueOf(var1));
	}
	public int getShadows() {
		return ((Integer)(this.entityData.get(SHADOWS))).intValue();
	}
}