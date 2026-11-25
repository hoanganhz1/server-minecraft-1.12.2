package xyz.pixelatedw.mineminenomi.api.events.ability;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.ICancellableEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

public class AbilityUseEvent$Pre extends AbilityUseEvent implements ICancellableEvent {
	public AbilityUseEvent$Pre(LivingEntity var1, IAbility var2) {
		super(var1,var2);
	}
}