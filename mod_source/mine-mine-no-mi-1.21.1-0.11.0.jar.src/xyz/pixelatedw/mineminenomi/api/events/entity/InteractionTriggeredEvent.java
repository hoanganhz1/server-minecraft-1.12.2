package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class InteractionTriggeredEvent extends LivingEvent implements ICancellableEvent {
	// Fields
	private final int button;
	private final InteractionHand hand;
	private final boolean isHittingEntity;

	public InteractionTriggeredEvent(LivingEntity var1, int var2, InteractionHand var3, boolean var4) {
		super(var1);
		this.button = var2;
		this.hand = var3;
		this.isHittingEntity = var4;
	}
	public boolean isAttack() {
		return (this.button) == (0);
	}
	public boolean isUseItem() {
		return (this.button) == (1);
	}
	public boolean isPickBlock() {
		return (this.button) == (2);
	}
	public boolean isHittingEntity() {
		return this.isHittingEntity;
	}
	public InteractionHand getHand() {
		return this.hand;
	}
}