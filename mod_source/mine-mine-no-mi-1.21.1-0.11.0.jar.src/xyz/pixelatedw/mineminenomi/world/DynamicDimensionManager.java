package xyz.pixelatedw.mineminenomi.world;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Lifecycle;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.LevelEvent;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeServerLevel;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDynDimensionsPacket;

public class DynamicDimensionManager {
	// Fields
	private static final RegistrationInfo DIMENSION_REGISTRATION_INFO = new RegistrationInfo(Optional.empty(),Lifecycle.stable());
	public static DynamicDimensionManager INSTANCE;
	private ConcurrentLinkedQueue<Map.Entry<ResourceKey<Level>, Consumer<ServerLevel>>> registrationQueue;

	public DynamicDimensionManager() {
		this.registrationQueue = (ConcurrentLinkedQueue) (new ConcurrentLinkedQueue());
	}
	private static ChallengeServerLevel createAndRegisterWorldAndDimension(MinecraftServer var0, ResourceKey<Level> var1, BiFunction<MinecraftServer, ResourceKey<LevelStem>, LevelStem> var2) {
		ServerLevel var3 = var0.getLevel(Level.OVERWORLD);
		ResourceKey var4 = ResourceKey.create(Registries.LEVEL_STEM,var1.location());
		LevelStem var5 = ((LevelStem)(((java.util.function.BiFunction)(var2)).apply((Object)(var0),(Object)(var4))));
		ChunkProgressListener var6 = var0.progressListenerFactory.create(11);
		Executor var7 = var0.executor;
		LevelStorageSource.LevelStorageAccess var8 = var0.storageSource;
		WorldData var9 = var0.getWorldData();
		WorldOptions var10 = var9.worldGenOptions();
		DerivedLevelData var11 = new DerivedLevelData(var9,var9.overworldData());
		Registry var12 = var0.registryAccess().registryOrThrow(Registries.LEVEL_STEM);
		if (var12 instanceof MappedRegistry){
			MappedRegistry var13 = ((MappedRegistry)(var12));
			var13.unfreeze();
			var13.register(var4,var5,DIMENSION_REGISTRATION_INFO);
			ChallengeServerLevel var14 = new ChallengeServerLevel(var0,var7,var8,(ServerLevelData)(var11),var1,var5,var6,false,BiomeManager.obfuscateSeed(var10.seed()),(List)(ImmutableList.of()),false,((RandomSequences)(null)));
			var14.noSave = true;
			var3.getWorldBorder().addListener((BorderChangeListener)(new BorderChangeListener.DelegateBorderChangeListener(var14.getWorldBorder())));
			var0.markWorldsDirty();
			NeoForge.EVENT_BUS.post((Event)(new LevelEvent.Load((LevelAccessor)(var14))));
			ModNetwork.sendToAll(new SSyncDynDimensionsPacket((Set)(ImmutableSet.of((Object)(var1))),(Set)(ImmutableSet.of())));
			return var14;
		}else{
			Object[] var13_1 = new Object[]{var4.location()};
			throw new IllegalStateException((String)(String.format("Unable to register dimension %s -- dimension registry not writable",var13_1)));
		}
	}
	public void queueLevelForRegistration(ResourceKey<Level> var1, Consumer<ServerLevel> var2) {
		this.registrationQueue.add(new AbstractMap.SimpleEntry(var1,var2));
	}
	public void registerQueuedLevels(MinecraftServer var1) {
		RegistryAccess.Frozen var8 = null;
		RegistryAccess.Frozen var2 = var1.registryAccess();
		do{
			if (!(this.registrationQueue.isEmpty())){
				Map.Entry var3 = ((Map.Entry)(this.registrationQueue.poll()));
				ResourceKey var4 = ((ResourceKey)(var3.getKey()));
				Map var5 = var1.forgeGetWorldMap();
				ServerLevel var6 = ((ServerLevel)(var5.get(var4)));
				if ((var6) != (null)){
					((Consumer)(var3.getValue())).accept(var6);
					continue;
				}else{
					ResourceKey var7 = ((ResourceKey)(var3.getKey()));
					var8 = var2;
					final RegistryAccess.Frozen var8_f1 = var8;
					BiFunction<MinecraftServer, ResourceKey, LevelStem> var9 = (l0, l1) -> {
			Holder.Reference lv1_3 = var8_f1.registryOrThrow(Registries.DIMENSION_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DIMENSION_TYPE,ModResources.DIMENSION_TYPE_CHALLENGES));
			Holder.Reference lv1_4 = ((HolderLookup.RegistryLookup)(var8_f1.lookup(Registries.BIOME).get())).getOrThrow(Biomes.THE_VOID);
			ChallengesChunkGenerator lv1_5 = new ChallengesChunkGenerator(lv1_4);
			return new LevelStem((Holder)(lv1_3),(ChunkGenerator)(lv1_5));
		};
					ChallengeServerLevel var10 = createAndRegisterWorldAndDimension(var1,var7,(BiFunction)(var9));
					var5.put(var4,var10);
					((Consumer)(var3.getValue())).accept(var10);
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	static  {
		INSTANCE = new DynamicDimensionManager();
	}
}