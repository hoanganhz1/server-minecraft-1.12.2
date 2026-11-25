package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.neoforged.neoforge.entity.PartEntity;

public class SeaCowEntity$SeaCowPartEntity extends PartEntity<SeaCowEntity> {
	// Fields
	public final SeaCowEntity parentMob;
	public final String name;
	private final EntityDimensions size;

	public SeaCowEntity$SeaCowPartEntity(SeaCowEntity var1, String var2, float var3, float var4) {
		super((Entity)(var1));
		this.size = EntityDimensions.scalable(var3,var4);
		this.refreshDimensions();
		this.parentMob = var1;
		this.name = var2;
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
	}
	protected void readAdditionalSaveData(CompoundTag var1) {
	}
	protected void addAdditionalSaveData(CompoundTag var1) {
	}
	public boolean isPickable() {
		return true;
	}
	public boolean hurt(DamageSource var1, float var2) {
		return (!(this.isInvulnerableTo(var1))) && (this.parentMob.hurt(this,var1,var2));
	}
	public boolean is(Entity var1) {
		return ((this) == (var1)) || ((this.parentMob) == (var1));
	}
	public EntityDimensions getDimensions(Pose var1) {
		return this.size;
	}
}