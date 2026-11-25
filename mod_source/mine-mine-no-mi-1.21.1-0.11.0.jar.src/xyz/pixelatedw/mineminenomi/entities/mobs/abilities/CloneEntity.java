package xyz.pixelatedw.mineminenomi.entities.mobs.abilities;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiEmissionAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.FactionHurtByTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public abstract class CloneEntity extends PathfinderMob implements IEntityWithComplexSpawn, TraceableEntity {
	// Fields
	protected EntityStatsData props;
	private LivingEntity owner;
	private UUID ownerUUID;
	private int maxAliveTicks;
	private boolean usesOwnerTexture;
	private boolean permaPanic;

	public CloneEntity(EntityType<? extends CloneEntity> var1, Level var2) {
		super(var1,var2);
		this.maxAliveTicks = 200;
		this.permaPanic = true;
	}
	public CloneEntity(EntityType<? extends CloneEntity> var1, Level var2, LivingEntity var3) {
		super(var1,var2);
		this.maxAliveTicks = 200;
		this.permaPanic = true;
		this.xpReward = 0;
		this.props = EntityStatsData.getOrCreate((LivingEntity)(this));
		this.setOwner(var3);
		if (((var2) != (null)) && ((!(var2.isClientSide)) && ((this.props) != (null)))){
			this.props.setHeart(false);
			this.props.setShadow(true);
			this.setSprinting(true);
		}
	}
	public void disablePermaPanic() {
		this.permaPanic = false;
	}
	public boolean isPermaPanic() {
		return this.permaPanic;
	}
	public void setUseOwnerTexture() {
		this.usesOwnerTexture = true;
	}
	public boolean isUsingOwnerTexture() {
		return this.usesOwnerTexture;
	}
	public void setMaxAliveTicks(int var1) {
		this.maxAliveTicks = var1;
	}
	public void copyOwnerEquipment() {
		if (((this.getOwner()) != (null)) && (this.getOwner().isAlive())){
			EquipmentSlot[] var1 = EquipmentSlot.values();
			int var2 = var1.length;
			int var3 = 0;
			do{
				if ((var3) < (var2)){
					EquipmentSlot var4 = var1[var3];
					this.setItemSlot(var4,this.getOwner().getItemBySlot(var4));
					var3++;
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}else{
			return;
		}
	}
	protected void registerGoals() {
		this.goalSelector.addGoal(1,(Goal)(new PanicGoal((PathfinderMob)(this),1.25D)));
	}
	public void tick() {
		super.tick();
		if (!(this.level().isClientSide)){
			if (((this.getOwner()) != (null)) && ((this.props) != (null))){
				if (this.isPermaPanic()){
					this.setLastHurtByMob(this.getOwner());
				}
				if (((this.maxAliveTicks) > (0)) && ((this.tickCount) >= (this.maxAliveTicks))){
					this.discard();
				}else{
					return;
				}
			}else{
				this.discard();
				return;
			}
		}
	}
	public void aiStep() {
		this.updateSwingTime();
		super.aiStep();
	}
	public void setOwner(LivingEntity var1) {
		this.owner = var1;
		this.ownerUUID = var1.getUUID();
		if ((this.props) == (null)){
			return;
		}else{
			Function<EntityStatsData, Faction> var2 = (l0) -> {
			return ((Faction)(((java.util.Optional)(l0.getFaction())).orElse((Object)(null))));
		};
			Faction var3 = ((Faction)(((java.util.Optional)(EntityStatsData.get(var1))).map((Function)(var2)).orElse(null)));
			this.props.setFaction(var3);
			Predicate var4 = ModEntityPredicates.getEnemyFactions((LivingEntity)(this));
			CloneEntity var5 = this;
			Predicate<LivingEntity> var6 = (l0) -> {
			return FactionHelper.isEnemyFactions((LivingEntity)(this),l0);
		};
			Predicate<LivingEntity> var7 = var6;
			this.targetSelector.addGoal(1,(Goal)(new FactionHurtByTargetGoal((PathfinderMob)(this),var4,new Class[0])));
			this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),LivingEntity.class,10,true,true,var7)));
			return;
		}
	}
	public void unlockHakiOfOwner(LivingEntity var1) {
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			if (var2.hasUnlockedAbility(((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))){
				this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
			}
			if (var2.hasUnlockedAbility(((AbilityCore)(BusoshokuHakiEmissionAbility.INSTANCE.get())))){
				this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiEmissionAbility.INSTANCE.get())))));
			}
			if (var2.hasUnlockedAbility(((AbilityCore)(BusoshokuHakiHardeningAbility.INSTANCE.get())))){
				this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiHardeningAbility.INSTANCE.get())))));
			}
			if (var2.hasUnlockedAbility(((AbilityCore)(BusoshokuHakiImbuingAbility.INSTANCE.get())))){
				this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiImbuingAbility.INSTANCE.get())))));
			}
			return;
		}
	}
	@Nullable()
	public LivingEntity getOwner() {
		if (((this.owner) == (null)) && ((this.ownerUUID) != (null))){
			Level var1 = this.level();
			if (var1 instanceof ServerLevel){
				Entity var2 = ((ServerLevel)(var1)).getEntity(this.ownerUUID);
				if (var2 instanceof LivingEntity){
					this.owner = ((LivingEntity)(var2));
				}
			}
		}
		return this.owner;
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		if ((this.ownerUUID) != (null)){
			var1.putUUID("ownerId",this.ownerUUID);
		}
		var1.putBoolean("isTextured",this.usesOwnerTexture);
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		if (var1.contains("ownerId")){
			this.ownerUUID = var1.getUUID("ownerId");
		}
		this.usesOwnerTexture = var1.getBoolean("isTextured");
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		int var2 = ((this.owner) != (null)) ? (1) : (0);
		var1.writeBoolean((var2) != (0));
		if ((var2) != (0)){
			var1.writeInt(this.owner.getId());
		}
		var1.writeBoolean(this.usesOwnerTexture);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		if (var1.readBoolean()){
			int var2 = var1.readInt();
			Entity var3 = this.level().getEntity(var2);
			if (var3 instanceof LivingEntity){
				this.setOwner(((LivingEntity)(var3)));
			}
		}
		this.usesOwnerTexture = var1.readBoolean();
	}
}