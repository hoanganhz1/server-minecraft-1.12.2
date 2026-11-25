package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public interface IEntityInteractObjective {
	public abstract boolean checkInteraction(Player var0, Entity var1);
}