package xyz.pixelatedw.mineminenomi.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import xyz.pixelatedw.mineminenomi.api.NuWorld;

public class SnowMountainFeature extends Feature<ProbabilityFeatureConfiguration> {
	public SnowMountainFeature() {
		super(ProbabilityFeatureConfiguration.CODEC);
	}
	public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> var1) {
		BlockPos var2 = var1.origin();
		WorldGenLevel var3 = var1.level();
		if ((var1.random().nextFloat()) >= (((ProbabilityFeatureConfiguration)(var1.config())).probability)){
			return false;
		}else{
			int var4 = var2.getX();
			int var5 = var2.getZ();
			int var6 = var3.getHeight(Heightmap.Types.WORLD_SURFACE_WG,var4,var5);
			int var7 = 1000;
			int var8 = var7;
			BlockPos var9 = new BlockPos(var4,var6,var5);
			BiomeSource var10 = var1.chunkGenerator().getBiomeSource();
			BlockPos.MutableBlockPos var11 = new BlockPos.MutableBlockPos(var4,var6,var5);
			int var12 = 0;
			int var13 = 0;
			int var14 = 7;
			LOOP_1:
			do{
				if ((var14) < (64)){
					int var15 = 7;
					do{
						if ((var15) < (64)){
							var11.set((var4) + (var14),var6,(var5) + (var15));
							int var16 = var3.getHeight(Heightmap.Types.WORLD_SURFACE_WG,var11.getX(),var11.getZ());
							if ((var12) == (0)){
								var12 = var16;
							}
							if ((var13) == (0)){
								var13 = var16;
							}
							var12 = Math.min(var12,var16);
							var13 = Math.max(var13,var16);
							var15 = (var15) + (16);
							continue;
						}else{
							break;
						}
					} while (true);
					var14 = (var14) + (16);
					continue LOOP_1;
				}else{
					break;
				}
			} while (true);
			double var15_1 = (double)(Math.abs((var13) - (var12)));
			var11.set(var4,var6,var5);
			int var16 = 0;
			LOOP_3:
			do{
				if ((var16) < (60)){
					int var17 = (var4) - (var7);
					LOOP_2:
					do{
						if ((var17) <= ((var4) + (var7))){
							int var18 = (var5) - (var7);
							do{
								if ((var18) <= ((var5) + (var7))){
									double var19 = (double)((((var4) - (var17)) * ((var4) - (var17))) + (((var5) - (var18)) * ((var5) - (var18))));
									if (((var16) > (20)) && ((var16) < (40))){
										var8 = 950;
									}
									if (((var16) > (40)) && ((var16) < (60))){
										var8 = 900;
									}
									if ((var19) < ((double)(var8))){
										var11.set((double)(var17),(((double)(var6)) - (var15_1)) + ((double)(var16)),(double)(var18));
										BlockState var20 = var3.getBlockState((BlockPos)(var11));
										Block var21 = var20.getBlock();
										if ((!(var20.isAir())) && ((!(isDirt(var20))) && (((var21) != (Blocks.SNOW_BLOCK)) && (((var21) != (Blocks.SNOW)) && ((var21) != (Blocks.ICE)))))){
											var18++;
											continue;
										}else{
											NuWorld.swapBlockData((LevelAccessor)(var3),(BlockPos)(var11),Blocks.SNOW_BLOCK.defaultBlockState());
											var18++;
											continue;
										}
									}else{
										var18++;
										continue;
									}
								}else{
									break;
								}
							} while (true);
							var17++;
							continue LOOP_2;
						}else{
							break;
						}
					} while (true);
					var16++;
					continue LOOP_3;
				}else{
					break;
				}
			} while (true);
			return true;
		}
	}
}