package xyz.pixelatedw.mineminenomi.quests.martialarts;

import java.util.function.Supplier;
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

public class CloseQuartersDrillQuest extends Quest {
	// Fields
	public static final DeferredHolder<QuestId<?>, QuestId<CloseQuartersDrillQuest>> INSTANCE;
	private static final int COUNT_1 = 20;
	private static final Component TITLE_1;
	private final HitEntityObjective objective1;

	public CloseQuartersDrillQuest(QuestId<CloseQuartersDrillQuest> var1) {
		super((QuestId)(var1));
		this.objective1 = new HitEntityObjective((Quest)(this),(Component)(TITLE_1),20,(HitEntityObjective$ICheckHit)(HitEntityObjective.MELEE_CHECK));
		Objective[] var2 = new Objective[]{this.objective1};
		super.addObjectives(var2);
		super.addCompleteEvent(QuestCompletionEvents.giveTrainingPoints(TrainingPointType.MARTIAL_ARTS,1));
	}
	static  {
		Supplier var0 = (Supplier) ((Supplier<QuestId>) (() -> {
			return (QuestId) (((xyz.pixelatedw.mineminenomi.api.quests.QuestId$Builder)(new QuestId$Builder((QuestId$IFactory)(((xyz.pixelatedw.mineminenomi.api.quests.QuestId$IFactory)(CloseQuartersDrillQuest::new)))).setRepeatCooldown((double)(WyHelper.hoursToTicks(1F))))).build());
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerQuest("close_quarters_drill","Close Quarters Drill",var0));
		TITLE_1 = ModRegistry.registerObjectiveTitle(INSTANCE.getId(),0,"Land %d unarmed hits",new Object[]{Integer.valueOf(20)});
	}
}