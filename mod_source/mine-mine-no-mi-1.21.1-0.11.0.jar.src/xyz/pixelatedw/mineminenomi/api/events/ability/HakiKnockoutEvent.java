package xyz.pixelatedw.mineminenomi.api.events.ability;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class HakiKnockoutEvent extends LivingEvent {
	// Fields
	private LivingEntity source;
	private boolean isInitialBurst;
	private int knockoutTimer;

	public HakiKnockoutEvent(LivingEntity var1, LivingEntity var2, boolean var3, int var4) {
		super(var1);
		this.source = var2;
		this.isInitialBurst = var3;
		this.knockoutTimer = var4;
	}
	public LivingEntity getSource() {
		return this.source;
	}
	public boolean isInitialBurst() {
		return this.isInitialBurst;
	}
	public int getKnockdownTimer() {
		return this.knockoutTimer;
	}
}