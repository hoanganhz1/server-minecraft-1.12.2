package xyz.pixelatedw.mineminenomi.config;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.neoforged.neoforge.common.ModConfigSpec;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nConfig;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen$Compactness;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen$Selection;

public class ClientConfig {
	// Fields
	public static final ModConfigSpec SPEC;
	private static Map<String, ModConfigSpec.BooleanValue> cooldownVisual;
	private static ModConfigSpec.IntValue onFireVisibility;
	private static ModConfigSpec.BooleanValue alwaysLegUp;

	private static void setupConfig(ModConfigSpec.Builder var0) {
		var0.translation(ModI18nConfig.GENERAL_CATEGORY).push("General");
		onFireVisibility = var0.comment("Visibility when on fire while using a fire resistant fruit \nDefault: 20").translation(ModI18nConfig.FIRE_VISIBILITY_OPTION).defineInRange("Fire Visibility",25,0,100);
		AbilitiesConfig.HAKI_COLOR.createValue(var0);
		alwaysLegUp = var0.comment("Always hold the leg up for combat as a Black Leg user\nDefault: true").translation(ModI18nConfig.BLACK_LEG_ALWAYS_UP_OPTION).define("Black Leg Always Up",true);
		var0.translation(ModI18nConfig.COOLDOWN_VISUALS_CATEGORY).push("Cooldown Visuals");
		String[] var1 = new String[]{"Text",ModI18nConfig.TEXT_OPTION,"Color",ModI18nConfig.COLOR_OPTION};
		String[] var2 = var1;
		cooldownVisual = new HashMap();
		int var3 = 0;
		do{
			if ((var3) < (var2.length)){
				String var4 = var2[var3];
				String var5 = var2[(var3) + (1)];
				cooldownVisual.put(var4,var0.translation(var5).define(var4,true));
				var3 = (var3) + (2);
				continue;
			}else{
				break;
			}
		} while (true);
		AbilitiesConfig.COMBAT_STATE_UPDATE_CHAT_MESSAGGE.createValue(var0);
		var0.pop();
		var0.pop();
		var0.translation(ModI18nConfig.UI_CATEGORY).push("UI");
		UIConfig.DISPLAY_IN_SECONDS.createValue(var0);
		UIConfig.ABILITY_BARS_ON_SCREEN.createValue(var0);
		UIConfig.SHOW_KEYBIND.createValue(var0);
		UIConfig.MERGE_ABILITY_BONUSES.createValue(var0);
		UIConfig.SIMPLE_DISPLAYS.createValue(var0);
		UIConfig.USE_HEARTS_BAR.createValue(var0);
		UIConfig.SLOT_NUMBER_DISPLAY.createValue(var0);
		UIConfig.HIDE_ABILITY_STATS.createValue(var0);
		var0.translation(ModI18nConfig.ABILITY_LIST_CATEGORY).push("Ability List");
		UIConfig.ABILITY_LIST_COMPACTNESS.createValue(var0);
		UIConfig.ABILITY_LIST_SELECTION.createValue(var0);
		UIConfig.ABILITY_LIST_SHOW_TOOLTIPS.createValue(var0);
		UIConfig.ABILITY_LIST_SHOW_HELP.createValue(var0);
		var0.pop();
		var0.pop();
		var0.translation(ModI18nConfig.SYSTEM_CATEGORY).push("System");
		SystemConfig.UPDATE_MESSAGE.createValue(var0);
		SystemConfig.MOD_SPLASH_TEXT.createValue(var0);
		var0.pop();
	}
	public static void toggleHelpButton() {
		int var0 = (!(((Boolean)(UIConfig.ABILITY_LIST_SHOW_HELP.get())).booleanValue())) ? (1) : (0);
		UIConfig.ABILITY_LIST_SHOW_HELP.getValue().set(Boolean.valueOf((var0) != (0)));
		SPEC.save();
	}
	public static boolean getHelpButtonShown() {
		return ((Boolean)(UIConfig.ABILITY_LIST_SHOW_HELP.get())).booleanValue();
	}
	public static void toggleShowingTooltips() {
		int var0 = (!(((Boolean)(UIConfig.ABILITY_LIST_SHOW_TOOLTIPS.get())).booleanValue())) ? (1) : (0);
		UIConfig.ABILITY_LIST_SHOW_TOOLTIPS.getValue().set(Boolean.valueOf((var0) != (0)));
		SPEC.save();
	}
	public static boolean getTooltipsShown() {
		return ((Boolean)(UIConfig.ABILITY_LIST_SHOW_TOOLTIPS.get())).booleanValue();
	}
	public static void setSelectionMode(AbilitiesListScreen$Selection var0) {
		UIConfig.ABILITY_LIST_SELECTION.getValue().set(var0);
		SPEC.save();
	}
	public static AbilitiesListScreen$Selection getSelectionMode() {
		return ((AbilitiesListScreen$Selection)(UIConfig.ABILITY_LIST_SELECTION.get()));
	}
	public static void setCompactness(AbilitiesListScreen$Compactness var0) {
		UIConfig.ABILITY_LIST_COMPACTNESS.getValue().set(var0);
		SPEC.save();
	}
	public static AbilitiesListScreen$Compactness getCompactness() {
		return ((AbilitiesListScreen$Compactness)(UIConfig.ABILITY_LIST_COMPACTNESS.get()));
	}
	public static boolean isDisplayInTicks() {
		return (UIConfig.SLOT_NUMBER_DISPLAY.get()) == (ClientConfig$SlotNumberVisuals.TICKS);
	}
	public static boolean isDisplayInSeconds() {
		return (UIConfig.SLOT_NUMBER_DISPLAY.get()) == (ClientConfig$SlotNumberVisuals.SECONDS);
	}
	public static boolean isDisplayInPercentage() {
		return (UIConfig.SLOT_NUMBER_DISPLAY.get()) == (ClientConfig$SlotNumberVisuals.PERCENTAGE);
	}
	public static boolean hidesAbilityStats() {
		return ((Boolean)(UIConfig.HIDE_ABILITY_STATS.get())).booleanValue();
	}
	public static boolean hasCombatUpdateChatMessageEnabled() {
		return ((Boolean)(AbilitiesConfig.COMBAT_STATE_UPDATE_CHAT_MESSAGGE.get())).booleanValue();
	}
	public static boolean isSimpleDisplaysEnabled() {
		return ((Boolean)(UIConfig.SIMPLE_DISPLAYS.get())).booleanValue();
	}
	public static boolean hasHeartsUI() {
		if (!(((Boolean)(GeneralConfig.EXTRA_HEARTS.get())).booleanValue())){
			return false;
		}else{
			return ((Boolean)(UIConfig.USE_HEARTS_BAR.get())).booleanValue();
		}
	}
	public static boolean isAbilityBonusesMergeEnable() {
		return ((Boolean)(UIConfig.MERGE_ABILITY_BONUSES.get())).booleanValue();
	}
	public static boolean isModSplashTextEnabled() {
		return ((Boolean)(SystemConfig.MOD_SPLASH_TEXT.get())).booleanValue();
	}
	public static boolean isBlackLegAlwaysUp() {
		return ((Boolean)(alwaysLegUp.get())).booleanValue();
	}
	public static Color getHakiColor() {
		Color var0 = new Color(16711680);
		try{
			String var1 = ((String)(AbilitiesConfig.HAKI_COLOR.get()));
			if (var1.startsWith("#")){
				var0 = WyHelper.hexToRGB(var1);
			}else{
				int var2 = Integer.parseInt(var1);
				if (((var2) >= (0)) && ((var2) <= (16777215))){
					var0 = new Color(var2);
				}else{
					int var3 = var2;
					String var4 = "Haki Color outside its bounds: " + var3 + " Can only use numbers between 0 and 16777215";
					throw new Exception((String)(var4));
				}
			}
		}catch(Exception var1_1){
			var1_1.printStackTrace();
		}
		return var0;
	}
	public static boolean showSlotKeybinds() {
		return ((Boolean)(UIConfig.SHOW_KEYBIND.get())).booleanValue();
	}
	public static int getAbilityBarsOnScreen() {
		return ((Integer)(UIConfig.ABILITY_BARS_ON_SCREEN.get())).intValue();
	}
	public static boolean hasTextCooldownVisuals() {
		return ((Boolean)(((ModConfigSpec.BooleanValue)(cooldownVisual.get("Text"))).get())).booleanValue();
	}
	public static boolean hasColorCooldownVisuals() {
		return ((Boolean)(((ModConfigSpec.BooleanValue)(cooldownVisual.get("Color"))).get())).booleanValue();
	}
	public static int getFireVisibility() {
		return ((Integer)(onFireVisibility.get())).intValue();
	}
	static  {
		ModConfigSpec.Builder var0 = new ModConfigSpec.Builder();
		setupConfig(var0);
		SPEC = var0.build();
	}
}