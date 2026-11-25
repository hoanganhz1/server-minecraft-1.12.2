package xyz.pixelatedw.mineminenomi.entities;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class PhysicalBodyEntity extends Mob {
	// Fields
	private static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(PhysicalBodyEntity.class,EntityDataSerializers.OPTIONAL_UUID);
	private Ability parentAbility;

	public PhysicalBodyEntity(EntityType<? extends PhysicalBodyEntity> var1, Level var2) {
		super(var1,var2);
		this.parentAbility = null;
	}
	public PhysicalBodyEntity(Level var1) {
		this(((EntityType)(ModMobs.PHYSICAL_BODY.get())),var1);
	}
	public void setParentAbility(Ability var1) {
		this.parentAbility = var1;
	}
	public void setOwner(LivingEntity var1) {
		this.entityData.set(OWNER,Optional.of(var1.getUUID()));
		EntityStatsData var2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(this)))).orElse((Object)(null))));
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))));
		var2.setFaction(((Faction)(((java.util.Optional)(var3.getFaction())).orElse((Object)(null)))));
		var2.setRace(((Race)(((java.util.Optional)(var3.getRace())).orElse((Object)(null)))));
		PhysicalBodyEntity var4 = this;
		LivingEntity var5 = var1;
		final LivingEntity var5_f1 = var5;
		Consumer<Holder.Reference> var6 = (l0) -> {
			if (((var5_f1.getAttribute((Holder)(l0))) != (null)) && ((this.getAttribute((Holder)(l0))) != (null))){
				double lv1_3 = var5_f1.getAttribute((Holder)(l0)).getBaseValue();
				this.getAttribute((Holder)(l0)).setBaseValue(lv1_3);
			}
		};
		((java.util.stream.Stream)(((Registry)(var1.level().registryAccess().registry(Registries.ATTRIBUTE).get())).holders())).forEach((Consumer)(var6));
		this.setHealth(var1.getHealth());
	}
	@Nullable()
	public UUID getOwnerUUID() {
		return (((Optional)(this.getEntityData().get(OWNER))).isPresent()) ? (((UUID)(((Optional)(this.getEntityData().get(OWNER))).get()))) : (null);
	}
	@Nullable()
	public Player getOwner() {
		UUID var1 = this.getOwnerUUID();
		if ((var1) != (null)){
			return this.level().getPlayerByUUID(var1);
		}else{
			return null;
		}
	}
	public boolean hurt(DamageSource var1, float var2) {
		Player var3 = this.getOwner();
		if ((var3) == (null)){
			return false;
		}else{
			Entity var4 = var1.getEntity();
			if ((var4 instanceof LivingEntity) && ((((LivingEntity)(var4))) == (var3))){
				return false;
			}else{
				var3.hurt(ModDamageSources.getInstance((LevelAccessor)(this.level())).outOfBody(),var2);
				this.setHealth(var3.getHealth());
				return false;
			}
		}
	}
	public void die(DamageSource var1) {
		Player var2 = this.getOwner();
		if ((var2) == (null)){
			return;
		}else{
			var2.hurt(ModDamageSources.getInstance((LevelAccessor)(this.level())).outOfBody(),var2.getMaxHealth());
			return;
		}
	}
	public void tick() {
		if (!(this.level().isClientSide)){
			Player var1 = this.getOwner();
			boolean var2 = false;
			if ((var1) != (null)){
				AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var1)))).orElse((Object)(null))));
				if ((var3) == (null)){
					return;
				}else{
					Ability var4 = ((Ability)(var3.getEquippedAbility(this.parentAbility)));
					if (((var4) != (null)) && (var4.isContinuous())){
						var2 = true;
						if (((!(AbilityHelper.isAffectedByWater((LivingEntity)(this)))) && (!(this.isPassenger()))) || ((this.getOwner()) == (null))){

						}else{
							((ContinuousComponent)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).get())).stopContinuity((LivingEntity)(this.getOwner()));
						}
					}
				}
			}
			if (((var1) != (null)) && ((var1.isAlive()) && (((this.parentAbility) != (null)) && (var2)))){

			}else{
				this.remove(Entity.RemovalReason.DISCARDED);
				return;
			}
		}
		super.tick();
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		super.defineSynchedData(var1);
		var1.define(OWNER,Optional.empty());
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.MAX_HEALTH,100D).add(Attributes.KNOCKBACK_RESISTANCE,1D);
	}
	protected void handlePortal() {
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		if ((this.entityData.get(OWNER)) != (null)){
			var1.putString("OwnerUUID",((UUID)(((Optional)(this.entityData.get(OWNER))).get())).toString());
		}
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		this.entityData.set(OWNER,Optional.of(UUID.fromString(var1.getString("OwnerUUID"))));
	}
	public boolean shouldRenderAtSqrDistance(double var1) {
		double var2 = this.getBoundingBox().getSize();
		if (Double.isNaN(var2)){
			var2 = 1D;
		}
		double var3 = (var2) * ((128D) * (Entity.getViewScale()));
		return (var1) < ((var3) * (var3));
	}
}