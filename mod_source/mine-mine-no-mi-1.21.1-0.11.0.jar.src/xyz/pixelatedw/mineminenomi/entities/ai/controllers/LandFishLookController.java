package xyz.pixelatedw.mineminenomi.entities.ai.controllers;

import java.util.Optional;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class LandFishLookController<T extends Mob> extends SmoothSwimmingLookControl {
	public LandFishLookController(T var1, int var2) {
		super(var1,var2);
	}
	public void tick() {
		if (WyHelper.isAprilFirst()){
			if (this.resetXRotOnTick()){
				this.mob.setXRot(0F);
			}
			if ((this.lookAtCooldown) > (0)){
				this.lookAtCooldown = (this.lookAtCooldown) - (1);
				if (this.getYRotD().isPresent()){
					this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot,((Float)(this.getYRotD().get())).floatValue(),this.yMaxRotSpeed);
				}
				if (this.getXRotD().isPresent()){
					this.mob.setXRot(this.rotateTowards(this.mob.getXRot(),((Float)(this.getXRotD().get())).floatValue(),this.xMaxRotAngle));
				}
			}else{
				this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot,this.mob.yBodyRot,10F);
			}
			if (!(this.mob.getNavigation().isDone())){
				this.mob.yHeadRot = Mth.rotateIfNecessary(this.mob.yHeadRot,this.mob.yBodyRot,(float)(this.mob.getMaxHeadYRot()));
			}
			return;
		}else{
			super.tick();
			return;
		}
	}
}