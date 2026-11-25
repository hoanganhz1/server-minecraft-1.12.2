package xyz.pixelatedw.mineminenomi.init.i18n;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class ModI18nChallenges {
	// Fields
	public static final Component MESSAGE_INSCRIPTION = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.inscription","You've noted the characters inscribed on the Poneglyph on a piece of paper."));
	public static final Component MESSAGE_INSCRIPTION_NO_PAPER = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.inscription_no_paper","You need a piece of paper in order to note the characters."));
	public static final Component MESSAGE_INSCRIPTION_ALREADY_COPIED = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.inscription_already_copied","You've already copied this inscription."));
	public static final String MESSAGE_UNLOCKED;
	public static final String MESSAGE_ALREADY_UNLOCKED;
	public static final Component MESSAGE_ARENA_IN_USE;
	public static final Component MESSAGE_CANNOT_USE_ITEM;
	public static final String MESSAGE_NOT_UNLOCKED;
	public static final Component MESSAGE_START_TITLE;
	public static final Component MESSAGE_START_SUBTITLE;
	public static final Component MESSAGE_START_FIGHT;
	public static final String MESSAGE_END_COUNTDOWN;
	public static final Component MESSAGE_COMPLETION_REPORT;
	public static final Component MESSAGE_TRAINING_INFO;
	public static final String MESSAGE_INVITATION;
	public static final String MESSAGE_GROUP_JOIN;
	public static final Component MESSAGE_GROUP_DISBAND;
	public static final Component MESSAGE_NOT_FINISHED;
	public static final String MESSAGE_BONUS_INCREASED;
	public static final String MESSAGE_BONUS_MAXED;
	public static final Component STARTING_CHALLENGE;
	public static final Component RESTRICTION_NO_FOODS;
	public static final Component RESTRICTION_NO_POTIONS;
	public static final Component RESTRICTION_NO_ADVANCED_BUSO_HAKI;

	public static void init() {
	}
	static  {
		MESSAGE_UNLOCKED = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.unlocked","New Challenge unlocked: %s!");
		MESSAGE_ALREADY_UNLOCKED = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.already_unlocked","%s challenge is already unlocked!");
		MESSAGE_ARENA_IN_USE = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.arena_in_use","This arena is currently used by anothe player, please wait!"));
		MESSAGE_CANNOT_USE_ITEM = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.cannot_use_item","Cannot use this item at the moment."));
		MESSAGE_NOT_UNLOCKED = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.not_unlocked","%s challenge is not unlocked!");
		MESSAGE_START_TITLE = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.start_title","Starting Challenge"));
		MESSAGE_START_SUBTITLE = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.start_subtitle","Please wait"));
		MESSAGE_START_FIGHT = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.start_fight","§a§oFIGHT!!!§r"));
		MESSAGE_END_COUNTDOWN = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.end_countdown","Challenge will end in %s seconds");
		MESSAGE_COMPLETION_REPORT = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.completion_report","Completion Time: "));
		MESSAGE_TRAINING_INFO = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.training_inf","During Training challenge restrictions are ignored however there will be no rewards, no records and the challenge won't be marked as completed."));
		MESSAGE_INVITATION = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.invitation","You've been invited by %s to %s.");
		MESSAGE_GROUP_JOIN = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.group_join","%s has joined your group.");
		MESSAGE_GROUP_DISBAND = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.group_disband","The group has disbanded."));
		MESSAGE_NOT_FINISHED = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.not_finished","You haven't finished this challenge yet!."));
		MESSAGE_BONUS_INCREASED = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.bonus_increased","Unlocked new knowledge about the %s challenge.");
		MESSAGE_BONUS_MAXED = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.bonus_maxed","%s has reached maximum bonus.");
		STARTING_CHALLENGE = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"message.starting_challenge","Starting Challenge"));
		RESTRICTION_NO_FOODS = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"restriction.no_foods","No Foods"));
		RESTRICTION_NO_POTIONS = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"restriction.no_potions","No Potions"));
		RESTRICTION_NO_ADVANCED_BUSO_HAKI = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"restriction.no_advanced_buso_haki","No Advanced Busoshoku Haki"));
	}
}