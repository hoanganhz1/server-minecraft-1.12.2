package xyz.pixelatedw.mineminenomi.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nConfig;

public class CommonConfig {
	// Fields
	public static final ModConfigSpec SPEC;

	private static void setupConfig(ModConfigSpec.Builder var0) {
		var0.translation(ModI18nConfig.SYSTEM_CATEGORY).push("System");
		SystemConfig.MASTER_COMMAND.createValue(var0);
		SystemConfig.ENABLE_PERMISSIONS.createValue(var0);
		SystemConfig.ENABLE_DEBUG_COMMANDS.createValue(var0);
		var0.pop();
	}
	static  {
		ModConfigSpec.Builder var0 = new ModConfigSpec.Builder();
		setupConfig(var0);
		SPEC = var0.build();
	}
}