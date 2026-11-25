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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class SetFruitClueFunction extends LootItemConditionalFunction {
	// Fields
	public static final MapCodec<SetFruitClueFunction> CODEC;

	protected SetFruitClueFunction(List<LootItemCondition> var1) {
		super(var1);
	}
	protected ItemStack run(ItemStack var1, LootContext var2) {
		DFEncyclopediaEntry var3 = DFEncyclopediaEntry.getClue((Level)(var2.getLevel()));
		if ((var3) == (null)){
			return ItemStack.EMPTY;
		}else{
			var1.set(ModDataComponents.FRUIT_CLUE,(Object)(var3));
			var1.set(DataComponents.CUSTOM_NAME,(Object)(ModI18n.ITEM_FRUIT_CLUE));
			var1.setCount(1);
			return var1;
		}
	}
	public LootItemFunctionType<SetFruitClueFunction> getType() {
		return ((LootItemFunctionType)(ModLootTypes.FRUIT_CLUE.get()));
	}
	public static LootItemConditionalFunction.Builder<?> builder() {
		return (LootItemConditionalFunction.Builder<?>) (LootItemConditionalFunction.Builder) (simpleBuilder((Function)(((Function<List, LootItemFunction>)(SetFruitClueFunction::new)))));
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			return commonFields(l0).apply((Applicative)(l0),(Function)(((Function<List, SetFruitClueFunction>)(SetFruitClueFunction::new))));
		};
		CODEC = (MapCodec) (RecordCodecBuilder.mapCodec(var0));
	}
}