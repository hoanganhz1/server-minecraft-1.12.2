package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import org.jetbrains.annotations.Nullable;

public class EntityCarryEvent extends LivingEvent implements ICancellableEvent {
	// Fields
	private final @Nullable() LivingEntity carryTarget;
	private final boolean isCarrying;

	public EntityCarryEvent(LivingEntity var1, @Nullable() LivingEntity var2, boolean var3) {
		super(var1);
		this.carryTarget = var2;
		this.isCarrying = var3;
	}
	@Nullable()
	public @Nullable() LivingEntity getCarryTarget() {
		return this.carryTarget;
	}
	public boolean isCarrying() {
		return this.isCarrying;
	}
}