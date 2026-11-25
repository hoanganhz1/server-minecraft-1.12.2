package xyz.pixelatedw.mineminenomi.api.config.options;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

public class StringOption extends ConfigOption<String> {
	public StringOption(String var1, String var2, @Nullable() String var3) {
		super(var1,var2,var3);
	}
	protected ModConfigSpec.ConfigValue<String> buildValue(ModConfigSpec.Builder var1) {
		return (ModConfigSpec.ConfigValue<String>) (ModConfigSpec.ConfigValue) (var1.define(this.getTitle(),((String)(this.getDefault()))));
	}
}