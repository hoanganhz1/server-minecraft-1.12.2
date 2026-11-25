package xyz.pixelatedw.mineminenomi.api.quests.components;

import java.util.function.Consumer;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;

public class UseAbilityQuestComponent extends QuestComponent {
	// Fields
	private final PriorityEventPool<UseAbilityQuestComponent$IUseAbilityEvent> useAbilityEvents;

	public UseAbilityQuestComponent(Objective var1) {
		super(var1);
		this.useAbilityEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public UseAbilityQuestComponent addUseEvent(UseAbilityQuestComponent$IUseAbilityEvent var1) {
		return this.addUseEvent(100,var1);
	}
	public UseAbilityQuestComponent addUseEvent(int var1, UseAbilityQuestComponent$IUseAbilityEvent var2) {
		this.useAbilityEvents.addEvent(var1,var2);
		return this;
	}
	public void useAbility(Player var1, IAbility var2) {
		if (!(super.isValid())){
			return;
		}else{
			UseAbilityQuestComponent var3 = this;
			Player var4 = var1;
			IAbility var5 = var2;
			final Player var4_f1 = var4;
			final IAbility var5_f2 = var5;
			Consumer<UseAbilityQuestComponent$IUseAbilityEvent> var6 = (l0) -> {
			l0.useAbility(var4_f1,var5_f2,super.getObjective());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.useAbilityEvents)).dispatch((Consumer)(var6));
			return;
		}
	}
}