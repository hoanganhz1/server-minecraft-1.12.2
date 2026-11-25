package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class ChargingUrsusShockEntity extends Entity {
	// Fields
	protected static final EntityDataAccessor<Float> CHARGE = SynchedEntityData.defineId(ChargingUrsusShockEntity.class,EntityDataSerializers.FLOAT);
	private LivingEntity owner;

	public ChargingUrsusShockEntity(EntityType<? extends ChargingUrsusShockEntity> var1, Level var2) {
		super(var1,var2);
	}
	public ChargingUrsusShockEntity(Level var1) {
		super(((EntityType)(ModProjectiles.CHARGING_URSUS_SHOCK.get())),var1);
	}
	public void tick() {
		super.tick();
		if (!(this.level().isClientSide)){
			if ((this.owner) == (null)){
				this.remove(Entity.RemovalReason.DISCARDED);
				return;
			}else{
				if ((this.tickCount) > (600)){
					this.remove(Entity.RemovalReason.DISCARDED);
					return;
				}else{
					this.moveTo(this.owner.getX(),(this.owner.getEyeY()) + ((double)((0.2F) * (this.getCharge()))),this.owner.getZ(),this.owner.getYRot(),0F);
				}
			}
		}
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		var1.define(CHARGE,Float.valueOf(0F));
	}
	public void setCharge(float var1) {
		this.entityData.set(CHARGE,Float.valueOf(var1));
	}
	public float getCharge() {
		return ((Float)(this.entityData.get(CHARGE))).floatValue();
	}
	public void setOwner(LivingEntity var1) {
		this.owner = var1;
	}
	protected void readAdditionalSaveData(CompoundTag var1) {
	}
	protected void addAdditionalSaveData(CompoundTag var1) {
	}
}