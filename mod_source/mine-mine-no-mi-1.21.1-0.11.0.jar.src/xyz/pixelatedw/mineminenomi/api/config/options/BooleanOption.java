package xyz.pixelatedw.mineminenomi.api.config.options;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

public class BooleanOption extends ConfigOption<Boolean> {
	public BooleanOption(Boolean var1, String var2, @Nullable() String var3) {
		super(var1,var2,var3);
	}
	protected ModConfigSpec.BooleanValue buildValue(ModConfigSpec.Builder var1) {
		return var1.define(this.getTitle(),((Boolean)(this.getDefault())).booleanValue());
	}
}