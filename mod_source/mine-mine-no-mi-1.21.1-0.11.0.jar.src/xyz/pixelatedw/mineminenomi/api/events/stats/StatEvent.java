package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public abstract class StatEvent extends PlayerEvent {
	// Fields
	private StatChangeSource source;

	public StatEvent(Player var1, StatChangeSource var2) {
		super(var1);
		this.source = var2;
	}
	public StatChangeSource getSource() {
		return this.source;
	}
}