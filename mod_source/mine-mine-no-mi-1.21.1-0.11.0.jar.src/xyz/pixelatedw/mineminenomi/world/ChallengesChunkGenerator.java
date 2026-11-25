package xyz.pixelatedw.mineminenomi.world;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

public class ChallengesChunkGenerator extends ChunkGenerator {
	// Fields
	public static final MapCodec<ChallengesChunkGenerator> CODEC;

	public ChallengesChunkGenerator(Holder.Reference<Biome> var1) {
		super((BiomeSource)(new FixedBiomeSource(var1)));
	}
	protected MapCodec<? extends ChunkGenerator> codec() {
		return (MapCodec<? extends ChunkGenerator>) (MapCodec) (CODEC);
	}
	public CompletableFuture<ChunkAccess> fillFromNoise(Blender var1, RandomState var2, StructureManager var3, ChunkAccess var4) {
		return (CompletableFuture<ChunkAccess>) (CompletableFuture) (CompletableFuture.completedFuture(var4));
	}
	public int getBaseHeight(int var1, int var2, Heightmap.Types var3, LevelHeightAccessor var4, RandomState var5) {
		return 0;
	}
	public NoiseColumn getBaseColumn(int var1, int var2, LevelHeightAccessor var3, RandomState var4) {
		return new NoiseColumn(0,new BlockState[0]);
	}
	public void applyCarvers(WorldGenRegion var1, long var2, RandomState var3, BiomeManager var4, StructureManager var5, ChunkAccess var6, GenerationStep.Carving var7) {
	}
	public void buildSurface(WorldGenRegion var1, StructureManager var2, RandomState var3, ChunkAccess var4) {
	}
	public void spawnOriginalMobs(WorldGenRegion var1) {
	}
	public int getGenDepth() {
		return 384;
	}
	public int getSeaLevel() {
		return 0;
	}
	public int getMinY() {
		return 0;
	}
	public void addDebugScreenInfo(List<String> var1, RandomState var2, BlockPos var3) {
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			Function<Holder.Reference, ChallengesChunkGenerator> lv1_1 = ChallengesChunkGenerator::new;
			return l0.group((App)(RegistryOps.retrieveElement(Biomes.THE_VOID))).apply((Applicative)(l0),l0.stable(lv1_1));
		};
		CODEC = (MapCodec) (RecordCodecBuilder.mapCodec(var0));
	}
}