package xyz.pixelatedw.mineminenomi.api.util;

public class IntRange implements INumberRange<Integer> {
	// Fields
	private int min;
	private int max;

	public IntRange(int var1) {
		this.min = var1;
		this.max = var1;
	}
	public IntRange(int var1, int var2) {
		this.min = var1;
		this.max = var2;
	}
	public boolean isInfinite() {
		return ((this.min) == (2147483647)) || ((this.max) == (2147483647));
	}
	public boolean isRange() {
		return !(this.isFixed());
	}
	public boolean isFixed() {
		return (this.min) == (this.max);
	}
	public Integer getMin() {
		return Integer.valueOf(this.min);
	}
	public Integer getMax() {
		return Integer.valueOf(this.max);
	}
}