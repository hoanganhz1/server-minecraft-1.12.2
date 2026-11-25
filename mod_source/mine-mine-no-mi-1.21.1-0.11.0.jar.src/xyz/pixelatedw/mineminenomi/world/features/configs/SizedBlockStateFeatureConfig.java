package xyz.pixelatedw.mineminenomi.world.features.configs;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class SizedBlockStateFeatureConfig implements FeatureConfiguration {
	// Fields
	public static final Codec<SizedBlockStateFeatureConfig> CODEC;
	public final BlockState state;
	public final int size;

	public SizedBlockStateFeatureConfig(BlockState var1, int var2) {
		this.state = var1;
		this.size = var2;
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			Function<SizedBlockStateFeatureConfig, BlockState> lv3_1 = (l2_0) -> {
				return l2_0.state;
			};
			Function<SizedBlockStateFeatureConfig, Integer> lv3_2 = (l2_0) -> {
				return Integer.valueOf(l2_0.size);
			};
			return l0.group((App)(((com.mojang.serialization.MapCodec)(BlockState.CODEC.fieldOf("state"))).forGetter((Function)(lv3_1))),(App)(((com.mojang.serialization.MapCodec)(Codec.intRange(0,10).orElse(Integer.valueOf(2)).fieldOf("size"))).forGetter((Function)(lv3_2)))).apply((Applicative)(l0),(BiFunction)(((BiFunction<BlockState, Integer, SizedBlockStateFeatureConfig>)(SizedBlockStateFeatureConfig::new))));
		};
		CODEC = (Codec) (RecordCodecBuilder.create(var0));
	}
}