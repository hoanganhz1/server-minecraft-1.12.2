package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class BountyEvent$Post extends BountyEvent {
	public BountyEvent$Post(Player var1, long var2, StatChangeSource var3) {
		super(var1,var2,var3);
	}
}