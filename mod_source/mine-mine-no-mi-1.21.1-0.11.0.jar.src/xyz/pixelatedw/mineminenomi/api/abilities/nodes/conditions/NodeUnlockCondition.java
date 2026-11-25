package xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions;

import java.util.Objects;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;

public interface NodeUnlockCondition {
	public abstract boolean test(LivingEntity var0);
	public default NodeUnlockCondition not() {
		NodeUnlockCondition var1 = this;
		return new NodeUnlockCondition$1(this,var1);
	}
	public default NodeUnlockCondition or(NodeUnlockCondition var1) {
		Objects.requireNonNull(var1);
		NodeUnlockCondition var2 = this;
		return new NodeUnlockCondition$2(this,var2,var1);
	}
	public default NodeUnlockCondition and(NodeUnlockCondition var1) {
		Objects.requireNonNull(var1);
		NodeUnlockCondition var2 = this;
		return new NodeUnlockCondition$3(this,var2,var1);
	}
	public abstract MutableComponent getTooltip();
}