package xyz.pixelatedw.mineminenomi.init;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;

public class ModMemoryModuleTypes {
	// Fields
	public static final Supplier<MemoryModuleType<List<AbstractDugongEntity>>> NEARBY_ADULT_DUGONGS = ModRegistry.registerMemoryModule("nearby_adult_dugongs");
	public static final Supplier<MemoryModuleType<GlobalPos>> LAST_EXPLOSION_HEARD = ModRegistry.registerMemoryModule("last_explosion_heard",GlobalPos.CODEC);

	public static void init() {
	}
}