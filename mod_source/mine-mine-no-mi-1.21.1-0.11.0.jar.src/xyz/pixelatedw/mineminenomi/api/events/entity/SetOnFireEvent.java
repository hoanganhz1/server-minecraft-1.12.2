package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;

public class SetOnFireEvent extends EntityEvent implements ICancellableEvent {
	// Fields
	private LivingEntity attacker;
	private int fireTime;

	public SetOnFireEvent(LivingEntity var1, Entity var2, int var3) {
		super(var2);
		this.attacker = var1;
		this.fireTime = var3;
	}
	public LivingEntity getAttacker() {
		return this.attacker;
	}
	public int getFireTime() {
		return this.fireTime;
	}
}