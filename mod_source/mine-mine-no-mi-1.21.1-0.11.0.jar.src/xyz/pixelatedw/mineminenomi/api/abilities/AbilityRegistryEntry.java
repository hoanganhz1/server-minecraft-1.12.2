package xyz.pixelatedw.mineminenomi.api.abilities;

@FunctionalInterface()
public interface AbilityRegistryEntry<T extends Object> {
	public abstract T get(String var0, String var1);
}