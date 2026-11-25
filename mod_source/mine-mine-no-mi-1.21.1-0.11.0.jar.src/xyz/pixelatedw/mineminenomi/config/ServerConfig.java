package xyz.pixelatedw.mineminenomi.config;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.ModConfigSpec;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nConfig;

public class ServerConfig {
	// Fields
	public static final ModConfigSpec SPEC;
	public static final int VERSION = 4;
	private static ModConfigSpec.ConfigValue<List<? extends String>> bannedItemsFromImbuing;
	private static ModConfigSpec.BooleanValue forceSelection;
	private static ModConfigSpec.BooleanValue abilityInvulnerability;
	private static ModConfigSpec.BooleanValue specialSourceEvents;
	private static ModConfigSpec.BooleanValue stopContinuousAbilities;
	private static ModConfigSpec.BooleanValue animeScreaming;
	private static ModConfigSpec.ConfigValue<Integer> version;
	private static List<AbilityCore<? extends IAbility>> protectionWhitelistedAbilities;
	private static List<Item> bannedImbueableItems;
	private static List<AbilityCore<? extends IAbility>> bannedAbilities;
	private static List<ResourceLocation> bannedDimensionsForStructures;

	private static void setupConfig(ModConfigSpec.Builder var0) {
		var0.translation(ModI18nConfig.GENERAL_CATEGORY).push("General");
		GeneralConfig.EXTRA_HEARTS.createValue(var0);
		GeneralConfig.MOB_REWARDS.createValue(var0);
		GeneralConfig.MINIMUM_DORIKI_PER_KILL.createValue(var0);
		GeneralConfig.DESTROY_SPAWNER.createValue(var0);
		GeneralConfig.DESTROY_WATER.createValue(var0);
		GeneralConfig.RACE_RANDOMIZER.createValue(var0);
		GeneralConfig.ALLOW_SUB_RACE_SELECT.createValue(var0);
		GeneralConfig.NATIVE_HAKI.createValue(var0);
		GeneralConfig.PUBLIC_REMOVEDF.createValue(var0);
		GeneralConfig.PUBLIC_CHECK_FRUITS.createValue(var0);
		GeneralConfig.DESPAWN_WITH_NAMETAG.createValue(var0);
		forceSelection = var0.comment("Forces new players to select their race, faction and fighting style upon joining the world\nDefault: false").translation(ModI18nConfig.FORCED_SELECTION_OPTION).define("Force Selection",false);
		Predicate<Object> var1 = (l0) -> {
			if (l0 instanceof String){
				String lv1_1 = ((String)(l0));
				if (Strings.isNullOrEmpty(lv1_1)){
					return false;
				}else{
					return true;
				}
			}else{
				return false;
			}
		};
		Predicate<Object> var2 = var1;
		ArrayList var3 = new ArrayList();
		var3.add("mineminenomi:bubbly_coral");
		var3.add("mineminenomi:medic_bag");
		bannedItemsFromImbuing = (ModConfigSpec.ConfigValue) (var0.comment("List with item ids that will not get the durability protection of Imbuing Haki").translation(ModI18nConfig.BANNED_IMBUING_ITEMS_OPTION).defineList("Banned Items from Imbuing",(List)(var3),var2));
		GeneralConfig.DORIKI_LIMIT.createValue(var0);
		GeneralConfig.HAKI_EXP_LIMIT.createValue(var0);
		GeneralConfig.TRAINING_POINT_LIMIT.createValue(var0);
		GeneralConfig.HEALTH_GAIN_FREQUENCY.createValue(var0);
		GeneralConfig.DORIKI_REWARD_MULTIPLIER.createValue(var0);
		GeneralConfig.BELLY_REWARD_MULTIPLIER.createValue(var0);
		GeneralConfig.BOUNTY_REWARD_MULTIPLIER.createValue(var0);
		GeneralConfig.HAKI_EXP_MULTIPLIER.createValue(var0);
		GeneralConfig.LOYALTY_MULTIPLIER.createValue(var0);
		var0.translation(ModI18nConfig.STATS_TO_KEEP_CATEGORY).push("Stats to Keep");
		GeneralConfig.RACE_KEEP.createValue(var0);
		GeneralConfig.FACTION_KEEP.createValue(var0);
		GeneralConfig.FIGHTING_STYLE_KEEP.createValue(var0);
		GeneralConfig.DEVIL_FRUIT_KEEP.createValue(var0);
		GeneralConfig.DORIKI_KEEP_PERCENTAGE.createValue(var0);
		GeneralConfig.BOUNTY_KEEP_PERCENTAGE.createValue(var0);
		GeneralConfig.BELLY_KEEP_PERCENTAGE.createValue(var0);
		GeneralConfig.HAKI_EXP_KEEP_PERCENTAGE.createValue(var0);
		GeneralConfig.LOYALTY_KEEP_PERCENTAGE.createValue(var0);
		GeneralConfig.TRAINING_POINTS_KEEP_PERCENTAGE.createValue(var0);
		var0.pop();
		var0.translation(ModI18nConfig.QUESTS_CATEGORY).push("Quests");
		GeneralConfig.ENABLE_TRIALS.createValue(var0);
		GeneralConfig.ENABLE_STYLES_PROGRESSION.createValue(var0);
		var0.pop();
		var0.pop();
		var0.translation(ModI18nConfig.ABILITIES_CATEGORY).push("Devil Fruits / Abilities");
		AbilitiesConfig.ABILITY_GRIEFING.createValue(var0);
		AbilitiesConfig.ABILITY_FRAUD_CHECKS.createValue(var0);
		AbilitiesConfig.ABILITY_BARS.createValue(var0);
		AbilitiesConfig.OPEN_WORLD_FRUIT_USERS.createValue(var0);
		AbilitiesConfig.PUNCH_ABILITY_LIMIT.createValue(var0);
		AbilitiesConfig.DESTRUCTION_MULTIPLIER.createValue(var0);
		AbilitiesConfig.YAMI_POWER.createValue(var0);
		AbilitiesConfig.ENABLE_AWAKENINGS.createValue(var0);
		AbilitiesConfig.WATER_CHECKS.createValue(var0);
		AbilitiesConfig.SHARED_COOLDOWNS.createValue(var0);
		AbilitiesConfig.REMOVE_Y_RESTRICTION.createValue(var0);
		AbilitiesConfig.RANDOMIZED_FRUITS.createValue(var0);
		AbilitiesConfig.DEVIL_FRUIT_DROP_FROM_LEAVES.createValue(var0);
		AbilitiesConfig.LOGIA_INVULNERABILITY.createValue(var0);
		AbilitiesConfig.LOGIA_RETURN_EFFECT.createValue(var0);
		AbilitiesConfig.LOGIA_PROJECTILE_HIT_LOGIC.createValue(var0);
		abilityInvulnerability = var0.comment("Invulnerability to avoid attacks\nDefault: true").translation(ModI18nConfig.ABILITY_INVULNERABILITY_OPTION).define("Ability Invulnerability",true);
		specialSourceEvents = var0.comment("Makes the fire and lava damage source to reduce fire resistance; only applies to move attacks from fruits \nDefault: true").translation(ModI18nConfig.SPECIAL_SOURCE_EVENTS_OPTION).define("Special Source Events",true);
		stopContinuousAbilities = var0.comment("Used to determine the logic for when a continuous ability is used while another continuous ability is being used;\n true - Currently used ability is stopped and the newly used ability starts its process\n false - The current ability is NOT stopped and the used ability has no effect\nDefault: true").translation(ModI18nConfig.STOP_CONTINUOUS_ABILITIES_OPTION).define("Stop Continuous Abilities",true);
		animeScreaming = var0.comment("Will send a chat message to nearby players with the used ability's name\nDefault: false").translation(ModI18nConfig.ANIME_SCREAM_OPTION).define("Anime Scream",false);
		AbilitiesConfig.BANNED_ABILITIES.createValue(var0);
		var0.comment("These options only work when \"One Fruit per World\" option is set to EXTENDED!").translation(ModI18nConfig.ONE_FRUIT_CATEGORY).push("One Fruit Per World");
		AbilitiesConfig.ONE_FRUIT_PER_WORLD.createValue(var0);
		AbilitiesConfig.UNABLE_TO_PICKUP_DF.createValue(var0);
		AbilitiesConfig.FRUITS_LIMIT_INVENTORY.createValue(var0);
		AbilitiesConfig.ONE_FRUIT_PER_WORLD_CHUNK_DELETION.createValue(var0);
		AbilitiesConfig.DAYS_FOR_INACTIVITY.createValue(var0);
		var0.pop();
		var0.translation(ModI18nConfig.FRUIT_REINCARNATION_CATEGORY).push("Devil Fruits Reincarnation");
		AbilitiesConfig.DROPPED_APPLES_RESPAWN_CHANCE.createValue(var0);
		AbilitiesConfig.ENTITY_INVENTORY_APPLES_RESPAWN_CHANCE.createValue(var0);
		AbilitiesConfig.CHESTS_APPLES_RESPAWN_CHANCE.createValue(var0);
		var0.pop();
		var0.translation(ModI18nConfig.ABILITY_PROTECTION_CATEGORY).push("Ability Protection");
		AbilitiesConfig.GLOBAL_PROTECTION_WHITELIST.createValue(var0);
		AbilitiesConfig.GLOBAL_PROTECTION_RESTORATION_GRACE.createValue(var0);
		var0.pop();
		var0.translation(ModI18nConfig.HAKI_CATEGORY).push("Haki");
		AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC.createValue(var0);
		AbilitiesConfig.HAOSHOKU_HAKI_COLORING_LOGIC.createValue(var0);
		var0.pop();
		var0.pop();
		var0.translation(ModI18nConfig.WORLD_FEATURES_CATEGORY).push("World Features");
		WorldFeaturesConfig.SPAWN_BIOMES.createValue(var0);
		WorldFeaturesConfig.DIMENSION_STRUCTURES_BANLIST.createValue(var0);
		WorldFeaturesConfig.SPAWN_CHANCE_TRAINING_STRUCTURES.createValue(var0);
		WorldFeaturesConfig.SPAWN_CHANCE_SKY_ISLANDS.createValue(var0);
		WorldFeaturesConfig.SPAWN_CHANCE_SMALL_SHIPS.createValue(var0);
		WorldFeaturesConfig.SPAWN_CHANCE_MEDIUM_SHIPS.createValue(var0);
		WorldFeaturesConfig.SPAWN_CHANCE_LARGE_SHIPS.createValue(var0);
		WorldFeaturesConfig.SPAWN_CHANCE_CAMPS.createValue(var0);
		WorldFeaturesConfig.SPAWN_CHANCE_SMALL_BASES.createValue(var0);
		WorldFeaturesConfig.SPAWN_CHANCE_LARGE_BASES.createValue(var0);
		WorldFeaturesConfig.SPAWN_CHANCE_WATCH_TOWERS.createValue(var0);
		WorldFeaturesConfig.SPAWN_CHANCE_PONEGLYPHS.createValue(var0);
		WorldFeaturesConfig.KAIROSEKI_SPAWN_COUNT.createValue(var0);
		var0.pop();
		var0.translation(ModI18nConfig.CHALLENGES_CATEGORY).push("Challenges");
		ChallengesConfig.ENABLE_CHALLENGES.createValue(var0);
		ChallengesConfig.CHALLENGE_CACHING.createValue(var0);
		ChallengesConfig.RETURN_TO_SAFETY.createValue(var0);
		ChallengesConfig.DORIKI_REWARD_POOL.createValue(var0);
		ChallengesConfig.BELLY_REWARD_POOL.createValue(var0);
		ChallengesConfig.HAKI_REWARD_POOL.createValue(var0);
		ChallengesConfig.ARENA_CONSTRUCTION_SPEED.createValue(var0);
		var0.pop();
		var0.translation(ModI18nConfig.WORLD_EVENTS_CATEGORY).push("World Events");
		WorldEventsConfig.SPAWN_WORLD_HUMANOIDS.createValue(var0);
		WorldEventsConfig.SPAWN_WORLD_ANIMALS.createValue(var0);
		WorldEventsConfig.NEW_WORLD_MODE.createValue(var0);
		WorldEventsConfig.NEW_WORLD_START_RADIUS.createValue(var0);
		var0.translation(ModI18nConfig.TRADERS_CATEGORY).push("Traders");
		WorldEventsConfig.TIME_BETWEEN_TRADER_SPAWNS.createValue(var0);
		WorldEventsConfig.SPAWN_CHANCE_TRADER.createValue(var0);
		var0.pop();
		var0.translation(ModI18nConfig.TRAINERS_CATEGORY).push("Trainers");
		WorldEventsConfig.TIME_BETWEEN_TRAINER_SPAWNS.createValue(var0);
		WorldEventsConfig.SPAWN_CHANCE_TRAINER.createValue(var0);
		var0.pop();
		var0.translation(ModI18nConfig.AMBUSHES_CATEGORY).push("Ambushes");
		WorldEventsConfig.TIME_BETWEEN_AMBUSH_SPAWNS.createValue(var0);
		WorldEventsConfig.SPAWN_CHANCE_AMBUSH.createValue(var0);
		var0.pop();
		var0.translation(ModI18nConfig.SPAWN_CHANCES_CATEGORY).push("Spawn Chances");
		WorldEventsConfig.GRUNT_SPAWN_CHANCE.createValue(var0);
		WorldEventsConfig.BRUTE_SPAWN_CHANCE.createValue(var0);
		WorldEventsConfig.SNIPER_SPAWN_CHANCE.createValue(var0);
		WorldEventsConfig.CAPTAIN_SPAWN_CHANCE.createValue(var0);
		WorldEventsConfig.PACIFISTA_SPAWN_CHANCE.createValue(var0);
		var0.pop();
		var0.pop();
		var0.translation(ModI18nConfig.FACTION_CATEGORY).push("Factions");
		FactionsConfig.DISABLE_FRIENDLY_FIRE.createValue(var0);
		var0.translation(ModI18nConfig.BOUNTY_CATEGORY).push("Bounty");
		FactionsConfig.TIME_BETWEEN_PACKAGE_DROPS.createValue(var0);
		var0.pop();
		var0.translation(ModI18nConfig.CREW_CATEGORY).push("Crews");
		FactionsConfig.CREW_BOUNTY_REQUIREMENT.createValue(var0);
		FactionsConfig.WORLD_MESSAGE_ON_CREW_CREATE.createValue(var0);
		var0.pop();
		var0.pop();
		var0.translation(ModI18nConfig.SYSTEM_CATEGORY).push("System");
		version = (ModConfigSpec.ConfigValue) (var0.comment("DO NOT EDIT!!!!").translation(ModI18nConfig.CONFIG_VERSION_OPTION).define("_v",(Object)(Integer.valueOf(0))));
		SystemConfig.EXPERIMENTAL_TIMERS.createValue(var0);
		var0.pop();
	}
	public static void updateConfigVersion() {
		int var0 = getConfigVersion();
		version.set(Integer.valueOf(4));
	}
	public static int getConfigVersion() {
		return ((Integer)(version.get())).intValue();
	}
	public static double getDestructionMultiplier() {
		return ((Double)(AbilitiesConfig.DESTRUCTION_MULTIPLIER.get())).doubleValue();
	}
	public static double getNewWorldStartRadius() {
		return ((Double)(WorldEventsConfig.NEW_WORLD_START_RADIUS.get())).doubleValue();
	}
	public static boolean isNewWorldModeEnabledFromStart() {
		return getNewWorldMode().equals(ServerConfig$NewWorldMode.START);
	}
	public static boolean isNewWorldModeEnabled() {
		ServerConfig$NewWorldMode var0 = getNewWorldMode();
		return (var0.equals(ServerConfig$NewWorldMode.NORMAL)) || (var0.equals(ServerConfig$NewWorldMode.START));
	}
	public static ServerConfig$NewWorldMode getNewWorldMode() {
		return ((ServerConfig$NewWorldMode)(WorldEventsConfig.NEW_WORLD_MODE.get()));
	}
	public static int getArenaGenerationSpeed() {
		return ((Integer)(ChallengesConfig.ARENA_CONSTRUCTION_SPEED.get())).intValue();
	}
	public static boolean hasExperimentalTimers() {
		return ((Boolean)(SystemConfig.EXPERIMENTAL_TIMERS.get())).booleanValue();
	}
	public static boolean canSpawnCaravans() {
		return ((Boolean)(WorldEventsConfig.SPAWN_CARAVANS.get())).booleanValue();
	}
	public static boolean canSpawnNotoriousTargets() {
		return ((Boolean)(WorldEventsConfig.SPAWN_NOTORIOUS_TARGETS.get())).booleanValue();
	}
	public static boolean canSpawnCelestialVisits() {
		return ((Boolean)(WorldEventsConfig.SPAWN_CELESTIAL_VISITS.get())).booleanValue();
	}
	public static int getHakiRewardPoolForDifficulty(ChallengeDifficulty var0) {
		List var1 = ((List)(ChallengesConfig.HAKI_REWARD_POOL.get()));
		if ((!(var1.isEmpty())) && ((var1.size()) >= (var0.ordinal()))){
			int var2 = ((Integer)(var1.get(var0.ordinal()))).intValue();
			var2 = Math.min(var2,getHakiExpLimit());
			return var2;
		}else{
			return 0;
		}
	}
	public static int getBellyRewardPoolForDifficulty(ChallengeDifficulty var0) {
		List var1 = ((List)(ChallengesConfig.BELLY_REWARD_POOL.get()));
		if ((!(var1.isEmpty())) && ((var1.size()) >= (var0.ordinal()))){
			int var2 = ((Integer)(var1.get(var0.ordinal()))).intValue();
			var2 = Math.min(var2,999999999);
			return var2;
		}else{
			return 0;
		}
	}
	public static int getDorikiRewardPoolForDifficulty(ChallengeDifficulty var0) {
		List var1 = ((List)(ChallengesConfig.DORIKI_REWARD_POOL.get()));
		if ((!(var1.isEmpty())) && ((var1.size()) >= (var0.ordinal()))){
			int var2 = ((Integer)(var1.get(var0.ordinal()))).intValue();
			var2 = Math.min(var2,getDorikiLimit());
			return var2;
		}else{
			return 0;
		}
	}
	public static boolean isReturnToSafetyEnabled() {
		return ((Boolean)(ChallengesConfig.RETURN_TO_SAFETY.get())).booleanValue();
	}
	public static double getPacifistaSpawnChance() {
		return ((Double)(WorldEventsConfig.PACIFISTA_SPAWN_CHANCE.get())).doubleValue();
	}
	public static double getCaptainSpawnChance() {
		return ((Double)(WorldEventsConfig.CAPTAIN_SPAWN_CHANCE.get())).doubleValue();
	}
	public static double getSniperSpawnChance() {
		return ((Double)(WorldEventsConfig.SNIPER_SPAWN_CHANCE.get())).doubleValue();
	}
	public static double getBruteSpawnChance() {
		return ((Double)(WorldEventsConfig.BRUTE_SPAWN_CHANCE.get())).doubleValue();
	}
	public static double getGruntSpawnChance() {
		return ((Double)(WorldEventsConfig.GRUNT_SPAWN_CHANCE.get())).doubleValue();
	}
	public static boolean hasPermissionsEnabled() {
		return ((Boolean)(SystemConfig.ENABLE_PERMISSIONS.get())).booleanValue();
	}
	public static boolean isForcedSelectionEnabled() {
		return ((Boolean)(forceSelection.get())).booleanValue();
	}
	public static boolean isAnimeScreamingEnabled() {
		return ((Boolean)(animeScreaming.get())).booleanValue();
	}
	public static boolean hasAwakeningsEnabled() {
		return ((Boolean)(AbilitiesConfig.ENABLE_AWAKENINGS.get())).booleanValue();
	}
	public static List<ResourceLocation> getBannedDimensionsForStructures() {
		if (bannedDimensionsForStructures.isEmpty()){
			Iterator var0 = ((List)(WorldFeaturesConfig.DIMENSION_STRUCTURES_BANLIST.get())).iterator();
			do{
				if (var0.hasNext()){
					Object var1 = var0.next();
					if (var1 instanceof String){
						String var2 = ((String)(var1));
						String[] var3 = var2.split(":");
						if ((var3.length) == (0)){
							continue;
						}else{
							if ((var3.length) == (1)){
								String[] var4 = new String[]{"minecraft",var2};
								var3 = var4;
							}
							bannedDimensionsForStructures.add(ResourceLocation.fromNamespaceAndPath(var3[0],var3[1]));
							continue;
						}
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
		return bannedDimensionsForStructures;
	}
	public static boolean hasPublicCheckFruitsCommand() {
		return ((Boolean)(GeneralConfig.PUBLIC_CHECK_FRUITS.get())).booleanValue();
	}
	public static long getGlobalProtectionGraceTime() {
		return ((Long)(AbilitiesConfig.GLOBAL_PROTECTION_RESTORATION_GRACE.get())).longValue();
	}
	public static boolean isItemBannedFromImbuing(Item var0) {
		return getBannedImbuingItems().contains(var0);
	}
	public static List<Item> getBannedImbuingItems() {
		if (bannedImbueableItems.isEmpty()){
			Iterator var0 = ((List)(bannedItemsFromImbuing.get())).iterator();
			do{
				if (var0.hasNext()){
					ResourceLocation var1 = ResourceLocation.parse(((String)(var0.next())));
					Item var2 = ((Item)(BuiltInRegistries.ITEM.get(var1)));
					if ((var2) != (null)){
						bannedImbueableItems.add(var2);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
		return bannedImbueableItems;
	}
	public static int getHealthGainFrequency() {
		return ((Integer)(GeneralConfig.HEALTH_GAIN_FREQUENCY.get())).intValue();
	}
	public static boolean hasYRestrictionRemoved() {
		return ((Boolean)(AbilitiesConfig.REMOVE_Y_RESTRICTION.get())).booleanValue();
	}
	public static boolean isHaoColoringRandom() {
		return (AbilitiesConfig.HAOSHOKU_HAKI_COLORING_LOGIC.get()) == (ServerConfig$HaoshokuColoringLogic.RANDOM);
	}
	public static boolean isHaoColoringCustom() {
		return (AbilitiesConfig.HAOSHOKU_HAKI_COLORING_LOGIC.get()) == (ServerConfig$HaoshokuColoringLogic.CUSTOM);
	}
	public static boolean isHaoColoringStandard() {
		return (AbilitiesConfig.HAOSHOKU_HAKI_COLORING_LOGIC.get()) == (ServerConfig$HaoshokuColoringLogic.STANDARD);
	}
	public static ServerConfig$HaoshokuColoringLogic getHaoshokuColoringLogic() {
		return ((ServerConfig$HaoshokuColoringLogic)(AbilitiesConfig.HAOSHOKU_HAKI_COLORING_LOGIC.get()));
	}
	public static boolean isNativeHakiEnabled() {
		return ((Boolean)(GeneralConfig.NATIVE_HAKI.get())).booleanValue();
	}
	public static boolean isAbilityProtectionWhitelisted(AbilityCore<?> var0) {
		return getProtectionWhitelistedAbilities().contains(var0);
	}
	public static List<AbilityCore<? extends IAbility>> getProtectionWhitelistedAbilities() {
		if (protectionWhitelistedAbilities.isEmpty()){
			Iterator var0 = ((List)(AbilitiesConfig.GLOBAL_PROTECTION_WHITELIST.get())).iterator();
			do{
				if (var0.hasNext()){
					Object var1 = var0.next();
					if (var1 instanceof String){
						String var2 = ((String)(var1));
						String[] var3 = var2.split(":");
						if ((var3.length) == (0)){
							continue;
						}else{
							if ((var3.length) == (1)){
								String[] var4 = new String[]{"mineminenomi",var2};
								var3 = var4;
							}
							AbilityCore var4_1 = AbilityCore.get(ResourceLocation.fromNamespaceAndPath(var3[0],var3[1]));
							if ((var4_1) != (null)){
								protectionWhitelistedAbilities.add(var4_1);
								continue;
							}else{
								continue;
							}
						}
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
		return protectionWhitelistedAbilities;
	}
	public static boolean getDespawnWithNametag() {
		return ((Boolean)(GeneralConfig.DESPAWN_WITH_NAMETAG.get())).booleanValue();
	}
	public static boolean getRandomizedFruits() {
		return ((Boolean)(AbilitiesConfig.RANDOMIZED_FRUITS.get())).booleanValue();
	}
	public static boolean getRaceRandomizer() {
		return ((Boolean)(GeneralConfig.RACE_RANDOMIZER.get())).booleanValue();
	}
	public static boolean getAllowSubRaceSelect() {
		return ((Boolean)(GeneralConfig.ALLOW_SUB_RACE_SELECT.get())).booleanValue();
	}
	public static double getChanceForSkyIslandSpawn() {
		return (double)(((Integer)(WorldFeaturesConfig.SPAWN_CHANCE_SKY_ISLANDS.get())).intValue());
	}
	public static boolean isChallengesEnabled() {
		return ((Boolean)(ChallengesConfig.ENABLE_CHALLENGES.get())).booleanValue();
	}
	public static boolean isChallengesCachingEnabled() {
		return ((Boolean)(ChallengesConfig.CHALLENGE_CACHING.get())).booleanValue();
	}
	public static double getChanceForPoneglyphSpawn() {
		return (double)(((Integer)(WorldFeaturesConfig.SPAWN_CHANCE_PONEGLYPHS.get())).intValue());
	}
	public static int getDaysForInactivity() {
		return ((Integer)(AbilitiesConfig.DAYS_FOR_INACTIVITY.get())).intValue();
	}
	public static int getBellyKeepPercentage() {
		return ((Integer)(GeneralConfig.BELLY_KEEP_PERCENTAGE.get())).intValue();
	}
	public static int getHakiExpKeepPercentage() {
		return ((Integer)(GeneralConfig.HAKI_EXP_KEEP_PERCENTAGE.get())).intValue();
	}
	public static int getBountyKeepPercentage() {
		return ((Integer)(GeneralConfig.BOUNTY_KEEP_PERCENTAGE.get())).intValue();
	}
	public static int getDorikiKeepPercentage() {
		return ((Integer)(GeneralConfig.DORIKI_KEEP_PERCENTAGE.get())).intValue();
	}
	public static int getInventoryLimitForFruits() {
		return ((Integer)(AbilitiesConfig.FRUITS_LIMIT_INVENTORY.get())).intValue();
	}
	public static boolean getUnableToPickDFAsUser() {
		return ((Boolean)(AbilitiesConfig.UNABLE_TO_PICKUP_DF.get())).booleanValue();
	}
	public static int getAbilityBars() {
		return ((Integer)(AbilitiesConfig.ABILITY_BARS.get())).intValue();
	}
	public static int getPunchAbilityLimit() {
		return ((Integer)(AbilitiesConfig.PUNCH_ABILITY_LIMIT.get())).intValue();
	}
	public static boolean getStopContinuousAbility() {
		return ((Boolean)(stopContinuousAbilities.get())).booleanValue();
	}
	public static int getHakiExpLimit() {
		return ((Integer)(GeneralConfig.HAKI_EXP_LIMIT.get())).intValue();
	}
	public static int getDorikiLimit() {
		return ((Integer)(GeneralConfig.DORIKI_LIMIT.get())).intValue();
	}
	public static int getTrainingPointLimit() {
		return ((Integer)(GeneralConfig.TRAINING_POINT_LIMIT.get())).intValue();
	}
	public static boolean getDestroySpawner() {
		return ((Boolean)(GeneralConfig.DESTROY_SPAWNER.get())).booleanValue();
	}
	public static boolean getDestroyWater() {
		return ((Boolean)(GeneralConfig.DESTROY_WATER.get())).booleanValue();
	}
	public static double getChanceForChestAppleReincarnation() {
		return (double)(((Integer)(AbilitiesConfig.CHESTS_APPLES_RESPAWN_CHANCE.get())).intValue());
	}
	public static double getChanceForInventoryAppleReincarnation() {
		return (double)(((Integer)(AbilitiesConfig.ENTITY_INVENTORY_APPLES_RESPAWN_CHANCE.get())).intValue());
	}
	public static double getChanceForDroppedAppleReincarnation() {
		return (double)(((Integer)(AbilitiesConfig.DROPPED_APPLES_RESPAWN_CHANCE.get())).intValue());
	}
	public static boolean isFriendlyDamageDisabled() {
		return ((Boolean)(FactionsConfig.DISABLE_FRIENDLY_FIRE.get())).booleanValue();
	}
	public static boolean hasOneFruitPerWorldExtendedLogic() {
		return ((ServerConfig$OneFruitPerWorldLogic)(AbilitiesConfig.ONE_FRUIT_PER_WORLD.get())).equals(ServerConfig$OneFruitPerWorldLogic.EXTENDED);
	}
	public static boolean hasOneFruitPerWorldSimpleLogic() {
		return (((ServerConfig$OneFruitPerWorldLogic)(AbilitiesConfig.ONE_FRUIT_PER_WORLD.get())).equals(ServerConfig$OneFruitPerWorldLogic.SIMPLE)) || (hasOneFruitPerWorldExtendedLogic());
	}
	public static boolean hasChunkUnloadingInstantLogic() {
		return ((ServerConfig$OneFruitPerWorldChunkDeletionLogic)(AbilitiesConfig.ONE_FRUIT_PER_WORLD_CHUNK_DELETION.get())).equals(ServerConfig$OneFruitPerWorldChunkDeletionLogic.INSTANT);
	}
	public static boolean hasChunkUnloadingRealisticLogic() {
		return ((ServerConfig$OneFruitPerWorldChunkDeletionLogic)(AbilitiesConfig.ONE_FRUIT_PER_WORLD_CHUNK_DELETION.get())).equals(ServerConfig$OneFruitPerWorldChunkDeletionLogic.REALISTIC);
	}
	public static double getChanceForLargeBasesSpawn() {
		return (double)(((Integer)(WorldFeaturesConfig.SPAWN_CHANCE_LARGE_BASES.get())).intValue());
	}
	public static double getChanceForWatchTowersSpawn() {
		return (double)(((Integer)(WorldFeaturesConfig.SPAWN_CHANCE_WATCH_TOWERS.get())).intValue());
	}
	public static double getChanceForLargeShipsSpawn() {
		return (double)(((Integer)(WorldFeaturesConfig.SPAWN_CHANCE_LARGE_SHIPS.get())).intValue());
	}
	public static double getChanceForSmallBasesSpawn() {
		return (double)(((Integer)(WorldFeaturesConfig.SPAWN_CHANCE_SMALL_BASES.get())).intValue());
	}
	public static double getChanceForCampsSpawn() {
		return (double)(((Integer)(WorldFeaturesConfig.SPAWN_CHANCE_CAMPS.get())).intValue());
	}
	public static double getChanceForSmallShipsSpawn() {
		return (double)(((Integer)(WorldFeaturesConfig.SPAWN_CHANCE_SMALL_SHIPS.get())).intValue());
	}
	public static double getChanceForMediumShipsSpawn() {
		return (double)(((Integer)(WorldFeaturesConfig.SPAWN_CHANCE_MEDIUM_SHIPS.get())).intValue());
	}
	public static double getChanceForTrainingStructureSpawn() {
		return (double)(((Integer)(WorldFeaturesConfig.SPAWN_CHANCE_TRAINING_STRUCTURES.get())).intValue());
	}
	public static boolean isCrewWorldMessageEnabled() {
		return ((Boolean)(FactionsConfig.WORLD_MESSAGE_ON_CREW_CREATE.get())).booleanValue();
	}
	public static int getBountyRequirementForCrews() {
		return ((Integer)(FactionsConfig.CREW_BOUNTY_REQUIREMENT.get())).intValue();
	}
	public static boolean isHaoshokuUnlockLogicExpBased() {
		return ((AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC.get()) == (ServerConfig$HaoshokuUnlockLogic.EXPERIENCE)) || ((AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC.get()) == (ServerConfig$HaoshokuUnlockLogic.COMBINED));
	}
	public static boolean isHaoshokuUnlockLogicChanceBased() {
		return ((AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC.get()) == (ServerConfig$HaoshokuUnlockLogic.RANDOM)) || (((AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC.get()) == (ServerConfig$HaoshokuUnlockLogic.COMBINED)) || ((AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC.get()) == (ServerConfig$HaoshokuUnlockLogic.TRUE_RANDOM)));
	}
	public static ServerConfig$HaoshokuUnlockLogic getHaoshokuUnlockLogic() {
		return ((ServerConfig$HaoshokuUnlockLogic)(AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC.get()));
	}
	public static int getChanceForAmbushSpawn() {
		return ((Integer)(WorldEventsConfig.SPAWN_CHANCE_AMBUSH.get())).intValue();
	}
	public static int getTimeBetweenAmbushSpawns() {
		return ((((Integer)(WorldEventsConfig.TIME_BETWEEN_AMBUSH_SPAWNS.get())).intValue()) * (60)) * (20);
	}
	public static boolean canSpawnAmbushes() {
		return ((getChanceForAmbushSpawn()) > (0)) && ((getTimeBetweenAmbushSpawns()) > (0));
	}
	public static int getChanceForTrainerSpawn() {
		return ((Integer)(WorldEventsConfig.SPAWN_CHANCE_TRAINER.get())).intValue();
	}
	public static int getTimeBetweenTrainerSpawns() {
		return ((((Integer)(WorldEventsConfig.TIME_BETWEEN_TRAINER_SPAWNS.get())).intValue()) * (60)) * (20);
	}
	public static boolean canSpawnTrainers() {
		return ((getChanceForTrainerSpawn()) > (0)) && ((getTimeBetweenTrainerSpawns()) > (0));
	}
	public static int getChanceForTraderSpawn() {
		return ((Integer)(WorldEventsConfig.SPAWN_CHANCE_TRADER.get())).intValue();
	}
	public static int getTimeBetweenTraderSpawns() {
		return ((((Integer)(WorldEventsConfig.TIME_BETWEEN_TRADER_SPAWNS.get())).intValue()) * (60)) * (20);
	}
	public static boolean canSpawnTraders() {
		return ((getChanceForTraderSpawn()) > (0)) && ((getTimeBetweenTraderSpawns()) > (0));
	}
	public static int getTimeBetweenPackages() {
		return ((((Integer)(FactionsConfig.TIME_BETWEEN_PACKAGE_DROPS.get())).intValue()) * (20)) * (60);
	}
	public static boolean isWantedPosterPackagesEnabled() {
		return (getTimeBetweenPackages()) > (0);
	}
	public static double getDorikiRewardMultiplier() {
		return ((Double)(GeneralConfig.DORIKI_REWARD_MULTIPLIER.get())).doubleValue();
	}
	public static double getBellyRewardMultiplier() {
		return ((Double)(GeneralConfig.BELLY_REWARD_MULTIPLIER.get())).doubleValue();
	}
	public static double getBountyRewardMultiplier() {
		return ((Double)(GeneralConfig.BOUNTY_REWARD_MULTIPLIER.get())).doubleValue();
	}
	public static double getHakiExpMultiplier() {
		return ((Double)(GeneralConfig.HAKI_EXP_MULTIPLIER.get())).doubleValue();
	}
	public static double getLoyaltyMultiplier() {
		return ((Double)(GeneralConfig.LOYALTY_MULTIPLIER.get())).doubleValue();
	}
	public static boolean isQuestProgressionEnabled() {
		return ((Boolean)(GeneralConfig.ENABLE_STYLES_PROGRESSION.get())).booleanValue();
	}
	public static boolean isQuestsEnabled() {
		return ((Boolean)(GeneralConfig.ENABLE_TRIALS.get())).booleanValue();
	}
	public static List<AbilityCore<? extends IAbility>> getBannedAbilities() {
		if (bannedAbilities.isEmpty()){
			Iterator var0 = ((List)(AbilitiesConfig.BANNED_ABILITIES.get())).iterator();
			do{
				if (var0.hasNext()){
					AbilityCore var1 = AbilityCore.get(ResourceLocation.parse(((String)(var0.next()))));
					if ((var1) != (null)){
						bannedAbilities.add(var1);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
		return bannedAbilities;
	}
	public static boolean isAbilityInvulnerabilityEnabled() {
		return ((Boolean)(abilityInvulnerability.get())).booleanValue();
	}
	public static boolean areExtraWaterChecksEnabled() {
		return ((Boolean)(AbilitiesConfig.WATER_CHECKS.get())).booleanValue();
	}
	public static boolean isLogiaDamageEffectEnabled() {
		return ((Boolean)(AbilitiesConfig.LOGIA_RETURN_EFFECT.get())).booleanValue();
	}
	public static boolean doLogiasHaveHurtHakiLogic() {
		return (((ServerConfig$LogiaProjectileHitLogic)(AbilitiesConfig.LOGIA_PROJECTILE_HIT_LOGIC.get())).equals(ServerConfig$LogiaProjectileHitLogic.HAKI)) || (((ServerConfig$LogiaProjectileHitLogic)(AbilitiesConfig.LOGIA_PROJECTILE_HIT_LOGIC.get())).equals(ServerConfig$LogiaProjectileHitLogic.EXTENDED));
	}
	public static boolean doLogiasHaveHurtExtendedLogic() {
		return ((ServerConfig$LogiaProjectileHitLogic)(AbilitiesConfig.LOGIA_PROJECTILE_HIT_LOGIC.get())).equals(ServerConfig$LogiaProjectileHitLogic.EXTENDED);
	}
	public static double getDevilFruitDropsFromLeavesChance() {
		return ((Double)(AbilitiesConfig.DEVIL_FRUIT_DROP_FROM_LEAVES.get())).doubleValue();
	}
	public static boolean isExtraHeartsEnabled() {
		return ((Boolean)(GeneralConfig.EXTRA_HEARTS.get())).booleanValue();
	}
	public static boolean isMobRewardsEnabled() {
		return ((Boolean)(GeneralConfig.MOB_REWARDS.get())).booleanValue();
	}
	public static boolean isAbilityGriefingEnabled() {
		return ((Boolean)(AbilitiesConfig.ABILITY_GRIEFING.get())).booleanValue();
	}
	public static boolean isSpecialSourceEventsEnabled() {
		return ((Boolean)(specialSourceEvents.get())).booleanValue();
	}
	public static boolean isYamiPowerEnabled() {
		return ((Boolean)(AbilitiesConfig.YAMI_POWER.get())).booleanValue();
	}
	public static boolean isAbilityFraudChecksEnabled() {
		return ((Boolean)(AbilitiesConfig.ABILITY_FRAUD_CHECKS.get())).booleanValue();
	}
	public static boolean isMinimumDorikiPerKillEnabled() {
		return ((Boolean)(GeneralConfig.MINIMUM_DORIKI_PER_KILL.get())).booleanValue();
	}
	public static int getKairosekiSpawnCount() {
		return ((Integer)(WorldFeaturesConfig.KAIROSEKI_SPAWN_COUNT.get())).intValue();
	}
	static  {
		protectionWhitelistedAbilities = new ArrayList();
		bannedImbueableItems = new ArrayList();
		bannedAbilities = new ArrayList();
		bannedDimensionsForStructures = new ArrayList();
		ModConfigSpec.Builder var0 = new ModConfigSpec.Builder();
		setupConfig(var0);
		SPEC = var0.build();
	}
}