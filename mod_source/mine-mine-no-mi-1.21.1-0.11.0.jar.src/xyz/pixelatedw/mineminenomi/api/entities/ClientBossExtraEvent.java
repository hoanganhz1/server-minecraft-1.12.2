package xyz.pixelatedw.mineminenomi.api.entities;

public class ClientBossExtraEvent {
	// Fields
	private int totalBars;
	private int activeBars;

	public void setTotalBars(int var1) {
		this.totalBars = var1;
	}
	public int getTotalBars() {
		return this.totalBars;
	}
	public void setActiveBars(int var1) {
		this.activeBars = var1;
	}
	public int getActiveBars() {
		return this.activeBars;
	}
}