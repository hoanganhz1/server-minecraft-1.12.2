package xyz.pixelatedw.mineminenomi.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.api.items.components.BusterCallData;
import xyz.pixelatedw.mineminenomi.api.items.components.FlagData;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.api.items.components.RandomizedFruit;
import xyz.pixelatedw.mineminenomi.api.items.components.RewardData;
import xyz.pixelatedw.mineminenomi.api.items.components.Soulbound;
import xyz.pixelatedw.mineminenomi.api.items.components.WantedPosterData;

public class ModDataComponents {
	// Fields
	public static final Supplier<DataComponentType<MultiChannelItemColor>> MULTI_CHANNEL_DYES;
	public static final Supplier<DataComponentType<Boolean>> IMBUING;
	public static final Supplier<DataComponentType<Boolean>> IS_CLONE;
	public static final Supplier<DataComponentType<Boolean>> IS_ANCIENT_MANUAL;
	public static final Supplier<DataComponentType<Long>> BELLY;
	public static final Supplier<DataComponentType<Long>> EXTOL;
	public static final Supplier<DataComponentType<Float>> STORED_HEALTH;
	public static final Supplier<DataComponentType<Soulbound>> SOULBOUND;
	public static final Supplier<DataComponentType<Integer>> TEARS;
	public static final Supplier<DataComponentType<Integer>> TYPE;
	public static final Supplier<DataComponentType<ResourceLocation>> SPAWNED_BY_ABILITY;
	public static final Supplier<DataComponentType<WantedPosterData>> WANTED_POSTER_DATA;
	public static final Supplier<DataComponentType<Integer>> KAIROSEKI_COATING_LEVEL;
	public static final Supplier<DataComponentType<WeatherBallKind[]>> WEATHER_BALL_CHARGES;
	public static final Supplier<DataComponentType<Double>> SWORDSMAN_BONUS;
	public static final Supplier<DataComponentType<Integer>> GUNPOWDER;
	public static final Supplier<DataComponentType<Boolean>> QUEST_MARKER;
	public static final Supplier<DataComponentType<CanvasSize>> CANVAS_SIZE;
	public static final Supplier<DataComponentType<ResourceLocation>> CHALLENGE;
	public static final Supplier<DataComponentType<FlagData>> FLAG_DATA;
	public static final Supplier<DataComponentType<ResourceLocation>> ABILITY_ICON;
	public static final Supplier<DataComponentType<RewardData>> REWARDS;
	public static final Supplier<DataComponentType<BusterCallData>> BUSTER_CALL;
	public static final Supplier<DataComponentType<DFEncyclopediaEntry>> FRUIT_CLUE;
	public static final Supplier<DataComponentType<HashMap<ResourceLocation, DFEncyclopediaEntry>>> ENCYCLOPEDIA_UNLOCKS;
	public static final Supplier<DataComponentType<RandomizedFruit>> RANDOMIZED_FRUIT;
	public static final Supplier<DataComponentType<Boolean>> FRUIT_DECIPHERED;
	public static final Supplier<DataComponentType<Boolean>> INFINITE_STOCK;
	public static final Supplier<DataComponentType<Integer>> PRICE;

	public static void init() {
	}
	static  {
		Supplier<DataComponentType> var0 = () -> {
			return new DataComponentType.Builder().persistent(MultiChannelItemColor.CODEC).networkSynchronized(MultiChannelItemColor.STREAM_CODEC).build();
		};
		MULTI_CHANNEL_DYES = ModRegistry.registerDataComponent("multi_channel_dyes",(Supplier)(var0));
		Supplier<DataComponentType> var1 = () -> {
			return new DataComponentType.Builder().persistent((Codec)(Codec.BOOL)).build();
		};
		IMBUING = ModRegistry.registerDataComponent("imbuing",(Supplier)(var1));
		Supplier<DataComponentType> var2 = () -> {
			return new DataComponentType.Builder().persistent((Codec)(Codec.BOOL)).build();
		};
		IS_CLONE = ModRegistry.registerDataComponent("is_clone",(Supplier)(var2));
		Supplier<DataComponentType> var3 = () -> {
			return new DataComponentType.Builder().persistent((Codec)(Codec.BOOL)).build();
		};
		IS_ANCIENT_MANUAL = ModRegistry.registerDataComponent("is_ancient_manual",(Supplier)(var3));
		Supplier<DataComponentType> var4 = () -> {
			return new DataComponentType.Builder().persistent((Codec)(Codec.LONG)).build();
		};
		BELLY = ModRegistry.registerDataComponent("belly",(Supplier)(var4));
		Supplier<DataComponentType> var5 = () -> {
			return new DataComponentType.Builder().persistent((Codec)(Codec.LONG)).build();
		};
		EXTOL = ModRegistry.registerDataComponent("extol",(Supplier)(var5));
		Supplier<DataComponentType> var6 = () -> {
			return new DataComponentType.Builder().persistent((Codec)(Codec.FLOAT)).build();
		};
		STORED_HEALTH = ModRegistry.registerDataComponent("stored_health",(Supplier)(var6));
		Supplier<DataComponentType> var7 = () -> {
			return new DataComponentType.Builder().persistent(Soulbound.CODEC).build();
		};
		SOULBOUND = ModRegistry.registerDataComponent("soulbound_owner",(Supplier)(var7));
		Supplier<DataComponentType> var8 = () -> {
			return new DataComponentType.Builder().persistent((Codec)(Codec.INT)).build();
		};
		TEARS = ModRegistry.registerDataComponent("tears",(Supplier)(var8));
		Supplier<DataComponentType> var9 = () -> {
			return new DataComponentType.Builder().persistent((Codec)(Codec.INT)).build();
		};
		TYPE = ModRegistry.registerDataComponent("type",(Supplier)(var9));
		Supplier<DataComponentType> var10 = () -> {
			return new DataComponentType.Builder().persistent(ResourceLocation.CODEC).build();
		};
		SPAWNED_BY_ABILITY = ModRegistry.registerDataComponent("spawned_by_ability",(Supplier)(var10));
		Supplier<DataComponentType> var11 = () -> {
			return new DataComponentType.Builder().persistent(WantedPosterData.CODEC).build();
		};
		WANTED_POSTER_DATA = ModRegistry.registerDataComponent("wanted_poster_data",(Supplier)(var11));
		Supplier<DataComponentType> var12 = () -> {
			return new DataComponentType.Builder().persistent((Codec)(Codec.INT)).build();
		};
		KAIROSEKI_COATING_LEVEL = ModRegistry.registerDataComponent("kairoseki_coating_level",(Supplier)(var12));
		Supplier<DataComponentType> var13 = () -> {
			return new DataComponentType.Builder().persistent(WeatherBallKind.CODEC).build();
		};
		WEATHER_BALL_CHARGES = ModRegistry.registerDataComponent("weather_ball_charges",(Supplier)(var13));
		Supplier<DataComponentType> var14 = () -> {
			return new DataComponentType.Builder().persistent((Codec)(Codec.DOUBLE)).build();
		};
		SWORDSMAN_BONUS = ModRegistry.registerDataComponent("swordsman_bonus",(Supplier)(var14));
		Supplier<DataComponentType> var15 = () -> {
			return new DataComponentType.Builder().persistent((Codec)(Codec.INT)).build();
		};
		GUNPOWDER = ModRegistry.registerDataComponent("gunpowder",(Supplier)(var15));
		Supplier<DataComponentType> var16 = () -> {
			return new DataComponentType.Builder().persistent((Codec)(Codec.BOOL)).build();
		};
		QUEST_MARKER = ModRegistry.registerDataComponent("quest_marker",(Supplier)(var16));
		Supplier<DataComponentType> var17 = () -> {
			return new DataComponentType.Builder().persistent(CanvasSize.CODEC).build();
		};
		CANVAS_SIZE = ModRegistry.registerDataComponent("canvas_size",(Supplier)(var17));
		Supplier<DataComponentType> var18 = () -> {
			return new DataComponentType.Builder().persistent(ResourceLocation.CODEC).build();
		};
		CHALLENGE = ModRegistry.registerDataComponent("challenge",(Supplier)(var18));
		Supplier<DataComponentType> var19 = () -> {
			return new DataComponentType.Builder().persistent(FlagData.CODEC).build();
		};
		FLAG_DATA = ModRegistry.registerDataComponent("flag_data",(Supplier)(var19));
		Supplier<DataComponentType> var20 = () -> {
			return new DataComponentType.Builder().persistent(ResourceLocation.CODEC).build();
		};
		ABILITY_ICON = ModRegistry.registerDataComponent("ability_icon",(Supplier)(var20));
		Supplier<DataComponentType> var21 = () -> {
			return new DataComponentType.Builder().persistent(RewardData.CODEC).build();
		};
		REWARDS = ModRegistry.registerDataComponent("rewards",(Supplier)(var21));
		Supplier<DataComponentType> var22 = () -> {
			return new DataComponentType.Builder().persistent(BusterCallData.CODEC).build();
		};
		BUSTER_CALL = ModRegistry.registerDataComponent("buster_call",(Supplier)(var22));
		Supplier<DataComponentType> var23 = () -> {
			return new DataComponentType.Builder().persistent(DFEncyclopediaEntry.CODEC).build();
		};
		FRUIT_CLUE = ModRegistry.registerDataComponent("fruit_clue",(Supplier)(var23));
		Supplier<DataComponentType> var24 = () -> {
			Function<Map, HashMap> lv26_0 = HashMap::new;
			Function lv26_1 = (Function) ((Function<HashMap, Map>) ((l2_0) -> {
				return l2_0;
			}));
			return new DataComponentType.Builder().persistent(Codec.unboundedMap(ResourceLocation.CODEC,DFEncyclopediaEntry.CODEC).xmap(lv26_0,lv26_1)).build();
		};
		ENCYCLOPEDIA_UNLOCKS = ModRegistry.registerDataComponent("encyclopedia_unlocks",(Supplier)(var24));
		Supplier<DataComponentType> var25 = () -> {
			return new DataComponentType.Builder().persistent(RandomizedFruit.CODEC).networkSynchronized(RandomizedFruit.STREAM_CODEC).build();
		};
		RANDOMIZED_FRUIT = ModRegistry.registerDataComponent("randomized_fruit",(Supplier)(var25));
		Supplier<DataComponentType> var26 = () -> {
			return new DataComponentType.Builder().persistent((Codec)(Codec.BOOL)).build();
		};
		FRUIT_DECIPHERED = ModRegistry.registerDataComponent("fruit_dechipehered",(Supplier)(var26));
		Supplier<DataComponentType> var27 = () -> {
			return new DataComponentType.Builder().persistent((Codec)(Codec.BOOL)).build();
		};
		INFINITE_STOCK = ModRegistry.registerDataComponent("infinite_stock",(Supplier)(var27));
		Supplier<DataComponentType> var28 = () -> {
			return new DataComponentType.Builder().persistent((Codec)(Codec.INT)).build();
		};
		PRICE = ModRegistry.registerDataComponent("price",(Supplier)(var28));
	}
}