package xyz.pixelatedw.mineminenomi.init.i18n;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class ModI18nAdvancements {
	// Fields
	public static final ModI18nAdvancements$AdvancementPair CHALLENGES_ROOT = register("challenges.root","Challenges","Unlock a challenge");
	public static final ModI18nAdvancements$AdvancementPair ADVENTURE_MEAT = register("adventure.meat","MEAT!!!","Consume Sea King Meat");
	public static final ModI18nAdvancements$AdvancementPair ADVENTURE_CAT_BURGLAR = register("adventure.cat_burglar","Cat Burglar","Collect a total of 100.000 belly");
	public static final ModI18nAdvancements$AdvancementPair ADVENTURE_MY_TREASURE = register("adventure.my_treasure","My Treasure","Collect a total of 10.000.000 belly");
	public static final ModI18nAdvancements$AdvancementPair ADVENTURE_WHATS_KAIROSEKI = register("adventure.whats_kairoseki","What's Kairoseki?","Unlock one Haki ability (Haoshoku Haki not counting)");
	public static final ModI18nAdvancements$AdvancementPair ADVENTURE_HAKI_MASTER = register("adventure.haki_master","Haki Master","Unlock all Haki abilities (Haoshoku Haki not counting)");
	public static final ModI18nAdvancements$AdvancementPair ADVENTURE_WHY_IS_THE_RUM_GONE = register("adventure.why_is_the_rum_gone","Why is the Rum Gone ?","Reach the highest state of Drunkenness");
	public static final ModI18nAdvancements$AdvancementPair ADVENTURE_ALL_FOR_ME_GROG = register("adventure.all_for_me_grog","All for me Grog","Continue drinking Rum while in the highest state of Drunkenness");
	public static final ModI18nAdvancements$AdvancementPair ADVENTURE_PRICELESS_BLADE = register("adventure.priceless_blade","Priceless Blade","Obtain a Supreme Grade blade");
	public static final ModI18nAdvancements$AdvancementPair ADVENTURE_HALFWAY_THERE = register("adventure.halfway_there","Halfway There","Complete 50% from a Devil Fruit Encyclopedia");
	public static final ModI18nAdvancements$AdvancementPair ADVENTURE_ONE_FOR_THE_BOOKS = register("adventure.one_for_the_books","One for the Books","Complete 100% from a Devil Fruit Encyclopedia");
	public static final ModI18nAdvancements$AdvancementPair ADVENTURE_SUBTLE_TWEAKS = register("adventure.subtle_tweaks","Just some subtle tweaks","Slightly modify a ship's design");
	public static final ModI18nAdvancements$AdvancementPair ADVENTURE_DRUNKEN_FIST = register("adventure.drunken_fist","Master of the Drunken Fist","Kill any entity using only your fists while drunk");
	public static final ModI18nAdvancements$AdvancementPair ADVENTURE_PAST_MEMORIES = register("adventure.past_memories","Past Memories","Find a buried poneglyph and note down its inscriptions using a piece of paper");
	public static final ModI18nAdvancements$AdvancementPair DEVIL_FRUITS_ROOT = register("devil_fruits.root","Devil Fruits","Eat a Devil Fruit");
	public static final ModI18nAdvancements$AdvancementPair DEVIL_FRUITS_BLACK_HOLE = register("devil_fruits.black_hole","Black Hole","Eat a 2nd Devil Fruit as the Yami Yami no Mi user");
	public static final ModI18nAdvancements$AdvancementPair DEVIL_FRUITS_MOOTEOROLOGIST = register("devil_fruits.mooteorologist","Mooteorologist","Use Hiso Hiso no Mi's Animal Forewarning ability and talk with a Cow about the upcoming weather");
	public static final ModI18nAdvancements$AdvancementPair DEVIL_FRUITS_THE_DONUT = register("devil_fruits.the_donut","The Donut","Turn a Mera Mera no Mi user into a donut");
	public static final ModI18nAdvancements$AdvancementPair DEVIL_FRUITS_SECOND_CHANCE = register("devil_fruits.second_chance","Second Chance","Get a second chance at life due to Yomi Yomi no Mi's effect");

	public static void init() {
	}
	private static ModI18nAdvancements$AdvancementPair register(String var0, String var1, String var2) {
		Component var3 = title(var0,var1);
		Component var4 = description(var0,var2);
		return new ModI18nAdvancements$AdvancementPair(var3,var4);
	}
	private static Component title(String var0, String var1) {
		String var2 = var0;
		String var3 = var2 + ".title";
		return Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.ADVANCEMENT,var3,var1));
	}
	private static Component description(String var0, String var1) {
		String var2 = var0;
		String var3 = var2 + ".description";
		return Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.ADVANCEMENT,var3,var1));
	}
}