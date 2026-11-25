package xyz.pixelatedw.mineminenomi.api.quests.components;

import java.util.function.Consumer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;

public class SwingTriggerQuestComponent extends QuestComponent {
	// Fields
	private final PriorityEventPool<SwingTriggerQuestComponent$IOnSwingEvent> swingEvents;
	private long lastSwingTime;

	public SwingTriggerQuestComponent(Objective var1) {
		super(var1);
		this.swingEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public SwingTriggerQuestComponent addSwingEvent(SwingTriggerQuestComponent$IOnSwingEvent var1) {
		return this.addSwingEvent(100,var1);
	}
	public SwingTriggerQuestComponent addSwingEvent(int var1, SwingTriggerQuestComponent$IOnSwingEvent var2) {
		this.swingEvents.addEvent(var1,var2);
		return this;
	}
	public void swing(LivingEntity var1) {
		if (!(super.isValid())){
			return;
		}else{
			SwingTriggerQuestComponent var2 = this;
			LivingEntity var3 = var1;
			final LivingEntity var3_f1 = var3;
			Consumer<SwingTriggerQuestComponent$IOnSwingEvent> var4 = (l0) -> {
			l0.swing(var3_f1,super.getObjective());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.swingEvents)).dispatch((Consumer)(var4));
			this.lastSwingTime = var1.level().getGameTime();
			return;
		}
	}
	public long getLastSwingTime() {
		return this.lastSwingTime;
	}
}