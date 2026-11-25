package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;

public class CrewEvent$Create extends CrewEvent implements ICancellableEvent {
	public CrewEvent$Create(Player var1, Crew var2) {
		super(var1,var2);
	}
}