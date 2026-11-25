package xyz.pixelatedw.mineminenomi.world.features;

import com.mojang.serialization.Codec;
import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import xyz.pixelatedw.mineminenomi.world.features.configs.SizedBlockStateFeatureConfig;

public class BoulderFeature extends Feature<SizedBlockStateFeatureConfig> {
	public BoulderFeature() {
		super(SizedBlockStateFeatureConfig.CODEC);
	}
	public boolean place(FeaturePlaceContext<SizedBlockStateFeatureConfig> var1) {
		BlockPos var2 = var1.origin();
		WorldGenLevel var3 = var1.level();
		do{
			if ((var2.getY()) > (3)){
				if (var3.isEmptyBlock(var2.below())){

				}else{
					BlockState var4 = var3.getBlockState(var2.below());
					if ((!(isDirt(var4))) && (!(isStone(var4)))){

					}else{
						break;
					}
				}
				var2 = var2.below();
				continue;
			}else{
				break;
			}
		} while (true);
		if ((var2.getY()) <= (3)){
			return false;
		}else{
			int var4_1 = 0;
			LOOP_1:
			do{
				if ((var4_1) < (3)){
					int var5 = var1.random().nextInt(((SizedBlockStateFeatureConfig)(var1.config())).size);
					int var6 = var1.random().nextInt(((SizedBlockStateFeatureConfig)(var1.config())).size);
					int var7 = var1.random().nextInt(((SizedBlockStateFeatureConfig)(var1.config())).size);
					float var8 = (((float)(((var5) + (var6)) + (var7))) * (0.333F)) + (0.5F);
					Iterator var9 = BlockPos.betweenClosed(var2.offset(-(var5),-(var6),-(var7)),var2.offset(var5,var6,var7)).iterator();
					do{
						if (var9.hasNext()){
							BlockPos var10 = ((BlockPos)(var9.next()));
							if ((var10.distSqr((Vec3i)(var2))) <= ((double)((var8) * (var8)))){
								var3.setBlock(var10,((SizedBlockStateFeatureConfig)(var1.config())).state,4);
								continue;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
					var2 = var2.offset((-1) + (var1.random().nextInt(2)),-(var1.random().nextInt(2)),(-1) + (var1.random().nextInt(2)));
					var4_1++;
					continue LOOP_1;
				}else{
					break;
				}
			} while (true);
			return true;
		}
	}
}