package xyz.pixelatedw.mineminenomi.api.quests.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

@FunctionalInterface()
public interface SwingTriggerQuestComponent$IOnSwingEvent {
	public abstract void swing(LivingEntity var0, Objective var1);
}