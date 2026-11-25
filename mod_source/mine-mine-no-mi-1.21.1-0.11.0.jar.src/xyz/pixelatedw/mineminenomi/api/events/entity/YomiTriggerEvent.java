package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class YomiTriggerEvent extends PlayerEvent {
	// Fields
	private Player newPlayer;

	public YomiTriggerEvent(Player var1, Player var2) {
		super(var1);
		this.newPlayer = var2;
	}
}