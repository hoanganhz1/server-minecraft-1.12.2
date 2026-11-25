package xyz.pixelatedw.mineminenomi.init;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import xyz.pixelatedw.mineminenomi.world.features.configs.SizedBlockStateFeatureConfig;

public class ModConfiguredFeatures {
	// Fields
	public static final ResourceKey<ConfiguredFeature<?, ?>> KAIROSEKI_ORE = registerKey("kairoseki_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PONEGLYPH = registerKey("poneglyph");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BEACH_NATURAL_DIAL = registerKey("beach_natural_dial");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SKY_ISLANDS_NATURAL_DIAL = registerKey("sky_natural_dial");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SNOW_MOUNTAIN = registerKey("snow_mountain");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SNOW_FLOOR = registerKey("snow_floor");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_ICE_SPIKE = registerKey("large_ice_spike");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SABAODY_TREE = registerKey("sabaody_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_SABAODY_TREE = registerKey("huge_sabaody_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LAKE_AIR = registerKey("lake_air");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DIORITE_BOULDER = registerKey("diorite_boulder");

	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> var0) {
		TagMatchTest var1 = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
		TagMatchTest var2 = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
		List var3 = List.of(OreConfiguration.target((RuleTest)(var1),((Block)(ModBlocks.KAIROSEKI_ORE.get())).defaultBlockState()),OreConfiguration.target((RuleTest)(var2),((Block)(ModBlocks.DEEPSLATE_KAIROSEKI_ORE.get())).defaultBlockState()));
		register(var0,KAIROSEKI_ORE,Feature.ORE,new OreConfiguration(var3,9));
		register(var0,PONEGLYPH,((Feature)(ModFeatures.PONEGLYPH.get())),NoneFeatureConfiguration.NONE);
		register(var0,BEACH_NATURAL_DIAL,((Feature)(ModFeatures.BEACH_NATURAL_DIAL.get())),NoneFeatureConfiguration.NONE);
		register(var0,SKY_ISLANDS_NATURAL_DIAL,((Feature)(ModFeatures.SKY_ISLANDS_NATURAL_DIAL.get())),NoneFeatureConfiguration.NONE);
		Feature var4 = ((Feature)(ModFeatures.SNOW_MOUNTAIN.get()));
		register(var0,SNOW_MOUNTAIN,var4,new ProbabilityFeatureConfiguration(0.01F));
		Feature var5 = ((Feature)(ModFeatures.LARGE_ICE_SPIKE.get()));
		register(var0,LARGE_ICE_SPIKE,var5,new ProbabilityFeatureConfiguration(0.01F));
		Feature var6 = ((Feature)(ModFeatures.LAKE_AIR.get()));
		register(var0,LAKE_AIR,var6,new SimpleBlockConfiguration((BlockStateProvider)(BlockStateProvider.simple(Blocks.AIR))));
		register(var0,SNOW_FLOOR,((Feature)(ModFeatures.SNOW_FLOOR.get())),NoneFeatureConfiguration.NONE);
		Feature var7 = ((Feature)(ModFeatures.DIORITE_BOULDER.get()));
		register(var0,DIORITE_BOULDER,var7,new SizedBlockStateFeatureConfig(Blocks.DIORITE.defaultBlockState(),5));
	}
	private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String var0) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE,ResourceLocation.fromNamespaceAndPath("mineminenomi",var0));
	}
	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> var0, ResourceKey<ConfiguredFeature<?, ?>> var1, F var2, FC var3) {
		var0.register(var1,new ConfiguredFeature(var2,var3));
	}
}