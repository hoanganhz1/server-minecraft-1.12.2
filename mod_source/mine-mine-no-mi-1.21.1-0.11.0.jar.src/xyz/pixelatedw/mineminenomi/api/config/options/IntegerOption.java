package xyz.pixelatedw.mineminenomi.api.config.options;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

public class IntegerOption extends MinMaxOption<Integer> {
	public IntegerOption(Integer var1, Integer var2, Integer var3, String var4, @Nullable() String var5) {
		super(var1,var2,var3,var4,var5);
	}
	protected ModConfigSpec.ConfigValue<Integer> buildValue(ModConfigSpec.Builder var1) {
		return var1.defineInRange(this.getTitle(),((Integer)(this.getDefault())).intValue(),((Integer)(this.getMin())).intValue(),((Integer)(this.getMax())).intValue());
	}
}