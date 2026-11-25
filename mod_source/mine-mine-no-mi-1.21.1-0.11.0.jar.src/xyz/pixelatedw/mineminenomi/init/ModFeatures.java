package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.world.features.BoulderFeature;
import xyz.pixelatedw.mineminenomi.world.features.LargeIceSpikeFeature;
import xyz.pixelatedw.mineminenomi.world.features.LargeLakesFeature;
import xyz.pixelatedw.mineminenomi.world.features.NaturalDialFeature;
import xyz.pixelatedw.mineminenomi.world.features.PoneglyphFeature;
import xyz.pixelatedw.mineminenomi.world.features.SnowFloorFeature;
import xyz.pixelatedw.mineminenomi.world.features.SnowMountainFeature;
import xyz.pixelatedw.mineminenomi.world.features.configs.SizedBlockStateFeatureConfig;

public class ModFeatures {
	// Fields
	public static final Supplier<Feature<NoneFeatureConfiguration>> PONEGLYPH = ModRegistry.registerFeature("poneglyph",(Supplier)(((Supplier<Feature>)(PoneglyphFeature::new))));
	public static final Supplier<Feature<NoneFeatureConfiguration>> BEACH_NATURAL_DIAL = ModRegistry.registerFeature("beach_natural_dial",(Supplier)(((Supplier<Feature>)(NaturalDialFeature::new))));
	public static final Supplier<Feature<NoneFeatureConfiguration>> SKY_ISLANDS_NATURAL_DIAL = ModRegistry.registerFeature("sky_natural_dial",(Supplier)(((Supplier<Feature>)(NaturalDialFeature::new))));
	public static final Supplier<Feature<ProbabilityFeatureConfiguration>> SNOW_MOUNTAIN = ModRegistry.registerFeature("snow_mountain",(Supplier)(((Supplier<Feature>)(SnowMountainFeature::new))));
	public static final Supplier<Feature<NoneFeatureConfiguration>> SNOW_FLOOR = ModRegistry.registerFeature("snow_floor",(Supplier)(((Supplier<Feature>)(SnowFloorFeature::new))));
	public static final Supplier<Feature<ProbabilityFeatureConfiguration>> LARGE_ICE_SPIKE = ModRegistry.registerFeature("large_ice_spike",(Supplier)(((Supplier<Feature>)(LargeIceSpikeFeature::new))));
	public static final Supplier<Feature<SimpleBlockConfiguration>> LAKE_AIR = ModRegistry.registerFeature("lake_air",(Supplier)(((Supplier<Feature>)(LargeLakesFeature::new))));
	public static final Supplier<Feature<SizedBlockStateFeatureConfig>> DIORITE_BOULDER = ModRegistry.registerFeature("diorite_boulder",(Supplier)(((Supplier<Feature>)(BoulderFeature::new))));

	public static void init() {
	}
}