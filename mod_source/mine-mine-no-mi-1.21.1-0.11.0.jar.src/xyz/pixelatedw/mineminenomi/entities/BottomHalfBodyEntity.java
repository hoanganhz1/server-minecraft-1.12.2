package xyz.pixelatedw.mineminenomi.entities;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
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
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class BottomHalfBodyEntity extends Mob implements TraceableEntity, IEntityWithComplexSpawn {
	// Fields
	private LivingEntity owner;
	private Ability parentAbility;

	public BottomHalfBodyEntity(EntityType<? extends BottomHalfBodyEntity> var1, Level var2) {
		super(var1,var2);
		this.parentAbility = null;
	}
	public BottomHalfBodyEntity(Level var1, LivingEntity var2) {
		this((EntityType<? extends BottomHalfBodyEntity>)(EntityType)(((EntityType)(ModMobs.BOTTOM_HALF.get()))),var1);
		this.setOwner(var2);
	}
	public void setParentAbility(Ability var1) {
		this.parentAbility = var1;
	}
	private void setOwner(LivingEntity var1) {
		this.owner = var1;
		EntityStatsData var2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(this)))).orElse((Object)(null))));
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))));
		var2.setFaction(((Faction)(((java.util.Optional)(var3.getFaction())).orElse((Object)(null)))));
		var2.setRace(((Race)(((java.util.Optional)(var3.getRace())).orElse((Object)(null)))));
		BottomHalfBodyEntity var4 = this;
		LivingEntity var5 = var1;
		final LivingEntity var5_f1 = var5;
		Consumer<Holder.Reference> var6 = (l0) -> {
			if (((var5_f1.getAttribute((Holder)(l0))) != (null)) && ((this.getAttribute((Holder)(l0))) != (null))){
				double lv1_3 = var5_f1.getAttribute((Holder)(l0)).getBaseValue();
				this.getAttribute((Holder)(l0)).setBaseValue(lv1_3);
			}
		};
		((java.util.stream.Stream)(((HolderLookup.RegistryLookup)(this.level().registryAccess().lookup(Registries.ATTRIBUTE).get())).listElements())).forEach((Consumer)(var6));
		this.setHealth(var1.getHealth());
	}
	@Nullable()
	public LivingEntity getOwner() {
		return this.owner;
	}
	public boolean hurt(DamageSource var1, float var2) {
		LivingEntity var3 = this.getOwner();
		if ((var3) == (null)){
			return false;
		}else{
			var3.hurt(ModDamageSources.getInstance((LevelAccessor)(this.level())).outOfBody(),var2);
			this.setHealth(var3.getHealth());
			return false;
		}
	}
	public void die(DamageSource var1) {
		LivingEntity var2 = this.getOwner();
		if ((var2) == (null)){
			return;
		}else{
			var2.hurt(ModDamageSources.getInstance((LevelAccessor)(this.level())).outOfBody(),var2.getMaxHealth());
			return;
		}
	}
	public void tick() {
		if (!(this.level().isClientSide)){
			LivingEntity var1 = this.getOwner();
			boolean var2 = false;
			if ((var1) != (null)){
				AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
				if ((var3) == (null)){
					return;
				}else{
					Ability var4 = ((Ability)(var3.getEquippedAbility(this.parentAbility)));
					if (((var4) != (null)) && (var4.isContinuous())){
						var2 = true;
						if (((!(AbilityHelper.isAffectedByWater((LivingEntity)(this)))) && (!(this.isPassenger()))) || ((this.getOwner()) == (null))){

						}else{
							((ContinuousComponent)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).get())).stopContinuity(this.getOwner());
						}
					}
				}
			}
			if (((var1) != (null)) && ((var1.isAlive()) && (((this.parentAbility) != (null)) && (var2)))){
				if ((this.distanceTo((Entity)(this.getOwner()))) > (5F)){
					this.getNavigation().moveTo((Entity)(this.getOwner()),4D);
				}
			}else{
				this.remove(Entity.RemovalReason.DISCARDED);
				return;
			}
		}
		super.tick();
	}
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH,100D).add(Attributes.KNOCKBACK_RESISTANCE,1D);
	}
	protected void handlePortal() {
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		int var2 = ((this.owner) != (null)) ? (1) : (0);
		var1.writeBoolean((var2) != (0));
		if ((var2) != (0)){
			var1.writeInt(this.owner.getId());
		}
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		if (var1.readBoolean()){
			int var2 = var1.readInt();
			Entity var3 = this.level().getEntity(var2);
			if (var3 instanceof LivingEntity){
				LivingEntity var4 = ((LivingEntity)(var3));
				this.owner = var4;
			}else{
				return;
			}
		}
	}
}