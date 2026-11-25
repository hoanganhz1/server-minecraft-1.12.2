package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.entity.player.Player;

@FunctionalInterface()
public interface Objective$ICompleteObjective {
	public abstract void complete(Player var0);
}