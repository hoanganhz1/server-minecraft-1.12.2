package xyz.pixelatedw.mineminenomi.entities;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;

public class CloudEntity extends Entity implements IEntityWithComplexSpawn, TraceableEntity {
	// Fields
	private int life;
	private UUID ownerUUID;
	private LivingEntity owner;
	private EntityDimensions size;

	public CloudEntity(EntityType<? extends CloudEntity> var1, Level var2) {
		super(var1,var2);
		this.life = 100;
		this.size = null;
	}
	public CloudEntity(EntityType<? extends CloudEntity> var1, Level var2, LivingEntity var3) {
		super(var1,var2);
		this.life = 100;
		this.size = null;
		this.setOwner(var3);
	}
	public void tick() {
		super.tick();
		if (!(this.level().isClientSide)){
			if ((this.owner) == (null)){
				this.remove(Entity.RemovalReason.DISCARDED);
				return;
			}else{
				if ((this.life) <= (0)){
					this.remove(Entity.RemovalReason.DISCARDED);
				}
				this.life = (this.life) - (1);
			}
		}
	}
	public void setOwner(LivingEntity var1) {
		this.owner = var1;
		if ((this.owner) != (null)){
			this.ownerUUID = var1.getUUID();
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
	public int getLife() {
		return this.life;
	}
	public void setLife(int var1) {
		this.life = var1;
	}
	public void setSize(EntityDimensions var1) {
		this.size = var1;
		this.refreshDimensions();
	}
	public EntityDimensions getDimensions(Pose var1) {
		if ((this.size) == (null)){
			return super.getDimensions(var1);
		}else{
			return this.size;
		}
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
	}
	protected void readAdditionalSaveData(CompoundTag var1) {
	}
	protected void addAdditionalSaveData(CompoundTag var1) {
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		int var2 = ((this.getOwner()) != (null)) ? (1) : (0);
		var1.writeBoolean((var2) != (0));
		if ((var2) != (0)){
			var1.writeInt(this.getOwner().getId());
		}
		var1.writeFloat(((this.size) != (null)) ? (this.size.width()) : (this.getType().getDimensions().width()));
		var1.writeFloat(((this.size) != (null)) ? (this.size.height()) : (this.getType().getDimensions().height()));
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		if (var1.readBoolean()){
			int var2 = var1.readInt();
			Entity var3 = this.level().getEntity(var2);
			if (var3 instanceof LivingEntity){
				this.setOwner(((LivingEntity)(var3)));
			}
		}
		float var2_1 = var1.readFloat();
		this.setSize(EntityDimensions.fixed(var2_1,var1.readFloat()));
	}
}