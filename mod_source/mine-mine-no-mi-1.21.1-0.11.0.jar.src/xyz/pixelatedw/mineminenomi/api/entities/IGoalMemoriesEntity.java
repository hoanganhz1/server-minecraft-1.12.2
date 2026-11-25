package xyz.pixelatedw.mineminenomi.api.entities;

import java.util.Optional;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public interface IGoalMemoriesEntity {
	public abstract GoalMemories getGoalMemories();
	public default <U> Optional<U> getMemory(MemoryModuleType<U> var1) {
		return (Optional<U>) (Optional) (this.getGoalMemories().getMemory((MemoryModuleType)(var1)));
	}
	public default boolean hasMemoryValue(MemoryModuleType<?> var1) {
		return this.getGoalMemories().hasMemoryValue(var1);
	}
	public default <U> void eraseMemory(MemoryModuleType<U> var1) {
		this.getGoalMemories().eraseMemory((MemoryModuleType)(var1));
	}
	public default <U> void setMemory(MemoryModuleType<U> var1, U var2) {
		this.getGoalMemories().setMemory((MemoryModuleType)(var1),(Object)(var2));
	}
	public default <U> void setMemoryWithExpiry(MemoryModuleType<U> var1, U var2, long var3) {
		this.getGoalMemories().setMemoryWithExpiry((MemoryModuleType)(var1),(Object)(var2),var3);
	}
}