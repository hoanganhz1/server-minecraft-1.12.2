package xyz.pixelatedw.mineminenomi.data.functions;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Function;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public abstract class IncreaseStatFunction extends LootItemConditionalFunction {
	// Fields
	protected NumberProvider amount;
	protected StatChangeSource source;
	protected boolean scaleDownChallengeCompletion;

	protected static <T extends IncreaseStatFunction> Products.P3<RecordCodecBuilder.Mu<T>, NumberProvider, StatChangeSource, Boolean> statIncrease(RecordCodecBuilder.Instance<T> var0) {
		Function<IncreaseStatFunction, NumberProvider> var1 = (l0) -> {
			return l0.amount;
		};
		Function<IncreaseStatFunction, StatChangeSource> var2 = (l0) -> {
			return l0.source;
		};
		Function<IncreaseStatFunction, Boolean> var3 = (l0) -> {
			return Boolean.valueOf(l0.scaleDownChallengeCompletion);
		};
		return (Products.P3<RecordCodecBuilder.Mu<T>, NumberProvider, StatChangeSource, Boolean>) (Products.P3) (var0.group((App)(((com.mojang.serialization.MapCodec)(NumberProviders.CODEC.fieldOf("amount"))).forGetter((Function)(var1))),(App)(((com.mojang.serialization.MapCodec)(StatChangeSource.CODEC.fieldOf("source"))).forGetter((Function)(var2))),(App)(((com.mojang.serialization.MapCodec)(Codec.BOOL.optionalFieldOf("scaleDownChallengeCompletion",Boolean.valueOf(false)))).forGetter((Function)(var3)))));
	}
	protected IncreaseStatFunction(List<LootItemCondition> var1, NumberProvider var2, StatChangeSource var3, boolean var4) {
		super(var1);
		this.scaleDownChallengeCompletion = false;
		this.amount = var2;
		this.source = var3;
		this.scaleDownChallengeCompletion = var4;
	}
	public NumberProvider getAmount() {
		return this.amount;
	}
	public StatChangeSource getSource() {
		return this.source;
	}
	public boolean scalesDownChallengeCompletion() {
		return this.scaleDownChallengeCompletion;
	}
	public int scaleValueFromCompletions(Challenge var1, int var2) {
		int var3 = var1.getCompletions();
		var3 = Math.min(var3,10);
		if ((var3) > (0)){
			var2 = (int)(((float)(var2)) / (((float)(var3)) * (1.5F)));
		}
		var2 = Math.max(var2,1);
		if ((((var2) % (10)) != (0)) && ((var2) > (10))){
			var2 = WyHelper.roundToNiceNumber((float)(var2));
		}
		return var2;
	}
}