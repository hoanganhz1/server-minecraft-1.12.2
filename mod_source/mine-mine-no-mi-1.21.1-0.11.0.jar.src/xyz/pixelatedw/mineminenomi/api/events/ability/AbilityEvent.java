package xyz.pixelatedw.mineminenomi.api.events.ability;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

public class AbilityEvent extends LivingEvent {
	// Fields
	private IAbility ability;

	public AbilityEvent(LivingEntity var1, IAbility var2) {
		super(var1);
		this.ability = var2;
	}
	public IAbility getAbility() {
		return this.ability;
	}
}