package xyz.pixelatedw.mineminenomi.api.items.components;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamMemberEncoder;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public final record MultiChannelItemColor(int[] colors, boolean showInTooltip) {
	// Fields
	private static final Codec<MultiChannelItemColor> FULL_CODEC;
	public static final Codec<MultiChannelItemColor> CODEC;
	public static final StreamCodec<FriendlyByteBuf, MultiChannelItemColor> STREAM_CODEC;
	public static final MultiChannelItemColor WEAPON_COLOR;
	public static final MultiChannelItemColor CLOTH_COLOR;

	public MultiChannelItemColor(int... var1) {
		this((int[])(var1),false);
	}
	private MultiChannelItemColor(FriendlyByteBuf var1) {
		this((int[])(var1.readVarIntArray()),var1.readBoolean());
	}
	private void write(FriendlyByteBuf var1) {
		var1.writeVarIntArray(this.colors);
		var1.writeBoolean(this.showInTooltip);
	}
	public int getMaxLayers() {
		return this.colors.length;
	}
	public int getLayerColor(int var1) {
		if (((this.colors.length) > (0)) && ((var1) < (this.colors.length))){
			int var2 = this.colors[var1];
			if ((((((var2) >>> (24)) & (255)) == (0)) ? (1) : (0)) != (0)){
				var2 = FastColor.ARGB32.opaque(var2);
			}
			return var2;
		}else{
			return -1;
		}
	}
	public boolean hasLayerColor(int var1) {
		if (((this.colors.length) > (0)) && ((var1) < (this.colors.length))){
			return true;
		}else{
			return false;
		}
	}
	public static boolean hasLayerColor(ItemStack var0, int var1) {
		MultiChannelItemColor var2 = ((MultiChannelItemColor)(var0.get(ModDataComponents.MULTI_CHANNEL_DYES)));
		if (((var2) != (null)) && (((var2.colors.length) > (0)) && ((var1) < (var2.colors.length)))){
			return true;
		}else{
			return false;
		}
	}
	public static void setLayerColor(ItemStack var0, int var1, int var2) {
		MultiChannelItemColor var3 = ((MultiChannelItemColor)(var0.get(ModDataComponents.MULTI_CHANNEL_DYES)));
		if (((var3) != (null)) && (((var3.colors.length) > (0)) && ((var1) < (var3.colors.length)))){
			int[] var4 = Arrays.copyOf(var3.colors,var3.colors.length);
			var4[var1] = FastColor.ARGB32.opaque(var2);
			var0.set(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor((int[])(var4),var3.showInTooltip()));
		}
	}
	public static void dyeArmor(ItemStack var0, int var1, int var2) {
		if ((((MultiChannelItemColor)(var0.get(ModDataComponents.MULTI_CHANNEL_DYES)))) != (null)){
			setLayerColor(var0,var1,var2);
		}
	}
	public static ItemStack dyeArmor(ItemStack var0, int var1, List<DyeItem> var2) {
		int var12 = 0;
		int var13 = 0;
		ItemStack var3 = ItemStack.EMPTY;
		int var4 = 0;
		int var5 = 0;
		int var6 = 0;
		int var7 = 0;
		int var8 = 0;
		MultiChannelItemColor var9 = ((MultiChannelItemColor)(var0.get(ModDataComponents.MULTI_CHANNEL_DYES)));
		if ((var9) != (null)){
			var3 = var0.copyWithCount(1);
			if (hasLayerColor(var0,var1)){
				int var10 = var9.colors[var1];
				int var11 = FastColor.ARGB32.red(var10);
				var12 = FastColor.ARGB32.green(var10);
				var13 = FastColor.ARGB32.blue(var10);
				var7 = (var7) + (Math.max(var11,Math.max(var12,var13)));
				var4 = (var4) + (var11);
				var5 = (var5) + (var12);
				var6 = (var6) + (var13);
				var8++;
			}
			Iterator<DyeItem> var10_1 = var2.iterator();
			do{
				if (var10_1.hasNext()){
					var12 = ((DyeItem)(var10_1.next())).getDyeColor().getTextureDiffuseColor();
					var13 = FastColor.ARGB32.red(var12);
					int var13_1 = FastColor.ARGB32.green(var12);
					int var14_1 = FastColor.ARGB32.blue(var12);
					var7 = (var7) + (Math.max(var13,Math.max(var13_1,var14_1)));
					var4 = (var4) + (var13);
					var5 = (var5) + (var13_1);
					var6 = (var6) + (var14_1);
					var8++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		int var10_2 = (var4) / (var8);
		int var11_1 = (var5) / (var8);
		var12 = (var6) / (var8);
		float var13_1_1 = ((float)(var7)) / ((float)(var8));
		float var14 = (float)(Math.max(var10_2,Math.max(var11_1,var12)));
		var10_2 = (int)((((float)(var10_2)) * (var13_1_1)) / (var14));
		var11_1 = (int)((((float)(var11_1)) * (var13_1_1)) / (var14));
		var12 = (int)((((float)(var12)) * (var13_1_1)) / (var14));
		int var14_1 = FastColor.ARGB32.color(0,var10_2,var11_1,var12);
		setLayerColor(var3,var1,var14_1);
		return var3;
	}
	public void addToTooltip(Item.TooltipContext var1, Consumer<Component> var2, TooltipFlag var3) {
		if (this.showInTooltip){

		}
	}
	public MultiChannelItemColor withTooltip(boolean var1) {
		return new MultiChannelItemColor((int[])(this.colors),var1);
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			Function<IntStream, int[]> lv1_1 = IntStream::toArray;
			return l0.group((App)(((com.mojang.serialization.codecs.PrimitiveCodec)(Codec.INT_STREAM)).xmap((Function)(lv1_1),(Function)(((java.util.function.Function)(((Function<int[], IntStream>)(Arrays::stream)))))).fieldOf("colors").forGetter((Function)(((Function<MultiChannelItemColor, int[]>)(MultiChannelItemColor::colors))))),(App)(Codec.BOOL.optionalFieldOf("show_in_tooltip",Boolean.valueOf(true)).forGetter((Function)(((Function<MultiChannelItemColor, Boolean>)(MultiChannelItemColor::showInTooltip)))))).apply((Applicative)(l0),(BiFunction)(((BiFunction<int[], Boolean, MultiChannelItemColor>)(MultiChannelItemColor::new))));
		};
		FULL_CODEC = (Codec) (RecordCodecBuilder.create(var0));
		Function<IntStream, int[]> var1 = IntStream::toArray;
		Function<int[], MultiChannelItemColor> var2 = (l0) -> {
			return new MultiChannelItemColor((int[])(l0),true);
		};
		CODEC = (Codec) (Codec.withAlternative(FULL_CODEC,Codec.INT_STREAM.xmap(var1,(Function)(((Function<int[], IntStream>)(Arrays::stream)))),var2));
		StreamMemberEncoder var3 = MultiChannelItemColor::write;
		STREAM_CODEC = (StreamCodec) (StreamCodec.ofMember(var3,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(MultiChannelItemColor::new)))));
		int[] var4 = new int[]{ItemsHelper.GENERIC_HANDLE_COLOR.getRGB()};
		WEAPON_COLOR = new MultiChannelItemColor(var4);
		int[] var5 = new int[]{-3092272};
		CLOTH_COLOR = new MultiChannelItemColor(var5);
	}
}