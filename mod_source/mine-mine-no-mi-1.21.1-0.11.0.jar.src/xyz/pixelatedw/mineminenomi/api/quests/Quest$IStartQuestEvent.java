package xyz.pixelatedw.mineminenomi.api.quests;

import net.minecraft.world.entity.player.Player;

@FunctionalInterface()
public interface Quest$IStartQuestEvent {
	public abstract void start(Player var0);
}