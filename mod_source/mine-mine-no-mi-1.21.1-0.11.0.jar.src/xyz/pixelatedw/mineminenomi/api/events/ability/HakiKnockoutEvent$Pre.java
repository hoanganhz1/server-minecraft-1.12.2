package xyz.pixelatedw.mineminenomi.api.events.ability;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.ICancellableEvent;

public class HakiKnockoutEvent$Pre extends HakiKnockoutEvent implements ICancellableEvent {
	public HakiKnockoutEvent$Pre(LivingEntity var1, LivingEntity var2, boolean var3, int var4) {
		super(var1,var2,var3,var4);
	}
}