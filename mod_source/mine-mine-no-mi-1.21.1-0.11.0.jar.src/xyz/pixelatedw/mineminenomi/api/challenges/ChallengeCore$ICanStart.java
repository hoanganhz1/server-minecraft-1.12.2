package xyz.pixelatedw.mineminenomi.api.challenges;

import net.minecraft.world.entity.player.Player;

@FunctionalInterface()
public interface ChallengeCore$ICanStart {
	public abstract boolean canStart(Player var0);
}