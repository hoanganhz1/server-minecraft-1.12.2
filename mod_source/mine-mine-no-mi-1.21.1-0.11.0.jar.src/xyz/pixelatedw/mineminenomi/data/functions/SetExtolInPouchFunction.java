package xyz.pixelatedw.mineminenomi.data.functions;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

public class SetExtolInPouchFunction extends LootItemConditionalFunction {
	// Fields
	public static final MapCodec<SetExtolInPouchFunction> CODEC;
	private NumberProvider range;

	protected SetExtolInPouchFunction(List<LootItemCondition> var1, NumberProvider var2) {
		super(var1);
		this.range = var2;
	}
	protected ItemStack run(ItemStack var1, LootContext var2) {
		var1.set(ModDataComponents.EXTOL,Long.valueOf((long)(this.range.getInt(var2))));
		return var1;
	}
	public LootItemFunctionType<SetExtolInPouchFunction> getType() {
		return ((LootItemFunctionType)(ModLootTypes.SET_EXTOL_IN_POUCH.get()));
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			Function<SetExtolInPouchFunction, NumberProvider> lv3_1 = (l2_0) -> {
				return l2_0.range;
			};
			return commonFields(l0).and((App)(((com.mojang.serialization.MapCodec)(NumberProviders.CODEC.fieldOf("extol_range"))).forGetter((Function)(lv3_1)))).apply((Applicative)(l0),(BiFunction)(((BiFunction<List, NumberProvider, SetExtolInPouchFunction>)(SetExtolInPouchFunction::new))));
		};
		CODEC = (MapCodec) (RecordCodecBuilder.mapCodec(var0));
	}

	public static LootItemConditionalFunction.Builder<?> builder(NumberProvider var0) {
		NumberProvider var1 = var0;
		final NumberProvider var1_f1 = var1;
		Function var2 = (Function) ((Function<List, LootItemFunction>) ((l0) -> {
			return new SetExtolInPouchFunction(l0,var1_f1);
		}));
		return (LootItemConditionalFunction.Builder<?>) (LootItemConditionalFunction.Builder) (simpleBuilder(var2));
	}}