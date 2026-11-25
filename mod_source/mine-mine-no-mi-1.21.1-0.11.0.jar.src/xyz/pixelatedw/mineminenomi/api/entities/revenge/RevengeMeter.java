package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import com.google.common.collect.EvictingQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class RevengeMeter {
	// Fields
	private static final int QUEUE_ENTRIES = 20;
	private LivingEntity entity;
	private int revengeValue;
	private int maxRevengeValue;
	private int passiveDecreasingValue;
	private List<IRevengeCheck> checks;
	private Map<Class<? extends IRevengeCheck>, Integer> passedChecksTotal;
	private Queue<IRevengeCheck> passedChecksHistory;

	public RevengeMeter(LivingEntity var1, int var2, int var3) {
		this.maxRevengeValue = 100;
		this.passiveDecreasingValue = 1;
		this.checks = new ArrayList();
		this.passedChecksTotal = new HashMap();
		this.passedChecksHistory = EvictingQueue.create(20);
		this.entity = var1;
		this.maxRevengeValue = var2;
		this.passiveDecreasingValue = var3;
	}
	public void addCheck(IRevengeCheck var1) {
		var1.setParent(this);
		this.checks.add(var1);
	}
	public void tick() {
		if (((this.entity.level().getGameTime()) % (60L)) != (0L)){
			return;
		}else{
			if (((this.revengeValue) > (0)) && ((this.passiveDecreasingValue) > (0))){
				this.reduceRevengeValue(this.passiveDecreasingValue);
			}
			return;
		}
	}
	public List<IRevengeCheck> getChecks() {
		return this.checks;
	}
	public Map<Class<? extends IRevengeCheck>, Integer> getLastChecksMap() {
		return this.passedChecksTotal;
	}
	public Queue<IRevengeCheck> getLastChecks() {
		return this.passedChecksHistory;
	}
	public boolean isCheckPresentInLastNEntries(Class<? extends IRevengeCheck> var1, int var2, int var3) {
		if (this.passedChecksHistory.isEmpty()){
			return false;
		}else{
			var2 = Math.min(var2,this.passedChecksHistory.size());
			var2 = Math.max(0,(this.passedChecksHistory.size()) - (var2));
			Class<? extends IRevengeCheck> var4 = var1;
			final Class<? extends IRevengeCheck> var4_f1 = var4;
			Predicate<IRevengeCheck> var5 = (l0) -> {
			return ((java.lang.Object)(l0.getClass())).equals((Object)(var4_f1));
		};
			return (this.passedChecksHistory.stream().skip((long)(var2)).filter(var5).count()) >= ((long)(var3));
		}
	}
	public int countCheckTriggers(Class<? extends IRevengeCheck> var1) {
		return ((Integer)(((java.util.Map)(this.getLastChecksMap())).getOrDefault((Object)(var1),(Object)(Integer.valueOf(0))))).intValue();
	}
	public void passCheck(IRevengeCheck var1) {
		this.passedChecksHistory.add(var1);
		Integer var2 = ((Integer)(((java.util.Map)(this.passedChecksTotal)).get((Object)(var1.getClass()))));
		if ((var2) == (null)){
			this.passedChecksTotal.put(var1.getClass(),Integer.valueOf(1));
		}else{
			this.passedChecksTotal.put(var1.getClass(),Integer.valueOf((var2.intValue()) + (1)));
		}
	}
	public void resetMarkers() {
		this.revengeValue = 0;
		this.passedChecksTotal.clear();
		this.passedChecksHistory.clear();
		Consumer<? super IRevengeCheck> var1 = (l0) -> {
			l0.resetMarkers();
		};
		((java.lang.Iterable)(this.checks)).forEach((Consumer)(var1));
	}
	public boolean isRevengeMaxed() {
		return (this.getRevengePercentage()) >= (1F);
	}
	public boolean isRevengeAbove(float var1) {
		return (this.getRevengePercentage()) > (var1);
	}
	public boolean isRevengeUnder(float var1) {
		return (this.getRevengePercentage()) < (var1);
	}
	public int getRevengeValue() {
		return Mth.clamp(this.revengeValue,0,this.maxRevengeValue);
	}
	public int getMaxRevengeValue() {
		return this.maxRevengeValue;
	}
	public void addRevengeValue(int var1) {
		this.setRevengeValue((this.revengeValue) + (var1));
	}
	public void addRevengeValueFrom(IRevengeCheck var1) {
		this.addRevengeValue(var1.revengeMeterGain());
		this.passCheck(var1);
	}
	public void reduceRevengeValue(int var1) {
		this.setRevengeValue((this.revengeValue) - (var1));
	}
	public void setRevengeValue(int var1) {
		this.revengeValue = Mth.clamp(var1,0,this.maxRevengeValue);
	}
	public float getRevengePercentage() {
		return ((float)(this.getRevengeValue())) / ((float)(this.maxRevengeValue));
	}
}