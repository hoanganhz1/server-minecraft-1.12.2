package xyz.pixelatedw.mineminenomi.quests.weaponmastery;

import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.Quest$ICompleteQuestEvent;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId$Builder;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId$IFactory;
import xyz.pixelatedw.mineminenomi.api.quests.components.QuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.components.SwingTriggerQuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.components.SwingTriggerQuestComponent$IOnSwingEvent;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.quests.QuestCompletionEvents;
import xyz.pixelatedw.mineminenomi.quests.objectives.HitEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.HitEntityObjective$ICheckHit;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective$ICheckKill;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedHitChecks;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;

public class WeaponDisciplineQuest extends Quest {
	// Fields
	public static final DeferredHolder<QuestId<?>, QuestId<WeaponDisciplineQuest>> INSTANCE;
	private static final int COUNT_1 = 15;
	private static final int COUNT_2 = 5;
	private static final Component TITLE_1;
	private static final Component TITLE_2;
	private final HitEntityObjective objective1;
	private final KillEntityObjective objective2;

	public WeaponDisciplineQuest(QuestId<WeaponDisciplineQuest> var1) {
		super((QuestId)(var1));
		this.objective1 = new HitEntityObjective((Quest)(this),(Component)(TITLE_1),15,(HitEntityObjective$ICheckHit)(SharedHitChecks.HAS_WEAPON));
		this.objective2 = ((KillEntityObjective)(new KillEntityObjective((Quest)(this),TITLE_2,5).addRequirement((Objective)(this.objective1))));
		this.objective1.addComponent(new SwingTriggerQuestComponent((Objective)(this.objective1)).addSwingEvent((SwingTriggerQuestComponent$IOnSwingEvent)(((xyz.pixelatedw.mineminenomi.api.quests.components.SwingTriggerQuestComponent$IOnSwingEvent)(this::handleSwing)))));
		this.objective2.addComponent(new SwingTriggerQuestComponent((Objective)(this.objective2)).addSwingEvent((SwingTriggerQuestComponent$IOnSwingEvent)(((xyz.pixelatedw.mineminenomi.api.quests.components.SwingTriggerQuestComponent$IOnSwingEvent)(this::handleSwing)))));
		this.objective2.addCheck(SharedKillChecks.HAS_WEAPON);
		Objective[] var2 = new Objective[]{this.objective1,this.objective2};
		super.addObjectives(var2);
		super.addCompleteEvent(QuestCompletionEvents.giveTrainingPoints(TrainingPointType.WEAPON_MASTERY,2));
	}
	private void handleSwing(LivingEntity var1, Objective var2) {
		if (var1 instanceof Player){
			Player var3 = ((Player)(var1));
			if ((WyHelper.rayTraceBlocksAndEntities((Entity)(var3),var3.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).getValue(),0F).getType()) != (HitResult.Type.ENTITY)){
				var2.setProgress(var3,0F,false);
			}
			return;
		}else{
			return;
		}
	}
	static  {
		Supplier var0 = (Supplier) ((Supplier<QuestId>) (() -> {
			Holder[] lv1_0 = new Holder[]{ProperFormQuest.INSTANCE};
			return (QuestId) (((xyz.pixelatedw.mineminenomi.api.quests.QuestId$Builder)(new QuestId$Builder((QuestId$IFactory)(((xyz.pixelatedw.mineminenomi.api.quests.QuestId$IFactory)(WeaponDisciplineQuest::new)))).addRequirements(lv1_0).setRepeatCooldown((double)(WyHelper.hoursToTicks(4F))))).build());
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerQuest("weapon_discipline","Weapon Discipline",var0));
		TITLE_1 = ModRegistry.registerObjectiveTitle(INSTANCE.getId(),0,"Land %d weapon hits without missing",new Object[]{Integer.valueOf(15)});
		TITLE_2 = ModRegistry.registerObjectiveTitle(INSTANCE.getId(),1,"Defeat %d enemies using weapon attacks without missing",new Object[]{Integer.valueOf(5)});
	}
}