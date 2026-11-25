package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class BountyEvent extends StatEvent {
	// Fields
	private long bounty;

	public BountyEvent(Player var1, long var2, StatChangeSource var3) {
		super(var1,var3);
		this.bounty = var2;
	}
	public long getBounty() {
		return this.bounty;
	}
	public void setBounty(long var1) {
		this.bounty = var1;
	}
}