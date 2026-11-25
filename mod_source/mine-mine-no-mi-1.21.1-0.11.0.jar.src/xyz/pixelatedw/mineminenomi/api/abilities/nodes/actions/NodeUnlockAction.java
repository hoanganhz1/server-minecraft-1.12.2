package xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions;

import java.util.Objects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;

public interface NodeUnlockAction {
	public abstract void onUnlock(LivingEntity var0);
	public abstract void onLock(LivingEntity var0);
	public default NodeUnlockAction onlyIf(NodeUnlockCondition var1) {
		Objects.requireNonNull(var1);
		NodeUnlockAction var2 = this;
		return new NodeUnlockAction$1(this,var1,var2);
	}
	public default NodeUnlockAction andThen(NodeUnlockAction var1) {
		Objects.requireNonNull(var1);
		NodeUnlockAction var2 = this;
		return new NodeUnlockAction$2(this,var2,var1);
	}
}