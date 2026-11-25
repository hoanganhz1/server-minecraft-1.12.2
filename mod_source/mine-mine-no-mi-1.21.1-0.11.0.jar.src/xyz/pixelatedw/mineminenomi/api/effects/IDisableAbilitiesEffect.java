package xyz.pixelatedw.mineminenomi.api.effects;

import java.util.function.Predicate;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

public interface IDisableAbilitiesEffect {
	public abstract Predicate<IAbility> getDisabledAbilities();
}