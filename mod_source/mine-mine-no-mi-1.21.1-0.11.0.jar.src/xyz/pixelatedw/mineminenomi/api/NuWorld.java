package xyz.pixelatedw.mineminenomi.api;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.FullChunkStatus;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea$RestorationEntry;
import xyz.pixelatedw.mineminenomi.api.util.DBlockPos;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;

public class NuWorld {
	public static boolean canMobGrief(@Nullable() Entity var0) {
		if ((var0) == (null)){
			return true;
		}else{
			if (var0 instanceof Mob){
				if ((!(isChallengeDimension(var0.level()))) && (!(EventHooks.canEntityGrief(var0.level(),var0)))){
					return false;
				}else{
					return true;
				}
			}else{
				if (!(var0 instanceof Player)){
					return EventHooks.canEntityGrief(var0.level(),var0);
				}else{
					return true;
				}
			}
		}
	}
	public static boolean setBlockState(@Nullable() Entity var0, BlockPos var1, BlockState var2, int var3, @Nullable() BlockProtectionRule var4) {
		if (((var0) != (null)) && (canMobGrief(var0))){
			return setBlockState(var0.level(),var1,var2,var3,var4);
		}else{
			return false;
		}
	}
	public static boolean setBlockState(Level var0, BlockPos var1, BlockState var2, int var3, @Nullable() BlockProtectionRule var4) {
		if (var0.isOutsideBuildHeight(var1)){
			return false;
		}else{
			if ((!(var2.isAir())) && (!(isWithinChallengeArenaBounds(var0,var1)))){
				return false;
			}else{
				BlockState var5 = var0.getBlockState(var1);
				ProtectedAreasData var6 = ProtectedAreasData.get(((ServerLevel)(var0)));
				if ((var6) == (null)){
					return false;
				}else{
					Optional var7 = var6.getProtectedArea(var1.getX(),var1.getY(),var1.getZ());
					int var8 = (!(ServerConfig.isAbilityGriefingEnabled())) ? (1) : (0);
					int var9 = 0;
					int var10 = (!(var5.is(ModTags$Blocks.BLOCK_PROT_RESTRICTED))) ? (1) : (0);
					if ((var4) != (null)){
						var9 = ((var5.isAir()) && (var4.canBypassGriefProtection())) ? (1) : (0);
						var10 = (var10) & (((var4.check(var0,var1,var5)) ? 1 : 0));
					}
					if (isChallengeDimension(var0)){
						var9 = 1;
						var8 = 0;
					}
					if (((var8) != (0)) && ((var9) == (0))){
						return false;
					}else{
						if (var7.isPresent()){
							if (!(((ProtectedArea)(var7.get())).canDestroyBlocks())){
								return false;
							}else{
								if ((((ProtectedArea)(var7.get())).canDestroyBlocks()) && (((ProtectedArea)(var7.get())).canRestoreBlocks())){
									DBlockPos var11 = new DBlockPos(var1.getX(),var1.getY(),var1.getZ(),var0.dimension());
									BlockSnapshot var12 = BlockSnapshot.create(var0.dimension(),(LevelAccessor)(var0),var1,2);
									((ProtectedArea)(var7.get())).queueForRestoration((BlockPos)(var11),new ProtectedArea$RestorationEntry(var0.getGameTime(),(BlockSnapshot)(var12)));
								}
							}
						}
						if ((var10) != (0)){
							setBlockStateInChunk(var0,var1,var2,var3);
							return true;
						}else{
							return false;
						}
					}
				}
			}
		}
	}
	public static boolean setBlockStateInChunk(Level var0, BlockPos var1, BlockState var2, int var3) {
		if ((var0 instanceof ServerLevel) && ((isChallengeDimension(var0)) && (((var3) & (512)) != (0)))){
			try{
				int var4 = ((swapBlockData((LevelAccessor)(var0),var1,var2)) != (null)) ? (1) : (0);
				return (((var4) & 1) != 0);
			}catch(Exception var4_1){
				var4_1.printStackTrace();
			}
		}
		if ((!(var0.isOutsideBuildHeight(var1))) && (!((!(var0.isClientSide)) && (var0.isDebug())))){
			LevelChunk var4_2 = var0.getChunkAt(var1);
			var1 = var1.immutable();
			BlockState var5 = var0.getBlockState(var1);
			int var6 = var5.getLightEmission((BlockGetter)(var0),var1);
			int var7 = var5.getLightBlock((BlockGetter)(var0),var1);
			BlockState var8 = var4_2.setBlockState(var1,var2,((var3) & (64)) != (0));
			if ((var0 instanceof ServerLevel) && ((!(var2.isAir())) && (isChallengeDimension(var0)))){
				ChallengesWorldData.get().getInProgressChallengeFor(((ServerLevel)(var0))).trackBlockPos(var1);
			}
			if ((var8) != (null)){
				BlockState var9 = var0.getBlockState(var1);
				if ((((var3) & (128)) == (0)) && (((var9) != (var8)) && (!(((var9.getLightBlock((BlockGetter)(var0),var1)) == (var7)) && (((var9.getLightEmission((BlockGetter)(var0),var1)) == (var6)) && ((!(var9.useShapeForLightOcclusion())) && (!(var8.useShapeForLightOcclusion())))))))){
					var0.getProfiler().push("queueCheckLight");
					var0.getChunkSource().getLightEngine().checkBlock(var1);
					var0.getProfiler().pop();
				}
				if (((var3) & (256)) != (0)){
					var0.markAndNotifyBlock(var1,var4_2,var8,var2,var3,512);
				}else{
					if (((((var3) & (2)) != (0)) && (!((var0.isClientSide) && (((var3) & (4)) != (0))))) && (!((!(var0.isClientSide)) && (!(((var4_2.getFullStatus()) != (null)) && (var4_2.getFullStatus().isOrAfter(FullChunkStatus.BLOCK_TICKING))))))){
						var0.sendBlockUpdated(var1,var8,var2,var3);
					}else{
						return true;
					}
				}
			}
			return true;
		}else{
			return false;
		}
	}
	@Nullable()
	public static @Nullable() BlockState swapBlockData(LevelAccessor var0, BlockPos var1, BlockState var2) {
		ChunkAccess var3 = var0.getChunk(var1);
		LevelChunkSection var4 = var3.getSection(var3.getSectionIndex(var1.getY()));
		if ((var4.hasOnlyAir()) && (var2.isAir())){
			return null;
		}else{
			int var5 = (var1.getX()) & (15);
			int var6 = (var1.getY()) & (15);
			int var7 = (var1.getZ()) & (15);
			BlockState var8 = ((BlockState)(var4.getStates().getAndSetUnchecked(var5,var6,var7,var2)));
			if ((var8) == (var2)){
				return null;
			}else{
				var3.setUnsaved(true);
				return var8;
			}
		}
	}
	public static boolean isWithinChallengeArenaBounds(Level var0, BlockPos var1) {
		if (!(isChallengeDimension(var0))){
			return true;
		}else{
			if (var0.isClientSide){
				return false;
			}else{
				ServerLevel var2 = ((ServerLevel)(var0));
				InProgressChallenge var3 = ChallengesWorldData.get().getInProgressChallengeFor(var2);
				if ((var3) == (null)){
					return false;
				}else{
					BoundingBox var4 = var3.getArena().getArenaLimits();
					BoundingBox var5 = new BoundingBox((var4.minX()) - (4),(var4.minY()) - (4),(var4.minZ()) - (4),(var4.maxX()) + (3),(var4.maxY()) + (3),(var4.maxZ()) + (3)).moved(var3.getArenaPos().getX(),var3.getArenaPos().getY(),var3.getArenaPos().getZ());
					if (var5.isInside((Vec3i)(var1))){
						return true;
					}else{
						return false;
					}
				}
			}
		}
	}
	public static boolean isChallengeDimension(Level var0) {
		return isChallengeDimension(var0.dimension());
	}
	public static boolean isChallengeDimension(ResourceKey<Level> var0) {
		return var0.location().getPath().contains("challenges_");
	}
}