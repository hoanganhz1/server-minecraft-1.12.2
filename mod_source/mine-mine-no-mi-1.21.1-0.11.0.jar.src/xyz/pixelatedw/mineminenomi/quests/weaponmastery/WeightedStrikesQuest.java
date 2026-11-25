package xyz.pixelatedw.mineminenomi.quests.weaponmastery;

import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.Quest$ICompleteQuestEvent;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId$Builder;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId$IFactory;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.quests.QuestCompletionEvents;
import xyz.pixelatedw.mineminenomi.quests.objectives.HitEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.HitEntityObjective$ICheckHit;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective$ICheckKill;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedHitChecks;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;

public class WeightedStrikesQuest extends Quest {
	// Fields
	public static final DeferredHolder<QuestId<?>, QuestId<WeightedStrikesQuest>> INSTANCE;
	private static final int COUNT_1 = 10;
	private static final int COUNT_2 = 5;
	private static final Component TITLE_1;
	private static final Component TITLE_2;
	private final HitEntityObjective objective1;
	private final KillEntityObjective objective2;

	public WeightedStrikesQuest(QuestId<WeightedStrikesQuest> var1) {
		super((QuestId)(var1));
		this.objective1 = new HitEntityObjective((Quest)(this),(Component)(TITLE_1),10,(HitEntityObjective$ICheckHit)(SharedHitChecks.HAS_MEDIUM_WEAPON.or(SharedHitChecks.HAS_HEAVY_WEAPON)));
		this.objective2 = ((KillEntityObjective)(new KillEntityObjective((Quest)(this),TITLE_2,5).addRequirement((Objective)(this.objective1))));
		this.objective2.addCheck(SharedKillChecks.HAS_MEDIUM_WEAPON.or(SharedKillChecks.HAS_HEAVY_WEAPON));
		Objective[] var2 = new Objective[]{this.objective1,this.objective2};
		super.addObjectives(var2);
		super.addCompleteEvent(QuestCompletionEvents.giveTrainingPoints(TrainingPointType.WEAPON_MASTERY,3));
	}
	static  {
		Supplier var0 = (Supplier) ((Supplier<QuestId>) (() -> {
			Holder[] lv1_0 = new Holder[]{WeaponDisciplineQuest.INSTANCE};
			return (QuestId) (((xyz.pixelatedw.mineminenomi.api.quests.QuestId$Builder)(new QuestId$Builder((QuestId$IFactory)(((xyz.pixelatedw.mineminenomi.api.quests.QuestId$IFactory)(WeightedStrikesQuest::new)))).addRequirements(lv1_0).setRepeatCooldown((double)(WyHelper.hoursToTicks(6F))))).build());
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerQuest("weighted_strikes","Weighted Strikes",var0));
		TITLE_1 = ModRegistry.registerObjectiveTitle(INSTANCE.getId(),0,"Land %d hits using medium or heavy weapons",new Object[]{Integer.valueOf(10)});
		TITLE_2 = ModRegistry.registerObjectiveTitle(INSTANCE.getId(),1,"Defeat %d enemies using medium or heavy weapons",new Object[]{Integer.valueOf(5)});
	}
}