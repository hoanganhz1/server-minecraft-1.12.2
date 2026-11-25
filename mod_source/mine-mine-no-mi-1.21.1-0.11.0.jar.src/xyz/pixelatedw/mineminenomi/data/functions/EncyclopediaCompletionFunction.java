package xyz.pixelatedw.mineminenomi.data.functions;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.items.DFEncyclopediaItem;

public class EncyclopediaCompletionFunction extends LootItemConditionalFunction {
	// Fields
	public static final MapCodec<EncyclopediaCompletionFunction> CODEC;

	protected EncyclopediaCompletionFunction(List<LootItemCondition> var1) {
		super(var1);
	}
	protected ItemStack run(ItemStack var1, LootContext var2) {
		Vec3 var3 = ((Vec3)(var2.getParamOrNull(LootContextParams.ORIGIN)));
		BlockPos var4 = BlockPos.ZERO;
		if ((var3) != (null)){
			var4 = BlockPos.containing((Position)(var3));
		}
		int var5 = (ExtendedWorldData.get().isNewWorldActive(var4)) ? (7) : (5);
		int var6 = (1) + (var2.getRandom().nextInt(var5));
		int var7 = 0;
		do{
			if ((var7) < (var6)){
				DFEncyclopediaEntry var8 = DFEncyclopediaEntry.getClue((Level)(var2.getLevel()));
				if ((var8) == (null)){

				}else{
					DFEncyclopediaItem.addFruitClues(var1,var8.getDevilFruit().getRegistryKey(),var8);
					var1.setCount(1);
				}
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
		return var1;
	}
	public LootItemFunctionType<EncyclopediaCompletionFunction> getType() {
		return ((LootItemFunctionType)(ModLootTypes.ENCYCLOPEDIA_COMPLETION.get()));
	}
	public static LootItemConditionalFunction.Builder<?> builder() {
		return (LootItemConditionalFunction.Builder<?>) (LootItemConditionalFunction.Builder) (simpleBuilder((Function)(((Function<List, LootItemFunction>)(EncyclopediaCompletionFunction::new)))));
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			return commonFields(l0).apply((Applicative)(l0),(Function)(((Function<List, EncyclopediaCompletionFunction>)(EncyclopediaCompletionFunction::new))));
		};
		CODEC = (MapCodec) (RecordCodecBuilder.mapCodec(var0));
	}
}