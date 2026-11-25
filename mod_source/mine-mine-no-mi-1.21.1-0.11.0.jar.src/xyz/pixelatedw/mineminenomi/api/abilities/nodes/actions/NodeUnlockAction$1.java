package xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;

public class NodeUnlockAction$1 implements NodeUnlockAction {
	// Fields
	final NodeUnlockCondition val$condition;
	final NodeUnlockAction val$ogAction;

	NodeUnlockAction$1(final NodeUnlockAction var1, final NodeUnlockCondition var2, final NodeUnlockAction var3) {
		this.val$condition = var2;
		this.val$ogAction = var3;
	}
	public void onUnlock(LivingEntity var1) {
		if (this.val$condition.test(var1)){
			this.val$ogAction.onUnlock(var1);
		}
	}
	public void onLock(LivingEntity var1) {
		if (this.val$condition.test(var1)){
			this.val$ogAction.onLock(var1);
		}
	}
}