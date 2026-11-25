package xyz.pixelatedw.mineminenomi.init;

import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.world.ChallengesChunkGenerator;

public class ModDimensions {
	// Fields
	public static final Supplier<MapCodec<ChallengesChunkGenerator>> CHUNK_GENERATOR_CHALLENGES;

	public static void init() {
	}
	static  {
		Supplier<MapCodec> var0 = () -> {
			return ChallengesChunkGenerator.CODEC;
		};
		CHUNK_GENERATOR_CHALLENGES = ModRegistry.registerChunkGenerator("challenges_chunk_generator",var0);
	}
}