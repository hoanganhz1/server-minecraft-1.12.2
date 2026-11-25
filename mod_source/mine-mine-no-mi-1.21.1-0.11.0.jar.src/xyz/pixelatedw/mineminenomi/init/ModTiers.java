package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import xyz.pixelatedw.mineminenomi.api.WyTierBuilder;

public class ModTiers {
	// Fields
	private static final Supplier<Ingredient> EMPTY;
	private static final Supplier<Ingredient> IRON_INGOT;
	private static final Supplier<Ingredient> GOLD_INGOT;
	private static final Supplier<Ingredient> LAPIS_LAZULI;
	public static final Tier SCISSORS;
	public static final Tier KIKOKU;
	public static final Tier KIRIBACHI;
	public static final Tier YORU;
	public static final Tier MURAKUMOGIRI;
	public static final Tier HOOK;
	public static final Tier UMBRELLA;
	public static final Tier NONOSAMA_STAFF;
	public static final Tier NONOSAMA_TRIDENT;
	public static final Tier HAMMER_5T;
	public static final Tier ACES_KNIFE;
	public static final Tier MIHAWKS_KNIFE;
	public static final Tier SANDAI_KITETSU;
	public static final Tier WADO_ICHIMONJI;
	public static final Tier NIDAI_KITETSU;
	public static final Tier SHUSUI;
	public static final Tier ENMA;
	public static final Tier AME_NO_HABAKIRI;
	public static final Tier SOUL_SOLID;
	public static final Tier DURANDAL;
	public static final Tier DAISENSO;
	public static final Tier ACE;
	public static final Tier MOGURA;
	public static final Tier DALTONS_SPADE;
	public static final Tier SAMEKIRI_BOCHO;
	public static final Tier CAT_CLAWS;
	public static final Tier HASSAIKAI;
	public static final Tier GRYPHON;
	public static final Tier AXE_HAND;
	public static final Tier PIPE;
	public static final Tier TONFA;
	public static final Tier JITTE;
	public static final Tier KATANA;
	public static final Tier CUTLASS;
	public static final Tier BROADSWORD;
	public static final Tier BISENTO;
	public static final Tier DAGGER;
	public static final Tier AXE;
	public static final Tier SPEAR;
	public static final Tier CLEAVER;
	public static final Tier MACE;
	public static final Tier CHAKRAM;
	public static final Tier CLIMA_TACT;
	public static final Tier PERFECT_CLIMA_TACT;
	public static final Tier SORCERY_CLIMA_TACT;
	public static final Tier ICE_SABER;
	public static final Tier AMA_NO_MURAKUMO;
	public static final Tier NORO_NORO_BEAM_SWORD;
	public static final Tier DORU_DORU_ARTS_KEN;
	public static final Tier BLUE_SWORD;
	public static final Tier TABIRA_YUKI;
	public static final Tier WARABIDE_SWORD;
	public static final Tier BARRIERBILITY_BAT;
	public static final Tier GAMMA_KNIFE;

	static  {
		Supplier<Ingredient> var0 = () -> {
			return Ingredient.EMPTY;
		};
		EMPTY = var0;
		Supplier<Ingredient> var1 = () -> {
			ItemLike[] lv2_0 = new ItemLike[]{Items.IRON_INGOT};
			return Ingredient.of(lv2_0);
		};
		IRON_INGOT = var1;
		Supplier<Ingredient> var2 = () -> {
			ItemLike[] lv3_0 = new ItemLike[]{Items.GOLD_INGOT};
			return Ingredient.of(lv3_0);
		};
		GOLD_INGOT = var2;
		Supplier<Ingredient> var3 = () -> {
			ItemLike[] lv4_0 = new ItemLike[]{Items.LAPIS_LAZULI};
			return Ingredient.of(lv4_0);
		};
		LAPIS_LAZULI = var3;
		SCISSORS = WyTierBuilder.uncommon(500,IRON_INGOT);
		KIKOKU = WyTierBuilder.epic(1000,IRON_INGOT);
		KIRIBACHI = WyTierBuilder.rare(400,IRON_INGOT);
		YORU = WyTierBuilder.legendary(2000,IRON_INGOT);
		MURAKUMOGIRI = WyTierBuilder.legendary(2500,IRON_INGOT);
		HOOK = WyTierBuilder.uncommon(250,IRON_INGOT);
		Supplier<Ingredient> var4 = () -> {
			return Ingredient.of(ItemTags.WOOL);
		};
		UMBRELLA = WyTierBuilder.uncommon(200,var4);
		NONOSAMA_STAFF = WyTierBuilder.rare(400,GOLD_INGOT);
		NONOSAMA_TRIDENT = WyTierBuilder.rare(400,GOLD_INGOT);
		HAMMER_5T = WyTierBuilder.trash(250,EMPTY);
		ACES_KNIFE = WyTierBuilder.uncommon(200,IRON_INGOT);
		MIHAWKS_KNIFE = WyTierBuilder.uncommon(200,IRON_INGOT);
		SANDAI_KITETSU = WyTierBuilder.epic(1200,IRON_INGOT);
		WADO_ICHIMONJI = WyTierBuilder.epic(1200,IRON_INGOT);
		NIDAI_KITETSU = WyTierBuilder.epic(1200,IRON_INGOT);
		SHUSUI = WyTierBuilder.epic(1500,IRON_INGOT);
		ENMA = WyTierBuilder.epic(1500,IRON_INGOT);
		AME_NO_HABAKIRI = WyTierBuilder.epic(1500,IRON_INGOT);
		SOUL_SOLID = WyTierBuilder.rare(500,IRON_INGOT);
		DURANDAL = WyTierBuilder.rare(400,IRON_INGOT);
		DAISENSO = WyTierBuilder.rare(600,IRON_INGOT);
		ACE = WyTierBuilder.legendary(2500,IRON_INGOT);
		MOGURA = WyTierBuilder.epic(1700,IRON_INGOT);
		DALTONS_SPADE = WyTierBuilder.rare(800,IRON_INGOT);
		SAMEKIRI_BOCHO = WyTierBuilder.rare(900,IRON_INGOT);
		CAT_CLAWS = WyTierBuilder.uncommon(750,IRON_INGOT);
		HASSAIKAI = WyTierBuilder.epic(3000,IRON_INGOT);
		GRYPHON = WyTierBuilder.epic(2200,IRON_INGOT);
		AXE_HAND = WyTierBuilder.uncommon(500,IRON_INGOT);
		PIPE = WyTierBuilder.uncommon(200,IRON_INGOT);
		TONFA = WyTierBuilder.uncommon(500,IRON_INGOT);
		Supplier<Ingredient> var5 = () -> {
			ItemLike[] lv6_0 = new ItemLike[]{((ItemLike)(ModItems.KAIROSEKI.get()))};
			return Ingredient.of(lv6_0);
		};
		JITTE = WyTierBuilder.uncommon(400,var5);
		KATANA = WyTierBuilder.uncommon(250,IRON_INGOT);
		CUTLASS = WyTierBuilder.uncommon(300,IRON_INGOT);
		BROADSWORD = WyTierBuilder.uncommon(250,IRON_INGOT);
		BISENTO = WyTierBuilder.uncommon(250,IRON_INGOT);
		DAGGER = WyTierBuilder.uncommon(200,IRON_INGOT);
		AXE = WyTierBuilder.uncommon(300,IRON_INGOT);
		SPEAR = WyTierBuilder.uncommon(200,IRON_INGOT);
		CLEAVER = WyTierBuilder.uncommon(250,IRON_INGOT);
		MACE = WyTierBuilder.uncommon(600,IRON_INGOT);
		CHAKRAM = WyTierBuilder.uncommon(300,IRON_INGOT);
		CLIMA_TACT = WyTierBuilder.uncommon(300,LAPIS_LAZULI);
		PERFECT_CLIMA_TACT = WyTierBuilder.uncommon(500,LAPIS_LAZULI);
		Supplier<Ingredient> var6 = () -> {
			ItemLike[] lv7_0 = new ItemLike[]{Items.LAPIS_LAZULI,Items.GOLD_INGOT};
			return Ingredient.of(lv7_0);
		};
		SORCERY_CLIMA_TACT = WyTierBuilder.rare(800,var6);
		ICE_SABER = WyTierBuilder.rare(2000,EMPTY);
		AMA_NO_MURAKUMO = WyTierBuilder.rare(2000,EMPTY);
		NORO_NORO_BEAM_SWORD = WyTierBuilder.rare(2000,EMPTY);
		DORU_DORU_ARTS_KEN = WyTierBuilder.rare(2000,EMPTY);
		BLUE_SWORD = WyTierBuilder.rare(1000,EMPTY);
		TABIRA_YUKI = WyTierBuilder.rare(2000,EMPTY);
		WARABIDE_SWORD = WyTierBuilder.rare(2000,EMPTY);
		BARRIERBILITY_BAT = WyTierBuilder.rare(2000,EMPTY);
		GAMMA_KNIFE = WyTierBuilder.rare(2000,EMPTY);
	}
}