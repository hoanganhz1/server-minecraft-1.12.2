package xyz.pixelatedw.mineminenomi.data.functions;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class AncientManualFunction extends LootItemConditionalFunction {
	// Fields
	public static final MapCodec<AncientManualFunction> CODEC;

	protected AncientManualFunction(List<LootItemCondition> var1) {
		super(var1);
	}
	protected ItemStack run(ItemStack var1, LootContext var2) {
		var1.set(ModDataComponents.IS_ANCIENT_MANUAL,(Object)(Boolean.valueOf(true)));
		var1.set(DataComponents.CUSTOM_NAME,(Object)(ModI18n.ITEM_ANCIENT_MANUAL));
		var1.setCount(1);
		return var1;
	}
	public LootItemFunctionType<AncientManualFunction> getType() {
		return ((LootItemFunctionType)(ModLootTypes.ANCIENT_MANUAL.get()));
	}
	public static LootItemConditionalFunction.Builder<?> builder() {
		return (LootItemConditionalFunction.Builder<?>) (LootItemConditionalFunction.Builder) (simpleBuilder((Function)(((Function<List, LootItemFunction>)(AncientManualFunction::new)))));
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			return commonFields(l0).apply((Applicative)(l0),(Function)(((Function<List, AncientManualFunction>)(AncientManualFunction::new))));
		};
		CODEC = (MapCodec) (RecordCodecBuilder.mapCodec(var0));
	}
}