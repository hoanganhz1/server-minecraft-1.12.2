package xyz.pixelatedw.mineminenomi.api.config.options;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

public class DoubleOption extends MinMaxOption<Double> {
	public DoubleOption(Double var1, Double var2, Double var3, String var4, @Nullable() String var5) {
		super(var1,var2,var3,var4,var5);
	}
	protected ModConfigSpec.ConfigValue<Double> buildValue(ModConfigSpec.Builder var1) {
		return var1.defineInRange(this.getTitle(),((Double)(this.getDefault())).doubleValue(),((Double)(this.getMin())).doubleValue(),((Double)(this.getMax())).doubleValue());
	}
}