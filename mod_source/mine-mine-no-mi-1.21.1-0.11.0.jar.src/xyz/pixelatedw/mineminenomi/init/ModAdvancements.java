package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.advancements.CriterionTrigger;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.data.triggers.CompleteChallengeTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.CompleteQuestTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.ConsumeDevilFruitTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.DFEncyclopediaCompletionTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.FreedSlavesTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.JoinCrewTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.LeaveCrewTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.MooteorologistTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.ObtainBellyTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.ObtainBountyTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.ObtainDorikiTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.ObtainLoyaltyTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SelectFactionTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SelectRaceTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SelectStyleTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SetCrewCaptainTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SubtleTweaksTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.UnlockAbilityTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.UnlockChallengeTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.YomiReviveTrigger;

public class ModAdvancements {
	// Fields
	public static final Supplier<ObtainBellyTrigger> OBTAIN_BELLY = ModRegistry.registerTrigger("obtain_belly",(Supplier)(((Supplier<ObtainBellyTrigger>)(ObtainBellyTrigger::new))));
	public static final Supplier<UnlockAbilityTrigger> UNLOCK_ABILITY = ModRegistry.registerTrigger("unlock_ability",(Supplier)(((Supplier<UnlockAbilityTrigger>)(UnlockAbilityTrigger::new))));
	public static final Supplier<DFEncyclopediaCompletionTrigger> ENCYCLOPEDIA_COMPLETION = ModRegistry.registerTrigger("encyclopedia_completion",(Supplier)(((Supplier<DFEncyclopediaCompletionTrigger>)(DFEncyclopediaCompletionTrigger::new))));
	public static final Supplier<ConsumeDevilFruitTrigger> CONSUME_DEVIL_FRUIT = ModRegistry.registerTrigger("consume_devil_fruit",(Supplier)(((Supplier<ConsumeDevilFruitTrigger>)(ConsumeDevilFruitTrigger::new))));
	public static final Supplier<MooteorologistTrigger> MOOTEOROLOGIST = ModRegistry.registerTrigger("mooteorologist",(Supplier)(((Supplier<MooteorologistTrigger>)(MooteorologistTrigger::new))));
	public static final Supplier<SubtleTweaksTrigger> SUBTLE_TWEAKS = ModRegistry.registerTrigger("subtle_tweaks",(Supplier)(((Supplier<SubtleTweaksTrigger>)(SubtleTweaksTrigger::new))));
	public static final Supplier<YomiReviveTrigger> YOMI_REVIVE = ModRegistry.registerTrigger("yomi_revive",(Supplier)(((Supplier<YomiReviveTrigger>)(YomiReviveTrigger::new))));
	public static final Supplier<CompleteChallengeTrigger> COMPLETE_CHALLENGE = ModRegistry.registerTrigger("complete_challenge",(Supplier)(((Supplier<CompleteChallengeTrigger>)(CompleteChallengeTrigger::new))));
	public static final Supplier<UnlockChallengeTrigger> UNLOCK_CHALLENGE = ModRegistry.registerTrigger("unlock_challenge",(Supplier)(((Supplier<UnlockChallengeTrigger>)(UnlockChallengeTrigger::new))));
	public static final Supplier<ObtainDorikiTrigger> OBTAIN_DORIKI = ModRegistry.registerTrigger("obtain_doriki",(Supplier)(((Supplier<ObtainDorikiTrigger>)(ObtainDorikiTrigger::new))));
	public static final Supplier<ObtainBountyTrigger> OBTAIN_BOUNTY = ModRegistry.registerTrigger("obtain_bounty",(Supplier)(((Supplier<ObtainBountyTrigger>)(ObtainBountyTrigger::new))));
	public static final Supplier<ObtainLoyaltyTrigger> OBTAIN_LOYALTY = ModRegistry.registerTrigger("obtain_loyalty",(Supplier)(((Supplier<ObtainLoyaltyTrigger>)(ObtainLoyaltyTrigger::new))));
	public static final Supplier<SelectFactionTrigger> SELECT_FACTION = ModRegistry.registerTrigger("select_faction",(Supplier)(((Supplier<SelectFactionTrigger>)(SelectFactionTrigger::new))));
	public static final Supplier<SelectStyleTrigger> SELECT_STYLE = ModRegistry.registerTrigger("select_style",(Supplier)(((Supplier<SelectStyleTrigger>)(SelectStyleTrigger::new))));
	public static final Supplier<SelectRaceTrigger> SELECT_RACE = ModRegistry.registerTrigger("select_race",(Supplier)(((Supplier<SelectRaceTrigger>)(SelectRaceTrigger::new))));
	public static final Supplier<SetCrewCaptainTrigger> SET_CREW_CAPTAIN = ModRegistry.registerTrigger("set_crew_captain",(Supplier)(((Supplier<SetCrewCaptainTrigger>)(SetCrewCaptainTrigger::new))));
	public static final Supplier<LeaveCrewTrigger> LEAVE_CREW = ModRegistry.registerTrigger("leave_crew",(Supplier)(((Supplier<LeaveCrewTrigger>)(LeaveCrewTrigger::new))));
	public static final Supplier<JoinCrewTrigger> JOIN_CREW = ModRegistry.registerTrigger("join_crew",(Supplier)(((Supplier<JoinCrewTrigger>)(JoinCrewTrigger::new))));
	public static final Supplier<CompleteQuestTrigger> COMPLETE_QUEST = ModRegistry.registerTrigger("complete_quest",(Supplier)(((Supplier<CompleteQuestTrigger>)(CompleteQuestTrigger::new))));
	public static final Supplier<FreedSlavesTrigger> FREED_SLAVES = ModRegistry.registerTrigger("freed_slaves",(Supplier)(((Supplier<FreedSlavesTrigger>)(FreedSlavesTrigger::new))));

	public static void init() {
	}
}