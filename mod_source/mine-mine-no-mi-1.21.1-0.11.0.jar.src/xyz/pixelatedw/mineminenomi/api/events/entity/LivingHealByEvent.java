package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

public class LivingHealByEvent extends LivingHealEvent implements ICancellableEvent {
	// Fields
	private LivingEntity healer;

	public LivingHealByEvent(LivingEntity var1, LivingEntity var2, float var3) {
		super(var2,var3);
		this.healer = var1;
	}
	public LivingEntity getHealer() {
		return this.healer;
	}
}