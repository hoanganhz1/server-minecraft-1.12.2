package xyz.pixelatedw.mineminenomi.api.entities;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class GoalMemories {
	// Fields
	private final Map<MemoryModuleType<?>, ExpirableValue<?>> memories;

	public GoalMemories() {
		this.memories = Maps.newHashMap();
	}
	public void tick() {
		Iterator var1 = this.memories.entrySet().iterator();
		do{
			if (var1.hasNext()){
				ExpirableValue var2 = ((ExpirableValue)(((Map.Entry)(var1.next())).getValue()));
				var2.tick();
				if (var2.hasExpired()){
					var1.remove();
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public <U> Optional<U> getMemory(MemoryModuleType<U> var1) {
		try{
			return (Optional<U>) (Optional) (Optional.of((Object)(((ExpirableValue)(((java.util.Map)(this.memories)).get((Object)(var1)))).getValue())));
		}catch(NullPointerException var2){
			return (Optional<U>) (Optional) (Optional.empty());
		}
	}
	public boolean hasMemoryValue(MemoryModuleType<?> var1) {
		return this.checkMemory(var1);
	}
	public <U> void eraseMemory(MemoryModuleType<U> var1) {
		this.memories.remove(var1);
	}
	public <U> void setMemory(MemoryModuleType<U> var1, U var2) {
		this.setMemoryInternal((MemoryModuleType)(var1),(ExpirableValue)(ExpirableValue.of(var2)));
	}
	public <U> void setMemoryWithExpiry(MemoryModuleType<U> var1, U var2, long var3) {
		this.setMemoryInternal((MemoryModuleType)(var1),(ExpirableValue)(ExpirableValue.of(var2,var3)));
	}
	public boolean checkMemory(MemoryModuleType<?> var1) {
		return (((ExpirableValue)(((java.util.Map)(this.memories)).get((Object)(var1))))) != (null);
	}
	public <U> boolean isMemoryValue(MemoryModuleType<U> var1, U var2) {
		if (!(this.hasMemoryValue(var1))){
			return false;
		}else{
			U var3 = var2;
			final U var3_f1 = var3;
			Predicate<? super U> var4 = (l0) -> {
			return l0.equals(var3_f1);
		};
			return this.getMemory(var1).filter(var4).isPresent();
		}
	}
	private <U> void setMemoryInternal(MemoryModuleType<U> var1, ExpirableValue<?> var2) {
		if ((this.memories.containsKey(var1)) && (this.isEmptyCollection(var2.getValue()))){
			this.eraseMemory((MemoryModuleType)(var1));
		}else{
			this.memories.put((MemoryModuleType)(var1),var2);
		}
	}
	private boolean isEmptyCollection(Object var1) {
		return (var1 instanceof Collection) && (((Collection)(var1)).isEmpty());
	}
}