package xyz.pixelatedw.mineminenomi.api.config.options;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

public class LongOption extends MinMaxOption<Long> {
	public LongOption(Long var1, Long var2, Long var3, String var4, @Nullable() String var5) {
		super(var1,var2,var3,var4,var5);
	}
	protected ModConfigSpec.ConfigValue<Long> buildValue(ModConfigSpec.Builder var1) {
		return var1.defineInRange(this.getTitle(),((Long)(this.getDefault())).longValue(),((Long)(this.getMin())).longValue(),((Long)(this.getMax())).longValue());
	}
}