package xyz.pixelatedw.mineminenomi.api.config.options;

import org.jetbrains.annotations.Nullable;

public abstract class MinMaxOption<T extends Object> extends ConfigOption<T> {
	// Fields
	private T min;
	private T max;

	public MinMaxOption(T var1, T var2, T var3, String var4, @Nullable() String var5) {
		super(var1,var4,var5);
		this.min = var2;
		this.max = var3;
	}
	public T getMin() {
		return (T) (this.min);
	}
	public T getMax() {
		return (T) (this.max);
	}
}