package xyz.pixelatedw.mineminenomi.data.functions;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Function4;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.items.components.RewardData;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class IncreaseKenExpFunction extends IncreaseStatFunction {
	// Fields
	public static final MapCodec<IncreaseKenExpFunction> CODEC;

	protected IncreaseKenExpFunction(List<LootItemCondition> var1, NumberProvider var2, StatChangeSource var3, boolean var4) {
		super(var1,var2,var3,var4);
	}
	protected ItemStack run(ItemStack var1, LootContext var2) {
		Entity var3 = ((Entity)(var2.getParamOrNull(LootContextParams.THIS_ENTITY)));
		if (((var3) != (null)) && (var3 instanceof LivingEntity)){
			LivingEntity var4 = ((LivingEntity)(var3));
			Entity var5 = ((Entity)(var2.getParamOrNull(LootContextParams.ATTACKING_ENTITY)));
			int var6 = this.amount.getInt(var2);
			if (((var5) != (null)) && (var5 instanceof LivingEntity)){
				var4 = ((LivingEntity)(var5));
				if (((this.source) == (StatChangeSource.KILL_NPC)) && (!(ServerConfig.isMobRewardsEnabled()))){
					var6 = 0;
				}
			}
			if (((this.source) == (StatChangeSource.CHALLENGE)) && (var3 instanceof Player)){
				Player var7 = ((Player)(var3));
				if (this.scaleDownChallengeCompletion){
					ChallengeCore var8 = ((ChallengeCore)(var2.getParamOrNull(ModLootTypes.COMPLETED_CHALLENGE)));
					ChallengeData var9 = ((ChallengeData)(((java.util.Optional)(ChallengeData.get(var7))).orElse((Object)(null))));
					if ((var9) != (null)){
						var6 = this.scaleValueFromCompletions(var9.getChallenge(var8),var6);
					}
				}
			}
			HakiData var7_1 = ((HakiData)(((java.util.Optional)(HakiData.get(var4))).orElse((Object)(null))));
			if ((var7_1) != (null)){
				boolean var8_1 = var7_1.alterKenbunshokuHakiExp((float)(var6),this.source);
				if (var4 instanceof ServerPlayer){
					ServerPlayer var9_1 = ((ServerPlayer)(var4));
					if (var8_1){
						ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var9_1)),(Player)(var9_1));
					}
				}
				var1.set(DataComponents.CUSTOM_NAME,(Object)(ModI18n.REWARDS_FUNC_NAME));
				var1.set(ModDataComponents.REWARDS,(Object)(RewardData.zero().kenExp(var6)));
			}
		}
		return var1;
	}
	public LootItemFunctionType<IncreaseKenExpFunction> getType() {
		return ((LootItemFunctionType)(ModLootTypes.INCREASE_KEN_EXP.get()));
	}
	public static LootItemConditionalFunction.Builder<?> builder(int var0, StatChangeSource var1) {
		return (LootItemConditionalFunction.Builder<?>) (LootItemConditionalFunction.Builder) (builder((NumberProvider)(ConstantValue.exactly((float)(var0))),var1));
	}
	public static LootItemConditionalFunction.Builder<?> builder(NumberProvider var0, StatChangeSource var1) {
		return (LootItemConditionalFunction.Builder<?>) (LootItemConditionalFunction.Builder) (builder(var0,var1,false));
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			return commonFields(l0).and(statIncrease(l0)).apply((Applicative)(l0),(Function4)(((com.mojang.datafixers.util.Function4)(IncreaseKenExpFunction::new))));
		};
		CODEC = (MapCodec) (RecordCodecBuilder.mapCodec(var0));
	}

	public static LootItemConditionalFunction.Builder<?> builder(NumberProvider var0, StatChangeSource var1, boolean var2) {
		NumberProvider var3 = var0;
		StatChangeSource var4 = var1;
		boolean var5 = var2;
		final NumberProvider var3_f1 = var3;
		final StatChangeSource var4_f2 = var4;
		final boolean var5_f3 = var5;
		Function var6 = (Function) ((Function<List, LootItemFunction>) ((l0) -> {
			return new IncreaseKenExpFunction(l0,var3_f1,var4_f2,var5_f3);
		}));
		return (LootItemConditionalFunction.Builder<?>) (LootItemConditionalFunction.Builder) (simpleBuilder(var6));
	}}