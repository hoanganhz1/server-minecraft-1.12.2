package xyz.pixelatedw.mineminenomi.api.util;

import java.util.function.Supplier;
import xyz.pixelatedw.mineminenomi.MineMineNoMi;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

public class TPSDelta {
	// Fields
	public static final TPSDelta INSTANCE = new TPSDelta();
	private boolean init;
	private boolean hasExpTimers;
	private ThreadLocal<Long> lastTickTime;
	private ThreadLocal<Float> deltaTime;

	public TPSDelta() {
		this.init = false;
		this.hasExpTimers = true;
		Supplier<Long> var1 = () -> {
			return Long.valueOf(MineMineNoMi.PAUSABLE_TIMER.getElapsedTime());
		};
		this.lastTickTime = (ThreadLocal) (ThreadLocal.withInitial(var1));
		Supplier<Float> var2 = () -> {
			return Float.valueOf(0F);
		};
		this.deltaTime = (ThreadLocal) (ThreadLocal.withInitial(var2));
	}
	public void init() {
		this.init = true;
		this.hasExpTimers = ServerConfig.hasExperimentalTimers();
	}
	public void tick() {
		if (!(this.hasExpTimers)){
			return;
		}else{
			long var1 = MineMineNoMi.PAUSABLE_TIMER.getElapsedTime();
			long var2 = (var1) - (((Long)(this.lastTickTime.get())).longValue());
			this.lastTickTime.set(Long.valueOf(var1));
			this.deltaTime.set(Float.valueOf(Math.max(((float)(var2)) / (50F),1F)));
			return;
		}
	}
	public float getDeltaTime() {
		if (!(this.hasExpTimers)){
			return 1F;
		}else{
			if (!(this.init)){
				throw new RuntimeException((String)("TPSDelta was never initialized!"));
			}else{
				return ((Float)(this.deltaTime.get())).floatValue();
			}
		}
	}
	public long getLastTickTime() {
		if (!(this.init)){
			throw new RuntimeException((String)("TPSDelta was never initialized!"));
		}else{
			return ((Long)(this.lastTickTime.get())).longValue();
		}
	}
}