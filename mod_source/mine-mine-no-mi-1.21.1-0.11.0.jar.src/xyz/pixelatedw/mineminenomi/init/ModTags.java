package xyz.pixelatedw.mineminenomi.init;

import xyz.pixelatedw.mineminenomi.MineMineNoMi;

public class ModTags {
	public static void init() {
		ModTags$Blocks.init();
		ModTags$Items.init();
		ModTags$Entities.init();
		ModTags$DamageTypes.init();
		ModTags$Structures.init();
		ModTags$Biomes.init();
		if (MineMineNoMi.hasCuriosInstalled()){
			ModTags$CurioItems.init();
		}
	}
}