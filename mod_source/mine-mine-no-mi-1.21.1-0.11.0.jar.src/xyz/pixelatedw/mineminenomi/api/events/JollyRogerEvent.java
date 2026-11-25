package xyz.pixelatedw.mineminenomi.api.events;

import net.neoforged.bus.api.Event;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;

public class JollyRogerEvent extends Event {
	// Fields
	private JollyRoger jollyRoger;
	private Crew crew;

	public JollyRogerEvent(JollyRoger var1, Crew var2) {
		this.jollyRoger = var1;
		this.crew = var2;
	}
	public Crew getCrew() {
		return this.crew;
	}
	public JollyRoger getJollyRoger() {
		return this.jollyRoger;
	}
}