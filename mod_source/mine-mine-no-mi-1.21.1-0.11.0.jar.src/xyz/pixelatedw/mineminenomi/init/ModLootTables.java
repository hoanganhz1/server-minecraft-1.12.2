package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class ModLootTables {
	// Fields
	public static final ResourceKey<LootTable> SMALL_PIRATE_SHIP_SUPPLIES_CHEST = register("chests/pirate_small_ship/supplies");
	public static final ResourceKey<LootTable> MEDIUM_PIRATE_SHIP_SUPPLIES_CHEST = register("chests/pirate_medium_ship/supplies");
	public static final ResourceKey<LootTable> MEDIUM_PIRATE_SHIP_FOOD_CHEST = register("chests/pirate_medium_ship/food");
	public static final ResourceKey<LootTable> MEDIUM_PIRATE_SHIP_CAPTAIN_CHEST = register("chests/pirate_medium_ship/captain");
	public static final ResourceKey<LootTable> LARGE_PIRATE_SHIP_SUPPLIES_CHEST = register("chests/pirate_large_ship/supplies");
	public static final ResourceKey<LootTable> LARGE_PIRATE_SHIP_WEAPONS_CHEST = register("chests/pirate_large_ship/weapons");
	public static final ResourceKey<LootTable> LARGE_PIRATE_SHIP_CAPTAIN_CHEST = register("chests/pirate_large_ship/captain");
	public static final ResourceKey<LootTable> LARGE_PIRATE_SHIP_TREASURE_CHEST = register("chests/pirate_large_ship/treasure");
	public static final ResourceKey<LootTable> GHOST_SHIP_SUPPLIES_CHEST = register("chests/ghost_ship/supplies");
	public static final ResourceKey<LootTable> GHOST_SHIP_CAPTAIN_CHEST = register("chests/ghost_ship/captain");
	public static final ResourceKey<LootTable> MARINE_CAMP_SMALL_TENT_CHEST = register("chests/marine_camp/small_tent");
	public static final ResourceKey<LootTable> MARINE_CAMP_LARGE_TENT_CHEST = register("chests/marine_camp/large_tent");
	public static final ResourceKey<LootTable> BANDIT_CAMP_SMALL_TENT_CHEST = register("chests/bandit_camp/small_tent");
	public static final ResourceKey<LootTable> BANDIT_CAMP_LARGE_TENT_CHEST = register("chests/bandit_camp/large_tent");
	public static final ResourceKey<LootTable> SMALL_BANDIT_BASE_GOLD_CHEST = register("chests/bandit_small_base/gold");
	public static final ResourceKey<LootTable> SMALL_BANDIT_BASE_MINE_CHEST = register("chests/bandit_small_base/mine");
	public static final ResourceKey<LootTable> SMALL_BANDIT_BASE_LAB_CHEST = register("chests/bandit_small_base/lab");
	public static final ResourceKey<LootTable> SMALL_BANDIT_BASE_ORES_CHEST = register("chests/bandit_small_base/ores");
	public static final ResourceKey<LootTable> SMALL_MARINE_BASE_CAPTAIN_CHEST = register("chests/marine_small_base/captain");
	public static final ResourceKey<LootTable> SMALL_MARINE_BASE_FOOD_CHEST = register("chests/marine_small_base/food");
	public static final ResourceKey<LootTable> SMALL_MARINE_BASE_DORM_CHEST = register("chests/marine_small_base/dorm");
	public static final ResourceKey<LootTable> LARGE_MARINE_BASE_CAPTAIN_CHEST = register("chests/marine_large_base/captain");
	public static final ResourceKey<LootTable> LARGE_MARINE_BASE_FOOD_CHEST = register("chests/marine_large_base/food");
	public static final ResourceKey<LootTable> LARGE_MARINE_BASE_GENERIC_CHEST = register("chests/marine_large_base/generic");
	public static final ResourceKey<LootTable> LARGE_MARINE_BASE_LAB_CHEST = register("chests/marine_large_base/lab");
	public static final ResourceKey<LootTable> LARGE_MARINE_SHIP_CAPTAIN_CHEST = register("chests/marine_battleship/captain");
	public static final ResourceKey<LootTable> LARGE_MARINE_SHIP_SUPPLIES_CHEST = register("chests/marine_battleship/supplies");
	public static final ResourceKey<LootTable> LARGE_MARINE_SHIP_RUM_CHEST = register("chests/marine_battleship/rum");
	public static final ResourceKey<LootTable> LARGE_BANDIT_BASE_TOWER_CHEST = register("chests/bandit_large_base/tower");
	public static final ResourceKey<LootTable> LARGE_BANDIT_BASE_TENT_CHEST = register("chests/bandit_large_base/tent");
	public static final ResourceKey<LootTable> LARGE_BANDIT_BASE_FOOD_CHEST = register("chests/bandit_large_base/food");
	public static final ResourceKey<LootTable> LARGE_BANDIT_BASE_DORM_CHEST = register("chests/bandit_large_base/dorm");
	public static final ResourceKey<LootTable> LARGE_BANDIT_BASE_SECRET_STASH_CHEST = register("chests/bandit_large_base/secret_stash");
	public static final ResourceKey<LootTable> CARAVAN_EASY_CHEST = register("chests/caravan/easy");
	public static final ResourceKey<LootTable> CARAVAN_HARD_CHEST = register("chests/caravan/hard");
	public static final ResourceKey<LootTable> SKYPIEAN_TRADER = register("entities/trader/skypiean_trader");
	public static final ResourceKey<LootTable> PIRATE_TRADER = register("entities/trader/pirate_trader");
	public static final ResourceKey<LootTable> MARINE_TRADER = register("entities/trader/marine_trader");
	public static final ResourceKey<LootTable> WOODEN_BOX = register("dfboxes/wooden_box");
	public static final ResourceKey<LootTable> IRON_BOX = register("dfboxes/iron_box");
	public static final ResourceKey<LootTable> GOLDEN_BOX = register("dfboxes/golden_box");
	public static final Pair<Integer, ResourceKey<LootTable>> TIER_1_FRUITS = ImmutablePair.of(Integer.valueOf(1),WOODEN_BOX);
	public static final Pair<Integer, ResourceKey<LootTable>> TIER_2_FRUITS = ImmutablePair.of(Integer.valueOf(2),IRON_BOX);
	public static final Pair<Integer, ResourceKey<LootTable>> TIER_3_FRUITS = ImmutablePair.of(Integer.valueOf(3),GOLDEN_BOX);
	public static final ResourceKey<LootTable> AXE_DIAL_BLOCK = register("blocks/axe_dial");
	public static final ResourceKey<LootTable> BREATH_DIAL_BLOCK = register("blocks/breath_dial");
	public static final ResourceKey<LootTable> DEN_DEN_MUSHI_BLOCK = register("blocks/den_den_mushi");
	public static final ResourceKey<LootTable> DESIGN_BARREL_BLOCK = register("blocks/design_barrel");
	public static final ResourceKey<LootTable> EISEN_DIAL_BLOCK = register("blocks/eisen_dial");
	public static final ResourceKey<LootTable> FLAG_BLOCK = register("blocks/flag");
	public static final ResourceKey<LootTable> FLAME_DIAL_BLOCK = register("blocks/flame_dial");
	public static final ResourceKey<LootTable> FLASH_DIAL_BLOCK = register("blocks/flash_dial");
	public static final ResourceKey<LootTable> IMPACT_DIAL_BLOCK = register("blocks/impact_dial");
	public static final ResourceKey<LootTable> KAIROSEKI_BARS_BLOCK = register("blocks/kairoseki_bars");
	public static final ResourceKey<LootTable> KAIROSEKI_BLOCK = register("blocks/kairoseki_block");
	public static final ResourceKey<LootTable> KAIROSEKI_ORE_BLOCK = register("blocks/kairoseki_ore");
	public static final ResourceKey<LootTable> DEEPSLATE_KAIROSEKI_ORE_BLOCK = register("blocks/deepslate_kairoseki_ore");
	public static final ResourceKey<LootTable> MANGROVE_LOG_BLOCK = register("blocks/mangrove_log");
	public static final ResourceKey<LootTable> MANGROVE_PLANKS_BLOCK = register("blocks/mangrove_planks");
	public static final ResourceKey<LootTable> MANGROVE_WOOD_BLOCK = register("blocks/mangrove_wood");
	public static final ResourceKey<LootTable> MILKY_DIAL_BLOCK = register("blocks/milky_dial");
	public static final ResourceKey<LootTable> REJECT_DIAL_BLOCK = register("blocks/reject_dial");
	public static final ResourceKey<LootTable> SKY_BLOCK_BLOCK = register("blocks/sky_block");
	public static final ResourceKey<LootTable> STRIPPED_MANGROVE_LOG_BLOCK = register("blocks/stripped_mangrove_log");
	public static final ResourceKey<LootTable> STRIPPED_MANGROVE_WOOD_BLOCK = register("blocks/stripped_mangrove_wood");

	public static ResourceKey<LootTable> register(String var0) {
		return ResourceKey.create(Registries.LOOT_TABLE,ResourceLocation.fromNamespaceAndPath("mineminenomi",var0));
	}
}