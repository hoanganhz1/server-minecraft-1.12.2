package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class DorikiEvent$Pre extends DorikiEvent implements ICancellableEvent {
	public DorikiEvent$Pre(Player var1, double var2, StatChangeSource var3) {
		super(var1,var2,var3);
	}
}