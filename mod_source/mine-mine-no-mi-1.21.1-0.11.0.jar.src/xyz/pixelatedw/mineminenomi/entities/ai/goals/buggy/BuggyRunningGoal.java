package xyz.pixelatedw.mineminenomi.entities.ai.goals.buggy;

import xyz.pixelatedw.mineminenomi.entities.ai.goals.RunAroundTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.BuggyEntity;

public class BuggyRunningGoal extends RunAroundTargetGoal<BuggyEntity> {
	public BuggyRunningGoal(BuggyEntity var1, double var2) {
		super(var1,var2,200,20);
	}
	public boolean canUse() {
		if (!(super.canUse())){
			return false;
		}else{
			return ((BuggyEntity)(this.entity)).shouldRun();
		}
	}
	public boolean canContinueToUse() {
		if (!(super.canContinueToUse())){
			return false;
		}else{
			return ((BuggyEntity)(this.entity)).shouldRun();
		}
	}
}