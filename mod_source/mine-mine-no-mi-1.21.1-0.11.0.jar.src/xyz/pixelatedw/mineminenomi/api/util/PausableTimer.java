package xyz.pixelatedw.mineminenomi.api.util;

public class PausableTimer {
	// Fields
	private long startTime;
	private long elapsed;
	private long pauseTime;
	private boolean paused;

	public PausableTimer() {
		this.startTime = 0L;
		this.elapsed = 0L;
		this.pauseTime = 0L;
		this.paused = false;
	}
	public void start() {
		if ((this.startTime) == (0L)){
			this.startTime = System.currentTimeMillis();
		}else{
			if (this.paused){
				this.elapsed = (this.elapsed) + ((System.currentTimeMillis()) - (this.pauseTime));
				this.paused = false;
			}else{
				return;
			}
		}
	}
	public void pause() {
		if ((!(this.paused)) && ((this.startTime) != (0L))){
			this.pauseTime = System.currentTimeMillis();
			this.paused = true;
		}
	}
	public void reset() {
		this.startTime = 0L;
		this.elapsed = 0L;
		this.pauseTime = 0L;
		this.paused = false;
	}
	public long getElapsedTime() {
		if ((this.startTime) == (0L)){
			return 0L;
		}else{
			if (this.paused){
				return ((this.pauseTime) - (this.startTime)) - (this.elapsed);
			}else{
				return ((System.currentTimeMillis()) - (this.startTime)) - (this.elapsed);
			}
		}
	}
}