package xyz.pixelatedw.mineminenomi.data.functions;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.items.components.RewardData;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class UnlockChallengesFunction extends LootItemConditionalFunction {
	// Fields
	public static final MapCodec<UnlockChallengesFunction> CODEC;
	private List<? extends ChallengeCore<?>> unlocks;

	protected UnlockChallengesFunction(List<LootItemCondition> var1, List<? extends ChallengeCore<?>> var2) {
		super(var1);
		this.unlocks = var2;
	}
	public LootItemFunctionType<UnlockChallengesFunction> getType() {
		return ((LootItemFunctionType)(ModLootTypes.UNLOCK_CHALLENGES.get()));
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			Function lv7_1 = (Function) ((Function<List, List>) ((l2_0) -> {
				ArrayList lv4_1 = new ArrayList();
				Function lv4_2 = (Function) ((Function<ResourceLocation, ChallengeCore>) ((l3_0) -> {
					return ((ChallengeCore)(WyRegistry.CHALLENGES.get(l3_0)));
				}));
				Objects.requireNonNull(lv4_1);
				l2_0.stream().map(lv4_2).forEach((Consumer)(((Consumer<ChallengeCore>)(lv4_1::add))));
				return lv4_1;
			}));
			Function lv7_2 = (Function) ((Function<List, List>) ((l2_0) -> {
				return l2_0.stream().map((Function)(((Function<ChallengeCore, ResourceLocation>)(ChallengeCore::getRegistryKey)))).toList();
			}));
			Function lv7_3 = (Function) ((Function<UnlockChallengesFunction, List>) ((l2_0) -> {
				return l2_0.unlocks;
			}));
			return commonFields(l0).and((App)(ResourceLocation.CODEC.listOf().fieldOf("unlocks").xmap(lv7_1,lv7_2).forGetter(lv7_3))).apply((Applicative)(l0),(BiFunction)(((BiFunction<List, List, UnlockChallengesFunction>)(UnlockChallengesFunction::new))));
		};
		CODEC = (MapCodec) (RecordCodecBuilder.mapCodec(var0));
	}

	protected ItemStack run(ItemStack var1, LootContext var2) {
		ChallengeCore var8 = null;
		Entity var3 = ((Entity)(var2.getParamOrNull(LootContextParams.THIS_ENTITY)));
		if (((var3) != (null)) && (var3 instanceof Player)){
			Player var4 = ((Player)(var3));
			RewardData var5 = RewardData.zero();
			Iterator var6 = this.unlocks.iterator();
			do{
				if (var6.hasNext()){
					ChallengeCore var7 = ((ChallengeCore)(var6.next()));
					if ((var7) == (null)){
						continue;
					}else{
						var8 = var7;
						final ChallengeCore var8_f1 = var8;
						Consumer<ChallengeData> var9 = (l0) -> {
			l0.addChallenge(var8_f1);
		};
						((java.util.Optional)(ChallengeData.get(var4))).ifPresent((Consumer)(var9));
						var5 = var5.unlockedChallenge(var7.getLocalizedTitle().getString());
						continue;
					}
				}else{
					break;
				}
			} while (true);
			var1.set(DataComponents.CUSTOM_NAME,(Object)(ModI18n.REWARDS_FUNC_NAME));
			var1.set(ModDataComponents.REWARDS,(Object)(var5));
		}
		return var1;
	}
	public static LootItemConditionalFunction.Builder<?> builder(ChallengeCore<?>... var0) {
		ChallengeCore<?>[] var1 = var0;
		final ChallengeCore<?>[] var1_f1 = var1;
		Function var2 = (Function) ((Function<List, LootItemFunction>) ((l0) -> {
			return new UnlockChallengesFunction(l0,(List)(Lists.newArrayList(var1_f1)));
		}));
		return (LootItemConditionalFunction.Builder<?>) (LootItemConditionalFunction.Builder) (simpleBuilder(var2));
	}}