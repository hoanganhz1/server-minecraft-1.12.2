package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.entity.player.Player;

@FunctionalInterface()
public interface Objective$IStartObjective {
	public abstract void start(Player var0);
}