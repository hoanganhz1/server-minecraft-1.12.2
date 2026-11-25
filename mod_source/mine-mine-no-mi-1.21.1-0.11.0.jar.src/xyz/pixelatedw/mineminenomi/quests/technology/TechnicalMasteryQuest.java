package xyz.pixelatedw.mineminenomi.quests.technology;

import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.Quest$ICompleteQuestEvent;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId$Builder;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId$IFactory;
import xyz.pixelatedw.mineminenomi.api.quests.components.DamageTakenQuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.components.DamageTakenQuestComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.quests.components.QuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.quests.QuestCompletionEvents;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective$ICheckKill;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;

public class TechnicalMasteryQuest extends Quest {
	// Fields
	public static final DeferredHolder<QuestId<?>, QuestId<TechnicalMasteryQuest>> INSTANCE;
	private static final int COUNT_1 = 10;
	private static final Component TITLE_1;
	private final KillEntityObjective objective1;

	public TechnicalMasteryQuest(QuestId<TechnicalMasteryQuest> var1) {
		super((QuestId)(var1));
		this.objective1 = new KillEntityObjective((Quest)(this),TITLE_1,10);
		this.objective1.addCheck(SharedKillChecks.USED_TECHNOLOGY);
		this.objective1.addComponent(new DamageTakenQuestComponent((Objective)(this.objective1)).addOnDamageEvent((DamageTakenQuestComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.quests.components.DamageTakenQuestComponent$IGetHitEvent)(this::onDamage)))));
		Objective[] var2 = new Objective[]{this.objective1};
		super.addObjectives(var2);
		super.addCompleteEvent(QuestCompletionEvents.giveTrainingPoints(TrainingPointType.TECHNOLOGY,8));
	}
	private void onDamage(Player var1, DamageSource var2, float var3, Objective var4) {
		var4.setProgress(var1,0F,false);
	}
	static  {
		Supplier var0 = (Supplier) ((Supplier<QuestId>) (() -> {
			Holder[] lv1_0 = new Holder[]{SpecialistControlQuest.INSTANCE};
			return (QuestId) (((xyz.pixelatedw.mineminenomi.api.quests.QuestId$Builder)(new QuestId$Builder((QuestId$IFactory)(((xyz.pixelatedw.mineminenomi.api.quests.QuestId$IFactory)(TechnicalMasteryQuest::new)))).addRequirements(lv1_0))).build());
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerQuest("technical_mastery","Technical Mastery",var0));
		TITLE_1 = ModRegistry.registerObjectiveTitle(INSTANCE.getId(),0,"Defeat %d enemies using technology-based attacks without taking damage",new Object[]{Integer.valueOf(10)});
	}
}