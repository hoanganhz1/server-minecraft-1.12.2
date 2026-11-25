package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;

public class CrewEvent extends PlayerEvent {
	// Fields
	private Crew crew;

	public CrewEvent(Player var1, Crew var2) {
		super(var1);
		this.crew = var2;
	}
	public Crew getCrew() {
		return this.crew;
	}
}