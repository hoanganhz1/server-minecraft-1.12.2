package xyz.pixelatedw.mineminenomi.api.abilities;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;

public enum WeatherBallKind {
	// Fields
	HEAT((byte)(1)),
	COOL((byte)(2)),
	THUNDER((byte)(3));
	public static final Codec<WeatherBallKind[]> CODEC;
	private byte kind;

	private WeatherBallKind(byte var3) {
		this.kind = var3;
	}
	public byte getKind() {
		return this.kind;
	}
	@Nullable()
	public static @Nullable() WeatherBallKind from(byte var0) {
		switch (var0){
		case 1:

		case 2:

		case 3:

		default:
			return null;
		}
	}
	public String toString() {
		String var1 = this.name().toLowerCase();
		String var2 = "ability.mineminenomi." + var1 + "_ball";
		return Component.translatable(var2).getString();
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			Function<WeatherBallKind[], Byte> lv5_1 = (l2_0) -> {
				return Byte.valueOf(l2_0[0].getKind());
			};
			Function<WeatherBallKind[], Byte> lv5_2 = (l2_0) -> {
				return Byte.valueOf(l2_0[1].getKind());
			};
			Function<WeatherBallKind[], Byte> lv5_3 = (l2_0) -> {
				return Byte.valueOf(l2_0[2].getKind());
			};
			Function3 lv5_4 = (l2_0$sam, l2_1$sam, l2_2$sam) -> {
				Byte l2_0 = ((Byte)(l2_0$sam));
				Byte l2_1 = ((Byte)(l2_1$sam));
				Byte l2_2 = ((Byte)(l2_2$sam));

				WeatherBallKind lv4_3 = from(l2_0.byteValue());
				WeatherBallKind lv4_4 = from(l2_1.byteValue());
				WeatherBallKind lv4_5 = from(l2_2.byteValue());
				WeatherBallKind[] lv4_6 = new WeatherBallKind[]{lv4_3,lv4_4,lv4_5};
				return lv4_6;
			};
			return l0.group((App)(((com.mojang.serialization.MapCodec)(Codec.BYTE.fieldOf("charge1"))).forGetter((Function)(lv5_1))),(App)(((com.mojang.serialization.MapCodec)(Codec.BYTE.fieldOf("charge1"))).forGetter((Function)(lv5_2))),(App)(((com.mojang.serialization.MapCodec)(Codec.BYTE.fieldOf("charge1"))).forGetter((Function)(lv5_3)))).apply((Applicative)(l0),lv5_4);
		};
		CODEC = (Codec) (RecordCodecBuilder.create(var0));
	}
}