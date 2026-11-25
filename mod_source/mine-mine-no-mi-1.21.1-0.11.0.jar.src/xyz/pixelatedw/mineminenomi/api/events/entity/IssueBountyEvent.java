package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.Nullable;

public class IssueBountyEvent extends PlayerEvent {
	// Fields
	private @Nullable() Player issuer;
	private long bounty;
	private IssueBountyEvent$Result result;

	public IssueBountyEvent(Player var1, long var2, @Nullable() Player var3) {
		super(var1);
		this.result = IssueBountyEvent$Result.ALLOW;
		this.issuer = var3;
	}
	@Nullable()
	public @Nullable() Player getIssuer() {
		return this.issuer;
	}
	public long getBounty() {
		return this.bounty;
	}
	public void setResult(IssueBountyEvent$Result var1) {
		this.result = var1;
	}
	public IssueBountyEvent$Result getResult() {
		return this.result;
	}
}