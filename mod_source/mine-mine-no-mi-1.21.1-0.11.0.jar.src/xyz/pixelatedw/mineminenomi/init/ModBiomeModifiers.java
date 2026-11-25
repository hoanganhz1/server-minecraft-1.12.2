package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.data.worldgen.BootstrapContext;
import net.neoforged.neoforge.common.world.BiomeModifier;
import xyz.pixelatedw.mineminenomi.world.spawners.SpawnsBiomeModifiers;

public class ModBiomeModifiers {
	public static void bootstrap(BootstrapContext<BiomeModifier> var0) {
		SpawnsBiomeModifiers.bootstrap(var0);
	}
}