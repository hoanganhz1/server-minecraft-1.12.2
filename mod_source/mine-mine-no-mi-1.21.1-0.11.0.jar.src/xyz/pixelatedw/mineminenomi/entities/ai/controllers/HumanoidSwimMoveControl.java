package xyz.pixelatedw.mineminenomi.entities.ai.controllers;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class HumanoidSwimMoveControl extends MoveControl {
	// Fields
	private final Mob entity;
	private final HumanoidSwimMoveControl$Style style;

	public HumanoidSwimMoveControl(Mob var1) {
		this(var1,HumanoidSwimMoveControl$Style.AGGRESSIVE);
	}
	public HumanoidSwimMoveControl(Mob var1, HumanoidSwimMoveControl$Style var2) {
		super(var1);
		this.entity = var1;
		this.style = var2;
	}
	public void tick() {
		AbilityHelper.setDeltaMovement((Entity)(this.entity),this.entity.getDeltaMovement().add(0D,0.005D,0D));
		if ((this.entity.getTarget()) != (null)){
			if (((this.entity.getTarget().getY()) - (this.entity.getY())) > (2D)){
				AbilityHelper.setDeltaMovement((Entity)(this.entity),this.entity.getDeltaMovement().add(0D,0.05D,0D));
			}else{
				if (((this.entity.getY()) - (this.entity.getTarget().getY())) > (2D)){
					AbilityHelper.setDeltaMovement((Entity)(this.entity),this.entity.getDeltaMovement().add(0D,-0.05D,0D));
				}
			}
		}
		if (((this.operation) == (MoveControl.Operation.MOVE_TO)) && (!(this.entity.getNavigation().isDone()))){
			double var1 = (this.wantedX) - (this.entity.getX());
			double var2 = (this.wantedY) - (this.entity.getY());
			double var3 = (this.wantedZ) - (this.entity.getZ());
			double var4 = (((var1) * (var1)) + ((var2) * (var2))) + ((var3) * (var3));
			if ((this.style) == (HumanoidSwimMoveControl$Style.AGGRESSIVE)){
				if ((var4) < (2.5D)){
					this.mob.setZza(0F);
				}else{
					GoalHelper.lookAtEntity(this.entity,(Entity)(this.entity.getTarget()));
					float var5 = (float)((this.speedModifier) * (this.entity.getAttributeValue(NeoForgeMod.SWIM_SPEED)));
					this.entity.setSpeed(var5);
				}
			}else{
				if ((this.style) == (HumanoidSwimMoveControl$Style.STALLING)){
					return;
				}else{
					return;
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