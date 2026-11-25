package xyz.pixelatedw.mineminenomi.init;

import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.level.structure.placement.ExtendedRandomSpreadStructurePlacement;
import xyz.pixelatedw.mineminenomi.api.level.structure.processor.BannditWoodTextureStructureProcessor;
import xyz.pixelatedw.mineminenomi.api.level.structure.processor.IgnoreWaterloggingStructureProcessor;
import xyz.pixelatedw.mineminenomi.api.level.structure.processor.RuleWithCopyStructureProcessor;
import xyz.pixelatedw.mineminenomi.api.level.structure.processor.StoneTextureStructureProcessor;

public class ModStructures {
	// Fields
	public static final Supplier<StructurePlacementType<ExtendedRandomSpreadStructurePlacement>> EXTENDED_RANDOM_SPREAD_PLACEMENT;
	public static final Supplier<StructureProcessorType<RuleWithCopyStructureProcessor>> RULE_WITH_COPY_PROCESSOR;
	public static final Supplier<StructureProcessorType<IgnoreWaterloggingStructureProcessor>> IGNORE_WATERLOGGING_PROCESSOR;
	public static final Supplier<StructureProcessorType<StoneTextureStructureProcessor>> STONE_TEXTURE_PROCESSOR;
	public static final Supplier<StructureProcessorType<BannditWoodTextureStructureProcessor>> BANDIT_WOOD_TEXTURE_PROCESSOR;
	public static final ResourceKey<Structure> PIRATE_SMALL_SHIP;
	public static final ResourceKey<Structure> PIRATE_MEDIUM_SHIP;
	public static final ResourceKey<Structure> PIRATE_LARGE_SHIP;
	public static final ResourceKey<Structure> MARINE_WATCH_TOWER;
	public static final ResourceKey<Structure> MARINE_SMALL_BASE;
	public static final ResourceKey<Structure> MARINE_LARGE_BASE;
	public static final ResourceKey<Structure> MARINE_BATTLESHIP;
	public static final ResourceKey<Structure> BANDIT_HOUSE;
	public static final ResourceKey<Structure> BANDIT_ARENA;
	public static final ResourceKey<Structure> BANDIT_FORT;
	public static final ResourceKey<Structure> BANDIT_CAMP;
	public static final ResourceKey<Structure> SKY_ISLANDS_SINGLE;
	public static final ResourceKey<Structure> SKY_ISLANDS_TOWN;
	public static final ResourceKey<Structure> BLACK_LEG_KITCHEN;
	public static final ResourceKey<Structure> BRAWLER_RING;
	public static final ResourceKey<Structure> MEDIC_TENT;
	public static final ResourceKey<Structure> SNIPER_RANGE;
	public static final ResourceKey<Structure> SWORDSMAN_DOJO;
	public static final ResourceKey<Structure> TAVERN;
	public static final ResourceKey<Structure> GHOST_SHIP;

	private static ResourceKey<Structure> key(String var0) {
		return ResourceKey.create(Registries.STRUCTURE,ResourceLocation.fromNamespaceAndPath("mineminenomi",var0));
	}
	public static void init() {
	}
	static  {
		Supplier<StructurePlacementType> var0 = () -> {
			StructurePlacementType lv2_0 = () -> {
				return ExtendedRandomSpreadStructurePlacement.CODEC;
			};
			return lv2_0;
		};
		EXTENDED_RANDOM_SPREAD_PLACEMENT = ModRegistry.registerStructurePlacement("extended_random_spread",var0);
		Supplier<StructureProcessorType> var1 = () -> {
			StructureProcessorType lv4_0 = () -> {
				return RuleWithCopyStructureProcessor.CODEC;
			};
			return lv4_0;
		};
		RULE_WITH_COPY_PROCESSOR = ModRegistry.registerStructureProcessor("rule_with_copy",var1);
		Supplier<StructureProcessorType> var2 = () -> {
			StructureProcessorType lv6_0 = () -> {
				return IgnoreWaterloggingStructureProcessor.CODEC;
			};
			return lv6_0;
		};
		IGNORE_WATERLOGGING_PROCESSOR = ModRegistry.registerStructureProcessor("ignore_waterlogging",var2);
		Supplier<StructureProcessorType> var3 = () -> {
			StructureProcessorType lv8_0 = () -> {
				return StoneTextureStructureProcessor.CODEC;
			};
			return lv8_0;
		};
		STONE_TEXTURE_PROCESSOR = ModRegistry.registerStructureProcessor("stone_texture",var3);
		Supplier<StructureProcessorType> var4 = () -> {
			StructureProcessorType lv10_0 = () -> {
				return BannditWoodTextureStructureProcessor.CODEC;
			};
			return lv10_0;
		};
		BANDIT_WOOD_TEXTURE_PROCESSOR = ModRegistry.registerStructureProcessor("bandit_wood_texture",var4);
		PIRATE_SMALL_SHIP = key("pirate/small_ship");
		PIRATE_MEDIUM_SHIP = key("pirate/medium_ship");
		PIRATE_LARGE_SHIP = key("pirate/large_ship");
		MARINE_WATCH_TOWER = key("marine/watch_tower");
		MARINE_SMALL_BASE = key("marine/small_base");
		MARINE_LARGE_BASE = key("marine/large_base");
		MARINE_BATTLESHIP = key("marine/battleship");
		BANDIT_HOUSE = key("bandit/house");
		BANDIT_ARENA = key("bandit/arena");
		BANDIT_FORT = key("bandit/fort");
		BANDIT_CAMP = key("bandit/camp");
		SKY_ISLANDS_SINGLE = key("sky_islands/single");
		SKY_ISLANDS_TOWN = key("sky_islands/town");
		BLACK_LEG_KITCHEN = key("unaligned/black_leg_kitchen");
		BRAWLER_RING = key("unaligned/brawler_ring");
		MEDIC_TENT = key("unaligned/medic_tent");
		SNIPER_RANGE = key("unaligned/sniper_range");
		SWORDSMAN_DOJO = key("unaligned/swordsman_dojo");
		TAVERN = key("unaligned/tavern");
		GHOST_SHIP = key("unaligned/ghost_ship");
	}
}