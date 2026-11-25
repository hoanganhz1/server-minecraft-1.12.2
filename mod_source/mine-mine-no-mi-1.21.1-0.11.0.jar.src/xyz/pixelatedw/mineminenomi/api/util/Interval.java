package xyz.pixelatedw.mineminenomi.api.util;

public class Interval {
	// Fields
	protected int interval;
	protected float tick;
	private boolean trackTPS;

	public Interval(int var1) {
		this.tick = 0F;
		this.trackTPS = false;
		this.interval = var1;
	}
	public static Interval startAtZero(int var0) {
		return new Interval(var0);
	}
	public static Interval startAtMax(int var0) {
		Interval var1 = new Interval(var0);
		var1.tick = (float)(var0);
		return var1;
	}
	public int getInterval() {
		return this.interval;
	}
	public <T> T trackTPS() {
		this.trackTPS = true;
		return (T) (this);
	}
	public boolean canTick() {
		if (this.trackTPS){
			this.tick = (this.tick) - (TPSDelta.INSTANCE.getDeltaTime());
		}else{
			this.tick = (this.tick) - (1F);
		}
		if ((this.tick) <= (0F)){
			this.tick = (float)(this.interval);
			return true;
		}else{
			return false;
		}
	}
	public void restartIntervalToZero() {
		this.tick = 0F;
	}
	public void restartIntervalToMax() {
		this.tick = (float)(this.interval);
	}
	public int getTick() {
		return Math.round(this.tick);
	}
}