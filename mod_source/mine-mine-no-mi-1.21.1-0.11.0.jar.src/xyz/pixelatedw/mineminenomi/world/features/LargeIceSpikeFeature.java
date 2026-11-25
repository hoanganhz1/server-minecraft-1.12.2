package xyz.pixelatedw.mineminenomi.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class LargeIceSpikeFeature extends Feature<ProbabilityFeatureConfiguration> {
	public LargeIceSpikeFeature() {
		super(ProbabilityFeatureConfiguration.CODEC);
	}
	public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> var1) {
		BlockPos var2 = var1.origin();
		WorldGenLevel var3 = var1.level();
		if ((var1.random().nextFloat()) >= (((ProbabilityFeatureConfiguration)(var1.config())).probability)){
			return false;
		}else{
			int var4 = (var2.getX()) + (var1.random().nextInt(64));
			int var5 = (var2.getZ()) + (var1.random().nextInt(64));
			int var6 = var3.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,var4,var5);
			BlockPos var7 = new BlockPos(var4,var6,var5);
			int var8 = (var1.random().nextInt(20)) + (12);
			int var9 = ((var8) / (4)) + (var1.random().nextInt(2));
			int var10 = 0;
			LOOP_2:
			do{
				if ((var10) < (var8)){
					float var11 = ((1F) - (((float)(var10)) / ((float)(var8)))) * ((float)(var9));
					int var12 = Mth.ceil(var11);
					int var13 = -(var12);
					LOOP_1:
					do{
						if ((var13) <= (var12)){
							float var14 = ((float)(Mth.abs(var13))) - (0.25F);
							int var15 = -(var12);
							do{
								if ((var15) <= (var12)){
									float var16 = ((float)(Mth.abs(var15))) - (0.25F);
									if (((((var13) == (0)) && ((var15) == (0))) || (!((((var14) * (var14)) + ((var16) * (var16))) > ((var11) * (var11))))) && ((((var13) != (-(var12))) && (((var13) != (var12)) && (((var15) != (-(var12))) && ((var15) != (var12))))) || (!((var1.random().nextFloat()) > (0.75F))))){
										BlockState var17 = var3.getBlockState(var7.offset(var13,var10,var15));
										Block var18 = var17.getBlock();
										if ((!(var17.isAir())) && ((!(isDirt(var17))) && (((var18) != (Blocks.SNOW_BLOCK)) && ((var18) != (Blocks.ICE))))){

										}else{
											this.setBlock((LevelWriter)(var3),var7.offset(var13,var10,var15),Blocks.PACKED_ICE.defaultBlockState());
										}
										if (((var10) != (0)) && ((var12) > (1))){
											BlockState var19 = var3.getBlockState(var7.offset(var13,-(var10),var15));
											Block var20 = var19.getBlock();
											if ((!(var19.isAir())) && ((!(isDirt(var19))) && (((var20) != (Blocks.SNOW_BLOCK)) && ((var20) != (Blocks.ICE))))){
												var15++;
												continue;
											}else{
												this.setBlock((LevelWriter)(var3),var7.offset(var13,-(var10),var15),Blocks.PACKED_ICE.defaultBlockState());
												var15++;
												continue;
											}
										}else{
											var15++;
											continue;
										}
									}else{
										var15++;
										continue;
									}
								}else{
									break;
								}
							} while (true);
							var13++;
							continue LOOP_1;
						}else{
							break;
						}
					} while (true);
					var10++;
					continue LOOP_2;
				}else{
					break;
				}
			} while (true);
			return true;
		}
	}
}