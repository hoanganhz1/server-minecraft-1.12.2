package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class CurrencyEvent$Pre extends CurrencyEvent implements ICancellableEvent {
	public CurrencyEvent$Pre(Player var1, long var2, Currency var3, StatChangeSource var4) {
		super(var1,var2,var3,var4);
	}
}