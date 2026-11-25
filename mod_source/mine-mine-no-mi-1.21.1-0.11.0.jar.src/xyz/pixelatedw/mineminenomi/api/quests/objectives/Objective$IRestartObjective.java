package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.entity.player.Player;

@FunctionalInterface()
public interface Objective$IRestartObjective {
	public abstract boolean restart(Player var0);
}