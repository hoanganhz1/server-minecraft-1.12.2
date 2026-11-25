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
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public final record BusterCallData(boolean inUse, int cooldown, BlockPos pos) {
	// Fields
	public static final Codec<BusterCallData> CODEC;

	public static void setInUse(ItemStack var0, int var1, BlockPos var2) {
		var0.set(ModDataComponents.BUSTER_CALL,new BusterCallData(true,var1,var2));
	}
	public void setCooldown(ItemStack var1, int var2) {
		var1.set(ModDataComponents.BUSTER_CALL,new BusterCallData(this.inUse,var2,this.pos));
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			Function<BusterCallData, Boolean> lv4_1 = (l2_0) -> {
				return Boolean.valueOf(l2_0.inUse);
			};
			Function<BusterCallData, Integer> lv4_2 = (l2_0) -> {
				return Integer.valueOf(l2_0.cooldown);
			};
			Function<BusterCallData, BlockPos> lv4_3 = (l2_0) -> {
				return l2_0.pos;
			};
			return l0.group((App)(((com.mojang.serialization.MapCodec)(Codec.BOOL.fieldOf("inUse"))).forGetter((Function)(lv4_1))),(App)(((com.mojang.serialization.MapCodec)(Codec.INT.fieldOf("cooldown"))).forGetter((Function)(lv4_2))),(App)(((com.mojang.serialization.MapCodec)(BlockPos.CODEC.fieldOf("pos"))).forGetter((Function)(lv4_3)))).apply((Applicative)(l0),(Function3)(((com.mojang.datafixers.util.Function3)(BusterCallData::new))));
		};
		CODEC = (Codec) (RecordCodecBuilder.create(var0));
	}
}