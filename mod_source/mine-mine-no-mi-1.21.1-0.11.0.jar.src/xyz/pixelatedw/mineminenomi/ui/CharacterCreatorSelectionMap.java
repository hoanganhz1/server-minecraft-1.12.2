package xyz.pixelatedw.mineminenomi.ui;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.abilities.DummyAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class CharacterCreatorSelectionMap {
	// Fields
	public static final AbilityCore<DummyAbility> CYBORG_ARMOR_PERK;
	public static final AbilityCore<DummyAbility> FISHMAN_SWIM_SPEED_PERK;
	public static final AbilityCore<DummyAbility> FISHMAN_DAMAGE_PERK;
	public static final AbilityCore<DummyAbility> MINK_SPEED_PERK;
	public static final AbilityCore<DummyAbility> MINK_JUMP_PERK;
	public static final AbilityCore<DummyAbility> SWORDSMAN_DAMAGE_PERK;
	public static final AbilityCore<DummyAbility> SNIPER_ACCURACY_PERK;
	public static final AbilityCore<DummyAbility> SNIPER_GOGGLES_PERK;
	public static final AbilityCore<DummyAbility> BLACK_LEG_DAMAGE_PERK;
	public static final AbilityCore<DummyAbility> BLACK_LEG_SPEED_PERK;
	public static final AbilityCore<DummyAbility> BRAWLER_DAMAGE_PERK;

	static  {
		Component[] var0 = new Component[]{ModI18nAbilities.PERK_CYBORG_ARMOR_BONUS};
		CYBORG_ARMOR_PERK = (AbilityCore) (new AbilityCore$Builder("cyborg_armor","Cyborg Armor",AbilityCategory.RACIAL,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DummyAbility::new)))).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi","cyborg_armor")).setIcon(ModResources.PERK_ICON).addDescriptionLine(var0).build("mineminenomi"));
		Component[] var1 = new Component[]{ModI18nAbilities.PERK_FISHMAN_SWIM_SPEED_BONUS};
		FISHMAN_SWIM_SPEED_PERK = (AbilityCore) (new AbilityCore$Builder("fishman_swim_speed","Fishman Swim Speed",AbilityCategory.RACIAL,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DummyAbility::new)))).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi","fishman_swim_speed")).setIcon(ModResources.PERK_ICON).addDescriptionLine(var1).build("mineminenomi"));
		Component[] var2 = new Component[]{ModI18nAbilities.PERK_FISHMAN_DAMAGE_SPEED_BONUS};
		FISHMAN_DAMAGE_PERK = (AbilityCore) (new AbilityCore$Builder("fishman_damage","Fishman Damage",AbilityCategory.RACIAL,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DummyAbility::new)))).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi","fishman_damage")).setIcon(ModResources.PERK_ICON).addDescriptionLine(var2).build("mineminenomi"));
		Component[] var3 = new Component[]{ModI18nAbilities.PERK_MINK_SPEED_BONUS};
		MINK_SPEED_PERK = (AbilityCore) (new AbilityCore$Builder("mink_speed","Mink Speed",AbilityCategory.RACIAL,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DummyAbility::new)))).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi","mink_speed")).setIcon(ModResources.PERK_ICON).addDescriptionLine(var3).build("mineminenomi"));
		Component[] var4 = new Component[]{ModI18nAbilities.PERK_MINK_JUMP_BONUS};
		MINK_JUMP_PERK = (AbilityCore) (new AbilityCore$Builder("mink_jump","Mink Jump",AbilityCategory.RACIAL,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DummyAbility::new)))).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi","mink_jump")).setIcon(ModResources.PERK_ICON).addDescriptionLine(var4).build("mineminenomi"));
		Component[] var5 = new Component[]{ModI18nAbilities.PERK_SWORDSMAN_DAMAGE_BONUS};
		SWORDSMAN_DAMAGE_PERK = (AbilityCore) (new AbilityCore$Builder("swordsman_damage","Swordsman Damage",AbilityCategory.STYLE,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DummyAbility::new)))).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi","swordsman_damage")).setIcon(ModResources.PERK_ICON).addDescriptionLine(var5).build("mineminenomi"));
		Component[] var6 = new Component[]{ModI18nAbilities.PERK_SNIPER_ACCURACY_BONUS};
		SNIPER_ACCURACY_PERK = (AbilityCore) (new AbilityCore$Builder("sniper_accuracy","Sniper Accuracy",AbilityCategory.STYLE,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DummyAbility::new)))).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi","sniper_accuracy")).setIcon(ModResources.PERK_ICON).addDescriptionLine(var6).build("mineminenomi"));
		Component[] var7 = new Component[]{ModI18nAbilities.PERK_SNIPER_GOGGLES_BONUS};
		SNIPER_GOGGLES_PERK = (AbilityCore) (new AbilityCore$Builder("sniper_goggles","Sniper Goggles",AbilityCategory.STYLE,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DummyAbility::new)))).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi","sniper_goggles")).setIcon(ModResources.PERK_ICON).addDescriptionLine(var7).build("mineminenomi"));
		Component[] var8 = new Component[]{ModI18nAbilities.PERK_BLACK_LEG_DAMAGE_BONUS};
		BLACK_LEG_DAMAGE_PERK = (AbilityCore) (new AbilityCore$Builder("black_leg_damage","Black Leg Damage",AbilityCategory.STYLE,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DummyAbility::new)))).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi","black_leg_damage")).setIcon(ModResources.PERK_ICON).addDescriptionLine(var8).build("mineminenomi"));
		Component[] var9 = new Component[]{ModI18nAbilities.PERK_BLACK_LEG_ATTACK_SPEED_BONUS};
		BLACK_LEG_SPEED_PERK = (AbilityCore) (new AbilityCore$Builder("black_leg_attack_speed","Black Leg Attack Speed",AbilityCategory.STYLE,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DummyAbility::new)))).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi","black_leg_attack_speed")).setIcon(ModResources.PERK_ICON).addDescriptionLine(var9).build("mineminenomi"));
		Component[] var10 = new Component[]{ModI18nAbilities.PERK_BRAWLER_DAMAGE_BONUS};
		BRAWLER_DAMAGE_PERK = (AbilityCore) (new AbilityCore$Builder("brawler_damage","Brawler Damage",AbilityCategory.STYLE,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DummyAbility::new)))).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi","brawler_damage")).setIcon(ModResources.PERK_ICON).addDescriptionLine(var10).build("mineminenomi"));
	}
}