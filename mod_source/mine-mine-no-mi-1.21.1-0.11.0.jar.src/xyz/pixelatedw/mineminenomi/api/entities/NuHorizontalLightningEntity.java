package xyz.pixelatedw.mineminenomi.api.entities;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;

public abstract class NuHorizontalLightningEntity extends NuLightningEntity {
	// Fields
	private Vec3 offset;
	private boolean followOwner;

	public NuHorizontalLightningEntity(EntityType<? extends NuHorizontalLightningEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.offset = Vec3.ZERO;
	}
	public NuHorizontalLightningEntity(EntityType<? extends NuHorizontalLightningEntity> var1, LivingEntity var2, float var3, float var4, IAbility var5) {
		this(var1,var2,var2.getX(),var2.getY(),var2.getZ(),var3,var4,var5,var5.getCore().getSourceElement(),var5.getCore().getSourceHakiNature(),var5.getCore().getSourceTypesArray());
	}
	public NuHorizontalLightningEntity(EntityType<? extends NuHorizontalLightningEntity> var1, LivingEntity var2, double var3, double var4, double var5, float var6, float var7, @Nullable() IAbility var8, SourceElement var9, SourceHakiNature var10, SourceType... var11) {
		super((EntityType)(var1),var2,var3,var4,var5,var2.getYRot(),var2.getXRot(),var6,var7,var8,var9,var10,var11);
		this.offset = Vec3.ZERO;
		this.offset(var2,Vec3.ZERO);
	}
	protected boolean shouldResetForLookChange(Vec3 var1) {
		if (this.canRetract()){
			return false;
		}else{
			if ((this.lookVec) == (Vec3.ZERO)){
				return false;
			}else{
				double var2 = Mth.clamp(this.lookVec.normalize().dot(var1.normalize()),-1D,1D);
				double var3 = Math.sqrt((1D) - ((var2) * (var2)));
				double var4 = (double)(super.getLength());
				return ((var4) * (var3)) >= ((Math.max((double)(super.getSize()),0.1D)) * (7D));
			}
		}
	}
	public void tick() {
		if (!(this.level().isClientSide)){
			LivingEntity var1 = super.getOwner();
			if (((this.getOwner()) != (null)) && (this.followOwner)){
				double var2 = Mth.lerp(0D,var1.xo,var1.getX());
				double var3 = (Mth.lerp(0D,var1.yo,var1.getY())) + ((double)(var1.getEyeHeight()));
				double var4 = Mth.lerp(0D,var1.zo,var1.getZ());
				float var5 = (var1.getViewYRot(0F)) % (360F);
				float var6 = (var1.getViewXRot(0F)) % (360F);
				Vec3 var7 = VectorHelper.getRelativeOffset(new Vec3(var2,var3,var4),var5,var6,this.offset);
				this.moveTo(var7.x,var7.y,var7.z,var5,var6);
				this.lookVec = var1.getLookAngle().normalize();
			}
		}
		super.tick();
	}
	public void offsetToHand(LivingEntity var1) {
		float var2 = ((var1.getMainArm()) == (HumanoidArm.RIGHT)) ? (-0.75F) : (0.75F);
		this.offset(var1,new Vec3((double)(var2),-0.25D,0D));
	}
	public void offset(LivingEntity var1, Vec3 var2) {
		this.offset = var2;
		Vec3 var3 = VectorHelper.getRelativeOffset(var1.position(),var1.getYHeadRot(),this.offset);
		this.moveTo(var3.x,(var3.y) + ((double)(var1.getEyeHeight())),var3.z,var1.getYRot(),var1.getXRot());
	}
	public void setFollowOwner() {
		this.followOwner = true;
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		super.writeSpawnData(var1);
		var1.writeDouble(this.offset.x);
		var1.writeDouble(this.offset.y);
		var1.writeDouble(this.offset.z);
		var1.writeBoolean(this.followOwner);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		super.readSpawnData(var1);
		double var2 = var1.readDouble();
		double var3 = var1.readDouble();
		double var4 = var1.readDouble();
		this.offset = new Vec3(var2,var3,var4);
		this.followOwner = var1.readBoolean();
	}
}