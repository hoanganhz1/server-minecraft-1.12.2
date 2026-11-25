package xyz.pixelatedw.mineminenomi.api.util;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PriorityEventPool<E extends Object> {
	// Fields
	public static final int DEFAULT_PRIORITY = 100;
	private final Multimap<Integer, E> events;
	private boolean isDirty;
	private long lastCount;

	public PriorityEventPool() {
		this.events = HashMultimap.create();
	}
	public void addEvent(E var1) {
		this.addEvent(100,var1);
	}
	public void addEvent(int var1, E var2) {
		this.events.put(Integer.valueOf(var1),var2);
		this.isDirty = true;
	}
	public void removeEvent(E var1) {
		if (this.events.containsValue(var1)){
			int var2 = -1;
			Iterator var3 = this.events.keySet().iterator();
			LOOP_1:
			do{
				if (var3.hasNext()){
					int var4 = ((Integer)(var3.next())).intValue();
					if ((var2) >= (0)){
						break;
					}else{
						Iterator var5 = this.events.get(Integer.valueOf(var4)).iterator();
						do{
							if (var5.hasNext()){
								if (var5.next().equals(var1)){
									var2 = var4;
									continue LOOP_1;
								}else{
									continue;
								}
							}else{
								continue LOOP_1;
							}
						} while (true);
					}
				}else{
					break;
				}
			} while (true);
			this.events.remove(Integer.valueOf(var2),var1);
			this.isDirty = true;
		}
	}
	public void clearEvents() {
		this.events.clear();
	}
	public void clearEvents(int var1) {
		this.events.removeAll(Integer.valueOf(var1));
	}
	public Stream<E> getEventsStream() {
		Comparator var1 = (Comparator) ((Comparator<Map.Entry>) ((l0, l1) -> {
			return ((((Integer)(l0.getKey())).intValue()) > (((Integer)(l1.getKey())).intValue())) ? (1) : (-1);
		}));
		return (Stream<E>) (Stream) (((java.util.stream.Stream)(this.events.entries().stream())).sorted((Comparator)(var1)).map((Function)(((Function<Map.Entry<?, Object>, Object>)(Map.Entry::getValue)))));
	}
	public boolean dispatchCancelable(Predicate<E> var1) {
		return (this.getEventsStream().filter(var1).count()) > (0L);
	}
	public void dispatch(Consumer<E> var1) {
		((java.util.stream.Stream)(this.getEventsStream())).forEachOrdered((Consumer)(var1));
	}
	public long countEventsInPool() {
		if (this.isDirty){
			this.lastCount = this.getEventsStream().count();
			this.isDirty = false;
		}
		return this.lastCount;
	}
}