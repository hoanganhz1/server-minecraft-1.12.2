package xyz.pixelatedw.mineminenomi.api.helpers;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.awt.Color;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;

public class CodecHelpers {
	// Fields
	public static final StreamCodec<ByteBuf, Vec3> VEC3;
	public static final Codec<Color> COLOR;

	static  {
		Function<Vec3, Double> var0 = (l0) -> {
			return Double.valueOf(l0.x);
		};
		Function<Vec3, Double> var1 = (l0) -> {
			return Double.valueOf(l0.y);
		};
		Function<Vec3, Double> var2 = (l0) -> {
			return Double.valueOf(l0.z);
		};
		VEC3 = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.DOUBLE,var0,ByteBufCodecs.DOUBLE,var1,ByteBufCodecs.DOUBLE,var2,(Function3)(((com.mojang.datafixers.util.Function3)(Vec3::new)))));
		Function<RecordCodecBuilder.Instance, App> var3 = (l0) -> {
			BiFunction<Integer, Integer, Color> lv5_1 = (Integer l2_0, Integer l2_1) -> {
				int lv4_2 = (((l2_0.intValue()) & (16777215)) << (8)) | ((l2_1.intValue()) & (255));
				return new Color(lv4_2,true);
			};
			return l0.group((App)(Codec.INT.fieldOf("rgb").forGetter((Function)(((Function<Color, Integer>)(Color::getRGB))))),(App)(Codec.INT.fieldOf("alpha").forGetter((Function)(((Function<Color, Integer>)(Color::getAlpha)))))).apply((Applicative)(l0),lv5_1);
		};
		COLOR = (Codec) (RecordCodecBuilder.create(var3));
	}
}