package xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;

public class NodeUnlockCondition$1 implements NodeUnlockCondition {
	// Fields
	final NodeUnlockCondition val$ogCondition;

	NodeUnlockCondition$1(final NodeUnlockCondition var1, final NodeUnlockCondition var2) {
		this.val$ogCondition = var2;
	}
	public boolean test(LivingEntity var1) {
		return !(this.val$ogCondition.test(var1));
	}
	public MutableComponent getTooltip() {
		return this.val$ogCondition.getTooltip();
	}
}