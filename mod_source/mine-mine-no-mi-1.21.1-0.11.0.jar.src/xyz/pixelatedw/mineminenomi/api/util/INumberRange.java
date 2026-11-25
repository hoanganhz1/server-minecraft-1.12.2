package xyz.pixelatedw.mineminenomi.api.util;

public interface INumberRange<N extends Number> {
	public abstract boolean isInfinite();
	public abstract boolean isFixed();
	public abstract boolean isRange();
	public abstract N getMin();
	public abstract N getMax();
}