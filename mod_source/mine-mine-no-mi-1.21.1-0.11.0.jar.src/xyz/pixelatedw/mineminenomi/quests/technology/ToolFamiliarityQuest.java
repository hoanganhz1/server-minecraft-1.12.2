package xyz.pixelatedw.mineminenomi.quests.technology;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.Quest$ICompleteQuestEvent;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId$Builder;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId$IFactory;
import xyz.pixelatedw.mineminenomi.api.quests.components.QuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.components.UseAbilityQuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.components.UseAbilityQuestComponent$IUseAbilityEvent;
import xyz.pixelatedw.mineminenomi.api.quests.components.UseItemQuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.components.UseItemQuestComponent$IUseItemEvent;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.quests.QuestCompletionEvents;

public class ToolFamiliarityQuest extends Quest {
	// Fields
	public static final DeferredHolder<QuestId<?>, QuestId<ToolFamiliarityQuest>> INSTANCE;
	private static final int COUNT_1 = 25;
	private static final Component TITLE_1;
	private final Objective objective1;
	private final UseItemQuestComponent useItemComp1;
	private final UseAbilityQuestComponent useAbilityComp1;

	public ToolFamiliarityQuest(QuestId<ToolFamiliarityQuest> var1) {
		super((QuestId)(var1));
		this.objective1 = new Objective((Quest)(this),TITLE_1);
		this.useItemComp1 = new UseItemQuestComponent(this.objective1).addUseEvent((UseItemQuestComponent$IUseItemEvent)(((xyz.pixelatedw.mineminenomi.api.quests.components.UseItemQuestComponent$IUseItemEvent)(this::useItem))));
		this.useAbilityComp1 = new UseAbilityQuestComponent(this.objective1).addUseEvent((UseAbilityQuestComponent$IUseAbilityEvent)(((xyz.pixelatedw.mineminenomi.api.quests.components.UseAbilityQuestComponent$IUseAbilityEvent)(this::useAbility))));
		this.objective1.setMaxProgress(25F);
		QuestComponent[] var2 = new QuestComponent[]{this.useItemComp1,this.useAbilityComp1};
		this.objective1.addComponents(var2);
		Objective[] var3 = new Objective[]{this.objective1};
		super.addObjectives(var3);
		super.addCompleteEvent(QuestCompletionEvents.giveTrainingPoints(TrainingPointType.TECHNOLOGY,1));
	}
	private void useItem(Player var1, ItemStack var2, int var3, Objective var4) {
		if (var2.is(ModTags$Items.TECHNOLOGY)){
			this.objective1.alterProgress(var1,1F);
			ModNetwork.sendTo(new SSyncQuestDataPacket(var1),var1);
		}
	}
	private void useAbility(Player var1, IAbility var2, Objective var3) {
		if ((var2 instanceof Ability) && (((Ability)(var2)).hasSourceTypes(SourceType.TECHNOLOGY))){
			this.objective1.alterProgress(var1,1F);
			ModNetwork.sendTo(new SSyncQuestDataPacket(var1),var1);
		}
	}
	static  {
		Supplier var0 = (Supplier) ((Supplier<QuestId>) (() -> {
			return (QuestId) (((xyz.pixelatedw.mineminenomi.api.quests.QuestId$Builder)(new QuestId$Builder((QuestId$IFactory)(((xyz.pixelatedw.mineminenomi.api.quests.QuestId$IFactory)(ToolFamiliarityQuest::new)))).setRepeatCooldown((double)(WyHelper.hoursToTicks(2F))))).build());
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerQuest("tool_familiarity","Tool Familiarity",var0));
		TITLE_1 = ModRegistry.registerObjectiveTitle(INSTANCE.getId(),0,"Use technology-based abilities or items %d times",new Object[]{Integer.valueOf(25)});
	}
}