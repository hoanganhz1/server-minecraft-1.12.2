package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ModTags$Structures {
	// Fields
	public static final TagKey<Structure> PIRATE = tag("pirate");
	public static final TagKey<Structure> MARINE = tag("marine");
	public static final TagKey<Structure> BANDIT = tag("bandit");
	public static final TagKey<Structure> REVOLUTIONARY = tag("revolutionary");
	public static final TagKey<Structure> IS_SHIP = tag("is_ship");
	public static final TagKey<Structure> IS_SMALL_SHIP = tag("is_small_ship");
	public static final TagKey<Structure> IS_MEDIUM_SHIP = tag("is_medium_ship");
	public static final TagKey<Structure> IS_LARGE_SHIP = tag("is_large_ship");
	public static final TagKey<Structure> IS_CAMP = tag("is_camp");
	public static final TagKey<Structure> IS_SMALL_BASE = tag("is_small_base");
	public static final TagKey<Structure> IS_LARGE_BASE = tag("is_large_base");
	public static final TagKey<Structure> IS_WATCH_TOWER = tag("is_watch_tower");
	public static final TagKey<Structure> IS_TRAINING_STRUCTURE = tag("is_training_structure");
	public static final TagKey<Structure> IS_SKY_ISLAND = tag("is_sky_island");

	static void init() {
	}
	static TagKey<Structure> tag(String var0) {
		return TagKey.create(Registries.STRUCTURE,ResourceLocation.fromNamespaceAndPath("mineminenomi",var0));
	}
}