package xyz.pixelatedw.mineminenomi.api.abilities;

@FunctionalInterface()
public interface AbilityCore$IFactory<A extends IAbility> {
	public abstract A create(AbilityCore<A> var0);
}