package xyz.pixelatedw.mineminenomi.api.util;

public class FloatRange implements INumberRange<Float> {
	// Fields
	private float min;
	private float max;

	public FloatRange(float var1) {
		this.min = var1;
		this.max = var1;
	}
	public FloatRange(float var1, float var2) {
		this.min = var1;
		this.max = var2;
	}
	public boolean isInfinite() {
		return ((this.min) == ((1.0F/0.0F))) || ((this.max) == ((1.0F/0.0F)));
	}
	public boolean isRange() {
		return !(this.isFixed());
	}
	public boolean isFixed() {
		return (this.min) == (this.max);
	}
	public Float getMin() {
		return Float.valueOf(this.min);
	}
	public Float getMax() {
		return Float.valueOf(this.max);
	}
}