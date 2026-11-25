package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class CurrencyEvent extends StatEvent {
	// Fields
	private long amount;
	private Currency currency;

	public CurrencyEvent(Player var1, long var2, Currency var3, StatChangeSource var4) {
		super(var1,var4);
		this.amount = var2;
		this.currency = var3;
	}
	public Currency getCurrency() {
		return this.currency;
	}
	public long getAmount() {
		return this.amount;
	}
	public void setAmount(long var1) {
		this.amount = var1;
	}
}