package xyz.pixelatedw.mineminenomi.api.abilities;

public abstract class AbilityPool$FlagValue<T extends Object> {
	// Fields
	protected T value;

	public AbilityPool$FlagValue(T var1) {
		this.value = var1;
	}
	public T get() {
		return (T) (this.value);
	}
}