package xyz.pixelatedw.mineminenomi.init;

import com.mojang.brigadier.arguments.ArgumentType;
import java.util.function.Supplier;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.commands.AbilityArgument;
import xyz.pixelatedw.mineminenomi.api.commands.ChallengeArgument;
import xyz.pixelatedw.mineminenomi.api.commands.CrewArgument;
import xyz.pixelatedw.mineminenomi.api.commands.CurrencyTypeArgument;
import xyz.pixelatedw.mineminenomi.api.commands.CurrencyTypeArgument$Info;
import xyz.pixelatedw.mineminenomi.api.commands.FactionArgument;
import xyz.pixelatedw.mineminenomi.api.commands.FightingStyleArgument;
import xyz.pixelatedw.mineminenomi.api.commands.FruitArgument;
import xyz.pixelatedw.mineminenomi.api.commands.HakiTypeArgument;
import xyz.pixelatedw.mineminenomi.api.commands.HakiTypeArgument$Info;
import xyz.pixelatedw.mineminenomi.api.commands.QuestArgument;
import xyz.pixelatedw.mineminenomi.api.commands.RaceArgument;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.enums.HakiType;

public class ModCommandArgumentTypes {
	// Fields
	private static final Supplier<SingletonArgumentInfo<AbilityArgument>> ABILITY;
	private static final Supplier<SingletonArgumentInfo<ChallengeArgument>> CHALLENGE;
	private static final Supplier<SingletonArgumentInfo<CrewArgument>> CREW;
	private static final Supplier<SingletonArgumentInfo<FruitArgument>> FRUIT;
	private static final Supplier<CurrencyTypeArgument$Info<Currency>> CURRENCY_TYPE;
	private static final Supplier<HakiTypeArgument$Info<HakiType>> HAKI_TYPE;
	private static final Supplier<SingletonArgumentInfo<QuestArgument>> QUEST;
	private static final Supplier<SingletonArgumentInfo<FactionArgument>> FACTION;
	private static final Supplier<SingletonArgumentInfo<RaceArgument>> RACE;
	private static final Supplier<SingletonArgumentInfo<FightingStyleArgument>> FIGHTING_STYLE;

	public static void init() {
	}
	static  {
		Supplier<SingletonArgumentInfo> var0 = () -> {
			return ((SingletonArgumentInfo)(ArgumentTypeInfos.registerByClass(AbilityArgument.class,(ArgumentTypeInfo)(SingletonArgumentInfo.contextFree((Supplier)(((Supplier<AbilityArgument>)(AbilityArgument::ability))))))));
		};
		ABILITY = ModRegistry.registerCommandArgumentType("ability",var0);
		Supplier<SingletonArgumentInfo> var1 = () -> {
			return ((SingletonArgumentInfo)(ArgumentTypeInfos.registerByClass(ChallengeArgument.class,(ArgumentTypeInfo)(SingletonArgumentInfo.contextFree((Supplier)(((Supplier<ChallengeArgument>)(ChallengeArgument::challenge))))))));
		};
		CHALLENGE = ModRegistry.registerCommandArgumentType("challenge",var1);
		Supplier<SingletonArgumentInfo> var2 = () -> {
			return ((SingletonArgumentInfo)(ArgumentTypeInfos.registerByClass(CrewArgument.class,(ArgumentTypeInfo)(SingletonArgumentInfo.contextFree((Supplier)(((Supplier<CrewArgument>)(CrewArgument::crew))))))));
		};
		CREW = ModRegistry.registerCommandArgumentType("crew",var2);
		Supplier<SingletonArgumentInfo> var3 = () -> {
			return ((SingletonArgumentInfo)(ArgumentTypeInfos.registerByClass(FruitArgument.class,(ArgumentTypeInfo)(SingletonArgumentInfo.contextFree((Supplier)(((Supplier<FruitArgument>)(FruitArgument::fruit))))))));
		};
		FRUIT = ModRegistry.registerCommandArgumentType("fruit",var3);
		Supplier var4 = (Supplier) ((Supplier<CurrencyTypeArgument$Info>) (() -> {
			return ((CurrencyTypeArgument$Info)(ArgumentTypeInfos.registerByClass(CurrencyTypeArgument.class,(ArgumentTypeInfo)(new CurrencyTypeArgument$Info()))));
		}));
		CURRENCY_TYPE = ModRegistry.registerCommandArgumentType("currency_type",var4);
		Supplier var5 = (Supplier) ((Supplier<HakiTypeArgument$Info>) (() -> {
			return ((HakiTypeArgument$Info)(ArgumentTypeInfos.registerByClass(HakiTypeArgument.class,(ArgumentTypeInfo)(new HakiTypeArgument$Info()))));
		}));
		HAKI_TYPE = ModRegistry.registerCommandArgumentType("haki_type",var5);
		Supplier<SingletonArgumentInfo> var6 = () -> {
			return ((SingletonArgumentInfo)(ArgumentTypeInfos.registerByClass(QuestArgument.class,(ArgumentTypeInfo)(SingletonArgumentInfo.contextFree((Supplier)(((Supplier<QuestArgument>)(QuestArgument::quest))))))));
		};
		QUEST = ModRegistry.registerCommandArgumentType("quest",var6);
		Supplier<SingletonArgumentInfo> var7 = () -> {
			return ((SingletonArgumentInfo)(ArgumentTypeInfos.registerByClass(FactionArgument.class,(ArgumentTypeInfo)(SingletonArgumentInfo.contextFree((Supplier)(((Supplier<FactionArgument>)(FactionArgument::faction))))))));
		};
		FACTION = ModRegistry.registerCommandArgumentType("faction",var7);
		Supplier<SingletonArgumentInfo> var8 = () -> {
			return ((SingletonArgumentInfo)(ArgumentTypeInfos.registerByClass(RaceArgument.class,(ArgumentTypeInfo)(SingletonArgumentInfo.contextFree((Supplier)(((Supplier<RaceArgument>)(RaceArgument::race))))))));
		};
		RACE = ModRegistry.registerCommandArgumentType("race",var8);
		Supplier<SingletonArgumentInfo> var9 = () -> {
			return ((SingletonArgumentInfo)(ArgumentTypeInfos.registerByClass(FightingStyleArgument.class,(ArgumentTypeInfo)(SingletonArgumentInfo.contextFree((Supplier)(((Supplier<FightingStyleArgument>)(FightingStyleArgument::fightingStyle))))))));
		};
		FIGHTING_STYLE = ModRegistry.registerCommandArgumentType("fighting_style",var9);
	}
}