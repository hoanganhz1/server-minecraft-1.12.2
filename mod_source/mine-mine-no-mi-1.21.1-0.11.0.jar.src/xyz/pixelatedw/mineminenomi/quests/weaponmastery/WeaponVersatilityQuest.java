package xyz.pixelatedw.mineminenomi.quests.weaponmastery;

import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.Quest$ICompleteQuestEvent;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId$Builder;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId$IFactory;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.quests.QuestCompletionEvents;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective$ICheckKill;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;

public class WeaponVersatilityQuest extends Quest {
	// Fields
	public static final DeferredHolder<QuestId<?>, QuestId<WeaponVersatilityQuest>> INSTANCE;
	private static final int COUNT_1 = 5;
	private static final int COUNT_2 = 5;
	private static final int COUNT_3 = 5;
	private static final Component TITLE_1;
	private static final Component TITLE_2;
	private static final Component TITLE_3;
	private final KillEntityObjective objective1;
	private final KillEntityObjective objective2;
	private final KillEntityObjective objective3;

	public WeaponVersatilityQuest(QuestId<WeaponVersatilityQuest> var1) {
		super((QuestId)(var1));
		this.objective1 = new KillEntityObjective((Quest)(this),TITLE_1,5);
		this.objective2 = new KillEntityObjective((Quest)(this),TITLE_2,5);
		this.objective3 = new KillEntityObjective((Quest)(this),TITLE_3,5);
		this.objective1.addCheck(SharedKillChecks.HAS_SWORD);
		this.objective2.addCheck(SharedKillChecks.HAS_POLEARM);
		this.objective3.addCheck(KillEntityObjective.PROJECTILE_CHECK);
		Objective[] var2 = new Objective[]{this.objective1,this.objective2,this.objective3};
		super.addObjectives(var2);
		super.addCompleteEvent(QuestCompletionEvents.giveTrainingPoints(TrainingPointType.WEAPON_MASTERY,8));
		super.addCompleteEvent(QuestCompletionEvents.giveTrainingPoints(TrainingPointType.ACCURACY,1));
	}
	static  {
		Supplier var0 = (Supplier) ((Supplier<QuestId>) (() -> {
			Holder[] lv1_0 = new Holder[]{WeaponDisciplineQuest.INSTANCE};
			return (QuestId) (((xyz.pixelatedw.mineminenomi.api.quests.QuestId$Builder)(new QuestId$Builder((QuestId$IFactory)(((xyz.pixelatedw.mineminenomi.api.quests.QuestId$IFactory)(WeaponVersatilityQuest::new)))).addRequirements(lv1_0))).build());
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerQuest("weapon_versatility","Weapon Versatility",var0));
		TITLE_1 = ModRegistry.registerObjectiveTitle(INSTANCE.getId(),0,"Defeat %d enemy using a sword",new Object[]{Integer.valueOf(5)});
		TITLE_2 = ModRegistry.registerObjectiveTitle(INSTANCE.getId(),1,"Defeat %d enemy using a polearm",new Object[]{Integer.valueOf(5)});
		TITLE_3 = ModRegistry.registerObjectiveTitle(INSTANCE.getId(),2,"Defeat %d enemy using a ranged weapon",new Object[]{Integer.valueOf(5)});
	}
}