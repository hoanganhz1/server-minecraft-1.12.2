package xyz.pixelatedw.mineminenomi.data.conditions;

import com.mojang.serialization.MapCodec;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

public class FirstCompletionRewardCondition implements LootItemCondition {
	// Fields
	public static final MapCodec<FirstCompletionRewardCondition> CODEC = MapCodec.unit((Supplier)(((Supplier<FirstCompletionRewardCondition>)(FirstCompletionRewardCondition::new))));

	public static LootItemCondition.Builder builder() {
		LootItemCondition.Builder var0 = FirstCompletionRewardCondition::new;
		return var0;
	}
	public boolean test(LootContext var1) {
		Entity var2 = ((Entity)(var1.getParamOrNull(LootContextParams.THIS_ENTITY)));
		ChallengeCore var3 = ((ChallengeCore)(var1.getParamOrNull(ModLootTypes.COMPLETED_CHALLENGE)));
		if (((var2) != (null)) && (((var3) != (null)) && (var2 instanceof Player))){
			Player var4 = ((Player)(var2));
			ChallengeCore var5 = var3;
			final ChallengeCore var5_f1 = var5;
			Function<ChallengeData, Boolean> var6 = (l0) -> {
			return Boolean.valueOf(l0.isChallengeCompleted(var5_f1));
		};
			if (!(((Boolean)(((java.util.Optional)(ChallengeData.get(var4))).map((Function)(var6)).orElse(Boolean.valueOf(false)))).booleanValue())){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	public LootItemConditionType getType() {
		return ((LootItemConditionType)(ModLootTypes.FIRST_COMPLETION.get()));
	}
}