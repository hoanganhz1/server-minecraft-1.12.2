package xyz.pixelatedw.mineminenomi.integrations.clothconfig;

import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.ColorEntry;
import me.shedaniel.clothconfig2.gui.entries.DoubleListEntry;
import me.shedaniel.clothconfig2.gui.entries.EnumListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerListListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerSliderEntry;
import me.shedaniel.clothconfig2.gui.entries.LongListEntry;
import me.shedaniel.clothconfig2.gui.entries.StringListListEntry;
import me.shedaniel.clothconfig2.gui.entries.SubCategoryListEntry;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.ColorFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.DoubleFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.EnumSelectorBuilder;
import me.shedaniel.clothconfig2.impl.builders.FieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntListBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntSliderBuilder;
import me.shedaniel.clothconfig2.impl.builders.LongFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.StringListBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.ModConfigSpec;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.config.options.BooleanOption;
import xyz.pixelatedw.mineminenomi.api.config.options.ColorOption;
import xyz.pixelatedw.mineminenomi.api.config.options.DoubleOption;
import xyz.pixelatedw.mineminenomi.api.config.options.EnumOption;
import xyz.pixelatedw.mineminenomi.api.config.options.IntegerListOption;
import xyz.pixelatedw.mineminenomi.api.config.options.IntegerOption;
import xyz.pixelatedw.mineminenomi.api.config.options.LongOption;
import xyz.pixelatedw.mineminenomi.api.config.options.StringListOption;
import xyz.pixelatedw.mineminenomi.config.AbilitiesConfig;
import xyz.pixelatedw.mineminenomi.config.ChallengesConfig;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.config.CommonConfig;
import xyz.pixelatedw.mineminenomi.config.FactionsConfig;
import xyz.pixelatedw.mineminenomi.config.GeneralConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.config.SystemConfig;
import xyz.pixelatedw.mineminenomi.config.UIConfig;
import xyz.pixelatedw.mineminenomi.config.WorldEventsConfig;
import xyz.pixelatedw.mineminenomi.config.WorldFeaturesConfig;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nConfig;

public class ClothConfigIntegration {
	public static void registerModConfig(ModContainer var0) {
		IConfigScreenFactory var1 = ClothConfigIntegration::createConfigScreen;
		var0.registerExtensionPoint(IConfigScreenFactory.class,(IExtensionPoint)(var1));
	}
	private static final Screen createConfigScreen(ModContainer var0, Screen var1) {
		ConfigBuilder var2 = ConfigBuilder.create();
		int var3 = (1) + (new Random().nextInt(3));
		int var4 = var3;
		String var5 = "textures/blocks/poneglyph" + var4 + ".png";
		var2.setTitle((Component)(Component.translatable(ModI18nConfig.CONFIG_TITLE))).setDefaultBackgroundTexture(ResourceLocation.fromNamespaceAndPath("mineminenomi",var5));
		ConfigEntryBuilder var6 = var2.entryBuilder();
		if (ServerConfig.SPEC.isLoaded()){
			ConfigCategory var7 = var2.getOrCreateCategory((Component)(Component.translatable(ModI18nConfig.GENERAL_CATEGORY)));
			var7.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.EXTRA_HEARTS)));
			var7.addEntry((AbstractConfigListEntry)(addIntSlider(var6,GeneralConfig.HEALTH_GAIN_FREQUENCY)));
			var7.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.RACE_RANDOMIZER)));
			var7.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.ALLOW_SUB_RACE_SELECT)));
			var7.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.PUBLIC_REMOVEDF)));
			var7.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.PUBLIC_CHECK_FRUITS)));
			var7.addEntry((AbstractConfigListEntry)(addIntField(var6,GeneralConfig.DORIKI_LIMIT)));
			var7.addEntry((AbstractConfigListEntry)(addIntField(var6,GeneralConfig.HAKI_EXP_LIMIT)));
			var7.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.DESTROY_SPAWNER)));
			var7.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.DESTROY_WATER)));
			SubCategoryBuilder var8 = var6.startSubCategory((Component)(Component.translatable(ModI18nConfig.NPCS_CATEGORY))).setExpanded(false);
			var8.add((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.MOB_REWARDS)));
			var8.add((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.MINIMUM_DORIKI_PER_KILL)));
			var8.add((AbstractConfigListEntry)(addDoubleField(var6,GeneralConfig.DORIKI_REWARD_MULTIPLIER)));
			var8.add((AbstractConfigListEntry)(addDoubleField(var6,GeneralConfig.BELLY_REWARD_MULTIPLIER)));
			var8.add((AbstractConfigListEntry)(addDoubleField(var6,GeneralConfig.BOUNTY_REWARD_MULTIPLIER)));
			var8.add((AbstractConfigListEntry)(addDoubleField(var6,GeneralConfig.HAKI_EXP_MULTIPLIER)));
			var8.add((AbstractConfigListEntry)(addDoubleField(var6,GeneralConfig.LOYALTY_MULTIPLIER)));
			var8.add((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.DESPAWN_WITH_NAMETAG)));
			var8.add((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.NATIVE_HAKI)));
			var7.addEntry((AbstractConfigListEntry)(var8.build()));
			Component[] var9 = new Component[]{Component.translatable(ModI18nConfig.KEEP_STATS_CATEGORY_TOOLTIP)};
			SubCategoryBuilder var10 = var6.startSubCategory((Component)(Component.literal("Keep Stats"))).setTooltip(var9).setExpanded(false);
			var10.add((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.RACE_KEEP)));
			var10.add((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.FACTION_KEEP)));
			var10.add((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.FIGHTING_STYLE_KEEP)));
			var10.add((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.DEVIL_FRUIT_KEEP)));
			var10.add((AbstractConfigListEntry)(addIntSlider(var6,GeneralConfig.DORIKI_KEEP_PERCENTAGE)));
			var10.add((AbstractConfigListEntry)(addIntSlider(var6,GeneralConfig.BOUNTY_KEEP_PERCENTAGE)));
			var10.add((AbstractConfigListEntry)(addIntSlider(var6,GeneralConfig.BELLY_KEEP_PERCENTAGE)));
			var10.add((AbstractConfigListEntry)(addIntSlider(var6,GeneralConfig.HAKI_EXP_KEEP_PERCENTAGE)));
			var10.add((AbstractConfigListEntry)(addIntSlider(var6,GeneralConfig.LOYALTY_KEEP_PERCENTAGE)));
			var7.addEntry((AbstractConfigListEntry)(var10.build()));
			SubCategoryBuilder var11 = var6.startSubCategory((Component)(Component.literal("Quests & Trials"))).setExpanded(false);
			var11.add((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.ENABLE_TRIALS)));
			var11.add((AbstractConfigListEntry)(addBooleanToggle(var6,GeneralConfig.ENABLE_STYLES_PROGRESSION)));
			var7.addEntry((AbstractConfigListEntry)(var11.build()));
			ConfigCategory var12 = var2.getOrCreateCategory((Component)(Component.translatable(ModI18nConfig.ABILITIES_CATEGORY)));
			var12.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,AbilitiesConfig.ABILITY_GRIEFING)));
			var12.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,AbilitiesConfig.ABILITY_FRAUD_CHECKS)));
			var12.addEntry((AbstractConfigListEntry)(addIntSlider(var6,AbilitiesConfig.ABILITY_BARS)));
			var12.addEntry((AbstractConfigListEntry)(addIntSlider(var6,AbilitiesConfig.OPEN_WORLD_FRUIT_USERS)));
			var12.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,AbilitiesConfig.YAMI_POWER)));
			var12.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,AbilitiesConfig.ENABLE_AWAKENINGS)));
			var12.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,AbilitiesConfig.WATER_CHECKS)));
			var12.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,AbilitiesConfig.SHARED_COOLDOWNS)));
			var12.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,AbilitiesConfig.REMOVE_Y_RESTRICTION)));
			var12.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,AbilitiesConfig.RANDOMIZED_FRUITS)));
			var12.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,AbilitiesConfig.COMBAT_STATE_UPDATE_CHAT_MESSAGGE)));
			var12.addEntry((AbstractConfigListEntry)(addDoubleField(var6,AbilitiesConfig.DEVIL_FRUIT_DROP_FROM_LEAVES)));
			var12.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,AbilitiesConfig.LOGIA_INVULNERABILITY)));
			var12.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,AbilitiesConfig.LOGIA_RETURN_EFFECT)));
			var12.addEntry((AbstractConfigListEntry)(addEnumSelector(var6,AbilitiesConfig.LOGIA_PROJECTILE_HIT_LOGIC)));
			var12.addEntry((AbstractConfigListEntry)(addIntSlider(var6,AbilitiesConfig.PUNCH_ABILITY_LIMIT)));
			var12.addEntry((AbstractConfigListEntry)(addDoubleField(var6,AbilitiesConfig.DESTRUCTION_MULTIPLIER)));
			var12.addEntry((AbstractConfigListEntry)(addStringList(var6,AbilitiesConfig.BANNED_ABILITIES)));
			SubCategoryBuilder var13 = var6.startSubCategory((Component)(Component.translatable(ModI18nConfig.HAKI_CATEGORY))).setExpanded(true);
			var13.add((AbstractConfigListEntry)(addEnumSelector(var6,AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC)));
			var13.add((AbstractConfigListEntry)(addEnumSelector(var6,AbilitiesConfig.HAOSHOKU_HAKI_COLORING_LOGIC)));
			var13.add((AbstractConfigListEntry)(addColorField(var6,AbilitiesConfig.HAKI_COLOR)));
			var12.addEntry((AbstractConfigListEntry)(var13.build()));
			SubCategoryBuilder var14 = var6.startSubCategory((Component)(Component.translatable(ModI18nConfig.ONE_FRUIT_CATEGORY))).setExpanded(false);
			var14.add((AbstractConfigListEntry)(addEnumSelector(var6,AbilitiesConfig.ONE_FRUIT_PER_WORLD)));
			var14.add((AbstractConfigListEntry)(addBooleanToggle(var6,AbilitiesConfig.UNABLE_TO_PICKUP_DF)));
			var14.add((AbstractConfigListEntry)(addIntSlider(var6,AbilitiesConfig.FRUITS_LIMIT_INVENTORY)));
			var14.add((AbstractConfigListEntry)(addIntSlider(var6,AbilitiesConfig.DAYS_FOR_INACTIVITY)));
			var14.add((AbstractConfigListEntry)(addEnumSelector(var6,AbilitiesConfig.ONE_FRUIT_PER_WORLD_CHUNK_DELETION)));
			var12.addEntry((AbstractConfigListEntry)(var14.build()));
			SubCategoryBuilder var15 = var6.startSubCategory((Component)(Component.translatable(ModI18nConfig.ABILITY_PROTECTION_CATEGORY))).setExpanded(false);
			var15.add((AbstractConfigListEntry)(addStringList(var6,AbilitiesConfig.GLOBAL_PROTECTION_WHITELIST)));
			var15.add((AbstractConfigListEntry)(addLongField(var6,AbilitiesConfig.GLOBAL_PROTECTION_RESTORATION_GRACE)));
			var12.addEntry((AbstractConfigListEntry)(var15.build()));
			SubCategoryBuilder var16 = var6.startSubCategory((Component)(Component.translatable(ModI18nConfig.FRUIT_REINCARNATION_CATEGORY))).setExpanded(false);
			var16.add((AbstractConfigListEntry)(addIntSlider(var6,AbilitiesConfig.DROPPED_APPLES_RESPAWN_CHANCE)));
			var16.add((AbstractConfigListEntry)(addIntSlider(var6,AbilitiesConfig.ENTITY_INVENTORY_APPLES_RESPAWN_CHANCE)));
			var16.add((AbstractConfigListEntry)(addIntSlider(var6,AbilitiesConfig.CHESTS_APPLES_RESPAWN_CHANCE)));
			var12.addEntry((AbstractConfigListEntry)(var16.build()));
			ConfigCategory var17 = var2.getOrCreateCategory((Component)(Component.translatable(ModI18nConfig.FACTION_CATEGORY)));
			var17.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,FactionsConfig.DISABLE_FRIENDLY_FIRE)));
			SubCategoryBuilder var18 = var6.startSubCategory((Component)(Component.translatable(ModI18nConfig.BOUNTY_CATEGORY))).setExpanded(false);
			var18.add((AbstractConfigListEntry)(addIntSlider(var6,FactionsConfig.TIME_BETWEEN_PACKAGE_DROPS)));
			var17.addEntry((AbstractConfigListEntry)(var18.build()));
			SubCategoryBuilder var19 = var6.startSubCategory((Component)(Component.translatable(ModI18nConfig.CREW_CATEGORY))).setExpanded(false);
			var19.add((AbstractConfigListEntry)(addIntField(var6,FactionsConfig.CREW_BOUNTY_REQUIREMENT)));
			var19.add((AbstractConfigListEntry)(addBooleanToggle(var6,FactionsConfig.WORLD_MESSAGE_ON_CREW_CREATE)));
			var17.addEntry((AbstractConfigListEntry)(var19.build()));
			ConfigCategory var20 = var2.getOrCreateCategory((Component)(Component.translatable(ModI18nConfig.CHALLENGES_CATEGORY)));
			var20.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,ChallengesConfig.ENABLE_CHALLENGES)));
			var20.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,ChallengesConfig.CHALLENGE_CACHING)));
			var20.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,ChallengesConfig.RETURN_TO_SAFETY)));
			var20.addEntry((AbstractConfigListEntry)(addIntSlider(var6,ChallengesConfig.ARENA_CONSTRUCTION_SPEED)));
			var20.addEntry((AbstractConfigListEntry)(addIntList(var6,ChallengesConfig.DORIKI_REWARD_POOL)));
			var20.addEntry((AbstractConfigListEntry)(addIntList(var6,ChallengesConfig.BELLY_REWARD_POOL)));
			var20.addEntry((AbstractConfigListEntry)(addIntList(var6,ChallengesConfig.HAKI_REWARD_POOL)));
			ConfigCategory var21 = var2.getOrCreateCategory((Component)(Component.translatable(ModI18nConfig.WORLD_EVENTS_CATEGORY)));
			var21.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,WorldEventsConfig.SPAWN_WORLD_HUMANOIDS)));
			var21.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,WorldEventsConfig.SPAWN_WORLD_ANIMALS)));
			var21.addEntry((AbstractConfigListEntry)(addEnumSelector(var6,WorldEventsConfig.NEW_WORLD_MODE)));
			var21.addEntry((AbstractConfigListEntry)(addDoubleField(var6,WorldEventsConfig.NEW_WORLD_START_RADIUS)));
			SubCategoryBuilder var22 = var6.startSubCategory((Component)(Component.translatable(ModI18nConfig.TRADERS_CATEGORY))).setExpanded(false);
			var22.add((AbstractConfigListEntry)(addIntSlider(var6,WorldEventsConfig.TIME_BETWEEN_TRADER_SPAWNS)));
			var22.add((AbstractConfigListEntry)(addIntSlider(var6,WorldEventsConfig.SPAWN_CHANCE_TRADER)));
			var21.addEntry((AbstractConfigListEntry)(var22.build()));
			SubCategoryBuilder var23 = var6.startSubCategory((Component)(Component.translatable(ModI18nConfig.TRAINERS_CATEGORY))).setExpanded(false);
			var23.add((AbstractConfigListEntry)(addIntSlider(var6,WorldEventsConfig.TIME_BETWEEN_TRAINER_SPAWNS)));
			var23.add((AbstractConfigListEntry)(addIntSlider(var6,WorldEventsConfig.SPAWN_CHANCE_TRAINER)));
			var21.addEntry((AbstractConfigListEntry)(var23.build()));
			SubCategoryBuilder var24 = var6.startSubCategory((Component)(Component.translatable(ModI18nConfig.AMBUSHES_CATEGORY))).setExpanded(false);
			var24.add((AbstractConfigListEntry)(addIntSlider(var6,WorldEventsConfig.TIME_BETWEEN_AMBUSH_SPAWNS)));
			var24.add((AbstractConfigListEntry)(addIntSlider(var6,WorldEventsConfig.SPAWN_CHANCE_AMBUSH)));
			var21.addEntry((AbstractConfigListEntry)(var24.build()));
			SubCategoryBuilder var25 = var6.startSubCategory((Component)(Component.translatable(ModI18nConfig.SPAWN_CHANCES_CATEGORY))).setExpanded(false);
			var25.add((AbstractConfigListEntry)(addDoubleField(var6,WorldEventsConfig.GRUNT_SPAWN_CHANCE)));
			var25.add((AbstractConfigListEntry)(addDoubleField(var6,WorldEventsConfig.BRUTE_SPAWN_CHANCE)));
			var25.add((AbstractConfigListEntry)(addDoubleField(var6,WorldEventsConfig.SNIPER_SPAWN_CHANCE)));
			var25.add((AbstractConfigListEntry)(addDoubleField(var6,WorldEventsConfig.CAPTAIN_SPAWN_CHANCE)));
			var25.add((AbstractConfigListEntry)(addDoubleField(var6,WorldEventsConfig.PACIFISTA_SPAWN_CHANCE)));
			var21.addEntry((AbstractConfigListEntry)(var25.build()));
			ConfigCategory var26 = var2.getOrCreateCategory((Component)(Component.translatable(ModI18nConfig.STRUCTURES_CATEGORY)));
			var26.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,WorldFeaturesConfig.SPAWN_BIOMES)));
			var26.addEntry((AbstractConfigListEntry)(addIntSlider(var6,WorldFeaturesConfig.SPAWN_CHANCE_TRAINING_STRUCTURES)));
			var26.addEntry((AbstractConfigListEntry)(addIntSlider(var6,WorldFeaturesConfig.SPAWN_CHANCE_SKY_ISLANDS)));
			var26.addEntry((AbstractConfigListEntry)(addIntSlider(var6,WorldFeaturesConfig.SPAWN_CHANCE_SMALL_SHIPS)));
			var26.addEntry((AbstractConfigListEntry)(addIntSlider(var6,WorldFeaturesConfig.SPAWN_CHANCE_MEDIUM_SHIPS)));
			var26.addEntry((AbstractConfigListEntry)(addIntSlider(var6,WorldFeaturesConfig.SPAWN_CHANCE_LARGE_SHIPS)));
			var26.addEntry((AbstractConfigListEntry)(addIntSlider(var6,WorldFeaturesConfig.SPAWN_CHANCE_CAMPS)));
			var26.addEntry((AbstractConfigListEntry)(addIntSlider(var6,WorldFeaturesConfig.SPAWN_CHANCE_SMALL_BASES)));
			var26.addEntry((AbstractConfigListEntry)(addIntSlider(var6,WorldFeaturesConfig.SPAWN_CHANCE_LARGE_BASES)));
			var26.addEntry((AbstractConfigListEntry)(addIntSlider(var6,WorldFeaturesConfig.SPAWN_CHANCE_WATCH_TOWERS)));
			var26.addEntry((AbstractConfigListEntry)(addIntSlider(var6,WorldFeaturesConfig.SPAWN_CHANCE_PONEGLYPHS)));
			var26.addEntry((AbstractConfigListEntry)(addIntSlider(var6,WorldFeaturesConfig.KAIROSEKI_SPAWN_COUNT)));
		}
		if (ClientConfig.SPEC.isLoaded()){
			ConfigCategory var7_1 = var2.getOrCreateCategory((Component)(Component.translatable(ModI18nConfig.UI_CATEGORY)));
			var7_1.addEntry((AbstractConfigListEntry)(addIntSlider(var6,UIConfig.ABILITY_BARS_ON_SCREEN)));
			var7_1.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,UIConfig.SHOW_KEYBIND)));
			var7_1.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,UIConfig.MERGE_ABILITY_BONUSES)));
			var7_1.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,UIConfig.HIDE_ABILITY_STATS)));
			var7_1.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,UIConfig.SIMPLE_DISPLAYS)));
			var7_1.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,UIConfig.USE_HEARTS_BAR)));
			var7_1.addEntry((AbstractConfigListEntry)(addEnumSelector(var6,UIConfig.SLOT_NUMBER_DISPLAY)));
			var7_1.addEntry((AbstractConfigListEntry)(addEnumSelector(var6,UIConfig.ABILITY_LIST_COMPACTNESS)));
			var7_1.addEntry((AbstractConfigListEntry)(addEnumSelector(var6,UIConfig.ABILITY_LIST_SELECTION)));
			var7_1.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,UIConfig.ABILITY_LIST_SHOW_TOOLTIPS)));
			var7_1.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,UIConfig.ABILITY_LIST_SHOW_HELP)));
		}
		ConfigCategory var7_2 = var2.getOrCreateCategory((Component)(Component.translatable(ModI18nConfig.SYSTEM_CATEGORY)));
		if (CommonConfig.SPEC.isLoaded()){
			var7_2.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,SystemConfig.MASTER_COMMAND)));
			var7_2.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,SystemConfig.ENABLE_DEBUG_COMMANDS)));
		}
		if (ClientConfig.SPEC.isLoaded()){
			var7_2.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,SystemConfig.UPDATE_MESSAGE)));
			var7_2.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,SystemConfig.MOD_SPLASH_TEXT)));
		}
		if (ServerConfig.SPEC.isLoaded()){
			var7_2.addEntry((AbstractConfigListEntry)(addBooleanToggle(var6,SystemConfig.EXPERIMENTAL_TIMERS)));
		}
		var2.setParentScreen(var1);
		return var2.build();
	}
	private static ColorEntry addColorField(ConfigEntryBuilder var0, ColorOption var1) {
		int var2 = WyHelper.hexToRGB(((String)(var1.getValue().get()))).getRGB();
		ColorOption var3 = var1;
		final ColorOption var3_f1 = var3;
		Consumer<Integer> var4 = (Integer l0) -> {
			var3_f1.saveColor(new Color(l0.intValue()));
		};
		return var0.startColorField(var1.getTitleComponent(),me.shedaniel.math.Color.ofOpaque(var2)).setDefaultValue(16711680).setTooltip(var1.getDescriptionComponent()).setSaveConsumer(var4).build();
	}
	private static StringListListEntry addStringList(ConfigEntryBuilder var0, StringListOption var1) {
		StringListOption var2 = var1;
		final StringListOption var2_f1 = var2;
		Consumer var3 = (Consumer) ((Consumer<List>) ((l0) -> {
			var2_f1.getValue().set(l0);
		}));
		return var0.startStrList(var1.getTitleComponent(),((List)(var1.getValue().get()))).setDefaultValue(var1.getDefault()).setTooltip(var1.getDescriptionComponent()).setSaveConsumer(var3).build();
	}
	private static IntegerListListEntry addIntList(ConfigEntryBuilder var0, IntegerListOption var1) {
		IntegerListOption var2 = var1;
		final IntegerListOption var2_f1 = var2;
		Consumer var3 = (Consumer) ((Consumer<List>) ((l0) -> {
			var2_f1.getValue().set(l0);
		}));
		return var0.startIntList(var1.getTitleComponent(),((List)(var1.getValue().get()))).setDefaultValue(var1.getDefault()).setTooltip(var1.getDescriptionComponent()).setSaveConsumer(var3).build();
	}
	private static <T extends Enum<T>> EnumListEntry<T> addEnumSelector(ConfigEntryBuilder var0, EnumOption<T> var1) {
		EnumOption<T> var2 = var1;
		final EnumOption<T> var2_f1 = var2;
		Consumer var3 = (Consumer) ((Consumer<Enum>) ((l0) -> {
			var2_f1.getValue().set(l0);
		}));
		return (EnumListEntry<T>) (EnumListEntry) (((me.shedaniel.clothconfig2.api.ConfigEntryBuilder)(var0)).startEnumSelector((Component)(var1.getTitleComponent()),(Class)(var1.getEnumClass()),(Enum)(((Enum)(var1.getValue().get())))).setTooltip(var1.getDescriptionComponent()).setSaveConsumer(var3).setDefaultValue(((Enum)(var1.getDefault()))).build());
	}
	private static BooleanListEntry addBooleanToggle(ConfigEntryBuilder var0, BooleanOption var1) {
		BooleanOption var2 = var1;
		final BooleanOption var2_f1 = var2;
		Consumer<Boolean> var3 = (Boolean l0) -> {
			var2_f1.getValue().set(l0);
		};
		return ((BooleanToggleBuilder)(((me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder)(var0.startBooleanToggle(var1.getTitleComponent(),((Boolean)(var1.getValue().get())).booleanValue()).setTooltip(var1.getDescriptionComponent()))).setSaveConsumer((Consumer)(var3)).setDefaultValue(((Boolean)(var1.getDefault()))))).build();
	}
	private static DoubleListEntry addDoubleField(ConfigEntryBuilder var0, DoubleOption var1) {
		DoubleOption var2 = var1;
		final DoubleOption var2_f1 = var2;
		Consumer<Double> var3 = (Double l0) -> {
			var2_f1.getValue().set(l0);
		};
		return ((DoubleFieldBuilder)(((DoubleFieldBuilder)(((DoubleFieldBuilder)(((me.shedaniel.clothconfig2.impl.builders.DoubleFieldBuilder)(var0.startDoubleField(var1.getTitleComponent(),((Double)(var1.getValue().get())).doubleValue()).setTooltip(var1.getDescriptionComponent()))).setSaveConsumer((Consumer)(var3)).setMin(((Double)(var1.getMin()))))).setMax(((Double)(var1.getMax()))))).setDefaultValue(((Double)(var1.getDefault()))))).build();
	}
	private static IntegerListEntry addIntField(ConfigEntryBuilder var0, IntegerOption var1) {
		IntegerOption var2 = var1;
		final IntegerOption var2_f1 = var2;
		Consumer<Integer> var3 = (Integer l0) -> {
			var2_f1.getValue().set(l0);
		};
		return ((IntFieldBuilder)(((IntFieldBuilder)(((IntFieldBuilder)(((me.shedaniel.clothconfig2.impl.builders.IntFieldBuilder)(var0.startIntField(var1.getTitleComponent(),((Integer)(var1.getValue().get())).intValue()).setTooltip(var1.getDescriptionComponent()))).setSaveConsumer((Consumer)(var3)).setMin(((Integer)(var1.getMin()))))).setMax(((Integer)(var1.getMax()))))).setDefaultValue(((Integer)(var1.getDefault()))))).build();
	}
	private static IntegerSliderEntry addIntSlider(ConfigEntryBuilder var0, IntegerOption var1) {
		IntegerOption var2 = var1;
		final IntegerOption var2_f1 = var2;
		Consumer<Integer> var3 = (Integer l0) -> {
			var2_f1.getValue().set(l0);
		};
		return ((IntSliderBuilder)(((IntSliderBuilder)(((IntSliderBuilder)(((me.shedaniel.clothconfig2.impl.builders.IntSliderBuilder)(var0.startIntSlider(var1.getTitleComponent(),((Integer)(var1.getValue().get())).intValue(),((Integer)(var1.getMin())).intValue(),((Integer)(var1.getMax())).intValue()).setTooltip(var1.getDescriptionComponent()))).setSaveConsumer((Consumer)(var3)).setMin(((Integer)(var1.getMin()))))).setMax(((Integer)(var1.getMax()))))).setDefaultValue(((Integer)(var1.getDefault()))))).build();
	}
	private static LongListEntry addLongField(ConfigEntryBuilder var0, LongOption var1) {
		LongOption var2 = var1;
		final LongOption var2_f1 = var2;
		Consumer<Long> var3 = (Long l0) -> {
			var2_f1.getValue().set(l0);
		};
		return ((LongFieldBuilder)(((LongFieldBuilder)(((LongFieldBuilder)(((me.shedaniel.clothconfig2.impl.builders.LongFieldBuilder)(var0.startLongField(var1.getTitleComponent(),((Long)(var1.getValue().get())).longValue()).setTooltip(var1.getDescriptionComponent()))).setSaveConsumer((Consumer)(var3)).setMin(((Long)(var1.getMin()))))).setMax(((Long)(var1.getMax()))))).setDefaultValue(((Long)(var1.getDefault()))))).build();
	}
}