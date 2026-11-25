package xyz.pixelatedw.mineminenomi.api.quests.components;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

@FunctionalInterface()
public interface CountdownQuestComponent$IFinishCountdownEvent {
	public abstract void tick(Player var0, Objective var1);
}