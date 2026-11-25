package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class LoyaltyEvent$Pre extends LoyaltyEvent implements ICancellableEvent {
	public LoyaltyEvent$Pre(Player var1, double var2, StatChangeSource var3) {
		super(var1,var2,var3);
	}
}