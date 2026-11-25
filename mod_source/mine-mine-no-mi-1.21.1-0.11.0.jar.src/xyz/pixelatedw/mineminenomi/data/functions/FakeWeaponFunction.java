package xyz.pixelatedw.mineminenomi.data.functions;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

public class FakeWeaponFunction extends LootItemConditionalFunction {
	// Fields
	public static final MapCodec<FakeWeaponFunction> CODEC;

	protected FakeWeaponFunction(List<LootItemCondition> var1) {
		super(var1);
	}
	protected ItemStack run(ItemStack var1, LootContext var2) {
		var1.set(ModDataComponents.IS_CLONE,Boolean.valueOf(true));
		return var1;
	}
	public LootItemFunctionType<FakeWeaponFunction> getType() {
		return ((LootItemFunctionType)(ModLootTypes.FAKE_WEAPON.get()));
	}
	public static LootItemConditionalFunction.Builder<?> builder() {
		return (LootItemConditionalFunction.Builder<?>) (LootItemConditionalFunction.Builder) (simpleBuilder((Function)(((Function<List, LootItemFunction>)(FakeWeaponFunction::new)))));
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			return commonFields(l0).apply((Applicative)(l0),(Function)(((Function<List, FakeWeaponFunction>)(FakeWeaponFunction::new))));
		};
		CODEC = (MapCodec) (RecordCodecBuilder.mapCodec(var0));
	}
}