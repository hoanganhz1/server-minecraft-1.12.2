package xyz.pixelatedw.mineminenomi.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class LargeLakesFeature extends Feature<SimpleBlockConfiguration> {
	public LargeLakesFeature() {
		super(SimpleBlockConfiguration.CODEC);
	}
	public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> var1) {
		BlockPos var2 = var1.origin();
		WorldGenLevel var3 = var1.level();
		do{
			if (((var2.getY()) > (9)) && (var3.isEmptyBlock(var2))){
				var2 = var2.below();
				continue;
			}else{
				break;
			}
		} while (true);
		if ((var2.getY()) <= (8)){
			return false;
		}else{
			int var4 = var2.getX();
			int var5 = var2.getY();
			int var6 = var2.getZ();
			int var7 = (4) + (var1.random().nextInt(4));
			int var8 = var7;
			int var9 = Math.max(1,(var7) / (2));
			BlockPos.MutableBlockPos var10 = new BlockPos.MutableBlockPos();
			double var11 = (double)((var5) - (var9));
			LOOP_2:
			do{
				if ((var11) < ((double)((var5) + (var9)))){
					double var12 = (double)((var4) - (var8));
					LOOP_1:
					do{
						if ((var12) < ((double)((var4) + (var8)))){
							double var13 = (double)((var6) - (var8));
							do{
								if ((var13) < ((double)((var6) + (var8)))){
									if (((((((double)(var4)) - (var12)) * (((double)(var4)) - (var12))) + ((((double)(var6)) - (var13)) * (((double)(var6)) - (var13)))) + ((((double)(var5)) - (var11)) * (((double)(var5)) - (var11)))) < ((double)((var8) * (var9)))){
										var10.set(var12,var11,var13);
										if ((var10.getY()) <= (var2.getY())){
											var3.setBlock((BlockPos)(var10),((SimpleBlockConfiguration)(var1.config())).toPlace().getState(var1.random(),var2),2);
										}
									}
									var13 = (var13) + (1D);
									continue;
								}else{
									break;
								}
							} while (true);
							var12 = (var12) + (1D);
							continue LOOP_1;
						}else{
							break;
						}
					} while (true);
					var11 = (var11) + (1D);
					continue LOOP_2;
				}else{
					break;
				}
			} while (true);
			return true;
		}
	}
}