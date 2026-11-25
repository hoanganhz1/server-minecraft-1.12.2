package xyz.pixelatedw.mineminenomi.api.effects;

public interface IBreakableEffect {
	public default float getBreakingMultiplier() {
		return 2F;
	}
}