package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.GustSwordAbility;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.WeatherEggAbility;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.CycloneTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.FogTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.HeatEggTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.MirageTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.ThunderLanceTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.ThunderstormTempo;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.AntiMannerKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.BienCuitGrillShotAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.ConcasseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.DiableJambeAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.SkywalkAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.GenkotsuMeteorAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.KingPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SpinningBrawlAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility;
import xyz.pixelatedw.mineminenomi.abilities.doctor.AntidoteShotAbility;
import xyz.pixelatedw.mineminenomi.abilities.doctor.DopingAbility;
import xyz.pixelatedw.mineminenomi.abilities.doctor.FailedExperimentAbility;
import xyz.pixelatedw.mineminenomi.abilities.doctor.FirstAidAbility;
import xyz.pixelatedw.mineminenomi.abilities.doctor.MedicBagExplosionAbility;
import xyz.pixelatedw.mineminenomi.abilities.doctor.VirusZoneAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.SanbyakurokujuPoundHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.HiNoToriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.HissatsuAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KemuriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.NemuriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.SakuretsuSabotenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TokuyoAburaBoshiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.CharacterCreatorSelectionInfo;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.ui.CharacterCreatorSelectionMap;

public class ModFightingStyles {
	// Fields
	private static int idx;
	private static final int SWORDSMAN_ORDER;
	private static final int SNIPER_ORDER;
	private static final int DOCTOR_ORDER;
	private static final int ART_OF_WEATHER_ORDER;
	private static final int BRAWLER_ORDER;
	private static final int BLACK_LEG_ORDER;
	public static final Supplier<FightingStyle> SWORDSMAN;
	public static final Supplier<FightingStyle> SNIPER;
	public static final Supplier<FightingStyle> DOCTOR;
	public static final Supplier<FightingStyle> ART_OF_WEATHER;
	public static final Supplier<FightingStyle> BRAWLER;
	public static final Supplier<FightingStyle> BLACK_LEG;

	public static void init() {
	}
	static  {
		idx = 1;
		SWORDSMAN_ORDER = idx++;
		SNIPER_ORDER = idx++;
		DOCTOR_ORDER = idx++;
		ART_OF_WEATHER_ORDER = idx++;
		BRAWLER_ORDER = idx++;
		BLACK_LEG_ORDER = idx++;
		Supplier<FightingStyle> var0 = () -> {
			CharacterCreatorSelectionInfo lv2_0 = new CharacterCreatorSelectionInfo(ModResources.SWORDSMAN,SWORDSMAN_ORDER);
			Supplier[] lv2_1 = new Supplier[]{ShiShishiSonsonAbility.INSTANCE,YakkodoriAbility.INSTANCE,SanbyakurokujuPoundHoAbility.INSTANCE,OTatsumakiAbility.INSTANCE,HiryuKaenAbility.INSTANCE};
			lv2_0.addTopAbilities(lv2_1);
			Supplier[] lv2_2 = new Supplier[1];
			Supplier lv2_3 = (Supplier) ((Supplier<AbilityCore>) (() -> {
				return CharacterCreatorSelectionMap.SWORDSMAN_DAMAGE_PERK;
			}));
			lv2_2[0] = lv2_3;
			lv2_0.addBottomAbilities(lv2_2);
			return new FightingStyle().setBookDetails(lv2_0).setTrainingAffinity(TrainingPointType.WEAPON_MASTERY);
		};
		SWORDSMAN = ModRegistry.registerStyle("swordsman","Swordsman",var0);
		Supplier<FightingStyle> var1 = () -> {
			CharacterCreatorSelectionInfo lv5_0 = new CharacterCreatorSelectionInfo(ModResources.SNIPER,SNIPER_ORDER);
			Supplier[] lv5_1 = new Supplier[]{HiNoToriBoshiAbility.INSTANCE,KemuriBoshiAbility.INSTANCE,NemuriBoshiAbility.INSTANCE,TokuyoAburaBoshiAbility.INSTANCE,SakuretsuSabotenBoshiAbility.INSTANCE,HissatsuAbility.INSTANCE};
			lv5_0.addTopAbilities(lv5_1);
			Supplier[] lv5_2 = new Supplier[2];
			Supplier lv5_3 = (Supplier) ((Supplier<AbilityCore>) (() -> {
				return CharacterCreatorSelectionMap.SNIPER_ACCURACY_PERK;
			}));
			lv5_2[0] = lv5_3;
			Supplier lv5_4 = (Supplier) ((Supplier<AbilityCore>) (() -> {
				return CharacterCreatorSelectionMap.SNIPER_GOGGLES_PERK;
			}));
			lv5_2[1] = lv5_4;
			lv5_0.addBottomAbilities(lv5_2);
			return new FightingStyle().setBookDetails(lv5_0).setTrainingAffinity(TrainingPointType.ACCURACY);
		};
		SNIPER = ModRegistry.registerStyle("sniper","Sniper",var1);
		Supplier<FightingStyle> var2 = () -> {
			CharacterCreatorSelectionInfo lv6_0 = new CharacterCreatorSelectionInfo(ModResources.DOCTOR,DOCTOR_ORDER);
			Supplier[] lv6_1 = new Supplier[]{FirstAidAbility.INSTANCE,FailedExperimentAbility.INSTANCE,DopingAbility.INSTANCE,VirusZoneAbility.INSTANCE,AntidoteShotAbility.INSTANCE,MedicBagExplosionAbility.INSTANCE};
			lv6_0.addTopAbilities(lv6_1);
			return new FightingStyle().setBookDetails(lv6_0).setTrainingAffinity(TrainingPointType.TECHNOLOGY);
		};
		DOCTOR = ModRegistry.registerStyle("doctor","Doctor",var2);
		Supplier<FightingStyle> var3 = () -> {
			CharacterCreatorSelectionInfo lv7_0 = new CharacterCreatorSelectionInfo(ModResources.ART_OF_WEATHER,ART_OF_WEATHER_ORDER);
			Supplier[] lv7_1 = new Supplier[]{WeatherEggAbility.INSTANCE,GustSwordAbility.INSTANCE};
			lv7_0.addTopAbilities(lv7_1);
			Supplier[] lv7_2 = new Supplier[]{ThunderstormTempo.INSTANCE,CycloneTempo.INSTANCE,MirageTempo.INSTANCE,ThunderLanceTempo.INSTANCE,HeatEggTempo.INSTANCE,FogTempo.INSTANCE};
			lv7_0.addBottomAbilities(lv7_2);
			return new FightingStyle().setBookDetails(lv7_0).setTrainingAffinity(TrainingPointType.TECHNOLOGY);
		};
		ART_OF_WEATHER = ModRegistry.registerStyle("art_of_weather","Art of Weather",var3);
		Supplier<FightingStyle> var4 = () -> {
			CharacterCreatorSelectionInfo lv9_0 = new CharacterCreatorSelectionInfo(ModResources.BRAWLER,BRAWLER_ORDER);
			Supplier[] lv9_1 = new Supplier[]{SuplexAbility.INSTANCE,GenkotsuMeteorAbility.INSTANCE,SpinningBrawlAbility.INSTANCE,HakaiHoAbility.INSTANCE,JishinHoAbility.INSTANCE,KingPunchAbility.INSTANCE};
			lv9_0.addTopAbilities(lv9_1);
			Supplier[] lv9_2 = new Supplier[1];
			Supplier lv9_3 = (Supplier) ((Supplier<AbilityCore>) (() -> {
				return CharacterCreatorSelectionMap.BRAWLER_DAMAGE_PERK;
			}));
			lv9_2[0] = lv9_3;
			lv9_0.addBottomAbilities(lv9_2);
			return new FightingStyle().setBookDetails(lv9_0).setTrainingAffinity(TrainingPointType.MARTIAL_ARTS);
		};
		BRAWLER = ModRegistry.registerStyle("brawler","Brawler",var4);
		Supplier<FightingStyle> var5 = () -> {
			CharacterCreatorSelectionInfo lv12_0 = new CharacterCreatorSelectionInfo(ModResources.BLACK_LEG,BLACK_LEG_ORDER);
			Supplier[] lv12_1 = new Supplier[]{ConcasseAbility.INSTANCE,AntiMannerKickCourseAbility.INSTANCE,PartyTableKickCourseAbility.INSTANCE,SkywalkAbility.INSTANCE,DiableJambeAbility.INSTANCE,BienCuitGrillShotAbility.INSTANCE};
			lv12_0.addTopAbilities(lv12_1);
			Supplier[] lv12_2 = new Supplier[2];
			Supplier lv12_3 = (Supplier) ((Supplier<AbilityCore>) (() -> {
				return CharacterCreatorSelectionMap.BLACK_LEG_DAMAGE_PERK;
			}));
			lv12_2[0] = lv12_3;
			Supplier lv12_4 = (Supplier) ((Supplier<AbilityCore>) (() -> {
				return CharacterCreatorSelectionMap.BLACK_LEG_SPEED_PERK;
			}));
			lv12_2[1] = lv12_4;
			lv12_0.addBottomAbilities(lv12_2);
			return new FightingStyle().setBookDetails(lv12_0).setTrainingAffinity(TrainingPointType.MARTIAL_ARTS);
		};
		BLACK_LEG = ModRegistry.registerStyle("black_leg","Black Leg",var5);
	}
}