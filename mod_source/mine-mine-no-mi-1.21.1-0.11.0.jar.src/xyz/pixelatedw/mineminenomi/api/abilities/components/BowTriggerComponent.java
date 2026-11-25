package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class BowTriggerComponent extends AbilityComponent<IAbility> {
	// Fields
	private final PriorityEventPool<BowTriggerComponent$IOnShootEvent> shootEvents;

	public BowTriggerComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.BOW_TRIGGER.get())),var1);
		this.shootEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public BowTriggerComponent addShootEvent(BowTriggerComponent$IOnShootEvent var1) {
		this.shootEvents.addEvent(var1);
		return this;
	}
	public boolean tryShoot(LivingEntity var1) {
		this.ensureIsRegistered();
		return !(this.shootEvents.dispatchCancelable((Predicate)(((Predicate<BowTriggerComponent$IOnShootEvent>)((l0) -> {
			return !(l0.shoot(var1,this.getAbility()));
		})))));
	}
}