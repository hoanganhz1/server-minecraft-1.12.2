package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import org.jetbrains.annotations.Nullable;

public class UpdateCombatStateEvent extends LivingEvent {
	// Fields
	private @Nullable() LivingEntity attacker;
	private boolean state;

	public UpdateCombatStateEvent(LivingEntity var1, LivingEntity var2, boolean var3) {
		super(var1);
		this.attacker = var2;
		this.state = var3;
	}
	@Nullable()
	public @Nullable() LivingEntity getLastAttacker() {
		return this.attacker;
	}
	public boolean getCurrentInCombatState() {
		return this.state;
	}
}