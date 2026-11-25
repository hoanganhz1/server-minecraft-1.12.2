package xyz.pixelatedw.mineminenomi.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class PoneglyphFeature extends Feature<NoneFeatureConfiguration> {
	public PoneglyphFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
		BlockPos var2 = var1.origin();
		if (((((var1.level().getRandom().nextDouble()) * (100D)) < (ServerConfig.getChanceForPoneglyphSpawn())) ? (1) : (0)) == (0)){
			return false;
		}else{
			int var6 = 0;
			int var7 = 0;
			int var3 = (2) + (var1.random().nextInt(3));
			int var4 = 0;
			int var5 = 0;
			LOOP_2:
			do{
				if ((var5) < (var3)){
					var6 = 0;
					LOOP_1:
					do{
						if ((var6) < (var3)){
							var7 = 0;
							do{
								if ((var7) < (var3)){
									BlockPos var8 = var2.offset(var5,var6,var7);
									if (!(isStone(var1.level().getBlockState(var8)))){
										return false;
									}else{
										var4++;
										var7++;
										continue;
									}
								}else{
									break;
								}
							} while (true);
							var6++;
							continue LOOP_1;
						}else{
							break;
						}
					} while (true);
					var5++;
					continue LOOP_2;
				}else{
					break;
				}
			} while (true);
			if ((var4) >= (((var3) * (var3)) * (var3))){
				var5 = 0;
				LOOP_4:
				do{
					if ((var5) < (var3)){
						var6 = 0;
						LOOP_3:
						do{
							if ((var6) < (var3)){
								var7 = 0;
								do{
									if ((var7) < (var3)){
										BlockPos var8_1 = var2.offset(var5,var6,var7);
										var1.level().setBlock(var8_1,((Block)(ModBlocks.PONEGLYPH.get())).defaultBlockState(),2);
										var7++;
										continue;
									}else{
										break;
									}
								} while (true);
								var6++;
								continue LOOP_3;
							}else{
								break;
							}
						} while (true);
						var5++;
						continue LOOP_4;
					}else{
						break;
					}
				} while (true);
				return true;
			}else{
				return false;
			}
		}
	}
}