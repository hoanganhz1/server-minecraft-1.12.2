package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

@FunctionalInterface()
public interface IRangeCheck {
	public abstract boolean test(Player var0, LivingEntity var1, float var2);
	public default IRangeCheck and(IRangeCheck var1) {
		IRangeCheck var2 = this;
		IRangeCheck var3 = var1;
		final IRangeCheck var3_f1 = var3;
		IRangeCheck var4 = (l0, l1, l2) -> {
			return (this.test(l0,l1,l2)) && (var3_f1.test(l0,l1,l2));
		};
		return var4;
	}
	public default IRangeCheck or(IRangeCheck var1) {
		IRangeCheck var2 = this;
		IRangeCheck var3 = var1;
		final IRangeCheck var3_f1 = var3;
		IRangeCheck var4 = (l0, l1, l2) -> {
			return (this.test(l0,l1,l2)) || (var3_f1.test(l0,l1,l2));
		};
		return var4;
	}
	public default IRangeCheck negate() {
		IRangeCheck var1 = this;
		IRangeCheck var2 = (l0, l1, l2) -> {
			return !(this.test(l0,l1,l2));
		};
		return var2;
	}
}