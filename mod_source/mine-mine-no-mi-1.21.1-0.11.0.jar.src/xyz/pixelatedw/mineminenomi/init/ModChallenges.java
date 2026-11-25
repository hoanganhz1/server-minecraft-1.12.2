package xyz.pixelatedw.mineminenomi.init;

import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry;
import xyz.pixelatedw.mineminenomi.challenges.arlongpirates.ArlongChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arlongpirates.ArlongHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arlongpirates.ChewChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arlongpirates.ChewHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arlongpirates.KuroobiChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arlongpirates.KuroobiHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr0Challenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr0HardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr1Challenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr1HardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr3Challenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr3HardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr5MissValentineChallenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr5MissValentineHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.blackcatpirates.JangoChallenge;
import xyz.pixelatedw.mineminenomi.challenges.blackcatpirates.JangoHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.blackcatpirates.KuroChallenge;
import xyz.pixelatedw.mineminenomi.challenges.blackcatpirates.KuroHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.blackcatpirates.NyanbanBrothersChallenge;
import xyz.pixelatedw.mineminenomi.challenges.blackcatpirates.NyanbanBrothersHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.buggypirates.AlvidaChallenge;
import xyz.pixelatedw.mineminenomi.challenges.buggypirates.AlvidaHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.buggypirates.BuggyChallenge;
import xyz.pixelatedw.mineminenomi.challenges.buggypirates.BuggyHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.buggypirates.CabajiChallenge;
import xyz.pixelatedw.mineminenomi.challenges.buggypirates.CabajiHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.kriegpirates.DonKriegChallenge;
import xyz.pixelatedw.mineminenomi.challenges.kriegpirates.DonKriegHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.kriegpirates.GinChallenge;
import xyz.pixelatedw.mineminenomi.challenges.kriegpirates.GinHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.kriegpirates.PearlChallenge;
import xyz.pixelatedw.mineminenomi.challenges.kriegpirates.PearlHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.marines.MorganChallenge;
import xyz.pixelatedw.mineminenomi.challenges.marines.MorganHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.ungrouped.HigumaChallenge;
import xyz.pixelatedw.mineminenomi.challenges.ungrouped.HigumaHardChallenge;

public class ModChallenges {
	// Fields
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<HigumaChallenge>> HIGUMA = ModRegistry.registerChallenge("higuma",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (HigumaChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<HigumaHardChallenge>> HIGUMA_HARD = ModRegistry.registerChallenge("higuma_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (HigumaHardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<MorganChallenge>> MORGAN = ModRegistry.registerChallenge("morgan",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (MorganChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<MorganHardChallenge>> MORGAN_HARD = ModRegistry.registerChallenge("morgan_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (MorganHardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<BuggyChallenge>> BUGGY = ModRegistry.registerChallenge("buggy",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (BuggyChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<BuggyHardChallenge>> BUGGY_HARD = ModRegistry.registerChallenge("buggy_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (BuggyHardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<CabajiChallenge>> CABAJI = ModRegistry.registerChallenge("cabaji",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (CabajiChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<CabajiHardChallenge>> CABAJI_HARD = ModRegistry.registerChallenge("cabaji_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (CabajiHardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<AlvidaChallenge>> ALVIDA = ModRegistry.registerChallenge("alvida",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (AlvidaChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<AlvidaHardChallenge>> ALVIDA_HARD = ModRegistry.registerChallenge("alvida_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (AlvidaHardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<KuroChallenge>> KURO = ModRegistry.registerChallenge("kuro",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (KuroChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<KuroHardChallenge>> KURO_HARD = ModRegistry.registerChallenge("kuro_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (KuroHardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<JangoChallenge>> JANGO = ModRegistry.registerChallenge("jango",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (JangoChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<JangoHardChallenge>> JANGO_HARD = ModRegistry.registerChallenge("jango_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (JangoHardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<NyanbanBrothersChallenge>> NYANBAN_BROTHERS = ModRegistry.registerChallenge("nyanban_brothers",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (NyanbanBrothersChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<NyanbanBrothersHardChallenge>> NYANBAN_BROTHERS_HARD = ModRegistry.registerChallenge("nyanban_brothers_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (NyanbanBrothersHardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<DonKriegChallenge>> DON_KRIEG = ModRegistry.registerChallenge("don_krieg",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (DonKriegChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<DonKriegHardChallenge>> DON_KRIEG_HARD = ModRegistry.registerChallenge("don_krieg_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (DonKriegHardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<GinChallenge>> GIN = ModRegistry.registerChallenge("gin",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (GinChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<GinHardChallenge>> GIN_HARD = ModRegistry.registerChallenge("gin_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (GinHardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<PearlChallenge>> PEARL = ModRegistry.registerChallenge("pearl",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (PearlChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<PearlHardChallenge>> PEARL_HARD = ModRegistry.registerChallenge("pearl_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (PearlHardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<ArlongChallenge>> ARLONG = ModRegistry.registerChallenge("arlong",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (ArlongChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<ArlongHardChallenge>> ARLONG_HARD = ModRegistry.registerChallenge("arlong_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (ArlongHardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<KuroobiChallenge>> KUROOBI = ModRegistry.registerChallenge("kuroobi",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (KuroobiChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<KuroobiHardChallenge>> KUROOBI_HARD = ModRegistry.registerChallenge("kuroobi_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (KuroobiHardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<ChewChallenge>> CHEW = ModRegistry.registerChallenge("chew",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (ChewChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<ChewHardChallenge>> CHEW_HARD = ModRegistry.registerChallenge("chew_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (ChewHardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<Mr0Challenge>> MR_0 = ModRegistry.registerChallenge("mr_0",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (Mr0Challenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<Mr0HardChallenge>> MR_0_HARD = ModRegistry.registerChallenge("mr_0_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (Mr0HardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<Mr1Challenge>> MR_1 = ModRegistry.registerChallenge("mr_1",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (Mr1Challenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<Mr1HardChallenge>> MR_1_HARD = ModRegistry.registerChallenge("mr_1_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (Mr1HardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<Mr3Challenge>> MR_3 = ModRegistry.registerChallenge("mr_3",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (Mr3Challenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<Mr3HardChallenge>> MR_3_HARD = ModRegistry.registerChallenge("mr_3_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (Mr3HardChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<Mr5MissValentineChallenge>> MR_5_AND_MISS_VALENTINE = ModRegistry.registerChallenge("mr_5_and_miss_valentine",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (Mr5MissValentineChallenge::create))))));
	public static final DeferredHolder<ChallengeCore<?>, ChallengeCore<Mr5MissValentineHardChallenge>> MR_5_AND_MISS_VALENTINE_HARD = ModRegistry.registerChallenge("mr_5_and_miss_valentine_hard",(ChallengeRegsitryEntry)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry)((ChallengeRegsitryEntry) ((ChallengeRegsitryEntry<ChallengeCore>) (Mr5MissValentineHardChallenge::create))))));

	public static void init() {
	}
}