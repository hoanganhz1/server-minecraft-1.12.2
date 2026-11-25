package xyz.pixelatedw.mineminenomi.entities.ai.controllers;

import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class FishSwimMoveController<T extends Mob> extends MoveControl {
	// Fields
	private final T entity;

	public FishSwimMoveController(T var1) {
		super(var1);
		this.entity = var1;
	}
	public void tick() {
		if (this.entity.isInWater()){
			AbilityHelper.setDeltaMovement((Entity)(this.entity),this.entity.getDeltaMovement().add(0D,0.005D,0D));
		}
		if (((this.operation) == (MoveControl.Operation.MOVE_TO)) && (!(this.entity.getNavigation().isDone()))){
			double var1 = (this.wantedX) - (this.entity.getX());
			double var2 = (this.wantedY) - (this.entity.getY());
			double var3 = (this.wantedZ) - (this.entity.getZ());
			if (((((var1) * (var1)) + ((var2) * (var2))) + ((var3) * (var3))) < (2.500000277905201e-07D)){
				this.mob.setZza(0F);
			}else{
				float var4 = ((float)((Mth.atan2(var3,var1)) * (57.2957763671875D))) - (90F);
				this.entity.setYRot(this.rotlerp(this.entity.getYRot(),var4,10F));
				this.entity.yBodyRot = this.entity.getYRot();
				this.entity.yHeadRot = this.entity.getYRot();
				float var5 = (float)((this.speedModifier) * (this.entity.getAttributeValue(Attributes.MOVEMENT_SPEED)));
				if (this.entity.isInWater()){
					this.entity.setSpeed((var5) * (0.02F));
					float var6 = -((float)((Mth.atan2(var2,Math.sqrt(((var1) * (var1)) + ((var3) * (var3))))) * (57.2957763671875D)));
					var6 = Mth.clamp(Mth.wrapDegrees(var6),-85F,85F);
					this.entity.setXRot(this.rotlerp(this.entity.getXRot(),var6,5F));
					float var7 = Mth.cos((this.entity.getXRot()) * (0.017453292F));
					float var8 = Mth.sin((this.entity.getXRot()) * (0.017453292F));
					this.entity.zza = (var7) * (var5);
					this.entity.yya = (-(var8)) * (var5);
				}else{
					this.entity.setSpeed((var5) * (0.1F));
				}
			}
		}else{
			this.entity.setSpeed(0F);
			this.entity.setXxa(0F);
			this.entity.setYya(0F);
			this.entity.setZza(0F);
		}
	}
}