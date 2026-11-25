package xyz.pixelatedw.mineminenomi.data.functions;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

public class FruitAlreadyExistsFunction extends LootItemConditionalFunction {
	// Fields
	public static final MapCodec<FruitAlreadyExistsFunction> CODEC;

	protected FruitAlreadyExistsFunction(List<LootItemCondition> var1) {
		super(var1);
	}
	protected ItemStack run(ItemStack var1, LootContext var2) {
		if (!(ServerConfig.hasOneFruitPerWorldSimpleLogic())){
			return var1;
		}else{
			if (var1.isEmpty()){
				return var1;
			}else{
				OneFruitWorldData var3 = OneFruitWorldData.get();
				if (var3.isFruitAvailable(BuiltInRegistries.ITEM.getKey(var1.getItem()))){
					return var1;
				}else{
					return new ItemStack((ItemLike)(Items.AIR));
				}
			}
		}
	}
	public LootItemFunctionType<FruitAlreadyExistsFunction> getType() {
		return ((LootItemFunctionType)(ModLootTypes.FRUIT_ALREADY_EXISTS.get()));
	}
	public static LootItemConditionalFunction.Builder<?> builder() {
		return (LootItemConditionalFunction.Builder<?>) (LootItemConditionalFunction.Builder) (simpleBuilder((Function)(((Function<List, LootItemFunction>)(FruitAlreadyExistsFunction::new)))));
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			return commonFields(l0).apply((Applicative)(l0),(Function)(((Function<List, FruitAlreadyExistsFunction>)(FruitAlreadyExistsFunction::new))));
		};
		CODEC = (MapCodec) (RecordCodecBuilder.mapCodec(var0));
	}
}