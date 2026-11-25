package xyz.pixelatedw.mineminenomi.world.features;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SnowFloorFeature extends Feature<NoneFeatureConfiguration> {
	public SnowFloorFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
		BlockPos var2 = var1.origin();
		WorldGenLevel var3 = var1.level();
		BlockPos.MutableBlockPos var4 = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos var5 = new BlockPos.MutableBlockPos();
		int var6 = 0;
		LOOP_1:
		do{
			if ((var6) < (16)){
				int var7 = 0;
				do{
					if ((var7) < (16)){
						int var8 = (var2.getX()) + (var6);
						int var9 = (var2.getZ()) + (var7);
						var4.set(var8,var3.getHeight(Heightmap.Types.MOTION_BLOCKING,var8,var9),var9);
						var5.set((Vec3i)(var4)).move(Direction.DOWN,1);
						Holder var10 = var3.getBiome((BlockPos)(var4));
						Biome var11 = ((Biome)(((Registry)(((Holder.Reference)(var3.registryAccess().holder(Registries.BIOME).get())).value())).get(var10.getKey())));
						if (var11.shouldFreeze((LevelReader)(var3),(BlockPos)(var5),false)){
							var3.setBlock((BlockPos)(var5),Blocks.ICE.defaultBlockState(),2);
						}
						if (var11.shouldSnow((LevelReader)(var3),(BlockPos)(var4))){
							var3.setBlock((BlockPos)(var4),Blocks.SNOW_BLOCK.defaultBlockState(),2);
							var3.setBlock(var4.above(),Blocks.SNOW.defaultBlockState(),2);
							BlockState var12 = var3.getBlockState((BlockPos)(var5));
							if (var12.hasProperty((Property)(SnowyDirtBlock.SNOWY))){
								var3.setBlock((BlockPos)(var5),((BlockState)(var12.setValue((Property)(SnowyDirtBlock.SNOWY),(Comparable)(Boolean.valueOf(true))))),2);
								var7++;
								continue;
							}else{
								var7++;
								continue;
							}
						}else{
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
		return true;
	}
}