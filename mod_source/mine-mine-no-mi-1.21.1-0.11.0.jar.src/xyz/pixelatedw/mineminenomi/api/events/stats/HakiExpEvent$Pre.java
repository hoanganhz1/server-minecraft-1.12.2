package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import xyz.pixelatedw.mineminenomi.api.enums.HakiType;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class HakiExpEvent$Pre extends HakiExpEvent implements ICancellableEvent {
	public HakiExpEvent$Pre(Player var1, float var2, HakiType var3, StatChangeSource var4) {
		super(var1,var2,var3,var4);
	}
}