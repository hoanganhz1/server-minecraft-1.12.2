package xyz.pixelatedw.mineminenomi.api.events.ability;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

public class AbilityUseEvent$Post extends AbilityUseEvent {
	public AbilityUseEvent$Post(LivingEntity var1, IAbility var2) {
		super(var1,var2);
	}
}