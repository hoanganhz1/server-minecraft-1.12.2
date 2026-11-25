package xyz.pixelatedw.mineminenomi.api.challenges;

import java.util.List;
import java.util.concurrent.Executor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;

public class ChallengeServerLevel extends ServerLevel {
	public ChallengeServerLevel(MinecraftServer var1, Executor var2, LevelStorageSource.LevelStorageAccess var3, ServerLevelData var4, ResourceKey<Level> var5, LevelStem var6, ChunkProgressListener var7, boolean var8, long var9, List<CustomSpawner> var10, boolean var11, RandomSequences var12) {
		super(var1,var2,var3,var4,var5,var6,var7,var8,var9,var10,var11,var12);
	}
}