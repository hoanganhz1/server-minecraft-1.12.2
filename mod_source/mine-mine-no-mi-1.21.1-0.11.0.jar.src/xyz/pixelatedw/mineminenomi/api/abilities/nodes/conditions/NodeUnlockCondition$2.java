package xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;

public class NodeUnlockCondition$2 implements NodeUnlockCondition {
	// Fields
	final NodeUnlockCondition val$ogCondition;
	final NodeUnlockCondition val$other;

	NodeUnlockCondition$2(final NodeUnlockCondition var1, final NodeUnlockCondition var2, final NodeUnlockCondition var3) {
		this.val$ogCondition = var2;
		this.val$other = var3;
	}
	public boolean test(LivingEntity var1) {
		return (this.val$ogCondition.test(var1)) || (this.val$other.test(var1));
	}
	public MutableComponent getTooltip() {
		MutableComponent var1 = this.val$ogCondition.getTooltip();
		MutableComponent var2 = this.val$other.getTooltip();
		if (((var2) != (null)) && (!(var2.equals(Component.empty())))){
			var1.append((Component)(Component.literal("\nOr\n")));
			var1.append((Component)(this.val$other.getTooltip()));
		}
		return var1;
	}
}