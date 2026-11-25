package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class ConsumptionComponent extends AbilityComponent<IAbility> {
	// Fields
	private final PriorityEventPool<ConsumptionComponent$IOnConsumptionEvent> consumptionEvents;

	public ConsumptionComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.CONSUMPTION.get())),var1);
		this.consumptionEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public ConsumptionComponent addConsumptionEvent(ConsumptionComponent$IOnConsumptionEvent var1) {
		this.consumptionEvents.addEvent(var1);
		return this;
	}
	public boolean tryConsumption(LivingEntity var1, int var2, float var3) {
		return !(this.consumptionEvents.dispatchCancelable((Predicate)(((Predicate<ConsumptionComponent$IOnConsumptionEvent>)((l0) -> {
			return !(l0.consume(var1,this.getAbility(),var2,var3));
		})))));
	}
}