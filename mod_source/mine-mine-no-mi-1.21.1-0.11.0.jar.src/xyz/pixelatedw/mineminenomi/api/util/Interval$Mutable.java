package xyz.pixelatedw.mineminenomi.api.util;

public class Interval$Mutable extends Interval {
	public Interval$Mutable(int var1) {
		super(var1);
	}
	public void setInterval(int var1) {
		this.interval = var1;
		this.restartIntervalToMax();
	}
}