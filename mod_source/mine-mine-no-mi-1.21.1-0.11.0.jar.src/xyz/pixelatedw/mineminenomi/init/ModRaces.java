package xyz.pixelatedw.mineminenomi.init;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.ColaOverdriveAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.CoupDeBooAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.CoupDeVentAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.FreshFireAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.RadicalBeamAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.StrongRightAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.EleclawAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalLunaAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalMissileAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalShowerAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.SulongAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KachiageHaisokuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KarakusagawaraSeikenAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.SamehadaShoteiAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.TwoFishEngineAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.UchimizuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.YarinamiAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.CharacterCreatorSelectionInfo;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.ui.CharacterCreatorSelectionMap;

public class ModRaces {
	// Fields
	private static int idx;
	private static final int HUMAN_ORDER;
	private static final int FISHMAN_ORDER;
	private static final int FISHMAN_SAWSHARK_ORDER = 1;
	private static final int FISHMAN_RAY_ORDER = 2;
	private static final int FISHMAN_GARFISH_ORDER = 3;
	private static final int CYBORG_ORDER;
	private static final int MINK_ORDER;
	private static final int MINK_DOG_ORDER = 1;
	private static final int MINK_LION_ORDER = 2;
	private static final int MINK_BUNNY_ORDER = 3;
	private static final int SKYPIEAN_ORDER;
	public static final Supplier<Race> HUMAN;
	public static final Supplier<Race> FISHMAN_SAWSHARK;
	public static final Supplier<Race> FISHMAN_RAY;
	public static final Supplier<Race> FISHMAN_GARFISH;
	public static final Supplier<Race> FISHMAN;
	public static final Supplier<Race> CYBORG;
	public static final Supplier<Race> MINK_DOG;
	public static final Supplier<Race> MINK_LION;
	public static final Supplier<Race> MINK_BUNNY;
	public static final Supplier<Race> MINK;
	public static final Supplier<Race> SKYPIEAN;

	public static void init() {
	}
	static  {
		idx = 1;
		HUMAN_ORDER = idx++;
		FISHMAN_ORDER = idx++;
		CYBORG_ORDER = idx++;
		MINK_ORDER = idx++;
		SKYPIEAN_ORDER = idx++;
		Supplier<Race> var0 = () -> {
			CharacterCreatorSelectionInfo lv1_0 = new CharacterCreatorSelectionInfo(ModResources.HUMAN,HUMAN_ORDER);
			Supplier[] lv1_1 = new Supplier[]{SoruAbility.INSTANCE,TekkaiAbility.INSTANCE,GeppoAbility.INSTANCE,KamieAbility.INSTANCE,RankyakuAbility.INSTANCE};
			lv1_0.addTopAbilities(lv1_1);
			return new Race().setBookDetails(lv1_0);
		};
		HUMAN = ModRegistry.registerRace("human","Human",var0);
		Supplier<Race> var1 = () -> {
			CharacterCreatorSelectionInfo lv2_0 = new CharacterCreatorSelectionInfo(ModResources.FISHMAN,1);
			return Race.subRace().setBookDetails(lv2_0);
		};
		FISHMAN_SAWSHARK = ModRegistry.registerRace("fishman_sawshark","Fishman Saw-shark",var1);
		Supplier<Race> var2 = () -> {
			CharacterCreatorSelectionInfo lv3_0 = new CharacterCreatorSelectionInfo(ModResources.FISHMAN,2);
			return Race.subRace().setBookDetails(lv3_0);
		};
		FISHMAN_RAY = ModRegistry.registerRace("fishman_ray","Fishman Ray",var2);
		Supplier<Race> var3 = () -> {
			CharacterCreatorSelectionInfo lv4_0 = new CharacterCreatorSelectionInfo(ModResources.FISHMAN,3);
			return Race.subRace().setBookDetails(lv4_0);
		};
		FISHMAN_GARFISH = ModRegistry.registerRace("fishman_garfish","Fishman Garfish",var3);
		Supplier<Race> var4 = () -> {
			CharacterCreatorSelectionInfo lv7_0 = new CharacterCreatorSelectionInfo(ModResources.FISHMAN,FISHMAN_ORDER);
			Supplier[] lv7_1 = new Supplier[]{UchimizuAbility.INSTANCE,SamehadaShoteiAbility.INSTANCE,KachiageHaisokuAbility.INSTANCE,TwoFishEngineAbility.INSTANCE,YarinamiAbility.INSTANCE,KarakusagawaraSeikenAbility.INSTANCE};
			lv7_0.addTopAbilities(lv7_1);
			Supplier[] lv7_2 = new Supplier[2];
			Supplier lv7_3 = (Supplier) ((Supplier<AbilityCore>) (() -> {
				return CharacterCreatorSelectionMap.FISHMAN_SWIM_SPEED_PERK;
			}));
			lv7_2[0] = lv7_3;
			Supplier lv7_4 = (Supplier) ((Supplier<AbilityCore>) (() -> {
				return CharacterCreatorSelectionMap.FISHMAN_DAMAGE_PERK;
			}));
			lv7_2[1] = lv7_4;
			lv7_0.addBottomAbilities(lv7_2);
			Supplier[] lv7_5 = new Supplier[]{FISHMAN_SAWSHARK,FISHMAN_RAY,FISHMAN_GARFISH};
			return new Race().setBookDetails(lv7_0).setRenderFeatures().setSubRaces((List)(Lists.newArrayList(lv7_5)));
		};
		FISHMAN = ModRegistry.registerRace("fishman","Fishman",var4);
		Supplier<Race> var5 = () -> {
			CharacterCreatorSelectionInfo lv9_0 = new CharacterCreatorSelectionInfo(ModResources.CYBORG,CYBORG_ORDER);
			Supplier[] lv9_1 = new Supplier[]{StrongRightAbility.INSTANCE,RadicalBeamAbility.INSTANCE,CoupDeVentAbility.INSTANCE,FreshFireAbility.INSTANCE,CoupDeBooAbility.INSTANCE,ColaOverdriveAbility.INSTANCE};
			lv9_0.addTopAbilities(lv9_1);
			Supplier[] lv9_2 = new Supplier[1];
			Supplier lv9_3 = (Supplier) ((Supplier<AbilityCore>) (() -> {
				return CharacterCreatorSelectionMap.CYBORG_ARMOR_PERK;
			}));
			lv9_2[0] = lv9_3;
			lv9_0.addBottomAbilities(lv9_2);
			return new Race().setBookDetails(lv9_0);
		};
		CYBORG = ModRegistry.registerRace("cyborg","Cyborg",var5);
		Supplier<Race> var6 = () -> {
			CharacterCreatorSelectionInfo lv10_0 = new CharacterCreatorSelectionInfo(ModResources.MINK1,1);
			return Race.subRace().setBookDetails(lv10_0);
		};
		MINK_DOG = ModRegistry.registerRace("mink_dog","Mink Dog",var6);
		Supplier<Race> var7 = () -> {
			CharacterCreatorSelectionInfo lv11_0 = new CharacterCreatorSelectionInfo(ModResources.MINK2,2);
			return Race.subRace().setBookDetails(lv11_0);
		};
		MINK_LION = ModRegistry.registerRace("mink_lion","Mink Lion",var7);
		Supplier<Race> var8 = () -> {
			CharacterCreatorSelectionInfo lv12_0 = new CharacterCreatorSelectionInfo(ModResources.MINK3,3);
			return Race.subRace().setBookDetails(lv12_0);
		};
		MINK_BUNNY = ModRegistry.registerRace("mink_bunny","Mink Bunny",var8);
		Supplier<Race> var9 = () -> {
			CharacterCreatorSelectionInfo lv15_0 = new CharacterCreatorSelectionInfo(ModResources.MINK1,MINK_ORDER);
			Supplier[] lv15_1 = new Supplier[]{EleclawAbility.INSTANCE,ElectricalShowerAbility.INSTANCE,ElectricalLunaAbility.INSTANCE,ElectricalMissileAbility.INSTANCE,SulongAbility.INSTANCE};
			lv15_0.addTopAbilities(lv15_1);
			Supplier[] lv15_2 = new Supplier[2];
			Supplier lv15_3 = (Supplier) ((Supplier<AbilityCore>) (() -> {
				return CharacterCreatorSelectionMap.MINK_SPEED_PERK;
			}));
			lv15_2[0] = lv15_3;
			Supplier lv15_4 = (Supplier) ((Supplier<AbilityCore>) (() -> {
				return CharacterCreatorSelectionMap.MINK_JUMP_PERK;
			}));
			lv15_2[1] = lv15_4;
			lv15_0.addBottomAbilities(lv15_2);
			Supplier[] lv15_5 = new Supplier[]{MINK_DOG,MINK_LION,MINK_BUNNY};
			return new Race().setBookDetails(lv15_0).setRenderFeatures().setSubRaces((List)(Lists.newArrayList(lv15_5)));
		};
		MINK = ModRegistry.registerRace("mink","Mink",var9);
		Supplier<Race> var10 = () -> {
			CharacterCreatorSelectionInfo lv16_0 = new CharacterCreatorSelectionInfo(ModResources.SKYPIEAN,SKYPIEAN_ORDER);
			return new Race().setRenderFeatures();
		};
		SKYPIEAN = ModRegistry.registerRace("skypiean","Skypiean",var10);
	}
}