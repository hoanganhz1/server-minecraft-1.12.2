package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class DorikiEvent extends StatEvent {
	// Fields
	private double doriki;

	public DorikiEvent(Player var1, double var2, StatChangeSource var3) {
		super(var1,var3);
		this.doriki = var2;
	}
	public double getDoriki() {
		return this.doriki;
	}
	public void setDoriki(double var1) {
		this.doriki = var1;
	}
}