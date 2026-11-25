package xyz.pixelatedw.mineminenomi.api.items.components;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Function8;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final record RewardData(int doriki, int belly, int extol, int bounty, int loyalty, int kenExp, int busoExp, List<String> unlockedChallenges) {
	// Fields
	public static final Codec<RewardData> CODEC;

	public static RewardData zero() {
		return new RewardData(0,0,0,0,0,0,0,(List)(Lists.newArrayList()));
	}
	public RewardData doriki(int var1) {
		return new RewardData((this.doriki) + (var1),this.belly,this.extol,this.bounty,this.loyalty,this.kenExp,this.busoExp,this.unlockedChallenges);
	}
	public RewardData belly(int var1) {
		return new RewardData(this.doriki,(this.belly) + (var1),this.extol,this.bounty,this.loyalty,this.kenExp,this.busoExp,this.unlockedChallenges);
	}
	public RewardData extol(int var1) {
		return new RewardData(this.doriki,this.belly,(this.extol) + (var1),this.bounty,this.loyalty,this.kenExp,this.busoExp,this.unlockedChallenges);
	}
	public RewardData bounty(int var1) {
		return new RewardData(this.doriki,this.belly,this.extol,(this.bounty) + (var1),this.loyalty,this.kenExp,this.busoExp,this.unlockedChallenges);
	}
	public RewardData loyalty(int var1) {
		return new RewardData(this.doriki,this.belly,this.extol,this.bounty,(this.loyalty) + (var1),this.kenExp,this.busoExp,this.unlockedChallenges);
	}
	public RewardData kenExp(int var1) {
		return new RewardData(this.doriki,this.belly,this.extol,this.bounty,this.loyalty,(this.kenExp) + (var1),this.busoExp,this.unlockedChallenges);
	}
	public RewardData busoExp(int var1) {
		return new RewardData(this.doriki,this.belly,this.extol,this.bounty,this.loyalty,this.kenExp,(this.busoExp) + (var1),this.unlockedChallenges);
	}
	public RewardData unlockedChallenge(String var1) {
		this.unlockedChallenges.add(var1);
		return new RewardData(this.doriki,this.belly,this.extol,this.bounty,this.loyalty,this.kenExp,this.busoExp,this.unlockedChallenges);
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			Function<RewardData, Integer> lv9_1 = (l2_0) -> {
				return Integer.valueOf(l2_0.doriki);
			};
			Function<RewardData, Integer> lv9_2 = (l2_0) -> {
				return Integer.valueOf(l2_0.belly);
			};
			Function<RewardData, Integer> lv9_3 = (l2_0) -> {
				return Integer.valueOf(l2_0.extol);
			};
			Function<RewardData, Integer> lv9_4 = (l2_0) -> {
				return Integer.valueOf(l2_0.bounty);
			};
			Function<RewardData, Integer> lv9_5 = (l2_0) -> {
				return Integer.valueOf(l2_0.loyalty);
			};
			Function<RewardData, Integer> lv9_6 = (l2_0) -> {
				return Integer.valueOf(l2_0.kenExp);
			};
			Function<RewardData, Integer> lv9_7 = (l2_0) -> {
				return Integer.valueOf(l2_0.busoExp);
			};
			Function lv9_8 = (Function) ((Function<RewardData, List>) ((l2_0) -> {
				return l2_0.unlockedChallenges;
			}));
			return l0.group((App)(((com.mojang.serialization.MapCodec)(Codec.INT.fieldOf("doriki"))).forGetter((Function)(lv9_1))),(App)(((com.mojang.serialization.MapCodec)(Codec.INT.fieldOf("belly"))).forGetter((Function)(lv9_2))),(App)(((com.mojang.serialization.MapCodec)(Codec.INT.fieldOf("extol"))).forGetter((Function)(lv9_3))),(App)(((com.mojang.serialization.MapCodec)(Codec.INT.fieldOf("bounty"))).forGetter((Function)(lv9_4))),(App)(((com.mojang.serialization.MapCodec)(Codec.INT.fieldOf("loyalty"))).forGetter((Function)(lv9_5))),(App)(((com.mojang.serialization.MapCodec)(Codec.INT.fieldOf("kenExp"))).forGetter((Function)(lv9_6))),(App)(((com.mojang.serialization.MapCodec)(Codec.INT.fieldOf("busoExp"))).forGetter((Function)(lv9_7))),(App)(Codec.STRING.listOf().fieldOf("unlockedChallenges").forGetter(lv9_8))).apply((Applicative)(l0),(Function8)(((com.mojang.datafixers.util.Function8)(RewardData::new))));
		};
		CODEC = (Codec) (RecordCodecBuilder.create(var0));
	}
}