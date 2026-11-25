package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BaraFestivalEntity extends Entity implements TraceableEntity, IEntityWithComplexSpawn {
	// Fields
	private LivingEntity owner;
	private LivingEntity target;

	public BaraFestivalEntity(EntityType<? extends BaraFestivalEntity> var1, Level var2) {
		super(var1,var2);
	}
	public BaraFestivalEntity(Level var1, LivingEntity var2) {
		super(((EntityType)(ModProjectiles.BARA_FESTIVAL.get())),var1);
		this.owner = var2;
		this.setNoGravity(true);
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
	}
	public void tick() {
		if (!(this.level().isClientSide)){
			if (((this.getOwner()) != (null)) && (!(this.getOwner().isAlive()))){
				this.remove(Entity.RemovalReason.DISCARDED);
				return;
			}else{
				if (((this.target) != (null)) && (this.target.isAlive())){
					this.teleportTo(this.target.getX(),this.target.getY(),this.target.getZ());
				}else{
					this.remove(Entity.RemovalReason.DISCARDED);
					return;
				}
			}
		}
		super.tick();
	}
	public void setTarget(LivingEntity var1) {
		this.target = var1;
	}
	@Nullable()
	public LivingEntity getOwner() {
		return this.owner;
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean shouldRender(double var1, double var2, double var3) {
		return true;
	}
	protected void readAdditionalSaveData(CompoundTag var1) {
	}
	protected void addAdditionalSaveData(CompoundTag var1) {
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