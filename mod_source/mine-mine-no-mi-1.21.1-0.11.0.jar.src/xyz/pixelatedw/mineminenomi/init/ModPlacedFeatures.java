package xyz.pixelatedw.mineminenomi.init;

import java.util.Collection;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class ModPlacedFeatures {
	// Fields
	public static final ResourceKey<PlacedFeature> KAIROSEKI_ORE = registerKey("kairoseki_ore_placed");
	public static final ResourceKey<PlacedFeature> PONEGLYPH = registerKey("poneglyph_placed");
	public static final ResourceKey<PlacedFeature> BEACH_NATURAL_DIAL = registerKey("beach_natural_dial_placed");
	public static final ResourceKey<PlacedFeature> SKY_ISLANDS_NATURAL_DIAL = registerKey("sky_natural_dial_placed");
	public static final ResourceKey<PlacedFeature> SNOW_MOUNTAIN = registerKey("snow_mountain");
	public static final ResourceKey<PlacedFeature> SNOW_FLOOR = registerKey("snow_floor");
	public static final ResourceKey<PlacedFeature> LARGE_ICE_SPIKE = registerKey("large_ice_spike");

	public static void bootstrap(BootstrapContext<PlacedFeature> var0) {
		HolderGetter var1 = var0.lookup(Registries.CONFIGURED_FEATURE);
		register(var0,KAIROSEKI_ORE,(Holder)(var1.getOrThrow(ModConfiguredFeatures.KAIROSEKI_ORE)),ore((PlacementModifier)(CountPlacement.of(6)),PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT));
		register(var0,PONEGLYPH,(Holder)(var1.getOrThrow(ModConfiguredFeatures.PONEGLYPH)),List.of(CountPlacement.of(1),InSquarePlacement.spread(),PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,BiomeFilter.biome()));
		register(var0,BEACH_NATURAL_DIAL,(Holder)(var1.getOrThrow(ModConfiguredFeatures.BEACH_NATURAL_DIAL)),List.of(CountPlacement.of(1),InSquarePlacement.spread(),PlacementUtils.HEIGHTMAP_WORLD_SURFACE,BiomeFilter.biome()));
		register(var0,SKY_ISLANDS_NATURAL_DIAL,(Holder)(var1.getOrThrow(ModConfiguredFeatures.SKY_ISLANDS_NATURAL_DIAL)),List.of(CountPlacement.of(50),InSquarePlacement.spread(),HeightRangePlacement.uniform(VerticalAnchor.absolute(150),VerticalAnchor.absolute(250))));
		register(var0,SNOW_MOUNTAIN,(Holder)(var1.getOrThrow(ModConfiguredFeatures.SNOW_MOUNTAIN)),List.of(CountPlacement.of(1),InSquarePlacement.spread(),PlacementUtils.HEIGHTMAP_WORLD_SURFACE,BiomeFilter.biome()));
		register(var0,SNOW_FLOOR,(Holder)(var1.getOrThrow(ModConfiguredFeatures.SNOW_FLOOR)),List.of((Object)(BiomeFilter.biome())));
		register(var0,LARGE_ICE_SPIKE,(Holder)(var1.getOrThrow(ModConfiguredFeatures.LARGE_ICE_SPIKE)),List.of(CountPlacement.of(1),InSquarePlacement.spread(),PlacementUtils.HEIGHTMAP_WORLD_SURFACE,BiomeFilter.biome()));
	}
	private static List<PlacementModifier> ore(PlacementModifier var0, PlacementModifier var1) {
		return (List<PlacementModifier>) (List) (List.of(var0,InSquarePlacement.spread(),var1,BiomeFilter.biome()));
	}
	private static ResourceKey<PlacedFeature> registerKey(String var0) {
		return ResourceKey.create(Registries.PLACED_FEATURE,ResourceLocation.fromNamespaceAndPath("mineminenomi",var0));
	}
	private static void register(BootstrapContext<PlacedFeature> var0, ResourceKey<PlacedFeature> var1, Holder<ConfiguredFeature<?, ?>> var2, List<PlacementModifier> var3) {
		var0.register(var1,new PlacedFeature(var2,List.copyOf(var3)));
	}
}