package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class CurrencyEvent$Post extends CurrencyEvent {
	public CurrencyEvent$Post(Player var1, long var2, Currency var3, StatChangeSource var4) {
		super(var1,var2,var3,var4);
	}
}