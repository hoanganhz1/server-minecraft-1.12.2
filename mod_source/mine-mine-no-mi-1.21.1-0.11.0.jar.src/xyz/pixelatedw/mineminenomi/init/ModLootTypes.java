package xyz.pixelatedw.mineminenomi.init;

import com.mojang.serialization.MapCodec;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.data.conditions.FirstCompletionRewardCondition;
import xyz.pixelatedw.mineminenomi.data.conditions.RandomChanceWithLuckCondition;
import xyz.pixelatedw.mineminenomi.data.conditions.RandomizedFruitsCondition;
import xyz.pixelatedw.mineminenomi.data.functions.AncientManualFunction;
import xyz.pixelatedw.mineminenomi.data.functions.EncyclopediaCompletionFunction;
import xyz.pixelatedw.mineminenomi.data.functions.FakeWeaponFunction;
import xyz.pixelatedw.mineminenomi.data.functions.FruitAlreadyExistsFunction;
import xyz.pixelatedw.mineminenomi.data.functions.IncreaseBellyFunction;
import xyz.pixelatedw.mineminenomi.data.functions.IncreaseBountyFunction;
import xyz.pixelatedw.mineminenomi.data.functions.IncreaseBusoExpFunction;
import xyz.pixelatedw.mineminenomi.data.functions.IncreaseDorikiFunction;
import xyz.pixelatedw.mineminenomi.data.functions.IncreaseExtolFunction;
import xyz.pixelatedw.mineminenomi.data.functions.IncreaseKenExpFunction;
import xyz.pixelatedw.mineminenomi.data.functions.IncreaseLoyaltyFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetExtolInPouchFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetFruitClueFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetInfiniteStockFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetItemColorFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetPriceFunction;
import xyz.pixelatedw.mineminenomi.data.functions.UnlockChallengesFunction;

public class ModLootTypes {
	// Fields
	public static final LootContextParam<ChallengeCore<?>> COMPLETED_CHALLENGE = createParameter("completed_challenge");
	public static final LootContextParamSet CHALLENGE;
	public static final Supplier<LootItemConditionType> RANDOMIZED_FRUIT;
	public static final Supplier<LootItemConditionType> FIRST_COMPLETION;
	public static final Supplier<LootItemConditionType> RANDOM_CHANCE_WITH_LUCK;
	public static final Supplier<LootItemFunctionType<SetPriceFunction>> SET_PRICE;
	public static final Supplier<LootItemFunctionType<SetInfiniteStockFunction>> INFINITE_STOCK;
	public static final Supplier<LootItemFunctionType<SetBellyInPouchFunction>> SET_BELLY_IN_POUCH;
	public static final Supplier<LootItemFunctionType<SetExtolInPouchFunction>> SET_EXTOL_IN_POUCH;
	public static final Supplier<LootItemFunctionType<FakeWeaponFunction>> FAKE_WEAPON;
	public static final Supplier<LootItemFunctionType<SetFruitClueFunction>> FRUIT_CLUE;
	public static final Supplier<LootItemFunctionType<EncyclopediaCompletionFunction>> ENCYCLOPEDIA_COMPLETION;
	public static final Supplier<LootItemFunctionType<SetItemColorFunction>> SET_ITEM_COLOR;
	public static final Supplier<LootItemFunctionType<IncreaseDorikiFunction>> INCREASE_DORIKI;
	public static final Supplier<LootItemFunctionType<IncreaseBountyFunction>> INCREASE_BOUNTY;
	public static final Supplier<LootItemFunctionType<IncreaseBellyFunction>> INCREASE_BELLY;
	public static final Supplier<LootItemFunctionType<IncreaseExtolFunction>> INCREASE_EXTOL;
	public static final Supplier<LootItemFunctionType<IncreaseLoyaltyFunction>> INCREASE_LOYALTY;
	public static final Supplier<LootItemFunctionType<IncreaseBusoExpFunction>> INCREASE_BUSO_EXP;
	public static final Supplier<LootItemFunctionType<IncreaseKenExpFunction>> INCREASE_KEN_EXP;
	public static final Supplier<LootItemFunctionType<FruitAlreadyExistsFunction>> FRUIT_ALREADY_EXISTS;
	public static final Supplier<LootItemFunctionType<UnlockChallengesFunction>> UNLOCK_CHALLENGES;
	public static final Supplier<LootItemFunctionType<AncientManualFunction>> ANCIENT_MANUAL;

	private static <T> LootContextParam<T> createParameter(String var0) {
		return (LootContextParam<T>) (LootContextParam) (new LootContextParam(ResourceLocation.fromNamespaceAndPath("mineminenomi",var0)));
	}
	public static void init() {
	}
	static  {
		Consumer<LootContextParamSet.Builder> var0 = (l0) -> {
			l0.required(LootContextParams.ORIGIN).required(COMPLETED_CHALLENGE).optional(LootContextParams.THIS_ENTITY).optional(LootContextParams.ATTACKING_ENTITY);
		};
		CHALLENGE = LootContextParamSets.register("challenge",var0);
		RANDOMIZED_FRUIT = ModRegistry.registerLootCondition("randomized_fruits",RandomizedFruitsCondition.CODEC);
		FIRST_COMPLETION = ModRegistry.registerLootCondition("first_completion",FirstCompletionRewardCondition.CODEC);
		RANDOM_CHANCE_WITH_LUCK = ModRegistry.registerLootCondition("random_chance_with_luck",RandomChanceWithLuckCondition.CODEC);
		SET_PRICE = ModRegistry.registerLootFunction("set_price",SetPriceFunction.CODEC);
		INFINITE_STOCK = ModRegistry.registerLootFunction("infinite_stock",SetInfiniteStockFunction.CODEC);
		SET_BELLY_IN_POUCH = ModRegistry.registerLootFunction("set_belly_in_pouch",SetBellyInPouchFunction.CODEC);
		SET_EXTOL_IN_POUCH = ModRegistry.registerLootFunction("set_extol_in_pouch",SetExtolInPouchFunction.CODEC);
		FAKE_WEAPON = ModRegistry.registerLootFunction("fake_weapon",FakeWeaponFunction.CODEC);
		FRUIT_CLUE = ModRegistry.registerLootFunction("fruit_clue",SetFruitClueFunction.CODEC);
		ENCYCLOPEDIA_COMPLETION = ModRegistry.registerLootFunction("encyclopedia_completion",EncyclopediaCompletionFunction.CODEC);
		SET_ITEM_COLOR = ModRegistry.registerLootFunction("set_item_color",SetItemColorFunction.CODEC);
		INCREASE_DORIKI = ModRegistry.registerLootFunction("increase_doriki",IncreaseDorikiFunction.CODEC);
		INCREASE_BOUNTY = ModRegistry.registerLootFunction("increase_bounty",IncreaseBountyFunction.CODEC);
		INCREASE_BELLY = ModRegistry.registerLootFunction("increase_belly",IncreaseBellyFunction.CODEC);
		INCREASE_EXTOL = ModRegistry.registerLootFunction("increase_extol",IncreaseExtolFunction.CODEC);
		INCREASE_LOYALTY = ModRegistry.registerLootFunction("increase_loyalty",IncreaseLoyaltyFunction.CODEC);
		INCREASE_BUSO_EXP = ModRegistry.registerLootFunction("increase_hardening_exp",IncreaseBusoExpFunction.CODEC);
		INCREASE_KEN_EXP = ModRegistry.registerLootFunction("increase_observation_exp",IncreaseKenExpFunction.CODEC);
		FRUIT_ALREADY_EXISTS = ModRegistry.registerLootFunction("fruit_already_exists",FruitAlreadyExistsFunction.CODEC);
		UNLOCK_CHALLENGES = ModRegistry.registerLootFunction("unlock_challenges",UnlockChallengesFunction.CODEC);
		ANCIENT_MANUAL = ModRegistry.registerLootFunction("ancient_manual",AncientManualFunction.CODEC);
	}
}