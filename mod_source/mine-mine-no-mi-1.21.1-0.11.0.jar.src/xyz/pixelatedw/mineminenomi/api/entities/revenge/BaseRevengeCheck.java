package xyz.pixelatedw.mineminenomi.api.entities.revenge;

public abstract class BaseRevengeCheck implements IRevengeCheck {
	// Fields
	private RevengeMeter parent;
	private boolean isFlagged;

	public void setParent(RevengeMeter var1) {
		this.parent = var1;
	}
	public void addRevengeValue(int var1) {
		this.parent.addRevengeValue(var1);
	}
	public void resetMarkers() {
		this.isFlagged = false;
	}
	public void markFlagged() {
		this.isFlagged = true;
	}
	public boolean isFlagged() {
		return this.isFlagged;
	}
}