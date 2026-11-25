package xyz.pixelatedw.mineminenomi.data.functions;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

public class SetItemColorFunction extends LootItemConditionalFunction {
	// Fields
	public static final MapCodec<SetItemColorFunction> CODEC;
	private int color;

	protected SetItemColorFunction(List<LootItemCondition> var1, int var2) {
		super(var1);
		this.color = var2;
	}
	public ItemStack run(ItemStack var1, LootContext var2) {
		if (var1.has(DataComponents.DYED_COLOR)){
			var1.set(DataComponents.DYED_COLOR,(Object)(new DyedItemColor(this.color,false)));
		}else{
			if (var1.has(ModDataComponents.MULTI_CHANNEL_DYES)){
				int[] var3 = new int[]{this.color};
				var1.set(ModDataComponents.MULTI_CHANNEL_DYES,(Object)(new MultiChannelItemColor((int[])(var3),false)));
			}else{
				WyDebug.error("Item cannot be colored because it doesn't implement any known color interface!");
			}
		}
		return var1;
	}
	public LootItemFunctionType<SetItemColorFunction> getType() {
		return ((LootItemFunctionType)(ModLootTypes.SET_ITEM_COLOR.get()));
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			Function<SetItemColorFunction, Integer> lv3_1 = (l2_0) -> {
				return Integer.valueOf(l2_0.color);
			};
			return commonFields(l0).and((App)(((com.mojang.serialization.MapCodec)(Codec.INT.fieldOf("color"))).forGetter((Function)(lv3_1)))).apply((Applicative)(l0),(BiFunction)(((BiFunction<List, Integer, SetItemColorFunction>)(SetItemColorFunction::new))));
		};
		CODEC = (MapCodec) (RecordCodecBuilder.mapCodec(var0));
	}

	public static LootItemConditionalFunction.Builder<?> builder(int var0) {
		int var1 = var0;
		final int var1_f1 = var1;
		Function var2 = (Function) ((Function<List, LootItemFunction>) ((l0) -> {
			return new SetItemColorFunction(l0,var1_f1);
		}));
		return (LootItemConditionalFunction.Builder<?>) (LootItemConditionalFunction.Builder) (simpleBuilder(var2));
	}}