package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.entity.player.Player;

public interface ISurviveObjective {
	public abstract boolean checkTime(Player var0);
}