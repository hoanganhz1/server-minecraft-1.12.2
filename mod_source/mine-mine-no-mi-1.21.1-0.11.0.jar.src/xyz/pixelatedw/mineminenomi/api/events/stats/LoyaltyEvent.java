package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class LoyaltyEvent extends StatEvent {
	// Fields
	private double loyalty;

	public LoyaltyEvent(Player var1, double var2, StatChangeSource var3) {
		super(var1,var3);
		this.loyalty = var2;
	}
	public double getLoyalty() {
		return this.loyalty;
	}
	public void setLoyalty(double var1) {
		this.loyalty = var1;
	}
}