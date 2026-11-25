package xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions;

import net.minecraft.world.entity.LivingEntity;

public class NodeUnlockAction$2 implements NodeUnlockAction {
	// Fields
	final NodeUnlockAction val$ogAction;
	final NodeUnlockAction val$after;

	NodeUnlockAction$2(final NodeUnlockAction var1, final NodeUnlockAction var2, final NodeUnlockAction var3) {
		this.val$ogAction = var2;
		this.val$after = var3;
	}
	public void onUnlock(LivingEntity var1) {
		this.val$ogAction.onUnlock(var1);
		this.val$after.onUnlock(var1);
	}
	public void onLock(LivingEntity var1) {
		this.val$ogAction.onLock(var1);
		this.val$after.onLock(var1);
	}
}