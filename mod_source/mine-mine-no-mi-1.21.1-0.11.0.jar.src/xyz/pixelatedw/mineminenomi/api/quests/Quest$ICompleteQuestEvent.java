package xyz.pixelatedw.mineminenomi.api.quests;

import net.minecraft.world.entity.player.Player;

@FunctionalInterface()
public interface Quest$ICompleteQuestEvent {
	public abstract void complete(Player var0);
}