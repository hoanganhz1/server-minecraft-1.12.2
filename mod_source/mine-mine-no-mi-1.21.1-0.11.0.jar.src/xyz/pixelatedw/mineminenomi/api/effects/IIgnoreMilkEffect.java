package xyz.pixelatedw.mineminenomi.api.effects;

public interface IIgnoreMilkEffect {
	public default boolean isRemoveable() {
		return false;
	}
}