package xyz.pixelatedw.mineminenomi.api.entities.revenge;

public interface IRevengeCheck {
	public abstract void setParent(RevengeMeter var0);
	public abstract int revengeMeterGain();
	public abstract void resetMarkers();
	public abstract boolean isFlagged();
}