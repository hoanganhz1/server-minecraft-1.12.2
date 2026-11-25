package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModTags$Biomes {
	// Fields
	public static final TagKey<Biome> MINK_DEBUFF_BIOMES = tag("mink_debuff_biomes");
	public static final TagKey<Biome> CAN_SPAWN_DUGONGS = tag("can_spawn/dugongs");
	public static final TagKey<Biome> CAN_SPAWN_WANDERING_DUGONG = tag("can_spawn/wandering_dugong");
	public static final TagKey<Biome> CAN_SPAWN_BIG_DUCK = tag("can_spawn/big_duck");
	public static final TagKey<Biome> CAN_SPAWN_BANANAWANI = tag("can_spawn/bananawani");
	public static final TagKey<Biome> CAN_SPAWN_DEN_DEN_MUSHI = tag("can_spawn/den_den_mushi");
	public static final TagKey<Biome> CAN_SPAWN_GORILLAS = tag("can_spawn/gorillas");
	public static final TagKey<Biome> CAN_SPAWN_HUMANDRILL = tag("can_spawn/humandrill");
	public static final TagKey<Biome> CAN_SPAWN_LAPAHN = tag("can_spawn/lapahn");
	public static final TagKey<Biome> CAN_SPAWN_WHITE_WALKIE = tag("can_spawn/white_walkie");
	public static final TagKey<Biome> CAN_SPAWN_HUMANS = tag("can_spawn/humans");
	public static final TagKey<Biome> CAN_SPAWN_FISH = tag("can_spawn/fish");
	public static final TagKey<Biome> CAN_SPAWN_FIGHTING_FISH = tag("can_spawn/fighting_fish");
	public static final TagKey<Biome> CAN_SPAWN_FLYING_FISH = tag("can_spawn/flying_fish");
	public static final TagKey<Biome> HAS_STRUCTURE_CAMP = tag("has_structure/camp");
	public static final TagKey<Biome> HAS_STRUCTURE_SMALL_BASE = tag("has_structure/small_base");
	public static final TagKey<Biome> HAS_STRUCTURE_LARGE_BASE = tag("has_structure/large_base");
	public static final TagKey<Biome> HAS_STRUCTURE_SKY_ISLAND = tag("has_structure/sky_island");
	public static final TagKey<Biome> HAS_STRUCTURE_TRAINER = tag("has_structure/trainer_building");
	public static final TagKey<Biome> HAS_STRUCTURE_WATCH_TOWER = tag("has_structure/watch_tower");
	public static final TagKey<Biome> HAS_STRUCTURE_SMALL_SHIP = tag("has_structure/small_ship");
	public static final TagKey<Biome> HAS_STRUCTURE_MEDIUM_SHIP = tag("has_structure/medium_ship");
	public static final TagKey<Biome> HAS_STRUCTURE_BIG_SHIP = tag("has_structure/big_ship");
	public static final TagKey<Biome> HAS_STRUCTURE_GHOST_SHIP = tag("has_structure/ghost_ship");
	public static final TagKey<Biome> HAS_KAIROSEKI_ORE = tag("ores/kairoseki");
	public static final TagKey<Biome> IS_SWAMP = tag("is_swamp");

	static void init() {
	}
	static TagKey<Biome> tag(String var0) {
		return TagKey.create(Registries.BIOME,ResourceLocation.fromNamespaceAndPath("mineminenomi",var0));
	}
}