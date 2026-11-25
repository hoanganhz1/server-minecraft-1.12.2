package xyz.pixelatedw.mineminenomi.ui.panel;

import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

public class AbilitiesListScrollPanel$Entry<T extends IAbility> {
	// Fields
	private @Nullable() T ability;
	 @Nullable() AbilityCore<T> abilityCore;

	public AbilitiesListScrollPanel$Entry(final AbilitiesListScrollPanel var1) {
	}
	public AbilitiesListScrollPanel$Entry(final AbilitiesListScrollPanel var1, IAbility var2) {
		this.ability = (T) (var2);
	}
	public AbilitiesListScrollPanel$Entry(final AbilitiesListScrollPanel var1, AbilityCore var2) {
		this.abilityCore = var2;
	}
	public AbilityCore<T> getActive() {
		return (AbilityCore<T>) (AbilityCore) (this.abilityCore);
	}
	public boolean isActive() {
		return (this.abilityCore) != (null);
	}
	public T getPassive() {
		return (T) (this.ability);
	}
	public boolean isPassive() {
		return (this.ability) != (null);
	}
}