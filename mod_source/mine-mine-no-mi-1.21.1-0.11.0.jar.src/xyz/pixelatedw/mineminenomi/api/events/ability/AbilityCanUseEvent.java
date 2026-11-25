package xyz.pixelatedw.mineminenomi.api.events.ability;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

public class AbilityCanUseEvent extends AbilityEvent implements ICancellableEvent {
	public AbilityCanUseEvent(Player var1, IAbility var2) {
		super((LivingEntity)(var1),var2);
	}
}