package xyz.pixelatedw.mineminenomi.quests.weaponmastery;

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
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedHitChecks;

public class ProperFormQuest extends Quest {
	// Fields
	public static final DeferredHolder<QuestId<?>, QuestId<ProperFormQuest>> INSTANCE;
	private static final int COUNT_1 = 20;
	private static final Component TITLE_1;
	private final HitEntityObjective objective1;

	public ProperFormQuest(QuestId<ProperFormQuest> var1) {
		super((QuestId)(var1));
		this.objective1 = new HitEntityObjective((Quest)(this),(Component)(TITLE_1),20,(HitEntityObjective$ICheckHit)(SharedHitChecks.HAS_WEAPON));
		Objective[] var2 = new Objective[]{this.objective1};
		super.addObjectives(var2);
		super.addCompleteEvent(QuestCompletionEvents.giveTrainingPoints(TrainingPointType.WEAPON_MASTERY,1));
	}
	static  {
		Supplier var0 = (Supplier) ((Supplier<QuestId>) (() -> {
			return (QuestId) (((xyz.pixelatedw.mineminenomi.api.quests.QuestId$Builder)(new QuestId$Builder((QuestId$IFactory)(((xyz.pixelatedw.mineminenomi.api.quests.QuestId$IFactory)(ProperFormQuest::new)))).setRepeatCooldown((double)(WyHelper.hoursToTicks(2F))))).build());
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerQuest("proper_form","Proper Form",var0));
		TITLE_1 = ModRegistry.registerObjectiveTitle(INSTANCE.getId(),0,"Land %d hits using weapon attacks",new Object[]{Integer.valueOf(20)});
	}
}