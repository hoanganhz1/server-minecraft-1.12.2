package xyz.pixelatedw.mineminenomi.api.events.ability;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public class UnlockAbilityEvent extends LivingEvent {
	// Fields
	private AbilityCore<?> abilityCore;
	private UnlockAbilityEvent$Result result;

	public UnlockAbilityEvent(LivingEntity var1, AbilityCore<?> var2) {
		super(var1);
		this.result = UnlockAbilityEvent$Result.DEFAULT;
		this.abilityCore = var2;
	}
	public AbilityCore<?> getAbilityCore() {
		return (AbilityCore<?>) (AbilityCore) (this.abilityCore);
	}
	public void setResult(UnlockAbilityEvent$Result var1) {
		this.result = var1;
	}
	public UnlockAbilityEvent$Result getResult() {
		return this.result;
	}
}