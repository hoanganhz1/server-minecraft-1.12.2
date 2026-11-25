package xyz.pixelatedw.mineminenomi.api.damagesources;

public abstract class IDamageSourceHandler$Tag<T extends Object> {
	// Fields
	private T value;

	public IDamageSourceHandler$Tag(T var1) {
		this.value = var1;
	}
	public T value() {
		return (T) (this.value);
	}
}