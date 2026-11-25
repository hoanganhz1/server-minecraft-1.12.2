package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class BountyEvent$Pre extends BountyEvent implements ICancellableEvent {
	public BountyEvent$Pre(Player var1, long var2, StatChangeSource var3) {
		super(var1,var2,var3);
	}
}