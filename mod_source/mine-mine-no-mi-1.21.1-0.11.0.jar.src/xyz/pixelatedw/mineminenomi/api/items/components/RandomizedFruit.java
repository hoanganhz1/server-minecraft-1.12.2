package xyz.pixelatedw.mineminenomi.api.items.components;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamMemberEncoder;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public final record RandomizedFruit(int shape, int baseColor, int stemColor) {
	// Fields
	public static final Codec<RandomizedFruit> CODEC;
	public static final StreamCodec<FriendlyByteBuf, RandomizedFruit> STREAM_CODEC;
	public static final RandomizedFruit DEFAULT;

	private RandomizedFruit(FriendlyByteBuf var1) {
		this(var1.readInt(),var1.readInt(),var1.readInt());
	}
	private void write(FriendlyByteBuf var1) {
		var1.writeInt(this.shape);
		var1.writeInt(this.baseColor);
		var1.writeInt(this.stemColor);
	}
	public static boolean hasBaseColor(ItemStack var0) {
		RandomizedFruit var1 = ((RandomizedFruit)(var0.get(ModDataComponents.RANDOMIZED_FRUIT)));
		if (((var1) != (null)) && ((var1.baseColor) > (0))){
			return true;
		}else{
			return false;
		}
	}
	public static boolean hasStemColor(ItemStack var0) {
		RandomizedFruit var1 = ((RandomizedFruit)(var0.get(ModDataComponents.RANDOMIZED_FRUIT)));
		if (((var1) != (null)) && ((var1.stemColor) > (0))){
			return true;
		}else{
			return false;
		}
	}
	public static int getBaseColor(ItemStack var0) {
		RandomizedFruit var1 = ((RandomizedFruit)(var0.get(ModDataComponents.RANDOMIZED_FRUIT)));
		if ((var1) != (null)){
			return var1.baseColor;
		}else{
			return -1;
		}
	}
	public static int getStemColor(ItemStack var0) {
		RandomizedFruit var1 = ((RandomizedFruit)(var0.get(ModDataComponents.RANDOMIZED_FRUIT)));
		if ((var1) != (null)){
			return var1.stemColor;
		}else{
			return -1;
		}
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			return l0.group((App)(Codec.INT.fieldOf("shape").forGetter((Function)(((Function<RandomizedFruit, Integer>)(RandomizedFruit::shape))))),(App)(Codec.INT.fieldOf("baseColor").forGetter((Function)(((Function<RandomizedFruit, Integer>)(RandomizedFruit::baseColor))))),(App)(Codec.INT.fieldOf("stemColor").forGetter((Function)(((Function<RandomizedFruit, Integer>)(RandomizedFruit::stemColor)))))).apply((Applicative)(l0),(Function3)(((com.mojang.datafixers.util.Function3)(RandomizedFruit::new))));
		};
		CODEC = (Codec) (RecordCodecBuilder.create(var0));
		StreamMemberEncoder var1 = RandomizedFruit::write;
		STREAM_CODEC = (StreamCodec) (StreamCodec.ofMember(var1,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(RandomizedFruit::new)))));
		DEFAULT = new RandomizedFruit(-1,-1,-1);
	}
}